package com.empreendapp.lojongtest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.empreendapp.lojongtest.data.db.entity.StepEntity

@Dao
interface StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stepEntity: StepEntity): Long

    @Update
    fun update(stepEntity: StepEntity): Int

    @Delete
    fun delete(vararg stepEntity: StepEntity): Int

    @Query("SELECT * FROM StepEntity WHERE id = :id")
    fun findById(id: Long): StepEntity?

    @Query("SELECT * FROM StepEntity WHERE text LIKE :text ORDER BY id")
    fun findByText(text: String): List<StepEntity>

    @Query("SELECT * FROM StepEntity")
    fun findAll(): List<StepEntity>



}