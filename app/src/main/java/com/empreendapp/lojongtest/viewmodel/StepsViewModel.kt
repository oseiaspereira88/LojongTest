package com.empreendapp.lojongtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empreendapp.lojongtest.data.repository.StepApiDataSource
import com.empreendapp.lojongtest.model.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepsViewModel(private val stepApiDataSource: StepApiDataSource) :
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            stepApiDataSource.getSteps()
        }
    }

    val steps: LiveData<List<Step>>
        get() = stepApiDataSource.steps


}