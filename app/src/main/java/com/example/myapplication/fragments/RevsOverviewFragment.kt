package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RevListItem
import com.example.myapplication.databinding.RevsOverviewBinding
import com.xwray.groupie.GroupieAdapter

class RevsOverviewFragment(): Fragment() {
    lateinit var binding: RevsOverviewBinding
    val args: RevsOverviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = RevsOverviewBinding.inflate(layoutInflater)
        val profile = args.profile

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listRevs
        recyclerView.adapter = yaAdapter

        for(rev in profile.revs){
            val listener = View.OnClickListener {
                val action = RevsOverviewFragmentDirections.openRevEdit(rev)
                findNavController().navigate(action)
            }

            yaAdapter.add(RevListItem(profile, listener))
        }

        return binding.root
    }
}