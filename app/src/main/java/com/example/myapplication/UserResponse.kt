package com.example.myapplication

class UserResponse {
    lateinit var job: Job
    var rev: Float = 0.0F
    lateinit var exp: Exp

    val profit: Float get() =  rev - exp.matCost //gross profit
    val profitAdj: Float get() = profit - exp.allowable //adjusted profit


    //For private hire or taxi drivers
    inner class TaxiExp(): Exp() {
        var erpCost: Float = 0.0F
        var fuelCost: Float = 0.0F
        var licenseCost: Float = 0.0F
        var washingCost: Float = 0.0F
        var parkingCost: Float = 0.0F //all allowable costs

        private val temp = erpCost + fuelCost + licenseCost + washingCost + parkingCost

        override val allowable: Float
            get() = if(temp > profit * 0.6F) temp else profit * 0.6F
    }
}

class Job(
    var job_type:String,
    var coy_name:String
) {}

abstract class Exp {
    open val allowable: Float = 0.0F
    open val matCost: Float = 0.0F
}




