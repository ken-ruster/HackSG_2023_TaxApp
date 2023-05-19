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
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.RevEditBinding

class RevEditFragment:Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var binding: RevEditBinding
    val args: RevEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RevEditBinding.inflate(layoutInflater)

        with(binding){
            val revTypeArray = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.RevTypeArray,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
            ).also {adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                revTypeEntry.adapter = adapter
            }

            revTypeEntry.onItemSelectedListener= this@RevEditFragment
            revNameEntry.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    args.profile.revName = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            revAmtEntry.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    if (!s.isNullOrBlank())
                        args.profile.amt = s.toString().toFloat()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        args.profile.revType = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}