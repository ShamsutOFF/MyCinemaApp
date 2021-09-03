package com.example.mycinemaapp.model.repos

import Json4KotlinBaseMovieDetailEntity
import com.example.mycinemaapp.model.MovieDetailWebServiceInt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

private const val TAG: String = "@@@ WebDataBaseMoviesRepoImpl"

class WebDataBaseMovieDetailRepoImpl(private val retrofit: Retrofit) :
    DataBaseDetailMovieRepositoryInterface {

    private val serviceInt: MovieDetailWebServiceInt by lazy {retrofit.create(MovieDetailWebServiceInt::class.java)}

    override fun getDataBaseDetailMovieRepos(
        movieId: Int,
        onSuccess: (Json4KotlinBaseMovieDetailEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        serviceInt.getMovieDetailFromServer(movieId).enqueue(object :
            Callback<Json4KotlinBaseMovieDetailEntity> {
            override fun onResponse(
                call: Call<Json4KotlinBaseMovieDetailEntity>,
                response: Response<Json4KotlinBaseMovieDetailEntity>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    onError(Throwable("App error ${response.code()}"))

                }
            }

            override fun onFailure(call: Call<Json4KotlinBaseMovieDetailEntity>, t: Throwable) {
                onError(t)
            }
        })
    }
}