package com.example.mycinemaapp.viewmodel

import MovieDetailEntity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.repos.DataBaseDetailMovieRepositoryInterface
import com.example.mycinemaapp.model.repos.WebDataBaseMovieDetailRepoImpl

private const val TAG: String = "@@@ MovieViewModel"

class MovieViewModel(
    val movieDetailLiveDataToObserve: MutableLiveData<MovieFragmentAppState> = MutableLiveData()
) : ViewModel() {

    private lateinit var dataBaseDetailMovieRepositoryInterface: DataBaseDetailMovieRepositoryInterface

    fun getMovieEntityFromServer(character:String, movieId: Int) {
        movieDetailLiveDataToObserve.value = MovieFragmentAppState.Loading
        dataBaseDetailMovieRepositoryInterface = WebDataBaseMovieDetailRepoImpl(MyApplication().retrofit)
        with(dataBaseDetailMovieRepositoryInterface) {
            getDataBaseDetailMovieRepos(character, movieId, {
                movieDetailLiveDataToObserve.value = MovieFragmentAppState.Success(character, it)
            }, {
                Log.d(TAG,"!!!ERROR: $it")
                movieDetailLiveDataToObserve.value = MovieFragmentAppState.Error(it)
            })
        }
    }
}

sealed class MovieFragmentAppState {
    data class Success(val character: String, val movieDetailData: MovieDetailEntity) :
        MovieFragmentAppState()
    data class Error(val error: Throwable) : MovieFragmentAppState()
    object Loading : MovieFragmentAppState()
}