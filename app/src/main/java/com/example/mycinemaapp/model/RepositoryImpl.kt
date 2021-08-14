package com.example.mycinemaapp.model

import android.util.Log
private const val TAG: String = "@@@ RepositoryImpl"
class RepositoryImpl : Repository {

    override fun getMoviesFromServer(): MovieEntity {
        Log.d(TAG, "getMoviesFromServer() called")
        return MovieEntity(1, "", "", "", 5.5F, false)
    }

    override fun getNowPlayingFromLocalStorage(): List<MovieEntity> {
        Log.d(TAG, "getNowPlayingFromLocalStorage() called")
        return listOf(
            MovieEntity(1, "Тест 1", null, "01.08.21", 5.5F, false),
            MovieEntity(2, "Тест 2", null, "01.08.21", 5.5F, false),
            MovieEntity(3, "Тест 3", null, "", 5.5F, false),
            MovieEntity(4, "Тест 4", null, "01.08.21", 5.5F, false),
            MovieEntity(5, "Тест 5", null, "", 5.5F, false),
            MovieEntity(6, "Тест 6", null, "01.08.21", 5.5F, false),
            MovieEntity(7, "Тест 7", null, "", 5.5F, false),
            MovieEntity(8, "Тест 8", null, "", 5.5F, false),
            MovieEntity(9, "Тест 9", null, "01.08.21", 5.5F, false),
            MovieEntity(10, "Тест 10", null, "", 5.5F, false)
        )
    }

    override fun getUpcomingFromLocalStorage(): List<MovieEntity> {
        Log.d(TAG, "getUpcomingFromLocalStorage() called")
        return listOf(
            MovieEntity(21, "Тест 21", null, "01.08.21", 5.5F, true),
            MovieEntity(22, "Тест 22", null, "", 5.5F, false),
            MovieEntity(23, "Тест 23", null, "01.08.21", 5.5F, true),
            MovieEntity(24, "Тест 24", null, "", 5.5F, true),
            MovieEntity(25, "Тест 25", null, "скоро", 5.5F, true),
            MovieEntity(26, "Тест 26", null, "01.08.21", 5.5F, true),
            MovieEntity(27, "Тест 27", null, "может и не выйдет", 5.5F, true),
            MovieEntity(28, "Тест 28", null, "", 5.5F, true),
            MovieEntity(29, "Тест 29", null, "неизвестно", 5.5F, true),
            MovieEntity(30, "Тест 30", null, "", 5.5F, true)
        )
    }
}