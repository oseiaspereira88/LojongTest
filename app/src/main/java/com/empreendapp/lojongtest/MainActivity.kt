package com.empreendapp.lojongtest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.empreendapp.lojongtest.api.ApiInterface
import com.empreendapp.lojongtest.api.ApiUtilities
import com.empreendapp.lojongtest.data.db.AppDatabase
import com.empreendapp.lojongtest.data.db.dao.StepDao
import com.empreendapp.lojongtest.data.repository.StepApiDataSource
import com.empreendapp.lojongtest.model.Step
import com.empreendapp.lojongtest.viewmodel.StepViewModelFactory
import com.empreendapp.lojongtest.viewmodel.StepsViewModel
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var gv: GridView? = null
    var db: AppDatabase? = null
    var dao: StepDao? = null
//    var adapter: StepAdapter? = null
    var steps: ArrayList<Step>? = null
    var stepsViewModel: StepsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDB(savedInstanceState)
        initViews()
        initApi(savedInstanceState)
        initScene()
    }

    private fun initScene() {
        var puzzle = ArrayList<Int>()

        puzzle.addAll(
            arrayOf(
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1, R.drawable.ic_c2, R.drawable.ic_c1,
                R.drawable.ic_c1, R.drawable.ic_c1
            )
        )

        val displayWidth = applicationContext.resources.displayMetrics.widthPixels
        val colSize = Math.round((displayWidth/6.00))

        gv!!.columnWidth = colSize.toInt()

        gv!!.adapter = SceneItemAdapter(this, puzzle, colSize.toInt())
    }

    private fun initDB(savedInstanceState: Bundle?) {
        db = AppDatabase.getDatabase(this)
        dao = db!!.stepDao()
    }

    private fun initViews(){
        gv = findViewById(R.id.gv)
        steps = ArrayList()
    }

    private fun initApi(savedInstanceState: Bundle?){
        val apiInterface = ApiUtilities.getIntence().create(ApiInterface::class.java)
        val stepApiDataSource = StepApiDataSource(apiInterface)

        stepsViewModel = ViewModelProvider(this, StepViewModelFactory(stepApiDataSource))
            .get(StepsViewModel::class.java)

        stepsViewModel!!.steps.observe(this) {
            steps = it as ArrayList<Step>?
            //rv!!.adapter = StepAdapter(this, steps!!, dao!!)
        }
    }

    class SceneItemAdapter(context: Context, itens: ArrayList<Int>, val colWidth: Int) :
        ArrayAdapter<Int>(context, 0, itens) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var viewItem = convertView

            if (viewItem == null) {
                viewItem = LayoutInflater.from(context).inflate(R.layout.single_image, parent, false)
            }

            val res = getItem(position)
            val img: ImageView = viewItem!!.findViewById(R.id.img)
            img.setImageResource(res!!)
            img.layoutParams.height = colWidth

            return viewItem
        }
    }
}