package com.example.myapplication

import android.view.View
import android.view.View.OnClickListener
import com.example.myapplication.data.Exp
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ExpRowBinding
import com.xwray.groupie.viewbinding.BindableItem


open class ExpListItem(
    private val profile: TaxProfile,
    private val exp: Exp,
    private val deleteListener: OnClickListener,
    private val editListener: OnClickListener): BindableItem<ExpRowBinding>(profile.fy.toLong()) {

    override fun bind(viewBinding: ExpRowBinding, position: Int) {
        with(viewBinding) {
            expName.text = exp.expName
            expType.text = if (exp.expType == 0) "Type: Material Cost"
            else "Type: Allowable Business Expense"
            expVal.text = "Amount: $${exp.amt}"

            var proportionsList = String()
            for (entry in exp.portion.entries){
                proportionsList += profile.searchJobById(entry.key) + ": " + entry.value.toString() + "%\n"
            }
            proportions.text = "Proportions: ${proportionsList}"

            editIcon.setOnClickListener(editListener)
            deleteIcon.setOnClickListener(deleteListener)
        }
    }

    override fun getLayout(): Int = R.layout.exp_row

    override fun initializeViewBinding(view: View): ExpRowBinding = ExpRowBinding.bind(view)
}