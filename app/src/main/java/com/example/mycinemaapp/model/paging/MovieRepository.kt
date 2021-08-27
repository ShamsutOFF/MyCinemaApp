package com.example.mycinemaapp.model.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mycinemaapp.model.movieEntitys.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(PagingConfig(20, enablePlaceholders = false)) { MoviesPagingSource() }.flow
    }
}