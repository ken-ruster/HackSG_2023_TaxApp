package com.example.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.JobEditBinding

class JobEditFragment:Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var binding: JobEditBinding
    val args: JobEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = JobEditBinding.inflate(inflater)

        with(binding){
            backButton.setOnClickListener(){
                findNavController().popBackStack()
            }
           ArrayAdapter.createFromResource(
                requireContext(),
                R.array.JobTypeArray,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
            ).also {adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                jobTypeEntry.adapter = adapter
            }

            jobTypeEntry.onItemSelectedListener= this@JobEditFragment
            jobTypeEntry.setSelection(args.profile.jobType)
            jobNameEntry.setText(args.profile.jobName)
            jobNameEntry.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    args.profile.jobName = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        args.profile.jobType = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
