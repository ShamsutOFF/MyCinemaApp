package com.example.mycinemaapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.Repository
import com.example.mycinemaapp.model.RepositoryImpl

private const val TAG: String = "@@@ HomeViewModel"

class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {
    // Получаем данные
    fun getLiveData() = liveDataToObserve

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
