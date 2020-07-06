package com.felixfavour.pidgipedia.util

import com.google.firebase.firestore.Source

object Pidgipedia {
    const val WORD_DATABASE = "word_database"
    const val EVENTSTAMP = "eventstamp"
    const val HOME_MODAL = "home_more_modal_sheet"
    const val WORD = "wordId"
    const val WORD_NAVIGATION = "wordNavigation"
    const val PREFERENCES = "MobotithePreferences"
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
    var SOURCE = Source.SERVER
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
    const val BADGE_1 = "Badge 1"
    const val BADGE_2 = "Badge 2"
    const val BADGE_5 = "Badge 5"
    const val BADGE_10 = "Badge 10"
    const val BADGE_20 = "Badge 20"
    const val BADGE_25 = "Badge 25"
    const val BADGE_50 = "Badge 50"
    const val BADGE_75 = "Badge 75"
    const val BADGE_100 = "Badge 100"
}

object Language {
    const val PIDGIN_NG = "pidgin_ng"
    const val ENGLISH_UK = "english_uk"
    const val ENGLISH_US = "english_us"
}

object Notifications {
    const val RANK_PROMOTION = "RP"
    const val COMMENT_RESPONSES = "CR"
    const val WORD_OF_THE_DAY = "WOD"
    const val WORDS_APPROVED = "WA"
    const val MISCELLANEOUS = "MISC"
}

object AppTheme {
    const val DARK_THEME = "dark_theme"
    const val LIGHT_THEME = "light_theme"
    const val DEFAULT = "default"
}