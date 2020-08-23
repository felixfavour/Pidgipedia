package com.felixfavour.pidgipedia

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService: FirebaseMessagingService() {
    var notificationId = 0

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        notificationId++
        val notif = remoteMessage.notification

        val notification = NotificationCompat.Builder(applicationContext, notif?.channelId!!)
            .setContentTitle(notif.title)
            .setContentText(notif.body)
            .setStyle(NotificationCompat.BigPictureStyle())
            .setSmallIcon(R.drawable.ic_pidgipedia_notif)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(null)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)

    }
}