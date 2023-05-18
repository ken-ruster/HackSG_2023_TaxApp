package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.RevEditBinding

class RevEditFragment:Fragment() {
    lateinit var binding: RevEditBinding
    val args: RevsOverviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RevEditBinding.inflate(layoutInflater)

        with(binding){

        }

        return binding.root
    }
}