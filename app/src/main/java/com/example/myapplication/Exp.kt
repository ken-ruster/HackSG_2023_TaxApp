package com.example.myapplication

data class Exp (
    val expType: Int, // 0 is mat cost, 1 is allowable expense
    val expName: String,
    var amt: Float,
    val portion: HashMap<String, Float> // <jobID, portion in %>
){}