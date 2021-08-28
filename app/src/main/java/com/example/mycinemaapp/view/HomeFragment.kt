package com.example.mycinemaapp.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.FragmentHomeForGroupieBinding
import com.example.mycinemaapp.model.entitys.MovieEntity
import com.example.mycinemaapp.model.items.MainCardContainer
import com.example.mycinemaapp.model.items.MovieItem
import com.example.mycinemaapp.viewmodel.AppState
import com.example.mycinemaapp.viewmodel.HomeViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

private const val TAG: String = "@@@ HomeFragment"
private const val UPCOMING: String = "upcoming"
private const val NOW_PLAYING: String = "now_playing"
private const val TOP_RATED: String = "top_rated"

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeForGroupieBinding? = null
    private val adapter =  GroupAdapter<GroupieViewHolder>()

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeForGroupieBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun onItemClick(movie: MovieEntity) {
        Toast.makeText(context,movie.id,Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, MovieFragment.newInstance(Bundle().apply {
                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                    }))
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
        Log.d(TAG, "initViewModel() called")
        homeViewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        homeViewModel.getOneMoviesListFromServer(UPCOMING)
        homeViewModel.getOneMoviesListFromServer(NOW_PLAYING)
        homeViewModel.getOneMoviesListFromServer(TOP_RATED)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(appState: AppState) {
        Log.d(TAG, "renderData() called with: appState = $appState")
        when (appState) {
            is AppState.SuccessOneList -> {
                var moviesItemList = mutableListOf<MovieItem>()
                appState.movieEntityList.forEach { moviesItemList.add(MovieItem(it)) }
                val mainCardContainer = MainCardContainer("Список фильмов", appState.typeOfMovies, ::onItemClick,moviesItemList )
//                val movies = mutableListOf<MainCardContainer>()
//                movies.add(mainCardContainer)
                binding.itemsContainer.adapter = adapter.apply { add (mainCardContainer) }
            }
            is AppState.Success -> {//
//                binding.loadingLayout.visibility = View.GONE
//                adapterPlayNow.setData(appState.movieDataPlay)
//                adapterPlayNow.notifyDataSetChanged()
//                adapterUpcoming.setData(appState.movieDataCome)
//                adapterUpcoming.notifyDataSetChanged()
            }
            is AppState.Loading -> {
//                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
//                binding.loadingLayout.visibility = View.GONE
//                view?.showSnackBar("Ошибка!" , "Перезагрузить", {initViewModel()} )
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: MovieEntity)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView() called")
        super.onDestroyView()
//        adapterPlayNow.removeListener()
//        adapterUpcoming.removeListener()
        _binding = null
    }
}