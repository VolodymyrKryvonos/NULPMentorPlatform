package com.nulp.mentor.presentation.home_screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nulp.mentor.R
import com.nulp.mentor.presentation.Screen
import com.nulp.mentor.presentation.common.SnackbarError
import com.nulp.mentor.presentation.home_screen.components.BestMentorsListItem
import com.nulp.mentor.presentation.home_screen.components.NotificationListItem
import com.nulp.mentor.presentation.home_screen.components.SubjectListItem
import com.nulp.mentor.presentation.theme.Background
import com.nulp.mentor.presentation.theme.ColorPrimary
import com.nulp.mentor.presentation.theme.LightGray
import com.nulp.mentor.presentation.theme.h3
import java.lang.Integer.min

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val bestMentorsState = homeViewModel.mentorsState.value
    val subjectsState = homeViewModel.subjectsState.value
    val notificationsState = homeViewModel.notificationsState.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Text(
            text = "Найкращі ментори",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 28.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (bestMentorsState.mentors.isNotEmpty()) {
            LazyRow(modifier = Modifier.padding(top = 15.dp)) {
                items(bestMentorsState.mentors) { bestMentor ->
                    BestMentorsListItem(
                        bestMentor = bestMentor,
                        onItemClick = { Log.e("BestMentorClick", bestMentor.mentor.email) })
                }
            }
        } else {
            val stroke = Stroke(
                width = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
            Surface(
                shape = RoundedCornerShape(30f, 30f, 30f, 30f),
                onClick = { navController.navigate(Screen.AccountScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(top = 15.dp, end = 20.dp, start = 20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawRoundRect(
                            color = DarkGray,
                            style = stroke,
                            cornerRadius = CornerRadius(30f, 30f)
                        )
                    }
                    Text(
                        fontSize = 22.sp,
                        style = h3,
                        text = "Немає менторів?\n Стань першим!",
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, end = 10.dp, start = 10.dp)
                .background(color = White, shape = RoundedCornerShape(10)),
            shape = RoundedCornerShape(10),
            elevation = 1.dp
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Transparent)
                        .clickable {
                            navController.navigate(Screen.FindMentorScreen.route)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Пошук ментора",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                    )
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "find mentor"
                    )
                }
                if (subjectsState.subjects.isNotEmpty()) {
                    Divider(color = LightGray, thickness = 1.dp)
                    LazyRow(modifier = Modifier.padding(top = 5.dp)) {
                        items(subjectsState.subjects) { subject ->
                            SubjectListItem(
                                subject = subject,
                                onItemClick = { Log.e("BestMentorClick", subject.name) })
                        }
                    }
                }
            }
        }
        if (notificationsState.notifications.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = "Сповіщення",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { navController.navigate(Screen.NotificationScreen.route) },
                    text = "Переглянути всі (${notificationsState.notifications.size})",
                    color = ColorPrimary,
                    fontSize = 16.sp
                )
                LazyColumn {
                    items(
                        notificationsState.notifications.subList(
                            0,
                            min(notificationsState.notifications.size, 3)
                        )
                    ) { notification ->
                        NotificationListItem(notification = notification, onItemClick = {})
                    }
                }
            }
        }
    }

    if (bestMentorsState.isLoading || subjectsState.isLoading || notificationsState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }

    if (bestMentorsState.error.isNotEmpty()) {
        Log.e("SignInScreen: ", "error")
        SnackbarError(bestMentorsState.error)
    }
}