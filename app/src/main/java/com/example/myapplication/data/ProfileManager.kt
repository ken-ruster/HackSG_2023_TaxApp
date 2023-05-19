package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.storage.ExpenseDefaults
import com.example.myapplication.storage.JobDefaults
import java.time.Year

class ProfileManager (context: Context) {
    private val expenseDefaults = ExpenseDefaults(context)
    private val jobDefaults = JobDefaults(context)

    init {
        expenseDefaults.loadDefaults()
        jobDefaults.loadDefaults()
    }

    fun createProfile(jobs: List<Job>, prev: TaxProfile?): TaxProfile {
        val revs = ArrayList<Rev>()
        val exps = HashMap<String, Exp>()

        if (prev != null) {
            revs.addAll(prev.revs)
            prev.exps.forEach{exp -> exps[exp.expName] = exp }
        }

        for (job in jobs) if (prev == null || !prev.jobs.contains(job)) {

            val l = jobDefaults.get(job.jobType)
            for (expName in l) {
                val settings = expenseDefaults.get(expName)
                val converted = String.format(settings.expName, job.jobType)
                if (exps.contains(converted)) exps[converted]!!.portion!![job.jobName] = 0F;
                else exps[converted] = Exp(settings.expType, converted, 0F, hashMapOf(job.jobName to 100F))
            }
        }

        return TaxProfile(jobs, revs, exps.values.toList(), Year.now().value)
    }
}