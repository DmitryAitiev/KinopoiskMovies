package com.example.movies

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("author") val author: String,
    @SerializedName("review") val review: String,
    @SerializedName("type") val type: String) {
}