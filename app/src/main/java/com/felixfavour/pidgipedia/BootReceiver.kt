package com.felixfavour.pidgipedia

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.util.wordOfTheDayAlarm

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            wordOfTheDayAlarm(context)
        }
    }
}
