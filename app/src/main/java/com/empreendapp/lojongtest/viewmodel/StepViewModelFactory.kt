package com.empreendapp.lojongtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.empreendapp.lojongtest.data.repository.StepApiDataSource

class StepViewModelFactory(private val stepApiDataSource: StepApiDataSource):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StepsViewModel(stepApiDataSource) as T
    }

}