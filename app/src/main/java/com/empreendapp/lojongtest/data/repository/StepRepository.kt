package com.empreendapp.lojongtest.data.repository

import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.ui.selection.StepViewParams

interface StepRepository {

    fun createStep(stepViewParams: StepViewParams)

    fun getStep(id: Long): Step?

}