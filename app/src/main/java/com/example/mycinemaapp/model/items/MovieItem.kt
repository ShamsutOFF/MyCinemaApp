package com.example.mycinemaapp.model.items

import coil.load
import com.example.mycinemaapp.R
import com.example.mycinemaapp.model.entitys.MovieEntity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_movie.*

private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class MovieItem(
    private val movie: MovieEntity,
    private val onClick: (movie: MovieEntity) -> Unit,
    private val character:String
) : Item() {

    override fun getLayout() = R.layout.item_movie

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        if (movie.title == null) {
            viewHolder.description.text = movie.name
        } else {
            viewHolder.description.text = movie.title
        }
        viewHolder.image_preview.load("$BASE_POSTERS_PATH${movie.posterPath}")
        viewHolder.itemView.setOnClickListener {
            onClick.invoke(movie)
        }
    }
}


