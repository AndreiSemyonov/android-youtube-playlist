package com.andreisemyonov.androidyoutube.api

import com.andreisemyonov.androidyoutube.model.BaseDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeAPI {

    @GET("search/")

    fun getAnswers(
        @Query("key") key: String?,
        @Query("part") part: String?,
        @Query("search") search: String?,
        @Query("type") type: String?,
        @Query("maxResults") maxResult: Int
    ): Call<BaseDataClass?>?
}