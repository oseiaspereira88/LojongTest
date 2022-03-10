package com.empreendapp.lojongtest

import android.content.Context
import android.content.SharedPreferences

class Preferences {
    companion object {
        private var sp: SharedPreferences? = null

        fun instance(ctx: Context): Companion {
            sp = ctx.getSharedPreferences("LojongPreferences", Context.MODE_PRIVATE)
            return this
        }

        fun setLastPosition(position: Int) {
            sp!!.edit().putInt("lastPosition", position).apply()
        }

        fun getLastPosition(): Int = sp!!.getInt("lastPosition", 0)
    }

}