package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.data.Exp
import com.example.myapplication.data.Job
import com.example.myapplication.data.Rev
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.storage.FileReader
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnView = findViewById<Button>(R.id.btnView)
        val fileData = findViewById<TextView>(R.id.editFile)
        var year = 2000

        btnSave.setOnClickListener(View.OnClickListener {
            val profile: TaxProfile = TaxProfile(
                listOf(Job("owo", "iwi"),),
                listOf(Rev(1, "hehehaha", 1F)),
                listOf(Exp(2, "uwuwu", 2F, mapOf("iyi" to 3F))),
                year++
            )
            val filename = FileReader.saveFile(profile, this.applicationContext)
            println("Saved file as $filename")
        })

        btnView.setOnClickListener(View.OnClickListener {
            val profile: List<TaxProfile>? = FileReader.readFiles(this.applicationContext)
            println("File loaded")
            val json = jacksonObjectMapper().writeValueAsString(profile)
            fileData.text = json
        })
    }
}