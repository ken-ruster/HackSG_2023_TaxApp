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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ProfileListBinding.inflate(layoutInflater)

        profileArray = FileReader.readFiles(requireContext())

        if(profileArray.isEmpty()){
            profileArray = listOf(TaxProfile())
            FileReader.saveFile(TaxProfile(),requireContext())
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

    override fun onPause() {
        super.onPause()
        for (profile in profileArray){
            FileReader.saveFile(profile, requireContext())
        }
    }
}