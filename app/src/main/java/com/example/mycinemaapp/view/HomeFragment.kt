package com.example.mycinemaapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycinemaapp.R
import com.example.mycinemaapp.databinding.FragmentHomeBinding
import com.example.mycinemaapp.model.MovieEntity
import com.example.mycinemaapp.viewmodel.AppState
import com.example.mycinemaapp.viewmodel.HomeViewModel

private const val TAG: String = "@@@ HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val adapterPlayNow = NowPlayingAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: MovieEntity) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                manager.beginTransaction()
                    .add(R.id.container, MovieFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })
    private val adapterUpcoming = UpcomingAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: MovieEntity) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                manager.beginTransaction()
                    .add(R.id.container, MovieFragment.newInstance(bundle))
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
    ): View? {
        Log.d(
            TAG,
            "onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState"
        )

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )

        super.onViewCreated(view, savedInstanceState)
        // Инициализация данных
        initRecycler()
        initViewModel()
    }

    private fun initRecycler() {
        Log.d(TAG, "initRecycler() called")
        // Создаем два списка
        binding.nowPlayingRecyclerView.adapter = adapterPlayNow
        binding.nowPlayingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.upcomingRecyclerView.adapter = adapterUpcoming
        binding.upcomingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initViewModel() {
        Log.d(TAG, "initViewModel() called")
        homeViewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        homeViewModel.getDataFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        Log.d(TAG, "renderData() called with: appState = $appState")
        //Заполняем списки
        when (appState) {
            is AppState.Success -> {
                val movieDataPlay = appState.movieDataPlay
                val movieDataCome = appState.movieDataCome
                adapterPlayNow.setData(movieDataPlay)
                adapterPlayNow.notifyDataSetChanged()
                adapterUpcoming.setData(movieDataCome)
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
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
}
