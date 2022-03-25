package com.example.weathertest.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentSettingsBinding
import com.example.weathertest.models.ApiCityResult
import com.example.weathertest.utils.textChangedFlow
import com.example.weathertest.viewModels.SettingsViewModel
import com.example.weathertest.viewModels.WeatherByDayViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getFlow().collect { result ->
                when (result) {
                    is ApiCityResult.Loading -> {
                        showProgressBar(true)
                    }
                    is ApiCityResult.Success -> {
//                        Toast.makeText(requireContext(), result.cities?.size.toString() , Toast.LENGTH_SHORT).show()
//                        recycler
                        val a = result.cities!!.random().apply { isCurrent = true }
                        viewModel.saveCity(a)
                        Toast.makeText(requireContext(), a.name, Toast.LENGTH_SHORT).show()
                        showProgressBar(false)
                    }
                    is ApiCityResult.Error -> {
                        showProgressBar(false)
                        showErrorSnackBar(result.exception) {
                            viewModel.load()
                        }
                    }
                }
            }
        }

        binding.button.setOnClickListener {
            viewModel.clearJob()
        }

        viewModel.setFlowForSearch(binding.cityEditText.textChangedFlow())
        viewModel.load()
    }

    private fun showErrorSnackBar(errorMsg: String, callBack: () -> Unit) {
        Snackbar.make(
            binding.mainContainer,
            errorMsg,
            Snackbar.LENGTH_LONG
        ).setAction(
            "retry"
        ) {
            it.visibility = View.INVISIBLE
            callBack()
        }.show()
    }

    private fun showProgressBar(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.citiesRecyclerView.isVisible = !loading
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearJob()
    }
}