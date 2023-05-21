package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ExpListItem
import com.example.myapplication.data.Exp
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ExpsOverviewBinding
import com.example.myapplication.storage.FileReader
import com.xwray.groupie.GroupieAdapter

class ExpsOverviewFragment(): Fragment() {
    lateinit var binding: ExpsOverviewBinding
    lateinit var profile: TaxProfile
    lateinit var yaAdapter: GroupieAdapter
    val args: ExpsOverviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ExpsOverviewBinding.inflate(layoutInflater)
        profile = args.profile
        yaAdapter = GroupieAdapter()

        val recyclerView: RecyclerView = binding.listExps
        recyclerView.adapter = yaAdapter

        loadView()

        binding.addExp.setOnClickListener {
            val action = ExpsOverviewFragmentDirections.openExpEdit(profile, Exp(0,"",0.0F, emptyMap<String,Float>().toMutableMap()), true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun loadView() {
        yaAdapter.clear()
        for (exp in profile.exps) {
            createElement(exp)
        }
    }

    private fun createElement(exp: Exp) {
        val editListener = View.OnClickListener {
            val action = ExpsOverviewFragmentDirections.openExpEdit(profile, exp, false)
            findNavController().navigate(action)
        }

        val deleteListener = View.OnClickListener {
            val builder = AlertDialog.Builder(this.context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    profile.exps.remove(exp)
                    loadView()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        yaAdapter.add(ExpListItem(profile, exp, deleteListener, editListener))
    }

    override fun onPause() {
        super.onPause()
        FileReader(this.requireContext()).saveFile(args.profile)
    }
}