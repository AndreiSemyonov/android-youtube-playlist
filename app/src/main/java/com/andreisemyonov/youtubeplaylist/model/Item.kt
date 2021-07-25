package com.andreisemyonov.youtubeplaylist.model

import com.google.gson.annotations.SerializedName

data class Item(@SerializedName ("snippet") val snippet: Snippet, var isSelected: Boolean = false)