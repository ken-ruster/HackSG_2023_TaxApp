package com.example.myapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rev (
    val revType: Int, // 0 is revenue, 1 is govt grant/incentive/etc
    val revName: String,
    var amt: Float
) : Parcelable {}