package com.felixfavour.pidgipedia.util

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.Badges.BADGE_1
import com.felixfavour.pidgipedia.util.Badges.BADGE_10
import com.felixfavour.pidgipedia.util.Badges.BADGE_100
import com.felixfavour.pidgipedia.util.Badges.BADGE_2
import com.felixfavour.pidgipedia.util.Badges.BADGE_20
import com.felixfavour.pidgipedia.util.Badges.BADGE_25
import com.felixfavour.pidgipedia.util.Badges.BADGE_5
import com.felixfavour.pidgipedia.util.Badges.BADGE_50
import com.felixfavour.pidgipedia.util.Badges.BADGE_75
import com.felixfavour.pidgipedia.util.Pidgipedia.COMMENTS
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.felixfavour.pidgipedia.util.Rank.RANK_1
import com.felixfavour.pidgipedia.util.Rank.RANK_2
import com.felixfavour.pidgipedia.util.Rank.RANK_3
import com.felixfavour.pidgipedia.util.Rank.RANK_CONTRIBUTOR
import com.felixfavour.pidgipedia.util.Rank.RANK_JJC
import com.felixfavour.pidgipedia.view.dictionary.WordListAdapter
import com.felixfavour.pidgipedia.view.home.EventstampCommentsAdapter
import com.felixfavour.pidgipedia.view.home.HomeRecyclerViewAdapter
import com.felixfavour.pidgipedia.view.home.UnapprovedWordListAdapter
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jp.wasabeef.glide.transformations.BlurTransformation
import org.joda.time.DateTime
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

const val MONTHS_IN_A_YEAR = 12
const val MARGIN = 4

private val firebaseAuth = FirebaseAuth.getInstance()
private val firebaseFirestore = FirebaseFirestore.getInstance()

@BindingAdapter("wordList")
fun populateWordList(recyclerView: RecyclerView, words: List<Word>?) {
    recyclerView.addItemDecoration(
        DividerItemDecoration(recyclerView.context,
        DividerItemDecoration.VERTICAL)
    )
    val adapter = recyclerView.adapter as WordListAdapter
    adapter.submitList(words)
}


@BindingAdapter("eventstampsList")
fun populateEventstampList(recyclerView: RecyclerView, eventstamps: List<Eventstamp>?) {
    /**
     *  RecyclerView item decoration to put a margin of 8dp above and below every item */
    val adapter = recyclerView.adapter as HomeRecyclerViewAdapter
    adapter.submitList(eventstamps)
}


@BindingAdapter("unapprovedWordsList")
fun unapprovedWordsList(recyclerView: RecyclerView, words: List<Word>?) {
    val adapter = recyclerView.adapter as UnapprovedWordListAdapter
    adapter.submitList(words)
}


@BindingAdapter("eventstampText")
fun eventstampText(textView: TextView, eventstamp: Eventstamp?) {
    var primaryUser = ""
    var secondaryUser = ""

    if (eventstamp?.humanEntityId != null) {

        firebaseFirestore.collection(USERS).document(eventstamp.humanEntityId)
            .get(SOURCE)
            .addOnSuccessListener { userDoc ->
                val primaryHumanEntity = userDoc.toObject(RemoteUser::class.java)
                primaryUser = primaryHumanEntity.toString()
                if (primaryHumanEntity?.userId == firebaseAuth.uid) primaryUser = "You"

                if (eventstamp.wordAuthorId != null) {
                    firebaseFirestore.collection(USERS).document(eventstamp.wordAuthorId)
                        .get(SOURCE)
                        .addOnSuccessListener { documentSnapshot ->
                            val secondaryHumanEntity = documentSnapshot.toObject(RemoteUser::class.java)
                            secondaryUser = secondaryHumanEntity.toString()
                            if (secondaryHumanEntity?.userId == firebaseAuth.uid) secondaryUser = "you"
                            else if (primaryHumanEntity?.equals(secondaryHumanEntity)!!) secondaryUser = "they"

                            when {
                                eventstamp.wordComment -> {
                                    textView.text = textView.context.applicationContext.getString(
                                        R.string.word_comment_placeholder,
                                        primaryUser,
                                        secondaryUser
                                    )
                                }
                                eventstamp.commentResponse -> {
                                    if (secondaryUser == "you") {
                                        textView.text = textView.context.applicationContext.getString(
                                            R.string.comment_response_placeholder2,
                                            primaryUser
                                        )
                                    } else if (secondaryUser == "they") {
                                        textView.text = textView.context.applicationContext.getString(
                                            R.string.comment_response_placeholder3,
                                            primaryUser
                                        )
                                    }
                                    else {
                                        textView.text = textView.context.applicationContext.getString(
                                            R.string.comment_response_placeholder,
                                            primaryUser,
                                            secondaryUser
                                        )
                                    }
                                }
                                eventstamp.suggested -> {
                                    textView.text = textView.context.applicationContext.getString(
                                        R.string.word_suggestion_placeholder,
                                        primaryUser
                                    )
                                }
                                eventstamp.approved -> {
                                    textView.text = textView.context.applicationContext.getString(
                                        R.string.word_approval_placeholder,
                                        primaryUser,
                                        secondaryUser
                                    )
                                }
                                eventstamp.rejected -> {
                                    textView.text = textView.context.applicationContext.getString(
                                        R.string.word_rejection_placeholder,
                                        primaryUser,
                                        secondaryUser
                                    )
                                }
                            }
                        }

                }
                if (eventstamp.badgeRewardType != null || eventstamp.rankRewardType != null) {
                    when {
                        eventstamp.rankRewardType != null -> {
                            textView.text = textView.context.applicationContext.getString(
                                R.string.rank_reward_placeholder,
                                primaryUser,
                                eventstamp.rankRewardType.let {
                                    when (it) {
                                        RANK_JJC -> return@let RANK_3
                                        RANK_CONTRIBUTOR -> return@let RANK_2
                                        else -> return@let RANK_1
                                    }
                                }
                            )
                        }
                        eventstamp.badgeRewardType.toString().isNotEmpty() -> {
                            textView.text = textView.context.applicationContext.getString(
                                R.string.badge_reward_placeholder,
                                primaryUser,
                                eventstamp.badgeRewardType
                            )
                        }
                    }
                }
            }
    }
}


@BindingAdapter("profileImage")
fun getProfileImage(imageView: ImageView, authorId: String?) {
    if (authorId != null) {
        try {
            firebaseFirestore.collection(USERS).document(authorId)
                .get().addOnSuccessListener {documentSnapshot ->
                    val user = documentSnapshot.toObject(RemoteUser::class.java)

                    Glide.with(imageView.context.applicationContext)
                        .load(user?.profileImageURL)
                        .placeholder(R.drawable.person)
                        .centerCrop()
                        .circleCrop()
                        .into(imageView)
                }
        } catch (ex: IllegalArgumentException) {}
    }
}


@BindingAdapter("authorName")
fun getAuthorFullName(textView: TextView, authorId: String?) {
    if (authorId != null) {
        firebaseFirestore.collection(USERS).document(authorId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val fullName = documentSnapshot["firstName"].toString() + " " + documentSnapshot["lastName"].toString()
                textView.text = fullName
            }
    }
}


@BindingAdapter("username")
fun getAuthorUsername(textView: TextView, authorId: String?) {
    if (authorId != null) {
        firebaseFirestore.collection(USERS).document(authorId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val username = documentSnapshot["username"].toString()
                textView.text = username
            }
    }
}


@BindingAdapter("word")
fun getWordName(textView: TextView, wordId: String?) {
    if (wordId != null) {
        firebaseFirestore.collection(SUGGESTED_WORDS).document(wordId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val word = documentSnapshot["name"].toString()
                textView.text = word
            }
    }
}


@BindingAdapter("wordBookmarked")
fun isWordBookmarked(imageButton: ImageButton, bookmarked: Boolean?) {
    if(bookmarked != null) {
        if(bookmarked) {
            val drawable = imageButton.context.getDrawable(R.drawable.bookmark)
            imageButton.setImageDrawable(drawable)
        } else {
            val drawable = imageButton.context.getDrawable(R.drawable.bookmark_border)
            imageButton.setImageDrawable(drawable)
        }
    } else {
        val drawable = imageButton.context.getDrawable(R.drawable.bookmark_border)
        imageButton.setImageDrawable(drawable)
    }
}


@BindingAdapter("isWordExplicit")
fun isWordExplicit(textView: TextView, derogatory: Boolean?) {
    derogatory?.let { isExplicit ->
        if (isExplicit) {
            textView.visibility = View.VISIBLE
        }
    }
}


@BindingAdapter("synonymsText")
fun getSynonymsText(textView: TextView, synonyms: String?) {
    val regex = Pattern.compile("[\\[\\]]").toRegex()
    val formattedSynonyms = synonyms?.replace(regex, "")
    textView.text = formattedSynonyms
}


@BindingAdapter("comment")
fun getCommentText(textView: TextView, commentId: String?) {
    if (commentId != null) {
        firebaseFirestore.collection(COMMENTS).document(commentId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val commentText = documentSnapshot["commentContent"].toString()
                textView.text = "\" $commentText \""
            }
    }
}


@BindingAdapter("getChips")
fun getListChips(chipGroup: ChipGroup, sentences: List<String>?) {
    sentences?.forEach { sentence ->
        val chip = Chip(chipGroup.context).apply {
            text = sentence
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                chipGroup.removeView(this)
            }
        }
        chipGroup.addView(chip)
    }
}


@BindingAdapter("chooseWordExplicitStatus")
fun getWordExplicitStatus(materialButtonToggleGroup: MaterialButtonToggleGroup, derogatory: Boolean) {
    if (derogatory) {
        materialButtonToggleGroup.check(R.id.derogatory_yes_selection)
    }
}


@BindingAdapter("commentActionVisibility")
fun hideUnnecessaryCommentActions(imageView: ImageView, comment: Comment?) {
    if (comment != null) {
        if (comment.authorId != firebaseAuth.uid) {
            imageView.visibility = View.GONE
        }
    }
}


@BindingAdapter("blurBGImage")
fun getBGImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context.applicationContext)
        .load(url)
        .placeholder(R.color.primaryColor)
        .transform(BlurTransformation(2, 3))
        .into(imageView)
}


@BindingAdapter("gameScoreRemark")
fun getGameScoreRemark(textView: TextView, score: Int?) {
    val arrayOfRemarks = textView.context.resources.getStringArray(R.array.gameScoreRemarks)
    score?.let {
        textView.text = textView.context.getString(R.string.score_to_ace, score)
        if (score == 10) {
            textView.text = arrayOfRemarks.random()
        }
    }
}


@BindingAdapter("longDate")
fun convertLongDate(textView: TextView, date: Long?) {
    val dateRef = Date(date!!)
    val formattedDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(dateRef)
    textView.text = formattedDate
}


@BindingAdapter("eventTime")
fun eventStampDate(textView: TextView, date: Long?) {
    val context = textView.context.applicationContext
    val dateThen = DateTime(date).toLocalDateTime()
    val dateNow = DateTime(System.currentTimeMillis()).toLocalDateTime()

    val yearDifference = dateNow.year - dateThen.year
    var monthDifference = dateNow.monthOfYear - dateThen.monthOfYear
    val dayDifference = dateNow.dayOfMonth - dateThen.dayOfMonth
    val weeksInDay = (dayDifference / Calendar.DAY_OF_WEEK)
    val hourDiffference = dateNow.hourOfDay - dateThen.hourOfDay
    val minuteDifference = dateNow.minuteOfHour - dateThen.minuteOfHour

    /*
    * Condition if the two events occurred in different years and in
    * a span of 12 months*/
    if ((dateNow.year != dateThen.year) && (dateNow.monthOfYear >= MONTHS_IN_A_YEAR)) {
        if ((yearDifference) == 1) {
            textView.text = context.getString(R.string.a_year_ago)
        }
        else {
            textView.text = context.getString(R.string.years_ago, yearDifference)
        }
    } else {
        // Condition if the two events occurred in the same year

        if (dateNow.monthOfYear != dateThen.monthOfYear) {

            /*
            * Having considered a dreadful anomaly in Month difference; in the sense that,
            * months are usually aligned in the range 1 to 12 and each 1 to 12 number is
            * assigned to a year, meaning checking the difference in months in different years could
            * create an anomaly and give a negative value. Hence, monthDifference coud be
            * referred to as the below*/
            if (dateNow.year != dateThen.year)
                monthDifference = dateNow.monthOfYear + (MONTHS_IN_A_YEAR - dateThen.monthOfYear)

            if (monthDifference == 1) {
                textView.text = context.getString(R.string.a_month_ago)
            }
            else {
                textView.text = context.getString(R.string.months_ago, monthDifference)
            }
        } else {
            // Condition if the two events occurred in the same month
            if (dayDifference == 0) {
                // Conditon if the two events occured in the same day
                if (hourDiffference == 0) {
                    // Conditon if the two events occured in the same hour
                    if (minuteDifference in 0..1) {
                        // Conditon if the two events occured in the same minute
                        textView.text = context.getString(R.string.a_few_seconds_ago)
                    }
                    else if (minuteDifference in 2..59) {
                        textView.text = context.getString(R.string.minutes_ago, minuteDifference)
                    }

                }
                else if (hourDiffference in 1..23) {
                    if (hourDiffference == 1)
                        textView.text = context.getString(R.string.an_hour_ago)
                    else
                        textView.text = context.getString(R.string.hours_ago, hourDiffference)
                } else if (hourDiffference < 0) {
                    /**
                     * To get the accurate [hourDifference], 24 hours is added because the hours are negative value
                     * The hours are negative because of the 24-hour time format.
                     */
                    textView.text = context.getString(R.string.hours_ago, hourDiffference + 24)
                }
            }
            else if (dayDifference == 1 && hourDiffference > 0) {
                textView.text = context.getString(R.string.a_day_ago)
            }
            else if (dayDifference in 2..6) {
                textView.text = context.getString(R.string.days_ago, dayDifference)
            }
            else {
                if (weeksInDay == 1) {
                    textView.text = context.getString(R.string.a_week_ago)
                }
                else if (weeksInDay in 2..5) {
                    textView.text = context.getString(R.string.weeks_ago, weeksInDay)
                }
            }
        }

    }

}


@SuppressLint("SetTextI18n")
@BindingAdapter("commentTime")
fun commentDate(textView: TextView, date: Long?) {
    val context = textView.context.applicationContext
    val dateThen = DateTime(date).toLocalDateTime()
    val dateNow = DateTime(System.currentTimeMillis()).toLocalDateTime()

    /*
    * Condition if the two events occurred in different years and in
    * a span of 12 months*/
    if ((dateNow.year != dateThen.year) && (dateNow.monthOfYear >= MONTHS_IN_A_YEAR)) {
        val yearDifference = dateNow.year - dateThen.year
        textView.text = "${yearDifference}y"
    } else {
        // Condition if the two events occurred in the same year

        if (dateNow.monthOfYear != dateThen.monthOfYear) {
            var monthDifference = dateNow.monthOfYear - dateThen.monthOfYear

            /*
            * Having considered a dreadful anomaly in Month difference; in the sense that,
            * months are usually aligned in the range 1 to 12 and each 1 to 12 number is
            * assigned to a year, meaning checking the difference in months in different years could
            * create an anomaly and give a negative value. Hence, monthDifference coud be
            * referred to as the below*/
            if (dateNow.year != dateThen.year)
                monthDifference = dateNow.monthOfYear + (MONTHS_IN_A_YEAR - dateThen.monthOfYear)

            if (monthDifference > 0) {
                textView.text = "${monthDifference}m"
            }
        } else {
            // Condition if the two events occurred in the same month
            val dayDifference = dateNow.dayOfMonth - dateThen.dayOfMonth
            val hourDiffference = dateNow.hourOfDay - dateThen.hourOfDay
            if (dayDifference in 0..1) {
                // Conditon if the two events occured in the same day
                if (hourDiffference == 0) {
                    // Conditon if the two events occured in the same hour
                    val minuteDifference = dateNow.minuteOfHour - dateThen.minuteOfHour
                    if (minuteDifference in 0..1) {
                        val secondsDifference = dateNow.secondOfMinute - dateThen.secondOfMinute
                        if (secondsDifference > 0) {
                            textView.text = "${secondsDifference}s"
                        } else {
                            textView.text = "${secondsDifference+60}s"
                        }
                    }
                    else {
                        textView.text = "${minuteDifference}m"
                    }

                }
                else if (hourDiffference in 1..23) {
                    textView.text = "${hourDiffference}h"
                } else if (hourDiffference < 0) {
                    textView.text = "${hourDiffference + 24}h"
                }
            }
            else if (dayDifference in 2..6) {
                textView.text = "${dayDifference}d"
            }
            else {
                val weeksInDay = (dayDifference / Calendar.DAY_OF_WEEK)
                textView.text = "${weeksInDay}w"
            }
        }

    }

}


//@BindingAdapter("rankPlaceholder")
//fun getRankTextForHomeScreen(textView: TextView, rank: Long?) {
//    val context = textView.context.applicationContext
//    when (rank) {
//        1L -> textView.text = context.getString(R.string.rank_reward_placeholder, Rank.RANK_1)
//        2L -> textView.text = context.getString(R.string.rank_reward_placeholder, Rank.RANK_2)
//        3L -> textView.text = context.getString(R.string.rank_reward_placeholder, Rank.RANK_3)
//    }
//}


@BindingAdapter("rank")
fun getRankForModal(textView: TextView, rank: Long?) {
    when (rank) {
        1L -> textView.text = Rank.RANK_1
        2L -> textView.text = Rank.RANK_2
        3L -> textView.text = Rank.RANK_3
    }
}


@BindingAdapter("rankImage")
fun getRankImage(imageView: ImageView, rank: Long?) {
    val context = imageView.context
    when (rank) {
        1L -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_rank_oga))
        2L -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_rank_contributor))
        3L -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_rank_jjc))
    }
}


@BindingAdapter("badgeImage")
fun getBadgeImage(imageView: ImageView, badge: String) {
    val context = imageView.context
    when(badge) {
        BADGE_1 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_1))
        BADGE_2 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_2))
        BADGE_5 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_5))
        BADGE_10 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_10))
        BADGE_20 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_20))
        BADGE_25 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_25))
        BADGE_50 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_50))
        BADGE_75 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_75))
        BADGE_100 -> imageView.setImageDrawable(context.getDrawable(R.drawable.ic_badge_100))
    }
}


@BindingAdapter("badgeLayout")
fun greyOutInactiveBadges(constraintLayout: ConstraintLayout, badges: List<String>?) {
    constraintLayout.forEach {badgeCard->
        val badgeCardView = badgeCard as? CardView
        badges?.forEach { badge->
            if (badgeCardView != null) {
                if (badge == badgeCardView[0].tag) {
                    badgeCard[0].alpha = 1f
                }
            }
        }
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("partOfSpeechText")
fun getPartOfSpeechText(textView: TextView, word: Word?) {
    if (word != null) {
        if (word.partOfSpeech.equals("Noun", true)) {
            textView.text = "${word.partOfSpeech} (plural:  ${word.plural})"
        } else {
            textView.text = word.partOfSpeech
        }
    }
}


@BindingAdapter("certifiedWordCheck")
fun getCertificationMarkVisibility(imageView: ImageView, certified: Boolean?) {
    if (certified != null) {
        if (certified) imageView.visibility = View.VISIBLE
        else imageView.visibility = View.GONE
    } else {
        imageView.visibility = View.GONE
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("sentencesText")
fun getSentencesText(textView: TextView, sentences: List<String>?) {
    sentences?.forEach { sentence ->
        textView.text = textView.text.toString() + "- $sentence\n"
    }
}


@BindingAdapter("wodImage")
fun getWODImage(imageView: ImageView, url: String?) {
    // ROUND IMAGE CORNERS
    Glide.with(imageView.context.applicationContext)
        .load(url)
        .placeholder(R.drawable.no_internet_image)
        .apply(RequestOptions().transform(RoundedCorners(128)))
        .into(imageView)
}


@BindingAdapter("wordImage")
fun getWordImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context.applicationContext)
        .load(url)
        .placeholder(R.drawable.no_internet_image)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
        .into(imageView)
}


@BindingAdapter("eventstampComments")
fun getEventStampComments(recyclerView: RecyclerView, comments: List<Comment>?) {
    if (comments != null) {
        val adapter = recyclerView.adapter as EventstampCommentsAdapter
        adapter.submitList(comments)
    }
    recyclerView.addItemDecoration(DividerItemDecoration(
        recyclerView.context,
        DividerItemDecoration.VERTICAL
    ))
}


@BindingAdapter("questionProgress")
fun setQuestionProgress(progressBar: ProgressBar, score: Int?) {
    val scoreProgress = score!! * 1000

    progressBar.max = 10000
    ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, scoreProgress).apply {
        duration = 1000
        start()
    }
}


@BindingAdapter("hideScrim")
fun hideLoginScrim(relativeLayout: RelativeLayout, loginStatus: Int) {
    if (loginStatus == Connection.LOADING) {
        relativeLayout.visibility = View.VISIBLE
    } else {
        relativeLayout.visibility = View.GONE
    }
}


@BindingAdapter("animateLogo")
fun animateScrimLogo(imageView: ImageView, loginStatus: Int) {
    val animation = AnimationUtils.loadAnimation(imageView.context, R.anim.spinner_anim)
    imageView.startAnimation(animation)
}



@BindingAdapter("validateUsernameField")
fun validateUsernameField(textInputLayout: TextInputLayout, status: Int) {
    /**
     * This method checks whether the text inserted is an actual username in server database.
     * If the username is found in the server (if connection succeeds) It means username is taken
     * Otherwise it means username is available*/
    when (status) {
        Connection.SUCCESS -> textInputLayout.error = textInputLayout.context.getString(R.string.username_taken)
        Connection.LOADING -> textInputLayout.error = textInputLayout.context.getString(R.string.checking)
        Connection.FAILED -> textInputLayout.isErrorEnabled = false
    }
}


@BindingAdapter("validateEmailField")
fun validateEmailField(textInputLayout: TextInputLayout, status: Int) {
    /**
     * This method checks whether the text inserted is an actual username in server database.
     * If the username is found in the server (if connection succeeds) It means username is taken
     * Otherwise it means username is available*/
    when (status) {
        Connection.SUCCESS -> textInputLayout.error = textInputLayout.context.getString(R.string.email_taken)
        Connection.LOADING -> textInputLayout.error = textInputLayout.context.getString(R.string.checking)
        Connection.FAILED -> textInputLayout.isErrorEnabled = false
    }
}