package com.andreisemyonov.youtubeplaylist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreisemyonov.youtubeplaylist.R
import com.andreisemyonov.youtubeplaylist.databinding.FragmentPlaylistBinding
import com.andreisemyonov.youtubeplaylist.db.AppDatabase
import com.andreisemyonov.youtubeplaylist.db.PlayListRepository
import com.andreisemyonov.youtubeplaylist.db.Video
import com.andreisemyonov.youtubeplaylist.viewholders.PlaylistFragmentViewHolder
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable

class PlaylistFragment: Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    private var compositeDisposable = CompositeDisposable()

    private var video: Video? = null
    private var playlist: MutableList<Video> = ArrayList()
    private var playlistRepository: PlayListRepository? = null

    private var playlistFragmentAdapter: RecyclerView.Adapter<PlaylistFragmentViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)

        val database = AppDatabase.getInstance(requireActivity())
        playlistRepository = PlayListRepository(database.itemDao())

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        playlistFragmentAdapter = object : RecyclerView.Adapter<PlaylistFragmentViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistFragmentViewHolder {
                val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.playlist_fragment_view_holder, parent, false)
                return PlaylistFragmentViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: PlaylistFragmentViewHolder, position: Int) {

                with(holder){
                    viewHolderBinding.title.text = playlist[position].title
                    viewHolderBinding.description.text = playlist[position].description

                    Glide.with(viewHolderBinding.imageView)
                        .load(playlist[position].url)
                        .centerCrop()
                        .into(viewHolderBinding.imageView)

                    viewHolderBinding.contentView.setOnClickListener{
                        Toast.makeText(requireActivity(), playlist[position].title, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun getItemCount(): Int {
                return playlist.size
            }
        }

        binding.recyclerView.adapter = playlistFragmentAdapter

        return binding.root
    }
}