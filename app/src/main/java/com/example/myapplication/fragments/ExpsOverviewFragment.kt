package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ExpListItem
import com.example.myapplication.databinding.ExpsOverviewBinding
import com.xwray.groupie.GroupieAdapter

class ExpsOverviewFragment(): Fragment() {
    lateinit var binding: ExpsOverviewBinding
    val args: ExpsOverviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ExpsOverviewBinding.inflate(layoutInflater)
        val profile = args.profile

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listExps
        recyclerView.adapter = yaAdapter

        for(exp in profile.exps){
            val listener = View.OnClickListener {
                val action = ExpsOverviewFragmentDirections.openExpEdit(exp)
                findNavController().navigate(action)
            }

            yaAdapter.add(ExpListItem(profile, listener))
        }

        return binding.root
    }
}