package com.andreisemyonov.androidyoutube.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreisemyonov.androidyoutube.R
import com.andreisemyonov.androidyoutube.api.Retrofit
import com.andreisemyonov.androidyoutube.api.YouTubeAPI
import com.andreisemyonov.androidyoutube.holder.ViewHolder
import com.andreisemyonov.androidyoutube.model.BaseDataClass
import com.andreisemyonov.androidyoutube.model.Item
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouTubeFragment: Fragment() {

    private val key = "AIzaSyBhU3HAUwva6OyZz5jRmiQxgkinFFRJW7Q"
    private val part = "snippet"

    private var videoList: List<Item> = ArrayList()

    private var youTubeFragmentAdapter: RecyclerView.Adapter<ViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_you_tube, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(activity)

        youTubeFragmentAdapter = object : RecyclerView.Adapter<ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.view_holder, parent, false)

                return ViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {

                with(holder){

                    binding.title.text = videoList[position].snippet.title
                    binding.description.text = videoList[position].snippet.description
                    binding.checkbox

                    Glide.with(binding.imageView)
                            .load(videoList[position].snippet.thumbnails.high.url)
                            .centerCrop()
                            .into(binding.imageView)
                }

            }

            override fun getItemCount(): Int {
                return videoList.size
            }
        }

        loadingYouTubeVideos("")

        recyclerView.adapter = youTubeFragmentAdapter

        return view
    }

    private fun loadingYouTubeVideos(query: String){

        val youTubeAPI: YouTubeAPI = Retrofit.getClient()!!.create(YouTubeAPI::class.java)

        val call: Call<BaseDataClass?>? = youTubeAPI.getAnswers(key, part, query, "video", 20)

        call?.enqueue(object : Callback<BaseDataClass?> {
            override fun onResponse(call: Call<BaseDataClass?>, response: Response<BaseDataClass?>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        videoList = response.body()!!.items
                        youTubeFragmentAdapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<BaseDataClass?>, t: Throwable) {

            }
        })
    }
}