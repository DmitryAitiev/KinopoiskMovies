package com.example.movies

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.ActivityMovieDetailBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var recycleViewTrailers: RecyclerView
    private lateinit var trailersAdapter: TrailerAdapter
    private lateinit var recycleViewReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private val TAG = "MovieDetailActivityTEST"
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
        recycleViewTrailers = binding.recycleViewTrailers
        trailersAdapter = TrailerAdapter()
        recycleViewTrailers.adapter = trailersAdapter
        recycleViewTrailers.layoutManager = LinearLayoutManager(this)
        recycleViewReviews = binding.recycleViewReviews
        reviewAdapter = ReviewAdapter()
        recycleViewReviews.adapter = reviewAdapter
        recycleViewReviews.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        Glide.with(this)
            .load(movie.poster.url)
            .into(binding.imageViewPoster)
        binding.textViewTitle.text = movie.name
        binding.textViewYear.text = movie.year.toString()
        binding.textViewDescriptin.text = movie.description
        viewModel.loadTrailers(movie.id)
        viewModel.loadReview(movie.id)
        viewModel.getTrailers().observe(this) {
            trailersAdapter.setTrailers(it)
        }
        viewModel.getReviews().observe(this) {
            reviewAdapter.setReviews(it)
        }
        trailersAdapter.setOnTrailerClickListener(object : TrailerAdapter.OnTrailerClickListener {
            override fun onTrailerClick(trailer: Trailer) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(trailer.url))
                startActivity(intent)
            }
        })
        val movieDao = MovieDatabase.getInstanse(application).movieDao()
        val starOff = ContextCompat.getDrawable(this, android.R.drawable.star_big_off)
        val starOn = ContextCompat.getDrawable(this, android.R.drawable.star_big_on)
        viewModel.getFavouriteMovie(movie.id).observe(this) {
            if (it == null) {
                binding.imageViewStar.setImageDrawable(starOff)
                binding.imageViewStar.setOnClickListener {
                    viewModel.insertMovie(movie)
                }
            }
            else {
                binding.imageViewStar.setImageDrawable(starOn)
                binding.imageViewStar.setOnClickListener {
                    viewModel.removeMovie(movie.id)
                }
            }
        }
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