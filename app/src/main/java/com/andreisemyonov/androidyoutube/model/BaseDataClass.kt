package com.andreisemyonov.androidyoutube.model

import com.google.gson.annotations.SerializedName

data class BaseDataClass(@SerializedName ("items") val items: List<Item>)