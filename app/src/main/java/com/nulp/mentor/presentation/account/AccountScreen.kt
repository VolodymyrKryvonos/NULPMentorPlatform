package com.nulp.mentor.presentation.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nulp.mentor.R
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.presentation.Screen
import com.nulp.mentor.presentation.theme.Background
import com.nulp.mentor.presentation.theme.ColorPrimary

@Composable
fun AccountScreen(
    navController: NavController,
    prefService: PrefService,
    accountViewModel: AccountViewModel = hiltViewModel()
) {

    val user = prefService.getUser()
    val mentorState = accountViewModel.mentorState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_user_icon),
            contentDescription = "mentor icon",
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 50.dp, bottom = 15.dp)
        )
        Text(
            text = "${user?.name} ${user?.surname}",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight(800),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "${user?.specialty}",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight(400),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "${user?.course} курс",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight(400),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        if (user?.isMentor == true) {
            Text(
                text = mentorState.mentor.subjectString(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight(400),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 40.dp)
            )
            Text(
                text = "${mentorState.mentor.mentees.size} менті",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight(400),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)

            ) {
                Button(
                    onClick = { navController.navigate(Screen.AddSubjectScreen.route) },
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

        } else {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)

            ) {
                Button(
                    onClick = { accountViewModel.becomeMentor(user?.id ?: -1) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ColorPrimary)
                ) {
                    Text(
                        text = "Стати ментором",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, bottom = 70.dp)

        ) {
            Button(
                onClick = {
                    prefService.logout()
                    navController.navigate(Screen.LoginScreenNavigation.route) {
                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Вийти",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600),
                )
            }
        }
    }
    if (mentorState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}