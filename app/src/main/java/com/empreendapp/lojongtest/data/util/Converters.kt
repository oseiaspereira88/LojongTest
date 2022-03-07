package com.empreendapp.lojongtest.data.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.empreendapp.lojongtest.data.db.entity.StepEntity
import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.model.StepStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time ?: 0
    }

    @TypeConverter
    fun fromJson(value: String): StepStatus =
        Gson().fromJson(value, object : TypeToken<StepStatus>() {}.type)

    @TypeConverter
    fun statusToJson(stepStatus: StepStatus): String = Gson().toJson(stepStatus)

}