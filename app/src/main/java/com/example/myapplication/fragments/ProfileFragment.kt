package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ProfileItem
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ProfileListBinding
import com.example.myapplication.storage.FileReader
import com.xwray.groupie.GroupieAdapter

class ProfileFragment(): Fragment() {
    lateinit var binding: ProfileListBinding
    lateinit var profileArray: List<TaxProfile>
    lateinit var fileReader: FileReader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ProfileListBinding.inflate(layoutInflater)
        fileReader = FileReader(requireContext())

        profileArray = fileReader.readFiles()

        if(profileArray.isEmpty()){
            profileArray = listOf(TaxProfile())
            fileReader.saveFile(TaxProfile())
        }

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listProfiles
        recyclerView.adapter = yaAdapter

        for(profile in profileArray){
            val listener = OnClickListener {
                val action = ProfileFragmentDirections.profileToStatement(profile)
                findNavController().navigate(action)
            }

            val jobOverviewListener = OnClickListener{
                val action = ProfileFragmentDirections.profileToJobs(profile)
                findNavController().navigate(action)
            }

            val revOverviewListener = OnClickListener {
                val action = ProfileFragmentDirections.profileToRevs(profile)
                findNavController().navigate(action)
            }

            val expOverviewListener = OnClickListener {
                val action = ProfileFragmentDirections.profileToExps(profile)
                findNavController().navigate(action)
            }

            yaAdapter.add(ProfileItem(profile, listener, jobOverviewListener, revOverviewListener, expOverviewListener))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listProfiles
        recyclerView.adapter = yaAdapter

        for (profile in profileArray){
            fileReader.saveFile(profile)

            val listener = OnClickListener {
                val action = ProfileFragmentDirections.profileToStatement(profile)
                findNavController().navigate(action)
            }

            val jobOverviewListener = OnClickListener{
                val action = ProfileFragmentDirections.profileToJobs(profile)
                findNavController().navigate(action)
            }

            val revOverviewListener = OnClickListener {
                val action = ProfileFragmentDirections.profileToRevs(profile)
                findNavController().navigate(action)
            }

            val expOverviewListener = OnClickListener {
                val action = ProfileFragmentDirections.profileToExps(profile)
                findNavController().navigate(action)
            }

            yaAdapter.add(ProfileItem(profile, listener, jobOverviewListener, revOverviewListener, expOverviewListener))
        }

        binding.addProfile.setOnClickListener {
            val newProfile = TaxProfile()
            fileReader.saveFile(newProfile)
            profileArray.toMutableList().add(newProfile)

            val listener = OnClickListener {
                val action = ProfileFragmentDirections.profileToStatement(newProfile)
                findNavController().navigate(action)
            }

            val jobOverviewListener = OnClickListener{
                val action = ProfileFragmentDirections.profileToJobs(newProfile)
                findNavController().navigate(action)
            }

            val revOverviewListener = OnClickListener {
                val action = ProfileFragmentDirections.profileToRevs(newProfile)
                findNavController().navigate(action)
            }

            val expOverviewListener = OnClickListener {
                val action = ProfileFragmentDirections.profileToExps(newProfile)
                findNavController().navigate(action)
            }

            yaAdapter.add(ProfileItem(newProfile,listener, jobOverviewListener, revOverviewListener, expOverviewListener))
        }
    }
}