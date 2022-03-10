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
import com.empreendapp.lojongtest.Preferences
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

        when(res){
            R.raw.arvore -> {
                if(viewItem == null) {
                    viewItem = LayoutInflater.from(context)
                        .inflate(R.layout.tree_amin_item, parent, false)
                }

                val animView: LottieAnimationView = viewItem!!.findViewById(R.id.animView)
                animView.layoutParams.height = colWidth
            }
            R.drawable.steph, R.drawable.stepv -> {
                if(viewItem == null){
                    viewItem = LayoutInflater.from(context)
                        .inflate(R.layout.step_amin_item, parent, false)
                    index++
                    step = steps!!.get(steps.size - index)
                }


                val animView: LottieAnimationView = viewItem!!.findViewById(R.id.animView)
                val imgView: ImageView = viewItem!!.findViewById(R.id.imgView)
                val tvStep: TextView = viewItem!!.findViewById(R.id.tvStep)

                animView.layoutParams.height = colWidth
                imgView.layoutParams.height = colWidth
                imgView.setImageResource(res!!)

                tvStep.setText((steps.size - index + 1).toString())

                tvStep.setOnClickListener {
                    Preferences.instance(context).setLastPosition(tvStep.text.toString().toInt() -1)
                    stepViewSelected?.visibility = View.GONE
                    animView.visibility = View.VISIBLE
                    animView.playAnimation()
                    stepViewSelected = animView

                    if(dialogView == null){
                        dialogView = act.layoutInflater
                            .inflate(R.layout.step_dialog,
                                (viewItem!!.parent.parent as ConstraintLayout)
                                    .findViewById<ConstraintLayout>(R.id.clDialog))

                        cardDialog = dialogView!!.findViewById(R.id.cardDialog)
                        cardDialog!!.layoutParams.width = (colWidth * 1.5).toInt()
                    }

                    val tvAlert = dialogView!!.findViewById<TextView>(R.id.tvAlert)
                    tvAlert.setText(step!!.text)

                    cardDialog!!.visibility = View.INVISIBLE

                    val r = Runnable{
                        cardDialog!!.visibility = View.VISIBLE

                        cardDialog!!.x = viewItem!!.x - (colWidth * 0.25).toInt()
                        cardDialog!!.y = viewItem!!.y + (cardDialog!!.top - cardDialog!!.bottom)

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

                if((steps.size - index) == Preferences.instance(context).getLastPosition()){
                    animView.visibility = View.VISIBLE
                    stepViewSelected = animView
                }

            }
            else -> {
                if(viewItem == null){
                    viewItem = LayoutInflater.from(context)
                        .inflate(R.layout.single_image_item, parent, false)
                }

                val imgView: ImageView = viewItem!!.findViewById(R.id.imgView)
                imgView.setImageResource(res!!)
                imgView.layoutParams.height = colWidth
            }
        }

        return viewItem
    }

}

