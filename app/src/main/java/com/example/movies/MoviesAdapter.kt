package com.example.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.ActivityMainBinding

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private var movies:List<Movie> = mutableListOf()
    private lateinit var onReachEndListener: OnReachEndListener
    private lateinit var onClick: OnClick

    fun setOnReachEndListener(onReachEndListener: OnReachEndListener){
        this.onReachEndListener = onReachEndListener
    }

    fun setOnClickListener(onClick: OnClick) {
        this.onClick = onClick
    }

    fun setMovies(movies: List<Movie>){
        this.movies = movies
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent,
            false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d("MoviesAdapter", "onBindViewHolder" + position)
        val movie = movies.get(position)
        Glide.with(holder.itemView)
            .load(movie.poster.url)
            .into(holder.imageViewPoster)
        val rating = movie.rating.kp
        val backgroundId = if(rating > 7) R.drawable.circle_green
        else if (rating > 5) R.drawable.circle_orange
        else R.drawable.circle_red
        val drawable = ContextCompat.getDrawable(holder.itemView.context, backgroundId)
        holder.textViewRating.setBackground(drawable)
        holder.textViewRating.setText(movie.rating.kp.toString().substring(0, 3))

        if (position >= movies.size - 15)
            onReachEndListener.onReachEnd()
        holder.itemView.setOnClickListener {
            onClick.onMovieClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    interface OnReachEndListener {
        fun onReachEnd(): Unit
    }

    interface OnClick {
        fun onMovieClick(movie: Movie): Unit
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)

    }
}