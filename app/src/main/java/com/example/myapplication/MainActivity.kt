package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.storage.ExpenseDefaults
import com.example.myapplication.storage.JobDefaults
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    private var expenseDefaults: ExpenseDefaults? = null
    private var jobDefaults: JobDefaults? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        expenseDefaults = ExpenseDefaults(applicationContext)
        jobDefaults = JobDefaults(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnView = findViewById<Button>(R.id.btnView)
        val fileData = findViewById<TextView>(R.id.editFile)

        fileData.text = "${expenseDefaults.toString()} ||| ${jobDefaults.toString()}"

        btnView.setOnClickListener(View.OnClickListener {
            expenseDefaults!!.loadDefaults()
            jobDefaults!!.loadDefaults()
            fileData.text = "${expenseDefaults.toString()} ||| ${jobDefaults.toString()}"
            println("Loaded defaults")
        })

        btnSave.setOnClickListener(View.OnClickListener {
            fileData.text = "${expenseDefaults.toString()} ||| ${jobDefaults.toString()}"
            expenseDefaults?.save()
            jobDefaults?.save()
            println("Saved defaults")
        })
    }
}