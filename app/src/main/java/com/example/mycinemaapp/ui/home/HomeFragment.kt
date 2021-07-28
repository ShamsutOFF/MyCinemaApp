package com.example.mycinemaapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycinemaapp.AppState
import com.example.mycinemaapp.NowPlayingAdapter
import com.example.mycinemaapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val adapterPlayNow = NowPlayingAdapter()
    private val binding get() = _binding!!
    private val TAG: String = "@@@ HomeFragment"

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
//        binding.upcomingRecyclerView.adapter = adapterUpcoming
//        val itemDecoration = dividerItemDecoration()
//        binding.recyclerPlaying.addItemDecoration(itemDecoration)
//        binding.recyclerUpcoming.addItemDecoration(itemDecoration)
    }

    private fun initViewModel() {
        Log.d(TAG, "initViewModel() called")

        //TODO Доразобраться с этого момента
//        homeViewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
//        homeViewModel.getDataFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        Log.d(TAG, "renderData() called with: appState = $appState")
        //Заполняем списки
//        val loadingLayout = binding.loadingLayout
//        val mainView = binding.mainView
        when (appState) {
            is AppState.Success -> {
                val movieDataPlay = appState.movieDataPlay
//                val movieDataCome = appState.movieDataCome
//                loadingLayout.visibility = View.GONE
                adapterPlayNow.setData(movieDataPlay)
//                adapterUpcoming.setData(movieDataCome)
            }
            is AppState.Loading -> {
//                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
//                loadingLayout.visibility = View.GONE
//                Snackbar
//                    .make(homeViewModel, "Error", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Reload") { homeViewModel.getDataFromLocalSource() }
//                    .show()
            }
        }
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView() called")
        super.onDestroyView()
        _binding = null
    }
}