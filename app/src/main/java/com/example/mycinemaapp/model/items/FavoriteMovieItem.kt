package com.example.mycinemaapp.model.items

import MovieDetailEntity
import coil.load
import com.example.mycinemaapp.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_favorite_movie.*

private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class FavoriteMovieItem(
    private val movie: MovieDetailEntity,
    private val onClick: (character: String, id: Int) -> Unit,
    private val character: String
) : Item() {

    override fun getLayout() = R.layout.item_favorite_movie

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        if (movie.title == null) {
            viewHolder.title_text_view.text = movie.name
        } else {
            viewHolder.title_text_view.text = movie.title
        }
        viewHolder.image_preview_fav.load("$BASE_POSTERS_PATH${movie.posterPath}")
        viewHolder.itemView.setOnClickListener {
            onClick.invoke(character, movie.id)
        }
    }
}


