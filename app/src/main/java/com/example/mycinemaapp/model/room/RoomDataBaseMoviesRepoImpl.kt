package com.example.mycinemaapp.model.room

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.mycinemaapp.model.entitys.MovieEntity
import com.example.mycinemaapp.model.repos.DataBaseMoviesRepositoryInterface

private const val TAG: String = "@@@ RoomMoviesRepoImpl"

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
            db.MovieRepoDao().delete(movieEntityRoomDto.character, movieEntityRoomDto.id)
        }.start()
    }

    fun getAllFavorites() {
        Thread {
            db.MovieRepoDao().getAll()
        }.start()
    }

    fun getFavorite(
        movieEntityRoomDto: MovieEntityRoomDto,
        onSuccess: (List<MovieEntityRoomDto>) -> Unit
    ) {
        Thread {
            val a = db.MovieRepoDao()
                .getMovieByCharacterAndId(movieEntityRoomDto.character, movieEntityRoomDto.id)
            Handler(Looper.getMainLooper()).post { onSuccess(a) }
            Log.d(TAG, "getFavorite() called a = $a")
        }.start()
        Log.d(TAG, "getFavorite() called onSuccess = $onSuccess")
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