package com.example.myapplication

import android.view.View
import android.view.View.OnClickListener
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.RevRowBinding
import com.xwray.groupie.viewbinding.BindableItem


open class RevListItem(private val profile: TaxProfile, private val listener: OnClickListener): BindableItem<RevRowBinding>(profile.fy.toLong()) {

    override fun bind(viewBinding: RevRowBinding, position: Int) {
        with(viewBinding) {
            revName.text = profile.revs[position].revName
            revType.text = if (profile.revs[position].revType == 0) "Type: Salary/commission"
            else "Type: Grant/incentive"
            revVal.text = "Amount: $${profile.revs[position].amt}"

            editIcon.setOnClickListener(listener)
        }
    }

    override fun getLayout(): Int = R.layout.rev_row

    override fun initializeViewBinding(view: View): RevRowBinding = RevRowBinding.bind(view)
}