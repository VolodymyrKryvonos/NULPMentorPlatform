package com.nulp.mentor.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nulp.mentor.R
import com.nulp.mentor.domain.model.Notification
import com.nulp.mentor.presentation.theme.h3

@ExperimentalMaterialApi
@Composable
fun NotificationListItem(
    notification: Notification,
    onItemClick: (Notification) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        onClick = { onItemClick(notification) },
        elevation = 2.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Icon(
                painter = painterResource(
                    id = if (notification.isConfirmed == null) {
                        R.drawable.ic_incoming_notification
                    } else {
                        if (notification.isConfirmed) {
                            R.drawable.ic_tick
                        } else {
                            R.drawable.ic_cross
                        }
                    }
                ),
                tint = if (notification.isConfirmed == null) {
                    Color(0xFF000000)
                } else {
                    if (notification.isConfirmed) {
                        Color(0xFF00B13C)
                    } else {
                        Color(0xFFFF4800)
                    }
                },
                contentDescription = "notification item",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
            )
            Text(
                text = if (notification.isConfirmed == null) {
                    "Новий запит"
                } else {
                    if (notification.isConfirmed) {
                        "Ментор підтвердив ваш запит"
                    } else {
                        "Ментор відхилив ваш запит"
                    }
                },
                style = h3,
                fontSize = 13.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp),
            )
        }
    }
}