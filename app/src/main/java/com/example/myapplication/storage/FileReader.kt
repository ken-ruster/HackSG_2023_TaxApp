package com.example.myapplication.storage
import android.content.Context
import com.example.myapplication.data.TaxProfile
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class FileReader (context: Context) {
    private val mapper = jacksonObjectMapper()
    private val dir = File(context.filesDir, "profiles")

    init {
        dir.mkdir()
    }

    fun readFiles(): List<TaxProfile> {
        val res = ArrayList<TaxProfile>()
        for (f in dir.listFiles()) {
            println("Reading ${f.name}")
            try {
                val profile: TaxProfile = mapper.readValue(f)
                res.add(profile)
            } catch (e: Exception) {e.printStackTrace()}
        }
        return res
    }

    fun saveFile(profile: TaxProfile): String? {
        val filename = profile.fy.toString() + ".json"
        println("Saving $filename")
        println(mapper.writeValueAsString(profile))
        try {
            mapper.writeValue(File(dir, filename), profile)
            return filename
        } catch (e: Exception) {e.printStackTrace()}
        return null
    }
}