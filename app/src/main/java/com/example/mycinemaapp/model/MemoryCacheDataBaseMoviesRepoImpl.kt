package com.example.mycinemaapp.model

class MemoryCacheDataBaseMoviesRepoImpl : DataBaseMoviesRepository {
    override fun getDataBaseMoviesRepos(
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}