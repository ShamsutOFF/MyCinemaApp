package com.example.mycinemaapp.model

import android.util.Log
import com.example.mycinemaapp.model.movieEntitys.MovieEntity
import com.example.mycinemaapp.model.movieEntitys.MovieListInJsonEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

private const val TAG: String = "@@@ WebDataBase"

class WebDataBaseMoviesRepoImpl(private val retrofit: Retrofit) : DataBaseMoviesRepository {

    private val service: MovieListWebService by lazy {retrofit.create(MovieListWebService::class.java)}

    override fun getDataBaseMoviesRepos(
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getMoviesFromServer(typeOfRequestedMovies).enqueue(object :
            Callback<MovieListInJsonEntity> {
            override fun onResponse(
                call: Call<MovieListInJsonEntity>,
                response: Response<MovieListInJsonEntity>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.results!!)
                } else {
                    onError(Throwable("Ape error ${response.code()}"))
                    Log.e(TAG, "onResponse: Fail message = ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieListInJsonEntity>, t: Throwable) {
                onError(t)
            }
        })
    }
}