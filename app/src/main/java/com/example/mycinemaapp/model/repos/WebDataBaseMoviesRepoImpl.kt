package com.example.mycinemaapp.model.repos

import android.util.Log
import com.example.mycinemaapp.model.MovieListWebServiceInt
import com.example.mycinemaapp.model.entitys.MovieEntity
import com.example.mycinemaapp.model.entitys.MovieListInJsonEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

private const val TAG: String = "@@@ WebDataBase"

class WebDataBaseMoviesRepoImpl(private val retrofit: Retrofit) : DataBaseMoviesRepositoryInterface {

    private val serviceInt: MovieListWebServiceInt by lazy {retrofit.create(MovieListWebServiceInt::class.java)}

    override fun getDataBaseMoviesRepos(
        character: String,
        typeOfRequestedMovies: String,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        serviceInt.getMoviesFromServer(character, typeOfRequestedMovies).enqueue(object :
            Callback<MovieListInJsonEntity> {
            override fun onResponse(
                call: Call<MovieListInJsonEntity>,
                response: Response<MovieListInJsonEntity>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.results!!)
                } else {
                    onError(Throwable("App error ${response.code()}"))
                    Log.e(TAG, "onResponse: Fail message = ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieListInJsonEntity>, t: Throwable) {
                onError(t)
            }
        })
    }
}