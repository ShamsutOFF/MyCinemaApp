package com.example.mycinemaapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ammymovie.ui.main.model.Repository
import com.example.mycinemaapp.AppState
import com.example.mycinemaapp.RepositoryImpl

class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()) : ViewModel() {

    private val TAG: String = "@@@ HomeViewModel"

    // Получаем данные
    fun getLiveData() = liveDataToObserve
//        Log.d(TAG, "getLiveData() called")

        fun getDataFromLocalSource() {
            Log.d(TAG, "getDataFromLocalSource() called")
            liveDataToObserve.value = AppState.Loading
            Thread {
                liveDataToObserve.postValue(
                    AppState.Success(
                        repositoryImpl.getNowPlayingFromLocalStorage(),
                        repositoryImpl.getUpcomingFromLocalStorage()
                    )
                )
            }.start()
        }
    }
