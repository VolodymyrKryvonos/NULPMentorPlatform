package com.nulp.mentor.presentation.find_mentor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nulp.mentor.presentation.common.SnackbarError
import com.nulp.mentor.presentation.theme.Background

@ExperimentalMaterialApi
@Composable
fun FindMentorScreen(
    navController: NavController,
    viewModel: FindMentorViewModel = hiltViewModel()
) {
    var searchInput by remember {
        mutableStateOf("")
    }
    val subjectsListState = viewModel.subjectsListState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Text(
            text = "Виберіть предмет",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight(600),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = searchInput,
            onValueChange = {
                searchInput = it
                viewModel.findByName(searchInput)
            },
            label = { Text(text = "Ведіть назву предмету") },
            singleLine = true,
            trailingIcon = {
                if (searchInput.isNotBlank())
                    IconButton(onClick = {
                        searchInput = ""
                        viewModel.findByName(searchInput)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = ""
                        )
                    }
            }
        )
        LazyColumn() {
            items(subjectsListState.subjectList) { subject ->
                SubjectListItem(subject, onClick = {})
            }
        }
    }
    if (subjectsListState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
    if (subjectsListState.error.isNotEmpty()) {
        SnackbarError(subjectsListState.error)
    }
}