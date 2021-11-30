package com.nulp.mentor.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nulp.mentor.R
import com.nulp.mentor.data.local.entities.NotificationEntity
import com.nulp.mentor.domain.repository.NotificationRepository
import com.nulp.mentor.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {
    @Inject
    lateinit var repository: NotificationRepository

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("onMessageReceived", "onMessageReceived")
        showNotification(
            remoteMessage.notification?.title,
            remoteMessage.notification?.body,
            remoteMessage.data
        )
        super.onMessageReceived(remoteMessage)
    }

    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)
        try {
            Log.e("handleIntent", "handleIntent" + intent.dataString)
            intent.getStringExtra("notification_id")?.let { it ->
                val notification = NotificationEntity(
                    id = it.toLong(),
                    applicationId = intent.getStringExtra("applicationId")?.toInt() ?: -1,
                    message = intent.getStringExtra("message") ?: "",
                    isConfirmed = intent.getStringExtra("isConfirmed")?.toBoolean(),
                )
                CoroutineScope(Dispatchers.IO).launch {
                    repository.addNotification(notification)
                }
            }
        } catch (e: Exception) {
            Log.e("handleIntent", e.message.toString())
        }

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
            .setSmallIcon(R.drawable.ic_app_icon)
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