package com.felixfavour.pidgipedia.util

import com.google.firebase.firestore.Source

object Pidgipedia {
    const val APP_NAME = "Pidgipedia"
    const val WORD_DATABASE = "word_database"
    const val EVENTSTAMP = "eventstamp"
    const val HOME_MODAL = "home_more_modal_sheet"
    const val WORD = "wordId"
    const val WORD_NAVIGATION = "wordNavigation"
    const val PREFERENCES = "PidgipediaPreferences"
    const val AUTHENTICATION_PREFERENCES = "AuthenticationPreferences"
    const val ONBOARDING_PREFERENCE = "hasActivityBeenOpenedBefore"
    const val RC_SIGN_IN = 10
    const val USERS = "users"
    const val SUGGESTED_WORDS = "suggested_words"
    const val EVENTSTAMPS = "eventstamps"
    const val QUIZZES = "quizzes"
    const val COMMENTS = "comments"
    const val PROFILE_IMAGES_REFERENCE = "profile_images/"
    const val WORD_IMAGES_REFERENCE = "word_images/"
    const val DATABASES_REFERENCE = "databases/"
    const val AUDIO_REFERENCE = "word_audio/"
    const val DUMMY_PASSWORD = "pidgipedia_dummy123"
    const val PROFILE_VISIBLE = "profile_visible"
    const val PROFILE_AUTHOR_VISIBLE = "profile_author_visible"
    var BOOKMARKS_VISIBILITY = true
    var SOURCE = Source.SERVER
    var HISTORY_LIMIT = HistorySize.FIFTY
}

object Connection {
    const val FAILED = -1
    const val LOADING = 1
    const val SUCCESS = 2
    const val SUCCESS_OAUTH = 3
}

object Game {
    const val EASY = 30
    const val MEDIUM = 15
    const val HARD = 8
}

object Rank {
    const val RANK_1 = "Oga"
    const val RANK_2 = "Contributor"
    const val RANK_3 = "JJC"
    const val RANK_JJC = 3L
    const val RANK_CONTRIBUTOR = 2L
    const val RANK_OGA = 1L
}

object Badges {
    const val BADGE_1 = "Level 1 Badge"
    const val BADGE_2 = "Level 2 Badge"
    const val BADGE_5 = "Level 5 Badge"
    const val BADGE_10 = "Level 10 Badge"
    const val BADGE_20 = "Level 20 Badge"
    const val BADGE_25 = "Level 25 Badge"
    const val BADGE_50 = "Level 50 Badge"
    const val BADGE_75 = "Level 75 Badge"
    const val BADGE_100 = "Level 100 Badge"
}

object Language {
    const val PIDGIN_NG = "Pidgin NG"
    const val ENGLISH_UK = "English UK"
    const val ENGLISH_US = "English US"
}

object Notification {
    const val USERS_UPDATES = "UserUpdates"
    const val COMMENTS = "Comments"
    const val WOD = "Wod"
    const val WORDS_UPDATES = "WordUpdates"
    const val MISCELLANEOUS = "Miscellaneous"
}

object NotificationsCode {
    const val USERS_UPDATES = "UUP"
    const val COMMENTS = "COM"
    const val WOD = "WOD"
    const val WORDS_UPDATES = "WUP"
    const val MISCELLANEOUS = "MIS"
}

object HistorySize {
    const val ONE_FIFTY = 150
    const val HUNDRED = 100
    const val FIFTY = 50
}

object AppTheme {
    const val DARK_THEME = "dark_theme"
    const val LIGHT_THEME = "light_theme"
    const val DEFAULT = "default"
}