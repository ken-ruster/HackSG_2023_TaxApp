package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.ExpEditBinding

class ExpEditFragment:Fragment() {
    lateinit var binding: ExpEditBinding
    val args: ExpsOverviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExpEditBinding.inflate(layoutInflater)

        with(binding){

        }

        return binding.root
    }
}