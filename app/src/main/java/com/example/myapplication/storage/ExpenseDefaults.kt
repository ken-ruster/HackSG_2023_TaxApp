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
        "vehicle_rental"       to ExpenseSetting("Vehicle Rental", 1, true),
        "fuel"                 to ExpenseSetting("Vehicle Fuel", 1, true),
        "vehicle_maintenance"  to ExpenseSetting("Vehicle Maintenance", 1, true),
        "fees"                 to ExpenseSetting("Service Fees (%s)", 1, false),
        "compensation"         to ExpenseSetting("Compensation", 1, false),
        "driving_license"      to ExpenseSetting("Driving License Fees", 1, true),
        "vocational_license"   to ExpenseSetting("Vocational License Fee (%s)", 1, false),
        "erp"                  to ExpenseSetting("ERP Fees", 1, true),
        "printing_stationery"  to ExpenseSetting("Printing/Stationery Costs (%s)", 1, false),
        "venue_rental"         to ExpenseSetting("Venue Rental (%s)", 1, false),
        "public_transport"     to ExpenseSetting("Public Transport (%s)", 1, false),
        "overseas_travelling"  to ExpenseSetting("Overseas Travel (%s)", 1, false),
        "entertainment"        to ExpenseSetting("Entertainment (%s)", 1, false),
        "staff_remuneration"   to ExpenseSetting("Staff Remuneration (%s)", 1, false),
        "cpf_contributions"    to ExpenseSetting("CPF Contributions (%s)", 1, false),
        "utility_charges"      to ExpenseSetting("Utility Charges (%s)", 1, false),
        "venue_maintenance"    to ExpenseSetting("Venue Maintenance (%s)", 1, false),
        "transport"            to ExpenseSetting("Misc. Transport (%s)", 1, false),
        "performance_expenses" to ExpenseSetting("Performance Expenses (%s)", 1, false),
        "promotion_expenses"   to ExpenseSetting("Promotional Expenses (%s)", 1, false),
        "ingredients"          to ExpenseSetting("Ingredients", 0, true),
        "property_tax"         to ExpenseSetting("Property Tax (%s)", 1, false),
        "mortgage_interest"    to ExpenseSetting("Mortgage Interest (%s)", 1, false),
        "association_sub"      to ExpenseSetting("Prof. Body/Trade Assn. Subscription (%s)", 1, false)
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
