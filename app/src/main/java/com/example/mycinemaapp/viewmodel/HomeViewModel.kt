package com.example.mycinemaapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.DataBaseMoviesRepository
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.WebDataBaseMoviesRepoImpl

private const val TAG: String = "@@@ HomeViewModel"

class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    private lateinit var dataBaseMoviesRepository: DataBaseMoviesRepository


    fun getOneMoviesListFromServer(typeOfMovies: String) {
        liveDataToObserve.value = AppState.Loading
        dataBaseMoviesRepository = WebDataBaseMoviesRepoImpl(MyApplication().retrofit)
        with(dataBaseMoviesRepository) {
            getDataBaseMoviesRepos(typeOfMovies, {
                liveDataToObserve.value = AppState.SuccessOneList(typeOfMovies, it)
            }, {
                liveDataToObserve.value = AppState.Error(it)
                Log.e(TAG, "getDataFromServer: Error ${it.message}")
            })
        }
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun getMoviesListFromServer() {
//        Log.d(TAG, "getDataFromServer() called")
//        liveDataToObserve.value = AppState.Loading
//        var moviesListUpcoming: List<MovieEntity> = emptyList()
//        var moviesListNowPlaying: List<MovieEntity> = emptyList()
//
//        dataBaseMoviesRepository = WebDataBaseMoviesRepoImpl(MyApplication().retrofit)
//        with(dataBaseMoviesRepository) {
//            getDataBaseMoviesRepos(UPCOMING, {
//                moviesListUpcoming = it
//            }, {
//                liveDataToObserve.value = AppState.Error(it)
//                Log.e(TAG, "getDataFromServer: Error ${it.message}")
//            })
//            getDataBaseMoviesRepos(NOW_PLAYING, {
//                moviesListNowPlaying = it
//            }, {
//                liveDataToObserve.value = AppState.Error(it)
//                Log.e(TAG, "getDataFromServer: Error ${it.message}")
//            })
//        }
//
//        Thread {
//            while (moviesListNowPlaying.isEmpty() || moviesListUpcoming.isEmpty()) {
//                Thread.sleep(10)
//            }
//            liveDataToObserve.postValue(
//                AppState.Success(moviesListNowPlaying, moviesListUpcoming)
//            )
//        }.start()
//    }
}
