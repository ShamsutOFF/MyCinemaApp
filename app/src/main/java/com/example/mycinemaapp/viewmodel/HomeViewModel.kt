package com.example.mycinemaapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.DataBaseMovieRepository
import com.example.mycinemaapp.model.MovieEntity
import com.example.mycinemaapp.model.RetrofitHolder
import com.example.mycinemaapp.model.WebDataBaseMoviesRepoImpl

private const val TAG: String = "@@@ HomeViewModel"
private const val UPCOMING: String = "upcoming"
private const val NOW_PLAYING: String = "now_playing"

class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve
    private lateinit var dataBaseMovieRepository: DataBaseMovieRepository

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDataFromServer() {
        Log.d(TAG, "getDataFromServer() called")
        liveDataToObserve.value = AppState.Loading
        var moviesListUpcoming: List<MovieEntity> = emptyList()
        var moviesListNowPlaying: List<MovieEntity> = emptyList()



        dataBaseMovieRepository = WebDataBaseMoviesRepoImpl(RetrofitHolder.retrofit)
        with(dataBaseMovieRepository) {
            getDataBaseRepos(UPCOMING, {
                moviesListUpcoming = it
            }, {
                liveDataToObserve.value = AppState.Error(it)
                Log.e(TAG, "getDataFromServer: Error ${it.message}")
            })
            getDataBaseRepos(NOW_PLAYING, {
                moviesListNowPlaying = it
            }, {
                liveDataToObserve.value = AppState.Error(it)
                Log.e(TAG, "getDataFromServer: Error ${it.message}")
            })
        }

        Thread {
            while (moviesListNowPlaying.isEmpty() || moviesListUpcoming.isEmpty()) {
                Thread.sleep(10)
            }
            liveDataToObserve.postValue(
                AppState.Success(moviesListNowPlaying, moviesListUpcoming)
            )
        }.start()
    }
}
