package com.example.myapplication.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore

class TaxProfile(
    val jobs: List<Job>,
    val revs: List<Rev>,
    val exps: List<Exp>,
    var fy: Int
) {
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

    @JsonIgnore
    fun isEmpty(): Boolean {return jobs.isEmpty()}
}