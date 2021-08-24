package com.example.mycinemaapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.model.DataBaseMovieRepository
import com.example.mycinemaapp.model.MovieEntity
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.WebDataBaseMoviesRepoImpl

private const val TAG: String = "@@@ HomeViewModel"
private const val UPCOMING: String = "upcoming"
private const val NOW_PLAYING: String = "now_playing"

class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve
    private lateinit var dataBaseMovieRepository: DataBaseMovieRepository

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDataFromServer() {
        Log.d(TAG, "getDataFromServer() called")
        liveDataToObserve.value = AppState.Loading
        var moviesListUpcoming: List<MovieEntity> = emptyList()
        var moviesListNowPlaying: List<MovieEntity> = emptyList()

        // Временно вручную создадим Мар жанров, потом будем получать с сервера
        val genresMap = mapOf<Int,String>(28 to "боевик", 12 to "приключения", 16 to "мультфильм",
            35 to "комедия", 80 to "криминал", 99 to "документальный", 18 to "драма", 10751 to "семейный",
            14 to "фэнтези", 36 to "история", 27 to "ужасы", 10402  to "музыка", 9648  to "детектив",
            10749 to "мелодрама", 878 to "фантастика", 10770  to "телевизионный фильм", 53 to "триллер",
            10752 to "военный", 37 to "вестерн")

        dataBaseMovieRepository = WebDataBaseMoviesRepoImpl(MyApplication().retrofit)
        with(dataBaseMovieRepository) {
            getDataBaseRepos(UPCOMING, {
                moviesListUpcoming = it
            }, {
                liveDataToObserve.value = AppState.Error(it)
                Log.e(TAG, "getDataFromServer: Error ${it.message}")
            })
            getDataBaseRepos(NOW_PLAYING, {
                moviesListNowPlaying = it
            }, {
                liveDataToObserve.value = AppState.Error(it)
                Log.e(TAG, "getDataFromServer: Error ${it.message}")
            })
        }

        Thread {
            while (moviesListNowPlaying.isEmpty() || moviesListUpcoming.isEmpty()) {
                Thread.sleep(10)
            }
            liveDataToObserve.postValue(
                AppState.Success(moviesListNowPlaying, moviesListUpcoming)
            )
        }.start()
    }
}
