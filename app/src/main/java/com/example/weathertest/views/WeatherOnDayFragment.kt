package com.example.weathertest.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentWeatherOnDayBinding
import com.example.weathertest.models.Weather
import com.example.weathertest.viewModels.WeatherOnDayViewModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherOnDayFragment : Fragment(R.layout.fragment_weather_on_day) {

    private val binding: FragmentWeatherOnDayBinding by viewBinding(FragmentWeatherOnDayBinding::bind)
    private val viewModel: WeatherOnDayViewModel by viewModels()
    private var currentJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weekForecastButton.setOnClickListener {
            findNavController().navigate(R.id.action_weatherOnDayFragment_to_weatherByDayFragment)
        }

//        binding.button.setOnClickListener {
//            viewModel.load()
//        }

//        binding.button.setOnClickListener {
//            currentJob = lifecycleScope.launch {
//                viewModel.getFlow().collect {
//
//                }
//            }
//        }
        currentJob = lifecycleScope.launch {
            viewModel.getFlow().collect {
                binding.weatherTv.text = it
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentJob?.cancel()
        viewModel.clearJob()
    }
}