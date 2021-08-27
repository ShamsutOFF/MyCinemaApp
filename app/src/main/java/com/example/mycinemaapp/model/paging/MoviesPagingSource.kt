package com.example.mycinemaapp.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycinemaapp.model.movieEntitys.MovieEntity
import retrofit2.HttpException
import java.io.IOException

private const val NOW_PLAYING: String = "now_playing"

class MoviesPagingSource : PagingSource<Int, MovieEntity>() {
    private lateinit var moviesRepository: MovieRepoImpl

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX

        return try {
            var moviesListUpcoming: List<MovieEntity> = emptyList()
            // 1 Обращаемся к API и получаем MoviesResponse
            moviesRepository.getMovies(NOW_PLAYING, position, { moviesListUpcoming = it}, {         })
            // 2 Через MoviesResponse получаем список фильмов
            val movies = moviesListUpcoming

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

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        TODO("Not yet implemented")
    }
}