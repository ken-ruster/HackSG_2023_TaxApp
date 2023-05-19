package com.example.myapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rev (
    var revName: String,
    var amt: Float,
    val id: String? = null,
    var revType: Int = if (id != null) 0 else 1 // 0 is salary/commission, 1 is govt grant/incentive/etc
) : Parcelable {
}