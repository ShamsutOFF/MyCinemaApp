package com.example.mycinemaapp.model.paging

import android.graphics.Movie
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(20, enablePlaceholders = false)) { MoviesPagingSource() }.flow
    }
}