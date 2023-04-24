package com.example.weathertest.views

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentWeatherOnDayBinding
import com.example.weathertest.models.ApiWeatherResult
import com.example.weathertest.viewModels.WeatherOnDayViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherOnDayFragment : Fragment(R.layout.fragment_weather_on_day) {

    private val binding: FragmentWeatherOnDayBinding by viewBinding(FragmentWeatherOnDayBinding::bind)
    private val viewModel: WeatherOnDayViewModel by viewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setUpFlow()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        init()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        viewModel.clearJob()
//    }

    override fun onResume() {
        super.onResume()
        viewModel.login()
//        viewModel.connectToHub()
        lifecycleScope.launch {
            viewModel.loginFlow.collect {
                binding.LoginTV.text = "login is $it"
            }
            viewModel.connectFlow.collect{
                binding.sendMailButton.text = "hub state = $it"
            }
            viewModel.msgFlow.collect{
                binding.msgRecieved.text = it
            }
        }

        binding.LoginTV.setOnClickListener { viewModel.login() }
        binding.sendMailButton.setOnClickListener {}
        binding.sendMsgButton.setOnClickListener {
            viewModel.sendMessage()
        }
    }


//    private fun setUpFlow() {
//        lifecycleScope.launch {
//            viewModel.getFlow().flowWithLifecycle(
//                lifecycle, Lifecycle.State.STARTED
//            ).collect { result ->
//                when (result) {
//                    is ApiWeatherResult.Loading -> {
//                        showProgressBar(true)
//                    }
//                    is ApiWeatherResult.Success -> {
//                        binding.weatherTv.text = result.weather?.current.toString()
//                        showProgressBar(false)
//                    }
//                    is ApiWeatherResult.Error -> {
//                        showErrorSnackBar(result.exception) {
//                            viewModel.load()
//                        }
//                        showProgressBar(false)
//                    }
//                }
//            }
//        }
//    }

//    private fun init() {
//        binding.weekForecastButton.setOnClickListener {
//            findNavController().navigate(R.id.action_weatherOnDayFragment_to_weatherByDayFragment)
//        }
//        binding.sendMailButton.setOnClickListener {
//            viewModel.searchWeather(requireContext())
//        }
//        viewModel.load()
//    }
//
//    private fun showErrorSnackBar(errorMsg: String, callBack: () -> Unit) {
//        Snackbar.make(
//            binding.frameLayoutContainer,
//            errorMsg,
//            Snackbar.LENGTH_LONG
//        ).setAction(
//            "retry"
//        ) {
//            it.visibility = View.INVISIBLE
//            callBack()
//        }.show()
//    }
//
//    private fun showProgressBar(loading: Boolean) {
//        binding.progressBar.isVisible = loading
//        binding.weatherTv.isVisible = !loading
//    }
}