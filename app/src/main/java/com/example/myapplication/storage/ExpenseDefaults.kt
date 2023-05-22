package com.example.myapplication.storage

import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class ExpenseDefaults (context: Context) {

    companion object {
        const val expense_filename = "expense_defaults.json"
    }

    private val expenseFile = File(context.filesDir, expense_filename)

    // Default settings can be created here
    private var expenseSettings: Map<String, ExpenseSetting> = mapOf(
        "vehicle_rental"       to ExpenseSetting("Vehicle Rental", 0, true),
        "fuel"                 to ExpenseSetting("Vehicle Fuel", 1, true),
        "vehicle_maintenance"  to ExpenseSetting("Vehicle Maintenance", 1, true),
        "fees"           to ExpenseSetting("Platform/Service Fees (%s)", 1, false),
        "teaching_materials"   to ExpenseSetting("Teaching Materials (%s)", 0, false),
        "venue_rental"         to ExpenseSetting("Venue Rental (%s)", 0, false),
    )

    fun loadDefaults() {
        if (expenseFile.exists()) {
            println("Expense types exist, loading existing settings")
            try {
                expenseSettings = jacksonObjectMapper().readValue(expenseFile)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Existing settings are invalid or corrupted. Using default settings.")
            }
        } else {
            try {
                save()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun get(name: String): ExpenseSetting {
        if (expenseSettings.contains(name)) return expenseSettings[name]!!
        return ExpenseSetting("$name (%s)", 0, false)
    }

    fun save() {
        try {
            jacksonObjectMapper().writeValue(expenseFile, expenseSettings)
            println("Current expense configuration was saved")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun toString(): String {
        return jacksonObjectMapper().writeValueAsString(expenseSettings)
    }
}

data class ExpenseSetting (
    val expName: String,
    val expType: Int,
    val shareable: Boolean,
){}
