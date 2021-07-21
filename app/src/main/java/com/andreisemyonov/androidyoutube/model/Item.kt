package com.andreisemyonov.androidyoutube.model

import com.google.gson.annotations.SerializedName

data class Item(@SerializedName ("snippet") val snippet: Snippet)