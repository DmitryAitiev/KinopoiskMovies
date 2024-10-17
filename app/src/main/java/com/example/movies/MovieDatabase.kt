package com.example.movies

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    companion object {
        private var instance: MovieDatabase? = null
        private val DB_NAME = "movie.db"
        fun getInstanse(context: Context): MovieDatabase {
            instance?.let { return it}
            val db =
                Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME).build()
            instance = db
            return db
        }
    }
    abstract fun movieDao(): MovieDao
}