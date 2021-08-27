package com.example.mycinemaapp.model.paging

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.mycinemaapp.model.movieEntitys.MovieEntity

class DiffUtilCallBack  : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }
}