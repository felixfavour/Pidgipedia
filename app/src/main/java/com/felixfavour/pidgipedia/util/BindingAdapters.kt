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
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jp.wasabeef.glide.transformations.BlurTransformation
import org.joda.time.DateTime
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

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
                                    textView.text = textView.context.applicationContext.getString(
                                        R.string.comment_response_placeholder,
                                        primaryUser,
                                        secondaryUser
                                    )
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
    }
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

    /*
    * Condition if the two events occurred in different years and in
    * a span of 12 months*/
    if ((dateNow.year != dateThen.year) && (dateNow.monthOfYear >= MONTHS_IN_A_YEAR)) {
        val yearDifference = dateNow.year - dateThen.year
        if ((yearDifference) == 1) {
            textView.text = context.getString(R.string.a_year_ago)
        }
        else {
            textView.text = context.getString(R.string.years_ago, yearDifference)
        }
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

            if (monthDifference == 1) {
                textView.text = context.getString(R.string.a_month_ago)
            }
            else {
                textView.text = context.getString(R.string.months_ago, monthDifference)
            }
        } else {
            // Condition if the two events occurred in the same month
            val dayDifference = dateNow.dayOfMonth - dateThen.dayOfMonth
            if (dayDifference in 0..1) {
                // Conditon if the two events occured in the same day
                val hourDiffference = dateNow.hourOfDay - dateThen.hourOfDay
                if (hourDiffference == 0) {
                    // Conditon if the two events occured in the same hour
                    val minuteDifference = dateNow.minuteOfHour - dateThen.minuteOfHour
                    if (minuteDifference in 0..1) {
                        // Conditon if the two events occured in the same minute
                        textView.text = context.getString(R.string.a_few_seconds_ago)
                    }
                    else if (minuteDifference in 2..59) {
                        textView.text = context.getString(R.string.minutes_ago, minuteDifference)
                    }
                    else if (minuteDifference < 0) {
                        /**
                         * To get the accurate [minuteDifference], 24 hours is added because the minutes are negative value
                         * The minutes are negative because of the 24-hour time format.
                         */
                        textView.text = context.getString(R.string.minutes_ago, minuteDifference + 60)
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
            else if (dayDifference == 1) {
                textView.text = context.getString(R.string.a_day_ago)
            }
            else if (dayDifference == Calendar.DAY_OF_WEEK) {
                textView.text = context.getString(R.string.a_week_ago)
            }
            else if (dayDifference in 2..6) {
                textView.text = context.getString(R.string.days_ago, dayDifference)
            }
            else {
                val weeksInDay = dayDifference % Calendar.DAY_OF_WEEK
                textView.text = context.getString(R.string.weeks_ago, weeksInDay)
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
fun getPartOfSpeechText(textView: TextView, string: String?) {
    if (string.equals("noun", true)) {
        textView.text = "$string (plural: ${string}s)"
    }
    else textView.text = "$string"
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