package com.example.plcus.multimedia

import java.util.*

private const val MINIMUM_POINTS_REQUIRED = 10

class GestureDetector {

    private var listPointX: ArrayList<Float> = ArrayList()

    fun addPointX(pointX:Float){
        listPointX.add(pointX)
    }

    fun isSwipeRight(): Boolean {

        if (listPointX.size < MINIMUM_POINTS_REQUIRED) {
            return false
        }

        for (i in 1..(listPointX.size-1)) {
            if(listPointX[i-1] > listPointX[i]) {
                return false
            }
        }

        return true
    }

    fun isSwipeLeft(): Boolean {

        if (listPointX.size < MINIMUM_POINTS_REQUIRED) {
            return false
        }

        for (i in 1..(listPointX.size-1)) {
            if(listPointX[i-1] < listPointX[i]) {
                return false
            }
        }

        return true
    }


}