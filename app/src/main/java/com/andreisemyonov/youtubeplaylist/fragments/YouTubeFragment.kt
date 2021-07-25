package com.andreisemyonov.youtubeplaylist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreisemyonov.youtubeplaylist.R
import com.andreisemyonov.youtubeplaylist.api.YouTubeAPI
import com.andreisemyonov.youtubeplaylist.databinding.FragmentYoutubeBinding
import com.andreisemyonov.youtubeplaylist.db.AppDatabase
import com.andreisemyonov.youtubeplaylist.db.PlayListRepository
import com.andreisemyonov.youtubeplaylist.db.Video
import com.andreisemyonov.youtubeplaylist.viewholders.YouTubeFragmentViewHolder
import com.andreisemyonov.youtubeplaylist.model.Item
import com.andreisemyonov.youtubeplaylist.model.BaseDataClass
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class YouTubeFragment: Fragment() {

    private var _binding: FragmentYoutubeBinding? = null
    private val binding get() = _binding!!

    private val key = "AIzaSyAzfjmrb2g26v6eesegW-1zP3nqxJ0eWHg"
    private val part = "snippet"

    private var compositeDisposable = CompositeDisposable()

    private var video: Video? = null
    private var videoList: MutableList<Item> = ArrayList()
    private var playlist: MutableList<Video> = ArrayList()
    private var playlistRepository: PlayListRepository? = null

    private var youTubeFragmentAdapter: RecyclerView.Adapter<YouTubeFragmentViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentYoutubeBinding.inflate(inflater, container, false)

        val database = AppDatabase.getInstance(requireActivity())
        playlistRepository = PlayListRepository(database.itemDao())

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        youTubeFragmentAdapter = object : RecyclerView.Adapter<YouTubeFragmentViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeFragmentViewHolder {
                val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.youtube_fragment_view_holder, parent, false)
                return YouTubeFragmentViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: YouTubeFragmentViewHolder, position: Int) {

                val item: Item = videoList[position]

                with(holder){

                    viewHolderBinding.title.text = videoList[position].snippet.title
                    viewHolderBinding.description.text = videoList[position].snippet.description

                    Glide.with(viewHolderBinding.imageView)
                        .load(videoList[position].snippet.thumbnails.high.url)
                        .centerCrop()
                        .into(viewHolderBinding.imageView)

                    viewHolderBinding.checkbox.setOnCheckedChangeListener(null)

                    viewHolderBinding.checkbox.isChecked = item.isSelected

                    viewHolderBinding.checkbox.setOnCheckedChangeListener { _, isChecked ->

                        item.isSelected = isChecked

                        if (isChecked) {

                            video = Video(videoList[position].snippet.thumbnails.high.url, videoList[position].snippet.title, videoList[position].snippet.description)

                            playlist.add(video!!)

                            binding.addButtom.visibility = View.VISIBLE

                        }

                        else {

                            video = Video(videoList[position].snippet.thumbnails.high.url, videoList[position].snippet.title, videoList[position].snippet.description)

                            playlist.remove(video)

                            if (playlist.isEmpty()){
                                binding.addButtom.visibility = View.GONE
                            }
                        }
                    }

                }
            }

            override fun getItemCount(): Int {
                return videoList.size
            }
        }

        loadingYouTubeVideos("")

        binding.recyclerView.adapter = youTubeFragmentAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                loadingYouTubeVideos(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        binding.addButtom.setOnClickListener{

            addVideosToPlaylist()
            uncheckAll()
            Toast.makeText(requireActivity(), "Selected video added to playlist", Toast.LENGTH_LONG).show()
            binding.addButtom.visibility = View.GONE
        }

        return binding.root
    }

    private fun loadingYouTubeVideos(query: String){

        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val youTubeAPI: YouTubeAPI = retrofit.create(YouTubeAPI::class.java)

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
                Toast.makeText(requireActivity(), "Network error: failed to load the videos.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addVideosToPlaylist() {
        compositeDisposable.add(Observable.create<Any> { emitter -> playlistRepository?.insertPlaylist(playlist)
            emitter.onNext(playlist.clear())
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun uncheckAll() {
        for (item in videoList) {
            item.isSelected = false
            youTubeFragmentAdapter?.notifyDataSetChanged()
        }
    }
}