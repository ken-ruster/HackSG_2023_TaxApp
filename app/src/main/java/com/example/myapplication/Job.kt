package com.example.myapplication

class Job {
    lateinit var jobType: String
    lateinit var jobName: String
    val earnings: MutableList<Rev> = mutableListOf<Rev>(Rev(0, "Earnings", 0.0F ))
    var exp: MutableList<Exp> = emptyList<Exp>().toMutableList()

    fun totalEarnings(): Float = earnings.sumOf{ it.amt.toDouble() }.toFloat()
    fun matCost(): Float = exp.sumOf {
        it.amt * (1 - it.expType).toDouble()
    }.toFloat()
    fun allowable(): Float = exp.sumOf {
        it.amt * it.expType.toDouble()
    }.toFloat()
    fun profit(): Float = totalEarnings()  - matCost() //gross profit
    fun profitAdj(): Float = profit() - allowable() //adjusted profit


}

data class Rev(
    val revType: Short, //0 is revenue, 1 is govt grant/incentive/etc
    val revName: String,
    var amt: Float
){}

data class Exp (
    val expType: Short, //0 is mat cost, 1 is allowable expense
    val expName: String,
    var amt: Float
){}




