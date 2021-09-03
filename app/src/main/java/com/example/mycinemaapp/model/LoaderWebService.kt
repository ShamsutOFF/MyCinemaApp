package com.example.mycinemaapp.model

import Json4KotlinBaseMovieDetailEntity
import com.example.mycinemaapp.BuildConfig
import com.example.mycinemaapp.model.entitys.MovieListInJsonEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieListWebServiceInt {
    @GET("{character}/{moviesListType}?api_key=${BuildConfig.THE_MOVIE_DP_API_KEY}&language=ru-RU&page=1")
    fun getMoviesFromServer(
        @Path("character")character : String,
        @Path("moviesListType")moviesListType : String
    ) : Call<MovieListInJsonEntity>
}

interface MovieDetailWebServiceInt {
    @GET("movie/{movieId}?api_key=${BuildConfig.THE_MOVIE_DP_API_KEY}&language=RU")
    fun getMovieDetailFromServer(@Path("movieId")movieId : Int) : Call<Json4KotlinBaseMovieDetailEntity>
}