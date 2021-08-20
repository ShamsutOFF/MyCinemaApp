package com.example.mycinemaapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.mycinemaapp.databinding.FragmentMovieBinding
import com.example.mycinemaapp.model.MovieEntity

private const val TAG: String = "@@@ MovieFragment"
private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(
            TAG,
            "onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState"
        )
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<MovieEntity>(BUNDLE_EXTRA)?.let {
            with(binding) {
                posterImageView.load("$BASE_POSTERS_PATH${it.poster_path}")
                movieTitleTextView.text = it.title
                movieTitleOnEnglishTextView.text = it.original_title
                movieGenreTextView.text = "Жанр ${it.genre_ids}"
                movieRatingTextView.text = "Рэйтинг ${it.vote_average}"
                movieReleaseDateTextView.text = it.release_date
                movieOverviewTextView.text = it.overview
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): MovieFragment {
            val fragment = MovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}