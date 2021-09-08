package com.example.mycinemaapp.viewmodel

import MovieDetailEntity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.repos.DataBaseDetailMovieRepositoryInterface
import com.example.mycinemaapp.model.repos.WebDataBaseMovieDetailRepoImpl
import com.example.mycinemaapp.model.room.MovieDataBaseRoom
import com.example.mycinemaapp.model.room.MovieEntityRoomDto
import com.example.mycinemaapp.model.room.RoomDataBaseMoviesRepoImpl
import retrofit2.Retrofit

private const val TAG: String = "@@@ MovieViewModel"

class MovieViewModel(
    val movieDetailLiveDataToObserve: MutableLiveData<MovieFragmentAppState> = MutableLiveData(),
    val checkMovieLiveData: MutableLiveData<CheckFavoriteAppState> = MutableLiveData()
) : ViewModel() {

    private lateinit var dataBaseDetailMovieRepositoryInterface: DataBaseDetailMovieRepositoryInterface

    fun getMovieEntityFromServer(retrofit: Retrofit, character: String, movieId: Int) {
        movieDetailLiveDataToObserve.value = MovieFragmentAppState.Loading

        dataBaseDetailMovieRepositoryInterface = WebDataBaseMovieDetailRepoImpl(retrofit)
        with(dataBaseDetailMovieRepositoryInterface) {
            getDataBaseDetailMovieRepos(character, movieId, {
                movieDetailLiveDataToObserve.value = MovieFragmentAppState.Success(character, it)
            }, {
                Log.d(TAG, "!!!ERROR: $it")
                movieDetailLiveDataToObserve.value = MovieFragmentAppState.Error(it)
            })
        }
    }

    fun addToFavoriteRoom(room: MovieDataBaseRoom, movieDto: MovieEntityRoomDto) {
        var dataBaseMovieRoom = RoomDataBaseMoviesRepoImpl(room)
        Log.d(TAG, "addToFavoriteRoom() called with: movie = $movieDto")
        dataBaseMovieRoom.addToFavorite(movieDto)
    }

    fun deleteFromFavoriteRoom(room: MovieDataBaseRoom, movieDto: MovieEntityRoomDto) {
        var dataBaseMovieRoom = RoomDataBaseMoviesRepoImpl(room)
        Log.d(TAG, "deleteFromFavoriteRoom() called with: movie = $movieDto")
        dataBaseMovieRoom.deleteFromFavorite(movieDto)
    }

    fun checkFromFavoriteRoom(room: MovieDataBaseRoom, movieDto: MovieEntityRoomDto) {
        checkMovieLiveData.value = CheckFavoriteAppState.Loading

        var dataBaseMovieRoom = RoomDataBaseMoviesRepoImpl(room)
        dataBaseMovieRoom.getFavorite(movieDto) {
            if (it.isEmpty()) {
                checkMovieLiveData.value = CheckFavoriteAppState.Success(false)
            } else {
                checkMovieLiveData.value = CheckFavoriteAppState.Success(true)
            }
        }
    }
}

sealed class CheckFavoriteAppState {
    data class Success(val result: Boolean) :
        CheckFavoriteAppState()
    data class Error(val error: Throwable) : CheckFavoriteAppState()
    object Loading : CheckFavoriteAppState()
}

sealed class MovieFragmentAppState {
    data class Success(val character: String, val movieDetailData: MovieDetailEntity) :
        MovieFragmentAppState()
    data class Error(val error: Throwable) : MovieFragmentAppState()
    object Loading : MovieFragmentAppState()
}
