package com.example.mycinemaapp.model.repos

import Json4KotlinBaseMovieDetailEntity
import com.example.mycinemaapp.model.entitys.MovieEntity

interface DataBaseMoviesRepositoryInterface {
    fun getDataBaseMoviesRepos(
        character: String,
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    )
}

interface DataBaseDetailMovieRepositoryInterface {
    fun getDataBaseDetailMovieRepos(
        movieId : Int,
        onSuccess: (Json4KotlinBaseMovieDetailEntity) -> Unit,
        onError: (Throwable) -> Unit
    )
}