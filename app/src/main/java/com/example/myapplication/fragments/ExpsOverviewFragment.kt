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
import com.example.myapplication.data.Exp
import com.example.myapplication.data.ProfileManager
import com.example.myapplication.databinding.ExpsOverviewBinding
import com.example.myapplication.storage.FileReader
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
        val profileManager = ProfileManager(requireContext())

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listExps
        recyclerView.adapter = yaAdapter

        profile.exps = profileManager.generateExps(profile)

        for(exp in profile.exps){
            val listener = View.OnClickListener {
                val action = ExpsOverviewFragmentDirections.openExpEdit(profile, exp)
                findNavController().navigate(action)
            }

            yaAdapter.add(ExpListItem(profile, exp, listener))
        }

        binding.addExp.setOnClickListener {
            val exp = Exp(0,"",0.0F, emptyMap<String,Float>().toMutableMap())
            profile.exps.toMutableList().add(exp)

            val listener = View.OnClickListener {
                val action = ExpsOverviewFragmentDirections.openExpEdit(profile, exp)
                findNavController().navigate(action)
            }

            yaAdapter.add(ExpListItem(profile, exp, listener))

            val action = ExpsOverviewFragmentDirections.openExpEdit(profile, exp)
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        FileReader.saveFile(args.profile, requireContext())
    }
}