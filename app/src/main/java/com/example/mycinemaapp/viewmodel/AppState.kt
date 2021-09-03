package com.example.mycinemaapp.viewmodel

import com.example.mycinemaapp.model.entitys.MovieEntity

sealed class AppState {
    data class SuccessOneList(val character:String, val typeOfMovies: String, val movieEntityList: List<MovieEntity>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
