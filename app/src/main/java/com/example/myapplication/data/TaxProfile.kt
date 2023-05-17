package com.example.myapplication.data

import com.fasterxml.jackson.annotation.JsonFormat

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
    fun totalAdjProfit() = totalGrossProfit() - totalAllowableExp()
    fun isEmpty(): Boolean {return jobs.isEmpty()}
}