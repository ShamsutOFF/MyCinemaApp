package com.example.mycinemaapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mycinemaapp.databinding.ItemUpcomingBinding

class UpcomingAdapter : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {

    private var movieData: List<MovieEntity> = emptyList()
    private val TAG: String = "@@@ NowPlayingAdapter"

    fun setData(data: List<MovieEntity>) {
        Log.d(TAG, "setData() called with: data = $data")
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_upcoming, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() called with: holder = $holder, position = $position")
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount() called")
        return movieData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUpcomingBinding.bind(itemView)

        fun bind(movie: MovieEntity) {
            Log.d(TAG, "bind() called with: movie = $movie")
            setText(movie)
            setPoster(movie.posterPath)
        }

        private fun setText(movie: MovieEntity) {
            Log.d(TAG, "setText() called with: movie = $movie")
            binding.titleTextView.text = movie.title
            binding.yearTextView.text = movie.releaseDate
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    movie.title,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        private fun setPoster(path: String?) {
            Log.d(TAG, "setPoster() called with: path = $path")
            if (path == null) {
                binding.posterImageView.setImageResource(R.drawable.default_movie_poster)
            } else {
                //todo set image via glide
            }
        }
    }

}