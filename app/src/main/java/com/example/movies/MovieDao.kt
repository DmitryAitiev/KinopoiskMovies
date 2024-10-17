package com.example.movies

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable

@Dao
interface MovieDao {
    @Query("Select * from favourite_movies")
    fun getAllFavouriteMovies(): LiveData<List<Movie>>
    @Query("Select* from favourite_movies where id = :movieId")
    fun getFavouriteMovie(movieId: Int): LiveData<Movie>
    @Insert
    fun insertMovie(movie: Movie): Completable
    @Query("Delete from favourite_movies where id = :movieId")
    fun removeMovie(movieId: Int): Completable
}