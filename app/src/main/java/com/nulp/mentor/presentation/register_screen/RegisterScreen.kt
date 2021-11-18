package com.nulp.mentor.presentation.register_screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.nulp.mentor.R
import com.nulp.mentor.data.remote.request_body.SignupBody
import com.nulp.mentor.presentation.Screen
import com.nulp.mentor.presentation.common.SnackbarError
import com.nulp.mentor.presentation.theme.ColorPrimary
import com.nulp.mentor.presentation.theme.Typography
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    var token by remember {
        mutableStateOf("")
    }

    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            return@OnCompleteListener
        }
        // Get new FCM registration token
        token = task.result ?: ""
    })

    val state = viewModel.userState

    var email by remember {
        mutableStateOf("")
    }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    var isMentor by remember {
        mutableStateOf(false)
    }

    var passwordValue by remember { mutableStateOf("") }
    var confirmPasswordValue by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    var course by remember { mutableStateOf(1) }
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

    Scaffold(backgroundColor = MaterialTheme.colors.primary) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = "App Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(200.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Card(
                Modifier
                    .weight(2f)
                    .padding(8.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Text(text = "Реєстрація", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                    Column(
                        Modifier
                            .weight(4f)
                            .animateContentSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = Typography.body1,
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "Еmail") },
                            singleLine = true,
                            trailingIcon = {
                                if (email.isNotBlank())
                                    IconButton(onClick = { email = "" }) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = ""
                                        )
                                    }
                            }
                        )
                        OutlinedTextField(
                            textStyle = Typography.body1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            value = name,
                            onValueChange = { name = it },
                            label = { Text(text = "Ім'я") },
                            singleLine = true,
                        )
                        OutlinedTextField(
                            textStyle = Typography.body1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            value = surname,
                            onValueChange = { surname = it },
                            label = { Text(text = "Фамілія") },
                            singleLine = true,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Column {
                            OutlinedTextField(
                                enabled = false,
                                textStyle = Typography.body1,
                                value = specialty,
                                label = { Text(text = "Спеціальність") },
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
                        Column {
                            OutlinedTextField(
                                enabled = false,
                                textStyle = Typography.body1,
                                value = course.toString(),
                                label = { Text(text = "Курс") },
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
                                    }) {
                                        Text(text = label.toString())
                                    }
                                }
                            }
                        }
                        OutlinedTextField(
                            textStyle = Typography.body1,
                            modifier = Modifier.fillMaxWidth(),
                            value = passwordValue,
                            onValueChange = { passwordValue = it },
                            label = { Text(text = "Пароль") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                    Icon(
                                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )
                                }
                            }
                        )
                        OutlinedTextField(
                            textStyle = Typography.body1,
                            modifier = Modifier.fillMaxWidth(),
                            value = confirmPasswordValue,
                            onValueChange = { confirmPasswordValue = it },
                            label = { Text(text = "Підтвердіть пароль") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = {
                                    confirmPasswordVisibility = !confirmPasswordVisibility
                                }) {
                                    Icon(
                                        imageVector = if (confirmPasswordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {

                            Text(style = Typography.body1, text = "Я хочу бути ментором")
                            Checkbox(
                                colors = CheckboxDefaults.colors(ColorPrimary),
                                checked = isMentor,
                                onCheckedChange = { isMentor = it }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Button(
                            enabled = true,
                            onClick = {

                                viewModel.signup(
                                    SignupBody(
                                        name = name,
                                        surname = surname,
                                        email = email,
                                        course = course,
                                        specialty = specialty,
                                        password = passwordValue,
                                        isMentor = isMentor,
                                        token = token
                                    ), confirmPasswordValue
                                )

                                Log.e("SignUpScreen: ", state.toString())
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Зареєструватися",
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        TextButton(onClick = {
                            navController.navigate(Screen.LoginScreenNavigation.route) {
                                popUpTo(Screen.RegisterScreenNavigation.route) {
                                    inclusive = true
                                }
                            }
                        }) {
                            Text(text = "Увійти")
                        }
                    }
                }

            }
        }
    }
    if (state.value is UserState.Loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
    if (state.value is UserState.Error) {
        SnackbarError((state.value as UserState.Error).error)
    }

    if (state.value is UserState.Success) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.RegisterScreenNavigation.route) {
                    inclusive = true
                }
            }
        }
    }
}
