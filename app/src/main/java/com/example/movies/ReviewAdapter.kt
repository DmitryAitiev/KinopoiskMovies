package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var reviews: List<Review> = mutableListOf()
    fun setReviews(reviews: List<Review>) {
        this.reviews = reviews
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item,
            parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews.get(position)
        val rating = review.type
        val context = holder.itemView.context

        val backgroundColor = if(rating.equals("Позитивный")) ContextCompat.getColor(context, android.R.color.holo_green_light)
        else if (rating.equals("Нейтральный")) ContextCompat.getColor(context, android.R.color.holo_orange_light)
        else ContextCompat.getColor(context, android.R.color.holo_red_light)
        holder.llr.setBackgroundColor(backgroundColor)
        holder.llr.setBackgroundColor(backgroundColor)
        holder.textViewAuthor.setText(review.author)
        holder.textViewReview.setText(review.review)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
    class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        val textViewReview: TextView = itemView.findViewById(R.id.textViewReview)
        val llr: LinearLayout = itemView.findViewById(R.id.linearLayoutReview)
    }
}