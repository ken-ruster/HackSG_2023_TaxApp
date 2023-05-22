package com.example.myapplication.data

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
class TaxProfile(
    var jobs: MutableList<Job> = emptyList<Job>().toMutableList(),
    var revs: MutableList<Rev> = emptyList<Rev>().toMutableList(),
    var exps: MutableList<Exp> = emptyList<Exp>().toMutableList(),

    var fy: Int = LocalDate.now().year
) : Parcelable {
    @JsonIgnore
    var modified = false
    fun totalRev(): Float = revs.sumOf { it.amt.toDouble() }.toFloat()
    fun totalMatCost(): Float = exps.sumOf {
        it.amt.toDouble() * (1 - it.expType) * it.portion.values.sum() / 100.0
    }.toFloat()
    fun totalAllowableExp(): Float = exps.sumOf {
        it.amt.toDouble() * it.expType * it.portion.values.sum() / 100.0
    }.toFloat()
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

    fun searchJobById(id: String): String? {
        for(job in jobs){
            if (job.id == id) return job.id
        }
        return null
    }

    fun revIsCreatedForJob(job: Job): Boolean{
        return revs.any { it.id == job.id }
/*        for (rev in revs){
            if(rev.id == job.id) return true
        }
        return false*/
    }

    @JsonIgnore
    fun isEmpty(): Boolean {return jobs.isEmpty()}
}
