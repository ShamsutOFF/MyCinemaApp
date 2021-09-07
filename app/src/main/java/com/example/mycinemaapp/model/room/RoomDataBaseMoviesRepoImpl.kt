package com.example.mycinemaapp.model.room

import com.example.mycinemaapp.model.entitys.MovieEntity
import com.example.mycinemaapp.model.repos.DataBaseMoviesRepositoryInterface

class RoomDataBaseMoviesRepoImpl(
    private val db: MovieDataBaseRoom
) :
    DataBaseMoviesRepositoryInterface {

    fun addToFavorite(movieEntityRoomDto: MovieEntityRoomDto) {
        Thread {
            db.MovieRepoDao().insert(movieEntityRoomDto)
        }.start()
    }

    fun deleteFromFavorite(movieEntityRoomDto: MovieEntityRoomDto) {
        Thread {
            db.MovieRepoDao().delete(movieEntityRoomDto)
        }.start()
    }

    fun getAllFavorites() {
        Thread {
            db.MovieRepoDao().getAll()
        }.start()
    }

    override fun getDataBaseMoviesRepos(
        character: String,
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}