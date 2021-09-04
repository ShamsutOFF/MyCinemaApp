package com.example.mycinemaapp.view

import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.FragmentMovieBinding
import com.example.mycinemaapp.viewmodel.MovieFragmentAppState
import com.example.mycinemaapp.viewmodel.MovieViewModel


private const val TAG: String = "@@@ MovieFragment"
private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViewModel() {
        movieViewModel.movieDetailLiveDataToObserve.observe(viewLifecycleOwner) { renderData(it) }
        val character = arguments?.getString(BUNDLE_CHARACTER)
        val id = arguments?.getInt(BUNDLE_ID)
        if (character != null && id != null) {
            Log.d(TAG, "initViewModel() called character = $character id = $id")
            movieViewModel.getMovieEntityFromServer(character, id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(movieFragmentAppState: MovieFragmentAppState) {
        when (movieFragmentAppState) {
            is MovieFragmentAppState.Success -> {
                val movie = movieFragmentAppState.movieDetailData
                Log.d(
                    TAG,
                    "renderData() called with: movieFragmentAppState = $movieFragmentAppState"
                )
                with(binding) {
                    loadingLayout.visibility = View.GONE
                    posterImageView.load("$BASE_POSTERS_PATH${movie.posterPath}")
                    movieOverviewTextView.movementMethod = ScrollingMovementMethod()
                    if (movie.title != "") {
                        movieTitleTextView.text = movie.title
                    }
                    if (movie.name != "") {
                        movieTitleTextView.text = movie.name
                    }
                    if (movie.originalTitle != "") {
                        movieTitleOnEnglishTextView.text = movie.originalTitle
                    }
                    if (movie.originalName != "") {
                        movieTitleOnEnglishTextView.text = movie.originalName
                    }
                    if (movie.tagline != "" && movie.tagline!=null) {
                        movieTaglineTextView.text = getString((R.string.tagline), movie.tagline)
                    }
                    val subGenresString =
                        buildString { movie.genres.forEach { append("\n" + it.name) } }
                    if (subGenresString != "") {
                        movieGenreTextView.text =
                            resources.getString((R.string.genres), subGenresString)
                    }
                    if (movie.voteAverage != 0.0 && movie.voteAverage!=null) {
                        movieRatingTextView.text =
                            resources.getString(
                                (R.string.vote_average),
                                movie.voteAverage.toString()
                            )
                    }
                    if (movie.releaseDate != "" && movie.releaseDate !=null) {
                        movieReleaseDateTextView.text =
                            resources.getString((R.string.release_date), movie.releaseDate)
                    }
                    movieOverviewTextView.text = movie.overview
                }
            }
            is MovieFragmentAppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is MovieFragmentAppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
//                view?.showSnackBar("Ошибка!", "Перезагрузить", { initViewModel() })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_CHARACTER = "character"
        const val BUNDLE_ID = "id"
        fun newInstance(bundle: Bundle): MovieFragment {
            val fragment = MovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}