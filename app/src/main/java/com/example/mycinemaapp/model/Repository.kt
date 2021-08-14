package com.example.mycinemaapp.model

interface Repository {
    fun getMoviesFromServer(): MovieEntity
    fun getNowPlayingFromLocalStorage(): List<MovieEntity>
    fun getUpcomingFromLocalStorage(): List<MovieEntity>
}