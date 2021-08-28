package com.example.mycinemaapp.model.items

import com.example.mycinemaapp.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card.*

class MainCardContainer(
    private val title: String? = "",
    private val description: String? = "",
    private val onClick: (url: String) -> Unit,
    private val items: List<Item>
) : Item() {

    override fun getLayout() = R.layout.item_card

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.title_text_view.text = title
        viewHolder.description_text_view.text = description
        viewHolder.items_container.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(items) }
    }
}
