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
}
