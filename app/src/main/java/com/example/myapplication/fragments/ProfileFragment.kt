package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.ProfileItem
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ProfileListBinding
import com.example.myapplication.storage.FileReader
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupieAdapter
import java.time.LocalDate

class ProfileFragment(): Fragment() {

    private lateinit var binding: ProfileListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        if (savedInstanceState == null) {
            binding = ProfileListBinding.inflate(inflater)
            binding.listProfiles.adapter = GroupieAdapter()

        } else {
            binding = ProfileListBinding.bind(requireView())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        var profileArray: MutableList<TaxProfile>
        val yaAdapter: GroupieAdapter = binding.listProfiles.adapter as GroupieAdapter

        profileArray = FileReader(requireContext()).readFiles().toMutableList()

        if(profileArray.isEmpty()){
            FileReader(requireContext()).saveFile(TaxProfile())
            profileArray = listOf(TaxProfile()).toMutableList()
            FileReader(requireContext()).saveFile(TaxProfile())
        }

        yaAdapter.clear()

        profileArray.forEach { profile ->
            yaAdapter.add(createProfileItem(profile))
        }

        binding.addProfile.setOnClickListener {
            if (profileArray.maxOf { it.fy } >= LocalDate.now().year) {
                Snackbar.make(binding.root, "Unable to add a tax profile beyond the current year", LENGTH_SHORT).show()
            } else {
                val newProfile = TaxProfile()

                FileReader(requireContext()).saveFile(newProfile)
                profileArray.add(newProfile)
                yaAdapter.add(createProfileItem(newProfile))
            }
        }
    }

    private fun createProfileItem(profile: TaxProfile): ProfileItem {
        return ProfileItem(
                profile,
                {
                    val action = ProfileFragmentDirections.profileToStatement(profile)
                    findNavController().navigate(action)
                },
                {
                    val action = ProfileFragmentDirections.profileToJobs(profile)
                    findNavController().navigate(action)
                },
                {
                    val action = ProfileFragmentDirections.profileToRevs(profile)
                    findNavController().navigate(action)
                },
                {
                    val action = ProfileFragmentDirections.profileToExps(profile)
                    findNavController().navigate(action)
                },
        )
    }
}
