package com.nulp.mentor.presentation.account.add_subject

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nulp.mentor.presentation.common.SnackbarError
import com.nulp.mentor.presentation.common.SnackbarSuccess
import com.nulp.mentor.presentation.theme.Background
import com.nulp.mentor.presentation.theme.ColorPrimary
import com.nulp.mentor.presentation.theme.Typography

@Composable
fun AddSubjectScreen(
    navController: NavController,
    viewModel: AddSubjectViewModel = hiltViewModel()
) {

    val subjects = viewModel.subjectsListState.value
    val subscriptionState = viewModel.subscribeState.value

    var selectedSubject by remember {
        mutableStateOf(-1)
    }

    var course by remember { mutableStateOf(-1) }
    val courses = listOf(1, 2, 3, 4, 5)
    var expandedCourse by remember { mutableStateOf(false) }
    var textfieldCourseSize by remember { mutableStateOf(Size.Zero) }

    var specialty by remember {
        mutableStateOf("")
    }
    val specialties = listOf("Комп'ютерні науки", "Системний аналіз")
    var expandedSpeciality by remember { mutableStateOf(false) }
    var textfieldSpecialitySize by remember { mutableStateOf(Size.Zero) }
    val iconSpec = if (expandedSpeciality) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    var subject by remember { mutableStateOf("") }
    var expandedSubject by remember { mutableStateOf(false) }
    var textfieldSubjectSize by remember { mutableStateOf(Size.Zero) }
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

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            OutlinedTextField(
                enabled = false,
                textStyle = Typography.body1,
                value = specialty,
                maxLines = 1,
                label = { Text(text = "Виберіть спеціальність") },
                onValueChange = { specialty = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSpecialitySize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(iconSpec, "dropDown",
                        Modifier.clickable {
                            expandedSpeciality = !expandedSpeciality
                        })
                }
            )
            DropdownMenu(
                expanded = expandedSpeciality,
                onDismissRequest = { expandedSpeciality = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSpecialitySize.width.toDp() })
            ) {
                specialties.forEach { label ->
                    DropdownMenuItem(onClick = {
                        expandedSpeciality = !expandedSpeciality
                        specialty = label
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            OutlinedTextField(
                enabled = false,
                maxLines = 1,
                textStyle = Typography.body1,
                value = if (course == -1) "" else course.toString(),
                label = { Text(text = "Виберіть курс") },
                onValueChange = { course = it.toInt() },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldCourseSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(iconSpec, "dropDown2",
                        Modifier.clickable {
                            expandedCourse = !expandedCourse
                        })
                }
            )
            DropdownMenu(
                expanded = expandedCourse,
                onDismissRequest = { expandedCourse = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldCourseSize.width.toDp() })
            ) {
                courses.forEach { label ->
                    DropdownMenuItem(onClick = {
                        expandedCourse = !expandedCourse
                        course = label
                        viewModel.selectByCourse(course)
                    }) {
                        Text(text = label.toString())
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            OutlinedTextField(
                enabled = false,
                textStyle = Typography.body1,
                value = subject,
                maxLines = 1,
                label = { Text(text = "Виберіть предмет") },
                onValueChange = { specialty = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSubjectSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(iconSpec, "dropDown",
                        Modifier.clickable {
                            expandedSubject = !expandedSubject
                        })
                }
            )
            DropdownMenu(
                expanded = expandedSubject,
                onDismissRequest = { expandedSubject = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSubjectSize.width.toDp() })
            ) {
                subjects.subjectList.forEach { item ->
                    DropdownMenuItem(onClick = {
                        expandedSubject = !expandedSubject
                        subject = item.name
                        selectedSubject = item.id
                    }) {
                        Text(text = item.name)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, bottom = 40.dp)

        ) {
            Button(
                onClick = { viewModel.subscribeOnSubject(selectedSubject) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorPrimary)
            ) {
                Text(
                    text = "Добавити предмет",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600),
                )
            }
        }
    }
    if (subscriptionState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
    if (subscriptionState.error.isNotEmpty() || subjects.error.isNotEmpty()) {
        SnackbarError(if (subscriptionState.error.isNotEmpty()) subscriptionState.error else subjects.error)
    }

    if (subscriptionState.isSuccess) {
        SnackbarSuccess(text = "Предмет успішно добавлено.")
    }
}