package com.example.myapplication

class UserResponse() {
    lateinit var job: Job
    var rev: Float = 0.0F
    lateinit var exp: Exp

    val profit: Float get() =  rev - exp.mat_cost //gross profit
    val profit_adj: Float get() = profit - exp.allowable //adjusted profit


    //For private hire or taxi drivers
    inner class Taxi_Exp(): Exp() {
        var erp_cost: Float = 0.0F
        var fuel_cost: Float = 0.0F
        var license_costs: Float = 0.0F
        var washing_costs: Float = 0.0F
        var parking_costs: Float = 0.0F //all allowable costs

        private val temp = erp_cost + fuel_cost + license_costs + washing_costs + parking_costs

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
    open val mat_cost: Float = 0.0F
}




