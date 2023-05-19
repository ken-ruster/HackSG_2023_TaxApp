package com.example.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ExpEditListItem
import com.example.myapplication.R
import com.example.myapplication.databinding.ExpEditBinding
import com.xwray.groupie.GroupieAdapter

class ExpEditFragment:Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ExpEditBinding
    val args: ExpEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExpEditBinding.inflate(layoutInflater)
        val profile = args.profile
        var exp = args.exp

        with(binding){

            expNameEntry.setText(exp.expName)
            expAmtEntry.setText(exp.amt.toString())
            val expTypeArray = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.ExpTypeArray,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
            ).also {adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                expTypeEntry.adapter = adapter
            }

            expTypeEntry.onItemSelectedListener= this@ExpEditFragment
            expTypeEntry.setSelection(exp.expType)

            expNameEntry.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    if(!s.isNullOrBlank()) {
                        exp.expName = s.toString()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            expAmtEntry.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    if(!s.isNullOrBlank()) {
                        exp.amt = s.toString().toFloat()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            val yaAdapter: GroupieAdapter = GroupieAdapter()
            val recyclerView: RecyclerView = listExps
            recyclerView.adapter = yaAdapter

            var stringArray = emptyArray<String>().apply {
                for (job in profile.jobs){
                    this.plus(job.id)
                }
            }

            val adapter = ArrayAdapter(requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,stringArray)

            for(job in profile.jobs){
                yaAdapter.add(ExpEditListItem(exp.portion, job))
            }
        }

        return binding.root
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        args.exp.expType = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}