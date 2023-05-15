package com.example.myapplication

class UserProfile {
    private lateinit var responses: MutableList<UserResponse>
    lateinit var mainJob: String
    var totalRev: Float = 0.0F
    private var _totalMatCost = 0.0F
    var totalAllowableExp = 0.0F
    val totalGrossProfit get() = totalRev - _totalMatCost
    val totalAdjProfit get() = totalRev - _totalMatCost - totalAllowableExp

    fun addJob(job: UserResponse){
        responses.add(job)
        totalRev += job.rev
        _totalMatCost += job.exp.matCost
        totalAllowableExp += job.exp.allowable
    }

    fun isEmpty(): Boolean {return responses.isEmpty()}
}