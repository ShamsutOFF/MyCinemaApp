package com.example.mycinemaapp.model.items

import android.widget.Toast
import coil.load
import com.example.mycinemaapp.R
import com.example.mycinemaapp.model.entitys.MovieEntity
import com.example.mycinemaapp.view.HomeFragment
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_movie.*

private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class MovieItem(private val movie: MovieEntity) : Item() {

    override fun getLayout() = R.layout.item_movie

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = movie.title
        viewHolder.image_preview.load("$BASE_POSTERS_PATH${movie.poster_path}")
        viewHolder.itemView.setOnClickListener {
            Toast.makeText(viewHolder.itemView.context,movie.title,Toast.LENGTH_SHORT).show()
            HomeFragment().onItemClick(movie)
        }
    }
}