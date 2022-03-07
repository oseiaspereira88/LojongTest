package com.empreendapp.lojongtest.ui.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.data.repository.StepRepository

class SelectionStepViewModel (private val dataSource: StepRepository):
    ViewModel() {

    private val steps: MutableLiveData<List<Step>> by lazy {
        MutableLiveData<List<Step>>().also {
            loadSteps()
        }
    }

    fun getSteps(): LiveData<List<Step>> {
        return steps
    }

    fun loadSteps() {

    }

    class StepViewModelFactory(private val stepRepository: StepRepository):
    ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SelectionStepViewModel(stepRepository) as T
        }
    }

}