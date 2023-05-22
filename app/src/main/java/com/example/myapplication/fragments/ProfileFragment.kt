package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ProfileItem
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ProfileListBinding
import com.example.myapplication.storage.FileReader
import com.xwray.groupie.GroupieAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class ProfileFragment(): Fragment() {
    lateinit var binding: ProfileListBinding
    lateinit var profileArray: MutableList<TaxProfile>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ProfileListBinding.inflate(layoutInflater)

        profileArray = FileReader(requireContext()).readFiles().toMutableList()

        if(profileArray.isEmpty()){
            FileReader(requireContext()).saveFile(TaxProfile())
            profileArray = listOf(TaxProfile()).toMutableList()
            FileReader(requireContext()).saveFile(TaxProfile())
        }

        val yaAdapter: GroupieAdapter = GroupieAdapter()
        val recyclerView: RecyclerView = binding.listProfiles
        recyclerView.adapter = yaAdapter

        for(profile in profileArray){
            val listener = OnClickListener {
                findNavController().navigate(ProfileFragmentDirections.profileToStatement(profile))
            }

            val jobOverviewListener = OnClickListener{
                findNavController().navigate(ProfileFragmentDirections.profileToJobs(profile))
            }

            val revOverviewListener = OnClickListener {
                findNavController().navigate(ProfileFragmentDirections.profileToRevs(profile))
            }

            val expOverviewListener = OnClickListener {
                findNavController().navigate(ProfileFragmentDirections.profileToExps(profile))
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
            FileReader(requireContext()).saveFile(profile)

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
            if (profileArray.all { profile -> profile.fy != newProfile.fy }) {
                lifecycleScope.launch {
                    FileReader(requireContext()).saveFile(newProfile)
                }
                profileArray.add(newProfile)

                val listener = OnClickListener {
                    findNavController().navigate(
                        ProfileFragmentDirections.profileToStatement(
                            newProfile
                        )
                    )
                }

                val jobOverviewListener = OnClickListener {
                    findNavController().navigate(ProfileFragmentDirections.profileToJobs(newProfile))
                }

                val revOverviewListener = OnClickListener {
                    findNavController().navigate(ProfileFragmentDirections.profileToRevs(newProfile))
                }

                val expOverviewListener = OnClickListener {
                    findNavController().navigate(ProfileFragmentDirections.profileToExps(newProfile))
                }

                yaAdapter.add(
                    ProfileItem(
                        newProfile,
                        listener,
                        jobOverviewListener,
                        revOverviewListener,
                        expOverviewListener
                    )
                )
            } else {
                makeText(requireContext(), "Profile already exists for current Year of Assessment!", Toast.LENGTH_LONG)
            }
        }
    }
}