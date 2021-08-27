package com.example.mycinemaapp.model.paging

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycinemaapp.R
import com.google.android.material.imageview.ShapeableImageView

class MoviesAdapter : PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindMovie(movie)
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView = itemView.description
        private val movieRating: AppCompatRatingBar = itemView.movie_rating
        private val image: ShapeableImageView = itemView.image_preview
        fun bindMovie(content: Movie) {
            description.text = content.title
            movieRating.rating = content.rating
            Picasso.get()
                .load(content.posterPath)
                .into(image)
        }
    }
}