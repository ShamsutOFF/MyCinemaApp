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
import com.example.mycinemaapp.databinding.FragmentHomeForGroupieBinding
import com.example.mycinemaapp.model.MovieEntity
import com.example.mycinemaapp.model.items.MainCardContainer
import com.example.mycinemaapp.model.items.MovieContent
import com.example.mycinemaapp.model.items.MovieItem
import com.example.mycinemaapp.viewmodel.HomeViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

private const val TAG: String = "@@@ HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeForGroupieBinding? = null

    val movies = listOf(
        getPopularMovies()
    )



    private fun getPopularMovies(): Item {
        return MainCardContainer(
            "Список фильмов", "Лучшие фильмы", ::onItemClick,
            listOf(
                MovieItem(
                    MovieContent(
                        "Джокер",
                        "https://upload.wikimedia.org/wikipedia/en/e/e1/Joker_%282019_film%29_poster.jpg"
                    )
                ),
                MovieItem(
                    MovieContent(
                        "Бладшот",
                        "https://upload.wikimedia.org/wikipedia/en/4/45/Bloodshot_-_official_film_poster.jpeg"
                    )
                ),
                MovieItem(
                    MovieContent(
                        "Джентельмены",
                        "https://upload.wikimedia.org/wikipedia/en/0/06/The_Gentlemen_poster.jpg"
                    )
                ),
                MovieItem(
                    MovieContent(
                        "Ford против Ferrari",
                        "https://upload.wikimedia.org/wikipedia/en/a/a4/Ford_v._Ferrari_%282019_film_poster%29.png"
                    )
                ),
                MovieItem(
                    MovieContent(
                        "Бойцовский клуб",
                        "https://upload.wikimedia.org/wikipedia/ru/8/8a/Fight_club.jpg"
                    )
                )
            )
        )
    }

    //    private val adapterPlayNow = NowPlayingAdapter(object : OnItemViewClickListener {
//        override fun onItemViewClick(movie: MovieEntity) {
//            activity?.supportFragmentManager?.apply {
//                Log.d(TAG, "onItemViewClick() called")
//                beginTransaction()
//                    .add(R.id.container, MovieFragment.newInstance(Bundle().apply {
//                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
//                    }))
//                    .addToBackStack("")
//                    .commitAllowingStateLoss()
//            }
//        }
//    })
//    private val adapterUpcoming = UpcomingAdapter(object : OnItemViewClickListener {
//        override fun onItemViewClick(movie: MovieEntity) {
//            Log.d(TAG, "onItemViewClick() called with: movie = $movie")
//            activity?.supportFragmentManager?.apply {
//                beginTransaction()
//                    .add(R.id.container, MovieFragment.newInstance(Bundle().apply {
//                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
//                    }))
//                    .addToBackStack("")
//                    .commitAllowingStateLoss()
//            }
//        }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {





        Log.d(TAG,"onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState")
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeForGroupieBinding.inflate(inflater, container, false)
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }




    fun onItemClick(url: String) {
    }

//    })

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG,"onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState")
        super.onViewCreated(view, savedInstanceState)
        binding.itemsContainer.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(movies) }
//        initRecycler()
//        initViewModel()
    }

//    private fun initRecycler() {
//        Log.d(TAG, "initRecycler() called")
//        with(binding) {
//            nowPlayingRecyclerView.adapter = adapterPlayNow
//            nowPlayingRecyclerView.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            upcomingRecyclerView.adapter = adapterUpcoming
//            upcomingRecyclerView.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun initViewModel() {
//        Log.d(TAG, "initViewModel() called")
//        homeViewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
//        homeViewModel.getMoviesListFromServer()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun renderData(appState: AppState) {
//        Log.d(TAG, "renderData() called with: appState = $appState")
//        when (appState) {
//            is AppState.Success -> {
//
//                binding.loadingLayout.visibility = View.GONE
//                adapterPlayNow.setData(appState.movieDataPlay)
//                adapterPlayNow.notifyDataSetChanged()
//                adapterUpcoming.setData(appState.movieDataCome)
//                adapterUpcoming.notifyDataSetChanged()
//            }
//            is AppState.Loading -> {
//                binding.loadingLayout.visibility = View.VISIBLE
//            }
//            is AppState.Error -> {
//                binding.loadingLayout.visibility = View.GONE
//                view?.showSnackBar("Ошибка!" , "Перезагрузить", {initViewModel()} )
//            }
//        }
//    }

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