package com.example.movies

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie?token=9P80DWH-9HV4BQJ-QVK0V6G-Z7VB0TY&field=rating.kp=6.5-10&sortField=votes.kp&sortType=-1&type=movie&type=cartoon&sortField=rating.kp&sortType=-1&limit=30#")
    fun loadMovies(@Query("page")page: Int): Single<MovieResponse>
}