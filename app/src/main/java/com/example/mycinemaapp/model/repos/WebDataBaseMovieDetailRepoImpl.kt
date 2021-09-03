package com.example.mycinemaapp.model.repos

import MovieDetailEntity
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
        character: String,
        movieId: Int,
        onSuccess: (MovieDetailEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        serviceInt.getMovieDetailFromServer(character, movieId).enqueue(object :
            Callback<MovieDetailEntity> {
            override fun onResponse(
                call: Call<MovieDetailEntity>,
                response: Response<MovieDetailEntity>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    onError(Throwable("App error ${response.code()}"))

                }
            }

            override fun onFailure(call: Call<MovieDetailEntity>, t: Throwable) {
                onError(t)
            }
        })
    }
}