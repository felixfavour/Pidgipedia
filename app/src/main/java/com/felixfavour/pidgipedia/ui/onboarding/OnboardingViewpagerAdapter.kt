package com.felixfavour.pidgipedia.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.felixfavour.pidgipedia.OnboardQuizFragment
import com.felixfavour.pidgipedia.OnboardRewardsFragment
import com.felixfavour.pidgipedia.ui.quiz.QuizFragment

class OnboardingViewpagerAdapter (private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val NUM_OF_PAGES = 3
    }

    override fun getItemCount(): Int {
        return NUM_OF_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardContributingFragment()
            1 -> OnboardQuizFragment()
            else -> OnboardRewardsFragment()
        }
    }
}