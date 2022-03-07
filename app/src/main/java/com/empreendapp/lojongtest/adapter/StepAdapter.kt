package com.empreendapp.lojongtest.adapter

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.*
import com.empreendapp.lojongtest.R
import com.empreendapp.lojongtest.data.db.dao.StepDao
import com.empreendapp.lojongtest.data.db.entity.toStepEntity
import com.empreendapp.lojongtest.model.Step

class StepAdapter(val act: Activity, open var steps: ArrayList<Step>, val dao: StepDao) :
    Adapter<StepAdapter.StepHolder>() {

    class StepHolder(itemView: ConstraintLayout) : ViewHolder(itemView) {
        var tvText: TextView? = null
        var bEdit: Button? = null
        var bDelete: Button? = null

        init {
            tvText = itemView.findViewById(R.id.tvText)
            bEdit = itemView.findViewById(R.id.bEdit)
            bDelete = itemView.findViewById(R.id.bDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        return StepHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.step_view, parent, false) as ConstraintLayout)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        val step = steps[position]
        holder.tvText!!.text = step.text

        holder.itemView.setOnClickListener {
            Toast.makeText(act, "Click()", Toast.LENGTH_SHORT).show()
        }

        holder.bEdit!!.setOnClickListener {
            step.text = step.text + " edited"
            dao!!.insert(step.toStepEntity())
            this.notifyDataSetChanged()
            Toast.makeText(act, "Item editado", Toast.LENGTH_SHORT).show()
        }

        holder.bDelete!!.setOnClickListener {
            dao!!.delete(step.toStepEntity())
            steps.remove(step)
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    public fun incrementItem(step: Step){
        steps.add(step)
        this.notifyDataSetChanged()
    }
}

