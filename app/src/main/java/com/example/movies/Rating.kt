package com.example.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(@SerializedName("kp") val kp: Double): Serializable {
}