package com.example.mycinemaapp.model.paging

import com.example.mycinemaapp.model.MovieApiInterface
import com.example.mycinemaapp.model.movieEntitys.MovieEntity
import com.example.mycinemaapp.model.movieEntitys.MovieListInJsonEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MovieRepoImpl(private val retrofit: Retrofit) : MovieRepository {

    private val service: MovieApiInterface by lazy { retrofit.create(MovieApiInterface::class.java) }

    suspend fun getMovies(
        typeOfRequestedMovies: String,
        position: Int,
        onSuccess: (List<MovieEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getMoviesFromServerPaging(typeOfRequestedMovies, page = position).enqueue(object :
            Callback<MovieListInJsonEntity> {
            override fun onResponse(
                call: Call<MovieListInJsonEntity>,
                response: Response<MovieListInJsonEntity>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.results!!)
                } else {
                    onError(Throwable("App error ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<MovieListInJsonEntity>, t: Throwable) {
                onError(t)
            }
        })
    }
}