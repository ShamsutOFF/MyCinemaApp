package com.example.mycinemaapp.viewmodel

import com.example.mycinemaapp.model.MovieEntity

sealed class AppState {
    data class SuccessOneList(val movieEntityList: List<MovieEntity>) : AppState()

    data class Success(val movieDataPlay: List<MovieEntity>, val movieDataCome: List<MovieEntity>) :
        AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
