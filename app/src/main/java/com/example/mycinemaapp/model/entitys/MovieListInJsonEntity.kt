package com.example.mycinemaapp.model.entitys

import com.google.gson.annotations.SerializedName

data class MovieListInJsonEntity(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieEntity>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
