package com.salsabila.idn.samaquran.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter internal constructor(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = HomeFragment()
        val bundle = Bundle()
        when (position) {
            0 -> {
                bundle.putString(HomeFragment.ARG_TAB, HomeFragment.TAB_SURAT)
            }
            1 -> {
                bundle.putString(HomeFragment.ARG_TAB, HomeFragment.TAB_TAFSIR)
            }
            else -> {
                bundle.putString(HomeFragment.ARG_TAB, HomeFragment.TAB_BOOKMARK)
            }
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}