package com.example.mycinemaapp.model

data class MovieListInJsonEntity(
    val page: Int,
    val results: List<MovieEntity>,
    val total_pages: Int,
    val total_results: Int
)
