package com.example.mycinemaapp

import android.util.Log
import com.example.ammymovie.ui.main.model.Repository
import java.util.*

class RepositoryImpl : Repository {
    private val TAG: String = "@@@ RepositoryImpl"

    override fun getMoviesFromServer(): MovieEntity {
        Log.d(TAG, "getMoviesFromServer() called")
        return MovieEntity(1, "", "", "", 5.5F, false)
    }

    override fun getNowPlayingFromLocalStorage(): List<MovieEntity> {
        Log.d(TAG, "getNowPlayingFromLocalStorage() called")
        return listOf(
            MovieEntity(1, "Тест 1", "", "", 5.5F, false),
            MovieEntity(2, "Тест 2", "", "", 5.5F, false),
            MovieEntity(3, "Тест 3", "", "", 5.5F, false),
            MovieEntity(4, "Тест 4", "", "", 5.5F, false),
            MovieEntity(5, "Тест 5", "", "", 5.5F, false)
        )
    }

    override fun getUpcomingFromLocalStorage(): List<MovieEntity> {
        Log.d(TAG, "getUpcomingFromLocalStorage() called")
        return listOf(
            MovieEntity(21, "", "", "", 5.5F, false),
            MovieEntity(22, "", "", "", 5.5F, false),
            MovieEntity(23, "", "", "", 5.5F, false),
            MovieEntity(24, "", "", "", 5.5F, false),
            MovieEntity(25, "", "", "", 5.5F, false)
        )
    }

}