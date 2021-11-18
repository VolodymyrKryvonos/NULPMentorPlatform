package com.nulp.mentor.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nulp.mentor.R
import com.nulp.mentor.presentation.MainActivity


class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(
            remoteMessage.notification?.title,
            remoteMessage.notification?.body,
            remoteMessage.data
        )
        super.onMessageReceived(remoteMessage)
    }

    private fun showNotification(
        title: String?,
        message: String?,
        data: MutableMap<String, String>
    ) {
        val intent = Intent(this, MainActivity::class.java)
        val channelId = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        data.forEach {
            intent.putExtra(it.key, it.value)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channelId
        )
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        val notificationChannel = NotificationChannel(
            channelId, "web_app",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(
            notificationChannel
        )
        notificationManager.notify(0, builder.build())
    }
}