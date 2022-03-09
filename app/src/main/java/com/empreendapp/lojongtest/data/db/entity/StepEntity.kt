package com.empreendapp.lojongtest.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.model.StepStatus
import com.empreendapp.lojongtest.ui.selection.StepViewParams
import java.util.Date

@Entity(indices = [Index("text")])
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var v: Int,
    var source: String,
    var updatedAt: Date,
    var type: String,
    var createdAt: Date,
    var deleted: Boolean,
    var used: Boolean,
    var stepStatus: StepStatus?
) {
    fun isExpired(): Boolean = (Date().time - createdAt.time < (86400 * 1000))
}

fun StepViewParams.toStepEntity(): StepEntity {
    return StepEntity(
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

fun Step.toStepEntity(): StepEntity {
    return StepEntity(
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