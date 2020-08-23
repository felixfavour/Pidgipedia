package com.felixfavour.pidgipedia.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channels = mutableListOf<NotificationChannel>(
            NotificationChannel(
                NotificationsCode.USERS_UPDATES,
                Notification.USERS_UPDATES,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )

        notificationManager.createNotificationChannels(channels)
    }
}
