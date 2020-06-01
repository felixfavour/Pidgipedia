package com.felixfavour.pidgipedia.util

import android.icu.text.DateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.ui.dictionary.WordListAdapter
import com.felixfavour.pidgipedia.ui.home.HomeRecyclerViewAdapter
import com.felixfavour.pidgipedia.ui.home.UnapprovedWordListAdapter
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

const val MONTHS_IN_A_YEAR = 12

@BindingAdapter("populateWordList")
fun populateWordList(recyclerView: RecyclerView, words: ArrayList<Word>?) {
    val adapter = recyclerView.adapter as WordListAdapter
    adapter.submitList(words)
}
@BindingAdapter("populateEventstampsList")
fun populateEventstampList(recyclerView: RecyclerView, eventstamps: ArrayList<Eventstamp>?) {
    val adapter = recyclerView.adapter as HomeRecyclerViewAdapter
    adapter.submitList(eventstamps)
}

@BindingAdapter("unapprovedWordsList")
fun unapprovedWordsList(recyclerView: RecyclerView, words: ArrayList<Word>?) {
    val adapter = recyclerView.adapter as UnapprovedWordListAdapter
    adapter.submitList(words)
}

@BindingAdapter("eventstampText")
fun eventstampText(textView: TextView, eventstamp: Eventstamp) {
    when {
        eventstamp.isWordComment -> {
            textView.text = textView.context.getString(
                R.string.word_comment_placeholder,
                eventstamp.humanEntity.toString()
            )
        }
        eventstamp.isCommentResponse -> {
            textView.text = textView.context.getString(
                R.string.comment_response_placeholder,
                eventstamp.humanEntity.toString())
        }
        eventstamp.isSuggested -> {
            textView.text = textView.context.getString(
                R.string.word_suggestion_placeholder,
                eventstamp.humanEntity.toString())
        }
        eventstamp.isApproved -> {
            textView.text = textView.context.getString(
                R.string.word_approval_placeholder,
                eventstamp.humanEntity.toString())
        }
    }
}
@BindingAdapter("eventTime")
fun eventStampDate(textView: TextView, date: Long?) {
    val context = textView.context
    val dateThen = DateTime(date).toLocalDateTime()
    val dateNow = DateTime(System.currentTimeMillis()).toLocalDateTime()

    /*
    * Condition if the two events occured in different years and in
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
            if (dayDifference <= 1) {
                // Conditon if the two events occured in the same day
                textView.text = context.getString(R.string.a_day_ago)
                val hourDiffference = dateNow.hourOfDay - dateThen.hourOfDay
                if (hourDiffference <= 1) {
                    // Conditon if the two events occured in the same hour
                    textView.text = context.getString(R.string.an_hour_ago)
                    val minuteDifference = dateNow.minuteOfHour - dateThen.minuteOfHour
                    if (minuteDifference <= 1) {
                        // Conditon if the two events occured in the same minute
                        textView.text = context.getString(R.string.a_few_seconds_ago)
                    }
                    else {
                        textView.text = context.getString(R.string.minutes_ago, minuteDifference)
                    }

                }
                else if (hourDiffference in 2..23) {
                    textView.text = context.getString(R.string.hours_ago, hourDiffference)
                }
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

@BindingAdapter("rankText")
fun getRankTextForHomeScreen(textView: TextView, rank: Int?) {
    val context = textView.context
    when (rank) {
        1 -> textView.text = context.getString(R.string.rank_reward_placeholder, Rank.RANK_1)
        2 -> textView.text = context.getString(R.string.rank_reward_placeholder, Rank.RANK_2)
        3 -> textView.text = context.getString(R.string.rank_reward_placeholder, Rank.RANK_3)
    }
}

@BindingAdapter("rank")
fun getRankForModal(textView: TextView, rank: Int?) {
    when (rank) {
        1 -> textView.text = Rank.RANK_1
        2 -> textView.text = Rank.RANK_2
        3 -> textView.text = Rank.RANK_3
    }
}