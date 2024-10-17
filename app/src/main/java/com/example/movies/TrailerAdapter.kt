package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrailerAdapter: RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    private var trailers: List<Trailer> = mutableListOf()
    private lateinit var onTrailerClickListener: OnTrailerClickListener
    fun setTrailers(trailers: List<Trailer>) {
        this.trailers = trailers
        notifyDataSetChanged()
    }
    fun setOnTrailerClickListener(onTrailerClickListener: OnTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_button, parent
            ,false)
        return TrailerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = trailers.get(position)
        holder.textViewTrailer.setText(trailer.name)
        holder.itemView.setOnClickListener {
            onTrailerClickListener.onTrailerClick(trailer)
        }
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    interface OnTrailerClickListener{
        fun onTrailerClick(trailer: Trailer)
    }
    class TrailerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewTrailer: TextView = itemView.findViewById(R.id.trailer_name)
    }
}