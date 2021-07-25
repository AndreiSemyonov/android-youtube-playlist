package com.andreisemyonov.youtubeplaylist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class Video (

    @PrimaryKey
    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String
)