package com.example.myapplication.data

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
class TaxProfile(
    val jobs: MutableList<Job> = emptyList<Job>().toMutableList(),
    val revs: MutableList<Rev> = emptyList<Rev>().toMutableList(),
    val exps: MutableList<Exp> = emptyList<Exp>().toMutableList(),

    var fy: Int = LocalDate.now().year
) : Parcelable {
    fun totalRev(): Float = revs.sumOf { it.amt.toDouble() }.toFloat()
    fun totalMatCost(): Float = exps.sumOf { it.amt.toDouble() * (1 - it.expType) }.toFloat()
    fun totalAllowableExp(): Float = exps.sumOf { it.amt.toDouble() * it.expType }.toFloat()
    fun totalGrossProfit() = totalRev() - totalMatCost()

    fun jobString(): String{
        var temp: String = "None"

        if(jobs.lastIndex >= 0) {
            temp = jobs[0].jobName
        }
        if(jobs.lastIndex >= 1){
            temp = "$temp, ${jobs[1].jobName}"
        }
        if(jobs.lastIndex > 1) temp = "$temp,..."

        return temp
    }
    fun totalAdjProfit() = totalGrossProfit() - totalAllowableExp()

    fun searchJobByName(name: String): String? {
        for(job in jobs){
            if (job.jobName == name) return job.id
        }
        return null
    }

    fun revIsCreatedForJob(job: Job): Boolean{
        for (rev in revs){
            if(rev.id == job.id) return true
        }
        return false
    }

    @JsonIgnore
    fun isEmpty(): Boolean {return jobs.isEmpty()}
}
