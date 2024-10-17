package com.example.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.databinding.ActivityFavouriteMoviesBinding

class FavouriteMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteMoviesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavouriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val moviesAdapter = MoviesAdapter()
        binding.main.layoutManager = GridLayoutManager(this, 2)
        binding.main.adapter = moviesAdapter
        moviesAdapter.setOnClickListener(object : MoviesAdapter.OnClick {
            override fun onMovieClick(movie: Movie) {
                val intent = MovieDetailActivity.newIntent(this@FavouriteMoviesActivity,
                    movie)
                startActivity(intent)
            }
        })

        val viewModel = ViewModelProvider(this).get(
            FavouriteMoviesViewModel::class.java
        )
        viewModel.getMovies().observe(this){
            moviesAdapter.setMovies(it)
        }
    }
    companion object{
        fun newIntent(context: Context): Intent {
        return Intent(context, FavouriteMoviesActivity::class.java)
    }
    }
}