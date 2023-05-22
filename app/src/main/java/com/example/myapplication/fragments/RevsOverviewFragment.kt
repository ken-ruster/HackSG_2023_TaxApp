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
import com.example.myapplication.data.Rev
import com.example.myapplication.databinding.RevsOverviewBinding
import com.example.myapplication.storage.FileReader
import com.xwray.groupie.GroupieAdapter

class RevsOverviewFragment(): Fragment() {
    lateinit var binding: RevsOverviewBinding
    private val args: RevsOverviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = RevsOverviewBinding.inflate(inflater)
        val profile = args.profile

        profile.revs.removeIf { it.revName.isBlank() && it.amt == 0f }

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listRevs
        recyclerView.adapter = yaAdapter

        for (job in profile.jobs){
            if(!profile.revIsCreatedForJob(job)){
                profile.revs.add(Rev(
                    job.jobName,
                    0.0F,
                    job.id,
                    0
                ))
            }
        }

        for(rev in profile.revs){
            val listener = View.OnClickListener {
                val action = RevsOverviewFragmentDirections.openRevEdit(rev)
                findNavController().navigate(action)
            }

            yaAdapter.add(RevListItem(profile, listener))

            binding.addRev.setOnClickListener {
                val rev = Rev("",0.0F,null)
                profile.revs.add(rev)

                yaAdapter.add(RevListItem(profile) {
                    val action = RevsOverviewFragmentDirections.openRevEdit(rev)
                    findNavController().navigate(action)
                })

                val action = RevsOverviewFragmentDirections.openRevEdit(rev)
                findNavController().navigate(action)
            }
        }

        binding.backButton.setOnClickListener(){
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        FileReader(requireContext()).saveFile(args.profile)
    }
}
