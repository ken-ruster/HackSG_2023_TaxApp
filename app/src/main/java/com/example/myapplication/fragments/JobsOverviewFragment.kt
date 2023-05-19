package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.JobListItem
import com.example.myapplication.R
import com.example.myapplication.data.Job
import com.example.myapplication.databinding.JobsOverviewBinding
import com.example.myapplication.storage.FileReader
import com.xwray.groupie.GroupieAdapter

class JobsOverviewFragment(): Fragment() {
    lateinit var binding: JobsOverviewBinding
    val args: JobsOverviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = JobsOverviewBinding.inflate(layoutInflater)
        val profile = args.profile
        val jobTypeArray = resources.getStringArray(R.array.JobTypeArray)

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listJobs
        recyclerView.adapter = yaAdapter

        for(job in profile.jobs){
            val listener = View.OnClickListener {
                val action = JobsOverviewFragmentDirections.openJobEdit(job)
                findNavController().navigate(action)
            }

            yaAdapter.add(JobListItem(profile, listener, jobTypeArray))
        }

        binding.addJob.setOnClickListener {
            val job = Job(0,"")
            profile.jobs.add(job)

            val listener = View.OnClickListener {
                val action = JobsOverviewFragmentDirections.openJobEdit(job)
                findNavController().navigate(action)
            }

            yaAdapter.add(JobListItem(profile, listener, jobTypeArray))

            val action = JobsOverviewFragmentDirections.openJobEdit(job)
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        FileReader.saveFile(args.profile, requireContext())
    }
}