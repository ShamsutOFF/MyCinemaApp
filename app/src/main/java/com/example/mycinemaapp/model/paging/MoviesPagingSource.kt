package com.example.mycinemaapp.model.paging

import android.graphics.Movie
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            // 1 Обращаемся к API и получаем MoviesResponse
            val response = MovieApiClient.apiClient.getNowPlayingMovies(page = position)
            // 2 Через MoviesResponse получаем список фильмов
            val movies = response.results

            // 3 Создаём объект класса Page - объект, который необходимо использовать при успешном получении
            // данных
            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            // 4 В случае ошибки возвращаем объект LoadResult.Error
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            // 5 В случае ошибки возвращаем объект LoadResult.Error
            return LoadResult.Error(exception)
        }
    }
    companion object {
        // Стартовый номер страницы
        private const val MOVIES_STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}