package com.example.movies

import com.google.gson.annotations.SerializedName

data class TrailersList(@SerializedName("trailers") val trailers: List<Trailer> = mutableListOf()) {
}