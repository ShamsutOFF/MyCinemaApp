package com.example.mycinemaapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mycinemaapp.databinding.FragmentMovieBinding
import com.example.mycinemaapp.model.MovieEntity

private const val TAG: String = "@@@ MovieFragment"

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<MovieEntity>(BUNDLE_EXTRA)
        if (movie != null){
            binding.movieTitleTextView.text = movie.title
            binding.movieRatingTextView.text = movie.voteAverage.toString()
            binding.movieReleaseDateTextView.text = movie.releaseDate
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