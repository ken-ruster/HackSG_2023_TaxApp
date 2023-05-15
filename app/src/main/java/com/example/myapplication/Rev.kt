package com.example.myapplication

data class Rev (
    val revType: Int, // 0 is revenue, 1 is govt grant/incentive/etc
    val revName: String,
    var amt: Float
){}