package com.andreisemyonov.androidyoutube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreisemyonov.androidyoutube.adapter.ViewPagerAdapter
import com.andreisemyonov.androidyoutube.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    val fragmentTitleArray = arrayOf("YouTube", "Playlist")

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragmentTitleArray[position]
        }.attach()
    }
}