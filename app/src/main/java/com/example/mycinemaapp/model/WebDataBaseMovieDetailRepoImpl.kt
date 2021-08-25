package com.example.mycinemaapp.model

import Json4KotlinBaseMovieDetailEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

private const val TAG: String = "@@@ WebDataBaseMoviesRepoImpl"

class WebDataBaseMovieDetailRepoImpl(private val retrofit: Retrofit) : DataBaseDetailMovieRepository {

    private val service: MovieDetailWebService by lazy {retrofit.create(MovieDetailWebService::class.java)}

    override fun getDataBaseDetailMovieRepos(
        movieId: Int,
        onSuccess: (Json4KotlinBaseMovieDetailEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getMovieDetailFromServer(movieId).enqueue(object :
            Callback<Json4KotlinBaseMovieDetailEntity> {
            override fun onResponse(
                call: Call<Json4KotlinBaseMovieDetailEntity>,
                response: Response<Json4KotlinBaseMovieDetailEntity>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    onError(Throwable("Ape error ${response.code()}"))

                }
            }

            override fun onFailure(call: Call<Json4KotlinBaseMovieDetailEntity>, t: Throwable) {
                onError(t)
            }
        })
    }
}