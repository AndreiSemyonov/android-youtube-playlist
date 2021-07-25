package com.andreisemyonov.youtubeplaylist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreisemyonov.youtubeplaylist.fragments.PlaylistFragment
import com.andreisemyonov.youtubeplaylist.fragments.YouTubeFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return YouTubeFragment()
        }
        return PlaylistFragment()
    }
}