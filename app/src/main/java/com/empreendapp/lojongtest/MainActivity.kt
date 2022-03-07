package com.empreendapp.lojongtest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.empreendapp.lojongtest.adapter.StepAdapter
import com.empreendapp.lojongtest.api.ApiInterface
import com.empreendapp.lojongtest.api.ApiUtilities
import com.empreendapp.lojongtest.data.db.dao.StepDao
import com.empreendapp.lojongtest.data.db.AppDatabase
import com.empreendapp.lojongtest.model.StepStatus
import com.empreendapp.lojongtest.data.db.entity.StepEntity
import com.empreendapp.lojongtest.data.db.entity.toStepEntity
import com.empreendapp.lojongtest.data.repository.StepApiDataSource
import com.empreendapp.lojongtest.data.repository.StepDbDataSource
import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.model.toStep
import com.empreendapp.lojongtest.ui.selection.SelectionStepViewModel
import com.empreendapp.lojongtest.viewmodel.StepViewModelFactory
import com.empreendapp.lojongtest.viewmodel.StepsViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var rv: RecyclerView? = null
    var db: AppDatabase? = null
    var dao: StepDao? = null
    var adapter: StepAdapter? = null
    var steps: ArrayList<Step>? = null
    var stepsViewModel: StepsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDB(savedInstanceState)
        initViews()
        initApi(savedInstanceState)
    }

    private fun initDB(savedInstanceState: Bundle?) {
        db = AppDatabase.getDatabase(this)
        dao = db!!.stepDao()
    }

    private fun initViews(){
        rv = findViewById(R.id.rv)
        rv!!.layoutManager = LinearLayoutManager(this)
        rv!!.itemAnimator = DefaultItemAnimator()

        steps = ArrayList()
        adapter = StepAdapter(this, steps!!, dao!!)

        findViewById<Button>(R.id.bCreate).setOnClickListener {
            val status = StepStatus(false, 0)
            dao!!.insert(
                Step(
                    (rv!!.adapter!!.itemCount + 1).toLong(),
                    findViewById<EditText>(R.id.editStepText).text.toString()
                            + (rv!!.adapter!!.itemCount + 1).toString(),
                    0,
                    "",
                    Date(),
                    "",
                    Date(),
                    false,
                    false,
                    status
                ).toStepEntity()
            )

            dao!!.findAll().forEach { stepEntity ->
                steps!!.add(stepEntity.toStep())
            }

            adapter = StepAdapter(this, steps!!, dao!!)
            rv!!.adapter = adapter
        }
    }

    private fun initApi(savedInstanceState: Bundle?){
        val apiInterface = ApiUtilities.getIntence().create(ApiInterface::class.java)
        val stepApiDataSource = StepApiDataSource(apiInterface)

        stepsViewModel = ViewModelProvider(this, StepViewModelFactory(stepApiDataSource))
            .get(StepsViewModel::class.java)

        stepsViewModel!!.steps.observe(this) {
            steps = it as ArrayList<Step>?
            rv!!.adapter = StepAdapter(this, steps!!, dao!!)
        }
    }
}