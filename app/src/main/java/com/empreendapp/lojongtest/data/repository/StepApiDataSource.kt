package com.empreendapp.lojongtest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.empreendapp.lojongtest.api.ApiInterface
import com.empreendapp.lojongtest.model.Step

class StepApiDataSource(private val apiInterface: ApiInterface){

    private val stepsLiveData = MutableLiveData<List<Step>>()

    val steps: LiveData<List<Step>>
        get() = stepsLiveData

    suspend fun getSteps(){
        val result = apiInterface.getSteps()

        if(result.body() != null){
            stepsLiveData.postValue(result.body())
        }
    }

}