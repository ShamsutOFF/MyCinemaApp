package com.example.mycinemaapp.model

interface DataBaseMovieRepository {
    fun getDataBaseRepos(
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    )
}