package com.example.mycinemaapp.viewmodel

import Json4KotlinBaseMovieDetailEntity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.repos.DataBaseDetailMovieRepositoryInterface
import com.example.mycinemaapp.model.repos.WebDataBaseMovieDetailRepoImpl

class MovieViewModel(
    val movieDetailLiveDataToObserve: MutableLiveData<MovieFragmentAppState> = MutableLiveData()
) : ViewModel() {

    private lateinit var dataBaseDetailMovieRepositoryInterface: DataBaseDetailMovieRepositoryInterface

    fun getMovieEntityFromServer(movieId: Int) {
        movieDetailLiveDataToObserve.value = MovieFragmentAppState.Loading
        dataBaseDetailMovieRepositoryInterface = WebDataBaseMovieDetailRepoImpl(MyApplication().retrofit)
        var json4KotlinBaseMovieDetailEntity: Json4KotlinBaseMovieDetailEntity
        with(dataBaseDetailMovieRepositoryInterface) {
            getDataBaseDetailMovieRepos(movieId, {
                movieDetailLiveDataToObserve.value = MovieFragmentAppState.Success(it)
            }, {
                movieDetailLiveDataToObserve.value = MovieFragmentAppState.Error(it)
            })
        }
    }
}

sealed class MovieFragmentAppState {
    data class Success(val movieDetailData: Json4KotlinBaseMovieDetailEntity) :
        MovieFragmentAppState()
    data class Error(val error: Throwable) : MovieFragmentAppState()
    object Loading : MovieFragmentAppState()
}