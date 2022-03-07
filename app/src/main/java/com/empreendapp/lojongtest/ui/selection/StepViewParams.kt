package com.empreendapp.lojongtest.ui.selection

import com.empreendapp.lojongtest.model.StepStatus
import java.util.Date

data class StepViewParams(
    var id: Long,
    var text: String,
    var v: Int,
    var source: String,
    var updatedAt: Date,
    var type: String,
    var createdAt: Date,
    var deleted: Boolean,
    var used: Boolean,
    var stepStatus: StepStatus
)