package com.andreisemyonov.youtubeplaylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreisemyonov.youtubeplaylist.databinding.ActivityMainBinding
import com.andreisemyonov.youtubeplaylist.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val fragmentTitleArray = arrayOf("YouTube", "Playlist")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position -> tab.text = fragmentTitleArray[position]
        }.attach()
    }
}