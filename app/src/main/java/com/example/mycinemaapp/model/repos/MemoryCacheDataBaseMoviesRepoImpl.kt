package com.example.mycinemaapp.model.repos

import com.example.mycinemaapp.model.entitys.MovieEntity

class MemoryCacheDataBaseMoviesRepoImpl : DataBaseMoviesRepositoryInterface {
    override fun getDataBaseMoviesRepos(
        character: String,
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}