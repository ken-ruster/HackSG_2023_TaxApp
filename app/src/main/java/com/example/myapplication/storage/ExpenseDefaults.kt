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
    private var expenseSettings: Map<String, ExpenseSetting> = HashMap()

    init {
        if (expenseFile.exists()) {
            println("Expense types exist, loading existing settings")
            try {
                expenseSettings = jacksonObjectMapper().readValue(expenseFile)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Existing settings are invalid or corrupted. Using default settings.")
            }
        } else {
            println("Created new expense type configuration file")
            try {
                jacksonObjectMapper().writeValue(expenseFile, expenseSettings)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun get(name: String): ExpenseSetting {
        if (expenseSettings.contains(name)) return expenseSettings[name]!!
        return ExpenseSetting(name, 0, false)
    }
}

data class ExpenseSetting (
    val expName: String,
    val expType: Int,
    val shareable: Boolean,
){}
