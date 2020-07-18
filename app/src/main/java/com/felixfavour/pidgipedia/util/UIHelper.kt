package com.felixfavour.pidgipedia.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Word
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Source
import java.util.*

const val THEME_PREFERENCES = "THEME PREFERENCES"
const val DEFAULT_WIDTH = 400f
const val DEFAULT_HEIGHT = 400f

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun snack(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

@ExperimentalStdlibApi
fun shareWord(context: Context, word: Word) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(
            Intent.EXTRA_TEXT,
            "${word.name.capitalize(Locale.getDefault())}\n${word.meaning}." +
                    "\n" + context.getString(R.string.download_app_for_more)
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(intent, null)
    context.startActivity(shareIntent)
}


fun showWarningDialog(context: Context, title: Int, message: Int) {
    MaterialAlertDialogBuilder(context)
        .setIcon(R.drawable.warning)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok, null)
        .show()
}


fun showSuccessDialog(context: Context, title: Int, message: Int) {
    MaterialAlertDialogBuilder(context)
        .setIcon(R.drawable.check_circle)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok, null)
        .show()
}


fun setAppTheme(theme: String, context: Context) {
    val sharedPreferences = context.getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
    when (theme) {
        AppTheme.DARK_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPreferences.edit().putString(THEME_PREFERENCES, AppTheme.DARK_THEME).apply()
        }
        AppTheme.LIGHT_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPreferences.edit().putString(THEME_PREFERENCES, AppTheme.LIGHT_THEME).apply()
        }
        AppTheme.DEFAULT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            sharedPreferences.edit().putString(THEME_PREFERENCES, AppTheme.DEFAULT).apply()
        }
    }
}

fun getAppTheme(context: Context, buttonToggleGroup: MaterialButtonToggleGroup?) {

    val sharedPreferences = context.getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
    when (sharedPreferences.getString(THEME_PREFERENCES, AppTheme.LIGHT_THEME)) {
        AppTheme.LIGHT_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            buttonToggleGroup?.check(R.id.light_theme_selection)
        }
        AppTheme.DARK_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            buttonToggleGroup?.check(R.id.dark_theme_selection)
        }
        AppTheme.DEFAULT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            buttonToggleGroup?.check(R.id.default_theme_selection)
        }
    }

}
fun wordOfTheDayAlarm(context: Context) {
    context.run {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 20, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val intervalMillis: Long = 100 * 60 * 60 * 24
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
            intervalMillis, pendingIntent)
    }
}

fun resizeImage(image: Bitmap): Bitmap {

    val width = image.width
    val height = image.height

    val scaleWidth = width / 10
    val scaleHeight = height / 10

    if (image.byteCount <= 8000000)
        return image

    return Bitmap.createScaledBitmap(image, scaleWidth, scaleHeight, false)
}



fun checkInternetConnectivity(context: Context) {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        connectivityManager.requestNetwork(request, object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Pidgipedia.SOURCE = Source.DEFAULT
            }

            override fun onUnavailable() {
                super.onUnavailable()
                Pidgipedia.SOURCE = Source.CACHE
                toast(context.applicationContext, context.getString(R.string.no_internet_access))
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Pidgipedia.SOURCE = Source.CACHE
                toast(context.applicationContext, context.getString(R.string.internet_unstable))
            }
        }, 1000)
    } else {
        connectivityManager.requestNetwork(request, object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Pidgipedia.SOURCE = Source.DEFAULT
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Pidgipedia.SOURCE = Source.CACHE
                toast(context.applicationContext, context.getString(R.string.internet_unstable))
            }
        })
    }
}