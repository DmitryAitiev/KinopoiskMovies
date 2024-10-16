package com.example.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.movies.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        Glide.with(this)
            .load(movie.poster.url)
            .into(binding.imageViewPoster)
        binding.textViewTitle.text = movie.name
        binding.textViewYear.text = movie.year.toString()
        binding.textViewDescriptin.text = movie.description
    }
    companion object {
        private val EXTRA_MOVIE = "movie"
        fun newIntent(context: Context, movie: Movie): Intent{
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }
}