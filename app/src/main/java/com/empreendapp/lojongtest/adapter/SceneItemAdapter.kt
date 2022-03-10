package com.empreendapp.lojongtest.adapter

import android.app.Activity
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.empreendapp.lojongtest.R
import com.empreendapp.lojongtest.model.Step


class SceneItemAdapter(val act: Activity, itens: ArrayList<Int>, val steps: ArrayList<Step>, val colWidth: Int) :
    ArrayAdapter<Int>(act, 0, itens) {
    var index: Int = 0

    var stepViewSelected: LottieAnimationView? = null
    var dialogView: View? = null
    var cardDialog: CardView? = null

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

                    if(dialogView == null){
                        dialogView = act.layoutInflater
                            .inflate(R.layout.step_dialog,
                                (viewItem.parent.parent as ConstraintLayout)
                                    .findViewById<ConstraintLayout>(R.id.clDialog))

                        cardDialog = dialogView!!.findViewById(R.id.cardDialog)
                        cardDialog!!.layoutParams.width = (colWidth * 1.5).toInt()
                    }

                    val tvAlert = dialogView!!.findViewById<TextView>(R.id.tvAlert)
                    tvAlert.setText(step!!.text)

                    cardDialog!!.visibility = View.INVISIBLE

                    val r = Runnable{
                        cardDialog!!.visibility = View.VISIBLE

                        cardDialog!!.x = viewItem.x - (colWidth * 0.25).toInt()
                        cardDialog!!.y = viewItem.y + (cardDialog!!.top - cardDialog!!.bottom)

                        if(cardDialog!!.x < 0f){
                            cardDialog!!.x += (colWidth * 0.5).toInt()
                        }
                    }

                    Handler().postDelayed(r, 200)

                    val bOkay = dialogView!!.findViewById<Button>(R.id.bOkay)
                    bOkay.setOnClickListener {
                        cardDialog!!.visibility = View.INVISIBLE
                    }

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

    private fun getRelativeLeft(myView: View): Int {
        return if (myView.parent === myView.rootView) myView.left else myView.left + getRelativeLeft(
            myView.parent as View
        )
    }

    private fun getRelativeTop(myView: View): Int {
        return if (myView.parent === myView.rootView) myView.top else myView.top + getRelativeTop(
            myView.parent as View
        )
    }
}

