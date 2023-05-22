package com.example.myapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO

class App: Application() {

	override fun onCreate() {
		super.onCreate()
		AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
	}
}
