package com.example.ammymovie.ui.main.model

import com.example.mycinemaapp.MovieEntity

interface Repository {
    fun getMoviesFromServer(): MovieEntity
    fun getNowPlayingFromLocalStorage(): List<MovieEntity>
    fun getUpcomingFromLocalStorage(): List<MovieEntity>
}