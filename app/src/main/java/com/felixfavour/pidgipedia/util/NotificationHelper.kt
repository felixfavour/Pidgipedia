package com.felixfavour.pidgipedia.util

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.WordOfTheDayActivity
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.util.Notification.MISCELLANEOUS_NEWS_CHANNEL_ID
import com.felixfavour.pidgipedia.util.Notification.REWARD_CHANNEL_ID
import com.felixfavour.pidgipedia.util.Notification.WORD_APPROVAL_CHANNEL_ID
import com.felixfavour.pidgipedia.util.Notification.WORD_COMMENTS_CHANNEL_ID
import com.felixfavour.pidgipedia.util.Notification.WORD_OF_THE_DAY_CHANNEL_ID
import com.felixfavour.pidgipedia.util.Notification.WORD_REJECTION_CHANNEL_ID
import com.felixfavour.pidgipedia.util.Notification.WORD_SUGGESTION_CHANNEL_ID

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val wordSuggestionChannel = NotificationChannel(
            WORD_SUGGESTION_CHANNEL_ID,
            "Word Suggestion",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val wordOfTheDayChannel = NotificationChannel(
            WORD_OF_THE_DAY_CHANNEL_ID,
            "Word of The Day",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val wordApprovalChannel = NotificationChannel(
            WORD_APPROVAL_CHANNEL_ID,
            "Word Approval",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val wordCommentChannel = NotificationChannel(
            WORD_COMMENTS_CHANNEL_ID,
            "Word Comments",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val profileRewardChannel = NotificationChannel(
            REWARD_CHANNEL_ID,
            "Rank and Badges Rewards",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val miscellaneousNewsChannel = NotificationChannel(
            MISCELLANEOUS_NEWS_CHANNEL_ID,
            "Miscellaneous News",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val wordRejectionChannel = NotificationChannel(
            WORD_REJECTION_CHANNEL_ID,
            "Word Rejection",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val channels = listOf(
            miscellaneousNewsChannel,
            profileRewardChannel,
            wordApprovalChannel,
            wordCommentChannel,
            wordOfTheDayChannel,
            wordRejectionChannel,
            wordSuggestionChannel
        )

        notificationManager.createNotificationChannels(channels)
    }
}

fun showNotification(context: Context, channel: String, eventstamp: Eventstamp) {
    createNotificationChannel(context)

    when (channel) {
        WORD_OF_THE_DAY_CHANNEL_ID -> {
            val intent = Intent(context, WordOfTheDayActivity::class.java)
            val notif = NotificationCompat.Builder(context, WORD_SUGGESTION_CHANNEL_ID).apply {
                setContentTitle(context.getString(R.string.word_of_the_day))
                setContentText("Let\'s learn a new word today. Check our word of the day for the day😛")
                setStyle(NotificationCompat.BigTextStyle())
                setSmallIcon(R.drawable.ic_pidgipedia_notif)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
                addAction(NotificationCompat.Action(null, "View", PendingIntent.getActivity(context, 0, intent, 0)))
            }.build()

            NotificationManagerCompat.from(context).notify(0, notif)
        }

        REWARD_CHANNEL_ID -> {
            val intent = Intent(context, WordOfTheDayActivity::class.java)
            val notif = NotificationCompat.Builder(context, REWARD_CHANNEL_ID).apply {
                setContentTitle("You earned a Badge")
                setContentText("Let\'s learn a new word today. Check our word of the day for the day😛")
                setStyle(NotificationCompat.BigTextStyle())
                setSmallIcon(R.drawable.ic_pidgipedia_notif)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
                addAction(NotificationCompat.Action(null, "View", PendingIntent.getActivity(context, 0, intent, 0)))
            }.build()

            NotificationManagerCompat.from(context).notify(0, notif)
        }
    }
}