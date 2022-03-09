package com.empreendapp.lojongtest

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.empreendapp.lojongtest.adapter.SceneItemAdapter
import com.empreendapp.lojongtest.api.ApiInterface
import com.empreendapp.lojongtest.api.ApiUtilities
import com.empreendapp.lojongtest.data.db.AppDatabase
import com.empreendapp.lojongtest.data.db.dao.StepDao
import com.empreendapp.lojongtest.data.db.entity.toStepEntity
import com.empreendapp.lojongtest.data.repository.StepApiDataSource
import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.model.toStep
import com.empreendapp.lojongtest.viewmodel.StepViewModelFactory
import com.empreendapp.lojongtest.viewmodel.StepsViewModel


class MainActivity : AppCompatActivity() {
    var gv: GridView? = null
    var db: AppDatabase? = null
    var dao: StepDao? = null
    var steps: ArrayList<Step>? = null
    var stepsViewModel: StepsViewModel? = null
    var scrollView: ScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        tryInitByDB()
    }

    private fun initViews() {
        gv = findViewById(R.id.gv)
        scrollView = findViewById(R.id.sv)
    }

    private fun tryInitByDB(){
        db = AppDatabase.getDatabase(this)
        dao = db!!.stepDao()
        steps = ArrayList()

        val all = dao!!.findAll()

        if(!all.isEmpty()){
            all.forEach { entity ->
                if (!entity.isExpired()) {
                    steps!!.add(entity.toStep())
                } else {
                    initByApi()
                    return
                }
            }
            setSceneItemAdapter(getResList(), steps!!, getColSize())
            Log.d("Steps::::::::", steps.toString())
            initScene()
        } else{
            initByApi()
        }
    }

    private fun initScene() {
        val colSize = getColSize()
        gv!!.columnWidth = colSize
        gv!!.layoutParams.height = (15 * colSize)
        scrollView!!.postDelayed({ scrollView!!.fullScroll(ScrollView.FOCUS_DOWN) }, 1600)
    }

    private fun getColSize(): Int {
        val displayWidth = applicationContext.resources.displayMetrics.widthPixels
        return Math.round((displayWidth / 4.00)).toInt()
    }

    private fun getResList(): ArrayList<Int> {
        var puzzle = ArrayList<Int>()

        puzzle.addAll(
            arrayOf(
                R.drawable.grass, R.drawable.rv, R.drawable.grass, R.raw.arvore,
                R.drawable.grass, R.drawable.c4, R.drawable.c2, R.drawable.grass,
                R.drawable.grass, R.raw.arvore, R.drawable.rv, R.drawable.grass,
                R.drawable.c1, R.drawable.rh, R.drawable.c3, R.drawable.grass,
                R.drawable.steph, R.drawable.grass, R.raw.arvore, R.raw.arvore,
                R.drawable.c4, R.drawable.c2, R.raw.arvore, R.raw.arvore,
                R.drawable.grass, R.drawable.steph, R.drawable.grass, R.drawable.grass,
                R.raw.arvore, R.drawable.rv, R.drawable.grass, R.drawable.grass,
                R.drawable.grass, R.drawable.c4, R.drawable.stepv, R.drawable.c2,
                R.drawable.grass, R.drawable.grass, R.raw.arvore, R.drawable.rv,
                R.drawable.grass, R.drawable.c1, R.drawable.rh, R.drawable.c3,
                R.drawable.grass, R.drawable.steph, R.drawable.grass, R.raw.arvore,
                R.drawable.grass, R.drawable.c4, R.drawable.c2, R.raw.arvore,
                R.drawable.grass, R.drawable.grass, R.drawable.steph, R.drawable.grass,
                R.raw.arvore, R.drawable.grass, R.drawable.rv, R.drawable.grass,
            )
        )

        return puzzle
    }

    private fun setSceneItemAdapter(resList: ArrayList<Int>, steps: ArrayList<Step>, colSize: Int) {
        gv!!.adapter = SceneItemAdapter(this, resList, steps, colSize)
    }

    private fun initByApi() {
        val apiInterface = ApiUtilities.getIntence().create(ApiInterface::class.java)
        val stepApiDataSource = StepApiDataSource(apiInterface)

        stepsViewModel = ViewModelProvider(this, StepViewModelFactory(stepApiDataSource))
            .get(StepsViewModel::class.java)

        stepsViewModel!!.steps.observe(this) {
            steps = it as ArrayList<Step>?

            steps!!.forEach { step ->
                dao!!.insert(step.toStepEntity())
            }

            steps?.let { steps -> setSceneItemAdapter(getResList(), steps, getColSize()) }
            initScene()
            Toast.makeText(this, "Api ok!", Toast.LENGTH_LONG).show()
        }
    }
}