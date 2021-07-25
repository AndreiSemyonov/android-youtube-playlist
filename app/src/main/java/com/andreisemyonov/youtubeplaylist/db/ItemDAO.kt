package com.andreisemyonov.youtubeplaylist.db

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface ItemDAO {

    @Query("SELECT * FROM videos")
    fun getAll(): Flowable<List<Video>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(video: List<Video>)

    @Delete
    fun delete(video: Video)
}