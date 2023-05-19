package com.example.myapplication.storage

import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class JobDefaults(context: Context) {

    companion object {
        const val job_filename = "job_defaults.json"
    }

    private val jobFile = File(context.filesDir, job_filename)

    // Default settings can be created here
    private var jobSettings : Map<String, List<String>> = HashMap()

    init {
        if (jobFile.exists()) {
            println("Expense types exist, loading existing settings")
            try {
                jobSettings= jacksonObjectMapper().readValue(jobFile)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Existing settings are invalid or corrupted. Using default settings.")
            }
        } else {
            println("Created new expense type configuration file")
            try {
                jacksonObjectMapper().writeValue(jobFile, jobSettings)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun get(name: String): List<String> {
        if (jobSettings.contains(name)) return jobSettings[name]!!
        return ArrayList()
    }
}