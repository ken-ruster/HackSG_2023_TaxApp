package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.data.Job
import com.example.myapplication.data.ProfileManager
import com.example.myapplication.data.TaxProfile
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val manager = ProfileManager(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnView = findViewById<Button>(R.id.btnView)
        val fileData = findViewById<TextView>(R.id.editFile)

        var profile : TaxProfile? = null
        val jobs = listOf(
            Job("Private Hire Transport", "Grab"),
            Job("Private Hire Transport", "Uber"),
            Job("Food Delivery", "Grab Food"),
            Job("Tuition", "The Learning Lab")
        )
        fileData.text = "${jacksonObjectMapper().writeValueAsString(profile)}"

        btnView.setOnClickListener(View.OnClickListener {
            println("Creating new profile")
            val newProfile = manager.createProfile(jobs, profile)
            fileData.text = "${jacksonObjectMapper().writeValueAsString(newProfile)}"
        })

        btnSave.setOnClickListener(View.OnClickListener {
            println("Modifying current profile")
            profile = manager.createProfile(jobs, profile)
            fileData.text = "${jacksonObjectMapper().writeValueAsString(profile)}"
        })
    }
}