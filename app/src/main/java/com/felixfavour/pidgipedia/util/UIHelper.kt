package com.felixfavour.pidgipedia.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.entity.Word
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

const val THEME_PREFERENCES = "THEME PREFERENCES"

private val firebaseAuth = FirebaseAuth.getInstance()
private val firestore = FirebaseFirestore.getInstance()

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

fun getButtonGroupSelection(context: Context, buttonToggleGroup: MaterialButtonToggleGroup?) {

    val sharedPreferences = context.getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
    when (sharedPreferences.getString(THEME_PREFERENCES, AppTheme.LIGHT_THEME)) {
        AppTheme.LIGHT_THEME -> {
            val group  =buttonToggleGroup?.get(2) as MaterialButton
            group.isChecked = true
        }
        AppTheme.DARK_THEME -> {
            val group  =buttonToggleGroup?.get(0) as MaterialButton
            group.isChecked = true
        }
        AppTheme.DEFAULT -> {
            val group  =buttonToggleGroup?.get(1) as MaterialButton
            group.isChecked = true
        }
    }

}

//fun hashMapToWord(hashMap: HashMap<String, Any>?): Word? {
//    if (hashMap != null) {
//        val wordId: String = hashMap["wordId"] as String
//        val name: String = hashMap["name"] as String
//        val meaning: String = hashMap["meaning"] as String
//        val etymology: String = hashMap["etymology"] as String
//        val plural: String = hashMap["plural"] as String
//        val partOfSpeech: String = hashMap["partOfSpeech"] as String
//        val syllables: Long = hashMap["syllables"] as Long
//        val syllabicDivision: String = hashMap["syllabicDivision"] as String
//        val englishEquivalent: String = hashMap["englishEquivalent"] as String
//        val imageReference: String = hashMap["imageReference"] as String
//        val transcription: String = hashMap["transcription"] as String
//        val pronunciationReference: String = hashMap["pronunciationReference"] as String
//        val synonyms: List<String>? = hashMap["synonyms"] as List<String>?
//        val sentences: List<String>? = hashMap["sentences"] as List<String>?
//        val authorId: String = hashMap["authorId"] as String
//        val dateCreated: Long = hashMap["dateCreated"] as Long
//        val lastUpdated: Long = hashMap["lastUpdated"] as Long
//        val approved: Boolean = hashMap["approved"] as Boolean
//        val rejected: Boolean = hashMap["rejected"] as Boolean
//        val wordOfTheDay_date: Long = hashMap["wordOfTheDay_date"] as Long
//
//        return Word(
//            wordId = wordId,
//            name = name,
//            meaning = meaning,
//            etymology = etymology,
//            plural = plural,
//            partOfSpeech = partOfSpeech,
//            syllables = syllables,
//            syllabicDivision = syllabicDivision,
//            englishEquivalent = englishEquivalent,
//            imageReference = imageReference,
//            transcription = transcription,
//            pronunciationReference = pronunciationReference,
//            synonyms = synonyms!!,
//            sentences = sentences!!,
//            authorId = authorId,
//            dateCreated = dateCreated,
//            lastUpdated = lastUpdated,
//            approved = approved,
//            rejected = rejected,
//            wordOfTheDay_date = wordOfTheDay_date
//        )
//    }
//
//    return null
//}
//
//
//fun hashMapToRemoteUser(hashMap: HashMap<String, Any>?): RemoteUser? {
//    if (hashMap != null) {
//        val userID: String? = hashMap["userId"] as String?
//        val firstName: String = hashMap["firstName"] as String
//        val lastName: String = hashMap["lastName"] as String
//        val email: String = hashMap["email"] as String
//        val dateOfBirth: Long = hashMap["dateOfBirth"] as Long
//        val rank: Long = hashMap["rank"] as Long
//        val location: String = hashMap["location"] as String
//        val bio: String = hashMap["bio"] as String
//        val badges: List<String> = hashMap["badges"] as List<String>
//        val suggestedWords: List<String> = hashMap["suggestedWords"] as List<String>
//        val approvedWords: List<String> = hashMap["approvedWords"] as List<String>
//        val highestScore: Long = hashMap["highestScore"] as Long
//        val profileImageURL: String = hashMap["profileImageURL"] as String
//        val username: String = hashMap["username"] as String
//        val eventstamps: List<Eventstamp> = emptyList()
//
//        return RemoteUser(
//            userID,
//            firstName,
//            lastName,
//            email,
//            dateOfBirth,
//            rank,
//            location,
//            bio,
//            badges,
//            suggestedWords,
//            approvedWords,
//            highestScore,
//            profileImageURL,
//            username,
//            eventstamps
//        )
//    }
//    return null
//}
//
//
//fun UIDToRemoteUser(userID: String?): RemoteUser? {
//    var user: RemoteUser? = null
//    if (!userID.isNullOrBlank()) {
//        return user
//    }
//    return user
//}


//fun hashMapToComment(hashMap: HashMap<String, Any>?): Comment? {
//    if (hashMap != null) {
//        val commentContent: String = hashMap["commentContent"] as String
//        val author: HashMap<String, Any> = hashMap["authorId"] as HashMap<String, Any>
//        val dateCreated: Long = hashMap["dateCreated"] as Long
//
//        return Comment(commentContent, hashMapToRemoteUser(author)!!, dateCreated)
//    }
//    return null
//}