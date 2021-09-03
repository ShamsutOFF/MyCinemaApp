package com.example.mycinemaapp.model.repos

import MovieDetailEntity
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
        character: String,
        movieId : Int,
        onSuccess: (MovieDetailEntity) -> Unit,
        onError: (Throwable) -> Unit
    )
}