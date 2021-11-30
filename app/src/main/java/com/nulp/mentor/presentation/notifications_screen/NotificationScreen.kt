package com.nulp.mentor.presentation.notifications_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nulp.mentor.presentation.common.SnackbarError
import com.nulp.mentor.presentation.home_screen.components.NotificationListItem
import com.nulp.mentor.presentation.theme.Background

@ExperimentalMaterialApi
@Composable
fun NotificationScreen(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {

    val notificationsState = notificationViewModel.notificationsState.value
    Column(
        modifier = Modifier
            .background(Background)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp, bottom = 15.dp),
            text = "Сповіщення",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
        )
        if (notificationsState.notifications.isNotEmpty()) {
            LazyColumn {
                items(
                    notificationsState.notifications
                ) { notification ->
                    NotificationListItem(notification = notification, onItemClick = {})
                }
            }
        }
    }
    if (notificationsState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }

    if (notificationsState.error.isNotEmpty()) {
        Log.e("SignInScreen: ", "error")
        SnackbarError(notificationsState.error)
    }

}