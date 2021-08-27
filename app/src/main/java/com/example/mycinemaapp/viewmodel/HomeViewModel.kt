package com.example.mycinemaapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mycinemaapp.model.DataBaseMoviesRepository
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.WebDataBaseMoviesRepoImpl
import com.example.mycinemaapp.model.movieEntitys.MovieEntity
import com.example.mycinemaapp.model.paging.MovieRepository
import kotlinx.coroutines.flow.Flow

private const val TAG: String = "@@@ HomeViewModel"
private const val UPCOMING: String = "upcoming"
private const val NOW_PLAYING: String = "now_playing"

class HomeViewModel(
    private val liveDataToObserve: MovieRepository = MutableLiveData(),
    private val repository: MovieRepository
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    private lateinit var dataBaseMoviesRepository: DataBaseMoviesRepository
        val movieLoadingStateLiveData = MutableLiveData<DataLoadingState>()
        fun getMovies(): Flow<PagingData<MovieEntity>> {
            return repository.getMovies().cachedIn(viewModelScope)
        }
        fun onMovieClicked(movie: MovieEntity) {
            // TODO handle navigation to details screen event
        }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getMoviesListFromServer() {
        Log.d(TAG, "getDataFromServer() called")
        liveDataToObserve.value = AppState.Loading
        var moviesListUpcoming: List<MovieEntity> = emptyList()
        var moviesListNowPlaying: List<MovieEntity> = emptyList()

        dataBaseMoviesRepository = WebDataBaseMoviesRepoImpl(MyApplication().retrofit)
        with(dataBaseMoviesRepository) {
            getDataBaseMoviesRepos(UPCOMING, {
                moviesListUpcoming = it
            }, {
                liveDataToObserve.value = AppState.Error(it)
                Log.e(TAG, "getDataFromServer: Error ${it.message}")
            })
            getDataBaseMoviesRepos(NOW_PLAYING, {
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
