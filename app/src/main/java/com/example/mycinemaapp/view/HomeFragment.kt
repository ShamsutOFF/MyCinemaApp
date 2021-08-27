package com.example.mycinemaapp.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.FragmentHomeBinding
import com.example.mycinemaapp.model.movieEntitys.MovieEntity
import com.example.mycinemaapp.model.paging.MovieRepository
import com.example.mycinemaapp.model.paging.MoviesAdapter
import com.example.mycinemaapp.utils.showSnackBar
import com.example.mycinemaapp.viewmodel.AppState
import com.example.mycinemaapp.viewmodel.DataLoadingState
import com.example.mycinemaapp.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG: String = "@@@ HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val adapterPlayNow = MoviesAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: MovieEntity) {
            activity?.supportFragmentManager?.apply {
                Log.d(TAG, "onItemViewClick() called")
                beginTransaction()
                    .add(R.id.container, MovieFragment.newInstance(Bundle().apply {
                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })
    private val adapterUpcoming = UpcomingAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: MovieEntity) {
            Log.d(TAG, "onItemViewClick() called with: movie = $movie")
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, MovieFragment.newInstance(Bundle().apply {
                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(
            TAG,
            "onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState"
        )
        val mainViewModelFactory = MainViewModelFactory(MovieRepository)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Запрашиваем фильмы
        fetchMovies()

        homeViewModel.movieLoadingStateLiveData.observe(viewLifecycleOwner) {
            onMovieLoadingStateChanged(it)
        }
        return binding.root
    }

    // Получение списка фильмов
    private fun fetchMovies() {
        lifecycleScope.launch {
            // Так как collectLatest suspend - функция то вызывать мы можем ее только через корутин билдер
            // В данном случае используется корутин билдер lifecycleScope.launch
            homeViewModel.getMovies().collectLatest { it ->
                adapterPlayNow.submitData(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initViewModel()
    }

    private fun initRecycler() {
        Log.d(TAG, "initRecycler() called")
        with(binding) {
            nowPlayingRecyclerView.adapter = adapterPlayNow
            nowPlayingRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            upcomingRecyclerView.adapter = adapterUpcoming
            upcomingRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViewModel() {
        Log.d(TAG, "initViewModel() called")
//        homeViewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        homeViewModel.getMovies()
//        homeViewModel.getMoviesListFromServer()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(appState: AppState) {
        Log.d(TAG, "renderData() called with: appState = $appState")
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapterPlayNow.setData(appState.movieDataPlay)
                adapterPlayNow.notifyDataSetChanged()
                adapterUpcoming.setData(appState.movieDataCome)
                adapterUpcoming.notifyDataSetChanged()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                view?.showSnackBar("Ошибка!", "Перезагрузить", { initViewModel() })
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: MovieEntity)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView() called")
        super.onDestroyView()
        adapterPlayNow.removeListener()
        adapterUpcoming.removeListener()
        _binding = null
    }

    private fun onMovieLoadingStateChanged(state: DataLoadingState) {
        binding.loadingLayout.visibility =
            if (state == DataLoadingState.LOADING) View.VISIBLE else View.GONE
    }
}
class MainViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}