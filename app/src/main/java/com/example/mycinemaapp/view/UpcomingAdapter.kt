package com.example.mycinemaapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.ItemUpcomingBinding
import com.example.mycinemaapp.model.MovieEntity

private const val TAG: String = "@@@ NowPlayingAdapter"
private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class UpcomingAdapter(
    private var onItemViewClickListener:
    HomeFragment.OnItemViewClickListener?
) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {

    private var movieData: List<MovieEntity> = emptyList()

    fun setData(data: List<MovieEntity>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_upcoming, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUpcomingBinding.bind(itemView)

        fun bind(movie: MovieEntity) {
            setText(movie)
            setPoster(movie.poster_path)
            setClickListener(movie)
        }

        private fun setText(movie: MovieEntity) {
            binding.titleTextView.text = movie.title
            binding.yearTextView.text = movie.release_date.toString()
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