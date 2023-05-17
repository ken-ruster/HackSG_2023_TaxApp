package com.example.myapplication

class TaxProfile(
    private val jobs: List<Job>,
    private val revs: List<Rev>,
    private val exps: List<Exp>
) {
    val fy: Int = 2023
    fun totalRev(): Float = revs.sumOf { it.amt.toDouble() }.toFloat()
    fun totalMatCost(): Float = exps.sumOf { it.amt.toDouble() * (1 - it.expType) }.toFloat()
    fun totalAllowableExp(): Float = exps.sumOf { it.amt.toDouble() * it.expType }.toFloat()
    fun totalGrossProfit() = totalRev() - totalMatCost()

    fun jobString(): String{
        var temp = jobs[0].jobName

        if(jobs.lastIndex >= 1){
            temp = temp + ", " + jobs[1].jobName
        }

        return temp
    }
    fun totalAdjProfit() = totalGrossProfit() - totalAllowableExp()
    fun isEmpty(): Boolean {return jobs.isEmpty()}
}