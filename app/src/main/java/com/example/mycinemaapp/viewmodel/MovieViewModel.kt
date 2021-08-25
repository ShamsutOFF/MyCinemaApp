package com.example.mycinemaapp.viewmodel

import Json4KotlinBaseMovieDetailEntity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.DataBaseDetailMovieRepository
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.WebDataBaseMovieDetailRepoImpl

class MovieViewModel(
    private val liveDataToObserve: MutableLiveData<MovieFragmentAppState> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    private lateinit var dataBaseDetailMovieRepository: DataBaseDetailMovieRepository

    fun getMovieEntityFromServer(movieId: Int) {
        liveDataToObserve.value = MovieFragmentAppState.Loading
        dataBaseDetailMovieRepository = WebDataBaseMovieDetailRepoImpl(MyApplication().retrofit)
        var json4KotlinBaseMovieDetailEntity: Json4KotlinBaseMovieDetailEntity
        with(dataBaseDetailMovieRepository) {
            getDataBaseDetailMovieRepos(movieId, {
                liveDataToObserve.value = MovieFragmentAppState.Success(it)
            }, {
                liveDataToObserve.value = MovieFragmentAppState.Error(it)
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