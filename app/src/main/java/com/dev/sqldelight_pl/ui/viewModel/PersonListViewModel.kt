package com.dev.sqldelight_pl.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.sqldelight_pl.data.PersonDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sqldelightpl.persondb.PersonEntity
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val personDataSource: PersonDataSource
): ViewModel() {
    val persons = personDataSource.getAllPersons()

    var personDetails by mutableStateOf<PersonEntity?>(null)
        private set

    var firstNameText by mutableStateOf("")
        private set
    var lastNameText by mutableStateOf("")
        private set

    fun onInsertPerson() {
        if(firstNameText.isBlank() || lastNameText.isBlank()) {
            return
        }

        viewModelScope.launch {
            personDataSource.insertPerson(
                firstNameText,
                lastNameText
            )

            firstNameText = ""
            lastNameText = ""
        }
    }

    fun onDeletePerson(id: Long) {
        viewModelScope.launch {
            personDataSource.deletePersonById(
                id
            )
        }
    }

    fun getPersonById(id: Long) {
        viewModelScope.launch {
            personDetails = personDataSource.getPersonById(id)
        }
    }

    fun onFirstNameChange(value: String) {
        firstNameText = value
    }

    fun onLastNameChange(value: String) {
        lastNameText = value
    }

    fun onPersonDetailDialogDismiss() {
        personDetails = null
    }
}