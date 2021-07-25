package com.andreisemyonov.youtubeplaylist.db

import io.reactivex.Flowable

class PlayListRepository(val itemDAO: ItemDAO) {

    fun getPlaylist(): Flowable<List<Video>> {
        return itemDAO.getAll()
    }

    fun insertPlaylist(videos: List<Video>){
        itemDAO.insert(videos)
    }

    fun deleteVideo(video: Video){
        itemDAO.delete(video)
    }
}