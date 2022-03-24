package com.example.weathertest.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentSettingsBinding
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
            viewModel.getFlow().collect { errorMsg ->
                showErrorSnackBar(errorMsg) {
                    viewModel.load()
                }
            }
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

    /*override fun onDestroyView() {
        super.onDestroyView()
        currentJob?.cancel()
        viewModel.clearJob()
    }*/
}