package com.example.mycinemaapp.model

import android.app.Application
import androidx.room.Room
import com.example.mycinemaapp.model.room.MovieDataBaseRoom
import com.example.mycinemaapp.model.room.MovieRepoDao
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val BASE_URL = "https://api.themoviedb.org/3/"

class MyApplication : Application() {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(createOkHttpClient(MovieApiInterceptor()))
            .build()
    }

    val roomDb by lazy {
        Room.databaseBuilder(
            applicationContext,
            MovieDataBaseRoom::class.java, "movie-db"
        ).build()
    }

    companion object {
        private var appInstance: MyApplication? = null
        private var db: MovieDataBaseRoom? = null
        private const val DB_NAME = "Movie.db"
        fun getMovieDao(): MovieRepoDao {
            if (db == null) {
                synchronized(MovieDataBaseRoom::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw
                        IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MovieDataBaseRoom::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.MovieRepoDao()
        }
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        return httpClient.build()
    }

    class MovieApiInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}