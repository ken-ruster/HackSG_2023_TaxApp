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
        "Private Hire Transport"                 to listOf("vehicle_rental", "fuel", "vehicle_maintenance", "fees", "erp", "driving_license", "vocational_license"),
        "Delivery"                               to listOf("fees", "compensation"),
        "Tuition"                                to listOf("printing_stationery", "venue_rental", "cpf_contributions", "utility_charges", "public_transport", "staff_remuneration", "overseas_travelling", "entertainment"),
        "Commission Agent"                       to listOf("venue_rental", "entertainment", "printing_stationery", "staff_remuneration", "overseas_travelling", "public_transport", "venue_maintenance"),
        "Beauty and Wellness Operator"           to listOf("public_transport", "entertainment", "staff_remuneration", "cpf_contributions", "utility_charges"),
        "Entertainer/ Entertainment Organiser"   to listOf("entertainment", "transport", "staff_remuneration", "cpf_contributions", "promotion_expenses", "performance_expenses"),
        "Hawker"                                 to listOf("venue_rental", "property_tax", "mortgage_interest", "utility_charges", "transport", "staff_remuneration", "cpf_contributions", "ingredients"),
        "Lawyer"                                 to listOf("entertainment", "public_transport", "printing_stationery", "staff_remuneration", "cpf_contributions", "association_sub"),
        "Maid Agency Operator"                   to listOf("venue_rental", "property_tax", "mortgage_interest", "utility_charges", "staff_remuneration", "cpf_contributions"),
        "Medical Practitioner"                   to listOf("venue_rental", "public_transport", "utility_charges", "staff_remuneration", "cpf_contributions", "association_sub"),
        "Religious Practitioner"                 to listOf("public_transport"),
        "Social Media Influencer"                 to listOf("utility_charges"),
        "Sportsperson"                           to listOf("public_transport")
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