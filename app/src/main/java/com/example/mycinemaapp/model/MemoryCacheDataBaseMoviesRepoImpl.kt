package com.example.mycinemaapp.model

class MemoryCacheDataBaseMoviesRepoImpl : DataBaseMovieRepository {
    override fun getDataBaseRepos(
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}