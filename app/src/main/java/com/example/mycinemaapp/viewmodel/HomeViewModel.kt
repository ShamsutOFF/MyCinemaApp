package com.example.mycinemaapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.repos.DataBaseMoviesRepositoryInterface
import com.example.mycinemaapp.model.repos.WebDataBaseMoviesRepoImpl

private const val TAG: String = "@@@ HomeViewModel"

class HomeViewModel(
    val movieLoadingLiveData: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {

    private lateinit var dataBaseMoviesRepositoryInterface: DataBaseMoviesRepositoryInterface

    fun loadMoviesListFromServer(character:String, typeOfMovies: String) {
        movieLoadingLiveData.value = AppState.Loading
        dataBaseMoviesRepositoryInterface = WebDataBaseMoviesRepoImpl(MyApplication().retrofit)
        with(dataBaseMoviesRepositoryInterface) {
            getDataBaseMoviesRepos(character, typeOfMovies, {
                movieLoadingLiveData.value = AppState.SuccessOneList(character,typeOfMovies, it)
            }, {
                movieLoadingLiveData.value = AppState.Error(it)
                Log.e(TAG, "getDataFromServer: Error ${it.message}")
            })
        }
    }
}
