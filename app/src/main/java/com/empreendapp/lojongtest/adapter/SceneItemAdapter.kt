package com.empreendapp.lojongtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.empreendapp.lojongtest.R
import com.empreendapp.lojongtest.model.Step

class SceneItemAdapter(context: Context, itens: ArrayList<Int>, val steps: ArrayList<Step>, val colWidth: Int) :
    ArrayAdapter<Int>(context, 0, itens) {
    var index: Int = 0

    var stepViewSelected: LottieAnimationView? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var viewItem = convertView
        val res = getItem(position)

        var step: Step? = null

        if (viewItem == null) {
             when(res){
                R.raw.arvore ->
                    viewItem = LayoutInflater.from(context)
                        .inflate(R.layout.tree_amin_item, parent, false)
                R.drawable.steph, R.drawable.stepv -> {
                    viewItem = LayoutInflater.from(context)
                        .inflate(R.layout.step_amin_item, parent, false)
                    index++
                    step = steps!!.get(steps.size - index)
                }
                else ->
                    viewItem = LayoutInflater.from(context)
                        .inflate(R.layout.single_image_item, parent, false)
            }
        }

        when(res){
            R.raw.arvore -> {
                val animView: LottieAnimationView = viewItem!!.findViewById(R.id.animView)
                animView.layoutParams.height = colWidth
            }
            R.drawable.steph, R.drawable.stepv -> {
                val animView: LottieAnimationView = viewItem!!.findViewById(R.id.animView)
                val imgView: ImageView = viewItem!!.findViewById(R.id.imgView)
                val tvStep: TextView = viewItem!!.findViewById(R.id.tvStep)

                animView.layoutParams.height = colWidth
                imgView.layoutParams.height = colWidth
                imgView.setImageResource(res!!)

                tvStep.setText((steps.size - index + 1).toString())

                tvStep.setOnClickListener {
                    stepViewSelected?.visibility = View.GONE
                    animView.visibility = View.VISIBLE
                    animView.playAnimation()
                    stepViewSelected = animView

                    Toast.makeText(context, step!!.text, Toast.LENGTH_LONG).show()
                }

                if((steps.size - index) == 0){
                    animView.visibility = View.VISIBLE
                    stepViewSelected = animView
                }

            }
            else -> {
                val imgView: ImageView = viewItem!!.findViewById(R.id.imgView)
                imgView.setImageResource(res!!)
                imgView.layoutParams.height = colWidth
            }
        }

        return viewItem
    }
}

