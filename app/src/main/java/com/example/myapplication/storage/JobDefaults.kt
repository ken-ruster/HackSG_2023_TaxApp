package com.example.myapplication.storage

import android.content.Context
import com.example.myapplication.R
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class JobDefaults(context: Context) {

    val typeMap = context.resources.getStringArray(R.array.JobTypeArray)

    companion object {
        const val job_filename = "job_defaults.json"
    }

    private val jobFile = File(context.filesDir, job_filename)

    // Default settings can be created here
    private var jobSettings : Map<String, List<String>> = mapOf(
        "Private Hire Transport" to listOf("vehicle_rental", "fuel", "vehicle_maintenance", "fees"),
        "Food Delivery"          to listOf("vehicle_rental", "fuel", "vehicle_maintenance", "fees"),
        "Tuition"                to listOf("teaching_materials", "venue_rental")
    )

    fun loadDefaults() {
        if (jobFile.exists()) {
            println("Job configurations exist, loading existing settings")
            try {
                jobSettings= jacksonObjectMapper().readValue(jobFile)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Existing settings are invalid or corrupted. Using default settings.")
            }
        } else {
            save()
        }
    }

    fun get(type: Int): List<String> {
        val name = typeMap[type]
        if (jobSettings.contains(name)) return jobSettings[name]!!
        return ArrayList()
    }

    override fun toString(): String {
        return jacksonObjectMapper().writeValueAsString(jobSettings)
    }
    fun save() {
        try {
            jacksonObjectMapper().writeValue(jobFile, jobSettings)
            println("Current job configuration was saved")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}