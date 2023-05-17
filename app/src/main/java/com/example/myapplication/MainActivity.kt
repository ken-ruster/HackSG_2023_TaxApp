package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.xwray.groupie.GroupieAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var profileArray: Array<TaxProfile> = emptyArray()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listProfiles
        recyclerView.adapter = yaAdapter

        for(profile in profileArray){
            yaAdapter.add(ProfileItem(profile))
        }

        setContentView(binding.root)
    }
}