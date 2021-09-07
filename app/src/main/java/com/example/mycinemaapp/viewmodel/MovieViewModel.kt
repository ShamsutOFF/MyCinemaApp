package com.example.mycinemaapp.viewmodel

import MovieDetailEntity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.repos.DataBaseDetailMovieRepositoryInterface
import com.example.mycinemaapp.model.repos.WebDataBaseMovieDetailRepoImpl
import com.example.mycinemaapp.model.room.MovieDataBaseRoom
import com.example.mycinemaapp.model.room.MovieEntityRoomDto
import com.example.mycinemaapp.model.room.RoomDataBaseMoviesRepoImpl

private const val TAG: String = "@@@ MovieViewModel"

class MovieViewModel(
    val movieDetailLiveDataToObserve: MutableLiveData<MovieFragmentAppState> = MutableLiveData()
) : ViewModel() {

    private lateinit var dataBaseDetailMovieRepositoryInterface: DataBaseDetailMovieRepositoryInterface

    fun getMovieEntityFromServer(character: String, movieId: Int) {
        movieDetailLiveDataToObserve.value = MovieFragmentAppState.Loading

        dataBaseDetailMovieRepositoryInterface =
            WebDataBaseMovieDetailRepoImpl(MyApplication().retrofit)
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
//        dataBaseMovieRoom.addToFavorite(movie)
    }
//    fun addToFavoriteRoom(character: String, movieId: Int, title: String, name: String) {
//        val movie = MovieEntityRoomDto(0, character, movieId, title, name)
//        dataBaseMovieRoom.addToFavorite(movie)
//    }
}

sealed class MovieFragmentAppState {
    data class Success(val character: String, val movieDetailData: MovieDetailEntity) :
        MovieFragmentAppState()

    data class Error(val error: Throwable) : MovieFragmentAppState()
    object Loading : MovieFragmentAppState()
}