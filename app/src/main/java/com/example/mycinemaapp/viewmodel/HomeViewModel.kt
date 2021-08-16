package com.example.mycinemaapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycinemaapp.BuildConfig
import com.example.mycinemaapp.model.JsonEntity
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val TAG: String = "@@@ HomeViewModel"

class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {
    fun getLiveData() = liveDataToObserve

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDataFromServer() {
        Log.d(TAG, "getDataFromServer() called")
        liveDataToObserve.value = AppState.Loading
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/now_playing?api_key=${BuildConfig.THE_MOVIE_DP_API_KEY}&language=ru-RU&page=1")
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10_000
                    val gson = Gson()
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val jsonStr: String = bufferedReader.readLines().joinToString()
                    val jsonEntity: JsonEntity = gson.fromJson(jsonStr, JsonEntity::class.java)
                    Log.d(TAG, "jsonEntity = $jsonEntity")
                    val parsedData = jsonEntity.results
                    liveDataToObserve.postValue(
                        AppState.Success(parsedData, parsedData)
                    )
                } catch (e: MalformedURLException) {
                    Log.e("", "Fail connection: ", e)
                    e.printStackTrace()
                    //Обработка ошибки
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI: ", e)
            e.printStackTrace()
            //Обработка ошибки
        }
    }
}
