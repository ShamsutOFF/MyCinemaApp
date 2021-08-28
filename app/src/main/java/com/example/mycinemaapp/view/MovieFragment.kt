package com.example.mycinemaapp.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mycinemaapp.databinding.FragmentMovieBinding
import com.example.mycinemaapp.model.entitys.MovieEntity
import com.example.mycinemaapp.utils.showSnackBar
import com.example.mycinemaapp.viewmodel.HomeViewModel
import com.example.mycinemaapp.viewmodel.MovieFragmentAppState
import com.example.mycinemaapp.viewmodel.MovieViewModel

private const val TAG: String = "@@@ MovieFragment"
private const val BASE_POSTERS_PATH = "https://image.tmdb.org/t/p/w500/"

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<MovieEntity>(BUNDLE_EXTRA)?.id?.let {
            initViewModel(
                it
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViewModel(id: Int) {
        Log.d(TAG, "initViewModel() called")
        movieViewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        movieViewModel.getMovieEntityFromServer(id)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(movieFragmentAppState: MovieFragmentAppState) {
        when (movieFragmentAppState) {
            is MovieFragmentAppState.Success -> {
                val movie = movieFragmentAppState.movieDetailData

                with(binding) {
                    loadingLayout.visibility = View.GONE
                    posterImageView.load("$BASE_POSTERS_PATH${movie.poster_path}")
                    movieTitleTextView.text = movie.title
                    movieTitleOnEnglishTextView.text = movie.original_title
                    movieTaglineTextView.text = "Слоган: ${movie.tagline}"

                    val subGenresString =
                        buildString { movie.genres.forEach { append("\n" + it.name) } }
                    movieGenreTextView.text = "Жанр : $subGenresString"

                    movieRatingTextView.text = "Рэйтинг ${movie.vote_average}"
                    movieReleaseDateTextView.text = "Дата релиза: ${movie.release_date}"
                    movieOverviewTextView.text = movie.overview
                }
            }
            is MovieFragmentAppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is MovieFragmentAppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                view?.showSnackBar("Ошибка!" , "Перезагрузить", {initViewModel(id) } )
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