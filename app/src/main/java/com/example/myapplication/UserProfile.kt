package com.example.myapplication

class UserProfile {
    val fy: Int = 2023
    private lateinit var responses: MutableList<Job>
    val mainJob: String get() = responses.maxBy { it.totalEarnings() }.jobName
    var totalRev: Float = 0.0F
    var totalMatCost = 0.0F
    var totalAllowableExp = 0.0F
    fun totalGrossProfit() = totalRev - totalMatCost
    fun totalAdjProfit() = totalRev - totalMatCost - totalAllowableExp

    fun addJob(job: Job){
        responses.add(job)
        totalRev += job.totalEarnings()
        totalMatCost += job.matCost()
        totalAllowableExp += job.allowable()
    }

    fun isEmpty(): Boolean {return responses.isEmpty()}
}