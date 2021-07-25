package com.andreisemyonov.youtubeplaylist.model

import com.google.gson.annotations.SerializedName

data class BaseDataClass(@SerializedName ("items") val items: MutableList<Item>)