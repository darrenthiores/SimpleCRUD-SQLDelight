package com.dev.sqldelight_pl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.sqldelight_pl.ui.viewModel.PersonListViewModel
import sqldelightpl.persondb.PersonEntity

@Composable
fun PersonListScreen(
    viewModel: PersonListViewModel = hiltViewModel()
) {
    val persons = viewModel.persons
        .collectAsState(initial = emptyList())
        .value
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                 items(persons) { person ->
                     PersonItem(
                         person = person,
                         onItemClick = {
                             viewModel.getPersonById(person.id)
                         },
                         onDeleteClick = {
                             viewModel.onDeletePerson(person.id)
                         },
                         modifier = Modifier
                             .fillMaxWidth()
                     )
                 }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewModel.firstNameText,
                    onValueChange = viewModel::onFirstNameChange,
                    placeholder = {
                        Text(text = "First Name")
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = viewModel.lastNameText,
                    onValueChange = viewModel::onLastNameChange,
                    placeholder = {
                        Text(text = "Last Name")
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = viewModel::onInsertPerson) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Insert Person",
                        tint = Color.Black
                    )
                }
            }
        }

        viewModel.personDetails?.let {
            Dialog(
                onDismissRequest = viewModel::onPersonDetailDialogDismiss
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${it.id}. ${it.firstName} ${it.lastName}"
                    )
                }
            }
        }
    }
}

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    person: PersonEntity,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
       Text(
           text = person.firstName,
           fontSize = 22.sp,
           fontWeight = FontWeight.Bold
       )
        
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Person",
                tint = Color.Gray
            )
        }
    }
}