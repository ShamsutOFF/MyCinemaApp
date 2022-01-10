package com.example.mycinemaapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.FragmentFavoritesBinding
import com.example.mycinemaapp.model.MyApplication
import com.example.mycinemaapp.model.items.FavoriteMovieItem
import com.example.mycinemaapp.model.room.MovieEntityRoomDto
import com.example.mycinemaapp.utils.showSnackBar
import com.example.mycinemaapp.viewmodel.FavoritesViewModel
import com.example.mycinemaapp.viewmodel.LoadFavoritesListAppState
import com.example.mycinemaapp.viewmodel.MovieLoadAppState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

private const val TAG: String = "@@@ FavoritesFragment"

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val app by lazy { context?.applicationContext as MyApplication }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        favoritesViewModel.favoritesListLD.observe(viewLifecycleOwner) { loadAllFavorites(it) }
        favoritesViewModel.movieLoadLD.observe(viewLifecycleOwner) { renderData(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
//        favoritesViewModel.favoritesListLD.observe(viewLifecycleOwner) { loadAllFavorites(it) }
        favoritesViewModel.readAllFavoritesRoom(app.roomDb)
//        favoritesViewModel.movieLoadLD.observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(loadAppState: MovieLoadAppState) {
        Log.d(TAG, "renderData() called with: loadAppState = $loadAppState")
        when (loadAppState) {
            is MovieLoadAppState.Success -> {
                val favoriteMovieItem = FavoriteMovieItem(
                    loadAppState.movieDetailData,
                    ::onItemClickFav,
                    loadAppState.character
                )
                Log.d(TAG, "renderData() called with: favoriteMovieItem = $favoriteMovieItem")
                binding.itemsContainer.adapter = adapter.apply { add(favoriteMovieItem) }
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }

    private fun loadAllFavorites(appState: LoadFavoritesListAppState) {
        Log.d(TAG, "loadAllFavorites() called with: appState = $appState")
        when (appState) {
            is LoadFavoritesListAppState.Success -> {
                appState.result.forEach { loadMovie(it) }
                binding.loadingLayout.visibility = View.GONE
            }
            is LoadFavoritesListAppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is LoadFavoritesListAppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                view?.showSnackBar(
                    "Ошибка! ${appState.error.message}",
                    "Перезагрузить",
                    { initViewModel() })
            }
        }
    }

    private fun loadMovie(movie: MovieEntityRoomDto) {
        favoritesViewModel.getMovieEntityFromServer(app.retrofit, movie.character, movie.id)
    }

    private fun onItemClickFav(character: String, id: Int) {
        Log.d(TAG, "onItemClick() called with: character = $character, id = $id")
        activity?.supportFragmentManager?.apply {
            beginTransaction()
//                .add(
//                    R.id.nav_host_fragment_activity_main,
//                    MovieFragment.newInstance(Bundle().apply {
                .add(R.id.container, MovieFragment.newInstance(Bundle().apply {
                        putString(MovieFragment.BUNDLE_CHARACTER, character)
                        putInt(MovieFragment.BUNDLE_ID, id)
                    }))
//                  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) // Тест анимации
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}