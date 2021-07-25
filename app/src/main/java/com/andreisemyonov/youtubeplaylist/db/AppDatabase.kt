package com.andreisemyonov.youtubeplaylist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Video::class)], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDAO

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, "Database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}
