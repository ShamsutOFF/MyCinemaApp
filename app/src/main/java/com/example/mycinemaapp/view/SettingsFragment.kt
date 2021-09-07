package com.example.mycinemaapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycinemaapp.databinding.FragmentSettingsBinding
import com.example.mycinemaapp.viewmodel.SettingsViewModel

private const val SETTINGS_KEY = "SETTINGS_KEY"

private const val NOW_PLAYING: String = "movie/now_playing"
private const val POPULAR: String = "movie/popular"
private const val TOP_RATED: String = "movie/top_rated"
private const val UPCOMING: String = "movie/upcoming"

private const val AIRING_TODAY_TV: String = "tv/airing_today"
private const val POPULAR_TV: String = "tv/popular"
private const val TOP_RATED_TV: String = "tv/top_rated"

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var allCheckBoxes: List<CheckBox>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        readSettings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allCheckBoxes = listOf(
            binding.checkBoxNowPlaying,
            binding.checkBoxPopular,
            binding.checkBoxTopRated,
            binding.checkBoxUpcoming,
            binding.checkBoxAiringTodayTv,
            binding.checkBoxPopularTv,
            binding.checkBoxTopRatedTv
        )
        allCheckBoxes.forEach { it.setOnClickListener { saveSettings() } }
    }

    private fun readSettings() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val settings = sharedPref?.getStringSet(SETTINGS_KEY, null)
        settings?.forEach {
            when (it) {
                NOW_PLAYING -> {
                    binding.checkBoxNowPlaying.isChecked = true
                }
                POPULAR -> {
                    binding.checkBoxPopular.isChecked = true
                }
                TOP_RATED -> {
                    binding.checkBoxTopRated.isChecked = true
                }
                UPCOMING -> {
                    binding.checkBoxUpcoming.isChecked = true
                }
                AIRING_TODAY_TV -> {
                    binding.checkBoxAiringTodayTv.isChecked = true
                }
                POPULAR_TV -> {
                    binding.checkBoxPopularTv.isChecked = true
                }
                TOP_RATED_TV -> {
                    binding.checkBoxTopRated.isChecked = true
                }
            }
        }
//        val settingsString =
//            buildString { settings?.forEach { append(it) } }
    }

    private fun saveSettings() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        val checkBoxesText = mutableSetOf<String>()
        with(binding) {
            if (checkBoxNowPlaying.isChecked) checkBoxesText.add(NOW_PLAYING)
            if (checkBoxPopular.isChecked) checkBoxesText.add(POPULAR)
            if (checkBoxTopRated.isChecked) checkBoxesText.add(TOP_RATED)
            if (checkBoxUpcoming.isChecked) checkBoxesText.add(UPCOMING)

            if (checkBoxAiringTodayTv.isChecked) checkBoxesText.add(AIRING_TODAY_TV)
            if (checkBoxPopularTv.isChecked) checkBoxesText.add(POPULAR_TV)
            if (checkBoxTopRatedTv.isChecked) checkBoxesText.add(TOP_RATED_TV)
        }
        editor?.putStringSet(SETTINGS_KEY, checkBoxesText)
        editor?.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}