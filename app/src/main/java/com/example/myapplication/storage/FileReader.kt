package com.example.myapplication.storage
import com.example.myapplication.data.TaxProfile
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class FileReader {
    companion object {
        private val mapper = jacksonObjectMapper()

        fun readFile(filename: String): TaxProfile {
            val json: String = File(filename).readText()
            return mapper.readValue<TaxProfile>(json)
        }

        fun saveFile(profile: TaxProfile) {
            val filename = profile.fy.toString() + ".json"
            val json: String = mapper.writeValueAsString(profile)
            File(filename).writeBytes(json.toByteArray())
        }
    }
}