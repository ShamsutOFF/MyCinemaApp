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

private const val TAG: String = "@@@ FavoritesViewModel"

class FavoritesViewModel : ViewModel() {

    val favoritesListLD = MutableLiveData<LoadFavoritesListAppState>()
    val movieLoadLD = MutableLiveData<MovieLoadAppState>()

    private lateinit var dataBaseDetailMovieRepositoryInterface: DataBaseDetailMovieRepositoryInterface

    fun readAllFavoritesRoom(room: MovieDataBaseRoom) {
        var dataBaseMovieRoom = RoomDataBaseMoviesRepoImpl(room)
        dataBaseMovieRoom.getAllFavorites {
            if (it.isEmpty()) {
                favoritesListLD.value =
                    LoadFavoritesListAppState.Success(it) //todo переделать на возврат пустого
                Log.d(TAG, "readAllFavoritesRoom() called it = $it")
            } else {
                favoritesListLD.value = LoadFavoritesListAppState.Success(it)
            }
        }
    }

    fun getMovieEntityFromServer(retrofit: Retrofit, character: String, movieId: Int) {
        movieLoadLD.value = MovieLoadAppState.Loading
        dataBaseDetailMovieRepositoryInterface = WebDataBaseMovieDetailRepoImpl(retrofit)
        with(dataBaseDetailMovieRepositoryInterface) {
            getDataBaseDetailMovieRepos(character, movieId, {
                movieLoadLD.value = MovieLoadAppState.Success(character, it)
                Log.d(TAG, "getMovieEntityFromServer() called it = $it")
            }, {
                android.util.Log.d(TAG, "!!!ERROR: $it")
                movieLoadLD.value = MovieLoadAppState.Error(it)
            })
        }
    }
}

sealed class LoadFavoritesListAppState {
    data class Success(val result: List<MovieEntityRoomDto>) :
        LoadFavoritesListAppState()

    data class Error(val error: Throwable) : LoadFavoritesListAppState()
    object Loading : LoadFavoritesListAppState()
}

sealed class MovieLoadAppState {
    data class Success(val character: String, val movieDetailData: MovieDetailEntity) :
        MovieLoadAppState()

    data class Error(val error: Throwable) : MovieLoadAppState()
    object Loading : MovieLoadAppState()
}