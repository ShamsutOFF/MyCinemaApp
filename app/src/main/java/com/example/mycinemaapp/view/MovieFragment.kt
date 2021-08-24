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

    // Временно вручную создадим Мар жанров, потом будем получать с сервера
    val genresMap = mapOf<Int, String>(
        28 to "боевик",
        12 to "приключения",
        16 to "мультфильм",
        35 to "комедия",
        80 to "криминал",
        99 to "документальный",
        18 to "драма",
        10751 to "семейный",
        14 to "фэнтези",
        36 to "история",
        27 to "ужасы",
        10402 to "музыка",
        9648 to "детектив",
        10749 to "мелодрама",
        878 to "фантастика",
        10770 to "телевизионный фильм",
        53 to "триллер",
        10752 to "военный",
        37 to "вестерн"
    )

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
//                movieGenreTextView.text = "Жанр ${it.genre_ids}"

                val subGenresString =
                    buildString { it.genre_ids.forEach { append("\n" + genresMap[it]) } }
                movieGenreTextView.text = "Жанр : $subGenresString"
                movieRatingTextView.text = "Рэйтинг ${it.vote_average}"
                movieReleaseDateTextView.text = "Дата релиза: ${it.release_date}"
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