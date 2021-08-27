package com.example.mycinemaapp.model

import Json4KotlinBaseMovieDetailEntity
import com.example.mycinemaapp.model.movieEntitys.MovieEntity

interface DataBaseMoviesRepository {
    fun getDataBaseMoviesRepos(
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    )
}

interface DataBaseDetailMovieRepository {
    fun getDataBaseDetailMovieRepos(
        movieId : Int,
        onSuccess: (Json4KotlinBaseMovieDetailEntity) -> Unit,
        onError: (Throwable) -> Unit
    )
}