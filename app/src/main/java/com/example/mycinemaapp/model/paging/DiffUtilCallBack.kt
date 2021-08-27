package com.example.mycinemaapp.model.paging

import android.annotation.SuppressLint
import android.graphics.Movie
import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBack  : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}