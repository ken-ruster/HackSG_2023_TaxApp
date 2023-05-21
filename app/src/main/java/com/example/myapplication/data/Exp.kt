package com.example.myapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exp (
    var expType: Int, // 0 is mat cost, 1 is allowable expense
    var expName: String,
    var amt: Float,
    var portion: MutableMap<String, Float> // <job XID, portion in %>
) : Parcelable {

    constructor(exp: Exp) : this(exp.expType, exp.expName, exp.amt, exp.portion)
    fun update(exp: Exp) {
        expType = exp.expType
        expName = exp.expName
        amt = exp.amt
        portion = exp.portion
    }
}