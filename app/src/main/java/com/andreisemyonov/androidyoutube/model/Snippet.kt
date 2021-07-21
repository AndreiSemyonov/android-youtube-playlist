package com.andreisemyonov.androidyoutube.model

import com.google.gson.annotations.SerializedName

data class Snippet(@SerializedName ("title") val title: String,
                   @SerializedName ("description") val description: String,
                   @SerializedName ("thumbnails") val thumbnails: Thumbnails,
                   @SerializedName ("channelTitle") val channelTitle: String)