package com.empreendapp.lojongtest.model

import com.empreendapp.lojongtest.data.db.entity.StepEntity
import java.util.*

data class Step(
    internal val id: Long,
    var text: String,
    internal val v: Int,
    internal val source: String,
    internal val updatedAt: Date,
    internal val type: String,
    internal val createdAt: Date,
    internal val deleted: Boolean,
    internal val used: Boolean,
    internal val stepStatus: StepStatus?
)

fun StepEntity.toStep(): Step {
    return Step(
        id = this.id,
        text = this.text,
        v = this.v,
        source = this.source,
        updatedAt = this.updatedAt,
        type = this.type,
        createdAt = this.createdAt,
        deleted = this.deleted,
        used = this.used,
        stepStatus = this.stepStatus
    )
}