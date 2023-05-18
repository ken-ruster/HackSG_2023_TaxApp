package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.StatementBinding

class StatementFragment() : Fragment() {
    val args: StatementFragmentArgs by navArgs()

    lateinit var binding: StatementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = StatementBinding.inflate(layoutInflater)

        val profile = args.profile

        with(binding.statementTable) {
            statementRevAmt.text = "$${profile.totalRev()}"

            if (profile.totalRev() >= 200000) {
                statementgplAmt.text = "$${profile.totalGrossProfit()}"
                allowableAmt.text = "$${profile.totalAllowableExp()}"
            } else {
                //disables 4-line statement exclusive fields
                statementgplAmt.text = "-"
                allowableAmt.text = "-"

                gplTitle.setTextColor(0xE0E0E0)
                allowableTitle.setTextColor(0xE0E0E0)
            }
            binding.statementTable.adjProfitLossAmt.text = "$${profile.totalAdjProfit()}"
        }

        return binding.root
    }

}