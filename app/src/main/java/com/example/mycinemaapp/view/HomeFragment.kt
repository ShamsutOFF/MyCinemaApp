package com.example.mycinemaapp.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.FragmentHomeBinding
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.items.MainCardContainer
import com.example.mycinemaapp.model.items.MovieItem
import com.example.mycinemaapp.utils.showSnackBar
import com.example.mycinemaapp.view.MovieFragment.Companion.BUNDLE_CHARACTER
import com.example.mycinemaapp.view.MovieFragment.Companion.BUNDLE_ID
import com.example.mycinemaapp.viewmodel.AppState
import com.example.mycinemaapp.viewmodel.HomeViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder


private const val TAG: String = "@@@ HomeFragment"
private const val SETTINGS_KEY = "SETTINGS_KEY"

private const val MOVIE: String = "movie"
private const val DEFAULT_CATEGORY: String = "movie/now_playing"
private const val NOW_PLAYING: String = "now_playing"
private const val POPULAR: String = "popular"
private const val TOP_RATED: String = "top_rated"
private const val UPCOMING: String = "upcoming"
private const val AIRING_TODAY_TV: String = "airing_today"

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val binding get() = _binding!!

    private val app by lazy { context?.applicationContext as MyApplication }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onItemClick(character:String, id:Int) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
//                .add(R.id.nav_host_fragment_activity_main, MovieFragment.newInstance(Bundle().apply {
                .add(R.id.container, MovieFragment.newInstance(Bundle().apply {
                    putString(BUNDLE_CHARACTER, character)
                    putInt(BUNDLE_ID, id)
                }))
//                  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) // Тест анимации
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViewModel() {
        homeViewModel.movieLoadingLiveData.observe(viewLifecycleOwner) { renderData(it) }
        val settings = readSettings()
        loadMovies(settings)
    }

    private fun loadMovies(settings: MutableSet<String>?) {
        settings?.forEach {
            val separated = it.split("/").toTypedArray()
            homeViewModel.loadMoviesListFromServer( app.retrofit, separated[0], separated[1])
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessOneList -> {
                val moviesItemList = mutableListOf<MovieItem>()
                appState.movieEntityList.forEach {
                    moviesItemList.add(
                        MovieItem(
                            it,
                            ::onItemClick,
                            appState.character
                        )
                    )
                }
                val mainCardContainer = MainCardContainer(
                    getMovieType(appState.character, appState.typeOfMovies),
                    "description will be here",
                    ::onItemClick,
                    moviesItemList
                )
                binding.itemsContainer.adapter = adapter.apply { add(mainCardContainer) }
                binding.loadingLayout.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                view?.showSnackBar(
                    "Ошибка! ${appState.error.message}",
                    "Перезагрузить",
                    { initViewModel() })
            }
        }
    }

    private fun getMovieType(character: String, typeOfMovies: String): String {
        if (character == MOVIE) {
            return when (typeOfMovies) {
                UPCOMING -> {
                    resources.getString(R.string.movies) + resources.getString(R.string.upcoming)
                }
                NOW_PLAYING -> {
                    resources.getString(R.string.movies) + resources.getString(R.string.now_playing)
                }
                POPULAR -> {
                    resources.getString(R.string.movies) + resources.getString(R.string.popular)
                }
                TOP_RATED -> {
                    resources.getString(R.string.movies) + resources.getString(R.string.top_rated)
                }
                else -> "Unknown type"
            }
        }else{
            return when (typeOfMovies) {
                AIRING_TODAY_TV -> {
                    resources.getString(R.string.tv) + resources.getString(R.string.tv_airing_today)
                }
                NOW_PLAYING -> {
                    resources.getString(R.string.tv) + resources.getString(R.string.now_playing)
                }
                POPULAR -> {
                    resources.getString(R.string.tv) + resources.getString(R.string.popular)
                }
                TOP_RATED -> {
                    resources.getString(R.string.tv) + resources.getString(R.string.top_rated)
                }
                else -> "Unknown type"
            }
        }
    }

    private fun readSettings(): MutableSet<String>? {
        val defSet = setOf(DEFAULT_CATEGORY)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        var settings = sharedPref?.getStringSet(SETTINGS_KEY, defSet)
        if (settings?.isEmpty() == true || settings == null) {
            val checkBoxesText = mutableSetOf<String>()
            checkBoxesText.add(DEFAULT_CATEGORY)
            editor?.putStringSet(SETTINGS_KEY, checkBoxesText)
            editor?.apply()
            settings = sharedPref?.getStringSet(SETTINGS_KEY, defSet)
        }
        return settings
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView() called")
        super.onDestroyView()
        _binding = null
    }
}