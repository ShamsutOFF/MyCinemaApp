package com.example.mycinemaapp.model

import MovieDetailEntity
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
    @GET("{character}/{movieId}?api_key=${BuildConfig.THE_MOVIE_DP_API_KEY}&language=RU")
    fun getMovieDetailFromServer(
        @Path("character")character : String,
        @Path("movieId")movieId : Int
    ) : Call<MovieDetailEntity>
}