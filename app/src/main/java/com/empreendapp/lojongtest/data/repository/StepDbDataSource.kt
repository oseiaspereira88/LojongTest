package com.empreendapp.lojongtest.data.repository

import com.empreendapp.lojongtest.data.db.dao.StepDao
import com.empreendapp.lojongtest.data.db.entity.StepEntity
import com.empreendapp.lojongtest.data.db.entity.toStepEntity
import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.model.toStep
import com.empreendapp.lojongtest.ui.selection.StepViewParams

class StepDbDataSource(private val stepDao: StepDao): StepRepository {
    override fun createStep(stepViewParams: StepViewParams) {
        stepDao.insert(stepViewParams.toStepEntity() as StepEntity)
    }

    override fun getStep(id: Long): Step? {
        return stepDao.findById(id)?.toStep() as Step
    }
}