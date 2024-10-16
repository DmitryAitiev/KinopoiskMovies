package com.example.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.databinding.MovieItemBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerViewMovies = binding.recycleViewMovies
        moviesAdapter = MoviesAdapter()
        recyclerViewMovies.adapter = moviesAdapter
        recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getMovies().observe(this) {
            moviesAdapter.setMovies(it)
        }
        viewModel.getisLoading().observe(this) {
            if (it) {
                binding.progressBarLoading.visibility = View.VISIBLE
            }
            else
                binding.progressBarLoading.visibility = View.GONE
        }
        moviesAdapter.setOnReachEndListener(object : MoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        })
        moviesAdapter.setOnClickListener(object : MoviesAdapter.OnClick {
            override fun onMovieClick(movie: Movie) {
                val intent = MovieDetailActivity.newIntent(this@MainActivity, movie)
                startActivity(intent)
            }
        })
    }
}