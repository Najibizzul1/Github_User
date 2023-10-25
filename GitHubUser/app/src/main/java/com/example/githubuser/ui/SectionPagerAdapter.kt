@file:Suppress("DEPRECATION")

package com.example.githubuser.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuser.R

class SectionPagerAdapter(private val context: Context, fragmentManager: FragmentManager, bundle: Bundle) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentArguments: Bundle = bundle

    @StringRes
    private val tabTitles = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = tabTitles.size

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment()
            1 -> FollowingFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }.apply {
            arguments = fragmentArguments
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }
}
