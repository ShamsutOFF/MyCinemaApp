package com.example.mycinemaapp.model.items

import com.example.mycinemaapp.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*


class MovieItem(private val content: MovieContent) : Item() {

    override fun getLayout() = R.layout.item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        Picasso.get()
            .load(content.url)
            .into(viewHolder.image_preview)
    }
}


data class MovieContent(
    val title: String,
    val url: String
)