package com.example.mycinemaapp.model

import com.example.mycinemaapp.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieListWebService {
    @GET("/3/movie/{moviesListType}?api_key=${BuildConfig.THE_MOVIE_DP_API_KEY}&language=ru-RU&page=1")
    fun getMoviesFromServer(@Path("moviesListType")moviesListType : String) : Call<List<JsonMovieListEntity>>
}