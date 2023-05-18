package com.example.myapplication.storage
import android.content.Context
import com.example.myapplication.data.TaxProfile
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class FileReader {
    companion object {
        private val mapper = jacksonObjectMapper()

        fun readFile(filename: String, context: Context): TaxProfile? {
            var fis: FileInputStream? = null
            try {
                fis = context.applicationContext.openFileInput(filename)
                var isr = InputStreamReader(fis)
                val br = BufferedReader(isr)
                val sb = StringBuilder()
                var line: String? = null
                while (run {
                        line = br.readLine()
                        line
                    } != null)
                    sb.append(line)
                val json: String = sb.toString()
                fis.close()
                println("obtained json")
                println(json)
                return mapper.readValue(json)
            } catch (e: Exception) { e.printStackTrace(); }
            fis?.close()
            return null
        }

        fun saveFile(profile: TaxProfile, context: Context): String? {
            val filename = profile.fy.toString() + ".json"
            val json: String = mapper.writeValueAsString(profile)
            println(json)

            var fos: FileOutputStream? = null
            try {
                fos = context.applicationContext.openFileOutput(filename, Context.MODE_PRIVATE)
                fos.write(json.toByteArray())
                return filename
            } catch (e: Exception) {e.printStackTrace()}
            fos?.close()
            return null
        }
    }
}