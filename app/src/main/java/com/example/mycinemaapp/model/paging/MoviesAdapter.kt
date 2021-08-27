package com.example.mycinemaapp.model.paging

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.ItemNowPlayingBinding
import com.example.mycinemaapp.model.movieEntitys.MovieEntity
import com.example.mycinemaapp.view.HomeFragment

private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class MoviesAdapter(
    private var onItemViewClickListener:
    HomeFragment.OnItemViewClickListener?
) : PagingDataAdapter<MovieEntity, MoviesAdapter.MovieViewHolder>(
    DiffUtilCallBack()
) {
    private var movieData: List<MovieEntity> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<MovieEntity>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindMovie(movie)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNowPlayingBinding.bind(itemView)

        fun bindMovie(movie: MovieEntity) {
            setText(movie)
            setPoster(movie.poster_path)
            setClickListener(movie)
        }

        private fun setText(movie: MovieEntity) {
            binding.titleTextView.text = movie.title
            binding.ratingTextView.text = movie.vote_average.toString()
            binding.yearTextView.text = movie.release_date
        }

        private fun setPoster(path: String?) {
            if (path == null) {
                binding.posterImageView.setImageResource(R.drawable.default_movie_poster)
            } else {
                binding.posterImageView.load("$BASE_POSTERS_PATH$path")
            }
        }

        private fun setClickListener(movie: MovieEntity) {
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(movie)
            }
        }
    }
    fun removeListener() {
        onItemViewClickListener = null
    }
}