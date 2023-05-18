package com.example.myapplication.data

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
class TaxProfile(
    val jobs: List<Job> = emptyList<Job>().plus(Job(
        "Private Hire/Taxi Driver",
        "Grab"
    )),
    val revs: List<Rev> = emptyList<Rev>().plus(Rev(
        0,
        "Grab",
        0.0F
    )),
    val exps: List<Exp> = emptyList<Exp>().plus(Exp(
        1,
        "Fuel",
        0.0F,
        emptyMap<String, Float>().plus(Pair("Grab", 100F))
        )),

    var fy: Int = LocalDate.now().year
) : Parcelable {
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
