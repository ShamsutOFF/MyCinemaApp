package com.example.mycinemaapp.model

import com.example.mycinemaapp.model.entitys.MovieEntity

class MemoryCacheDataBaseMoviesRepoImpl : DataBaseMoviesRepository {
    override fun getDataBaseMoviesRepos(
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}