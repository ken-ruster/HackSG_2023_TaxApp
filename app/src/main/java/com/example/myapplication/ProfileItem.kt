package com.example.myapplication

import android.view.View
import com.example.myapplication.databinding.ProfileItemBinding
import com.example.myapplication.databinding.ProfileRowExpBinding
import com.example.myapplication.databinding.ProfileRowJobBinding
import com.example.myapplication.databinding.ProfileRowRevBinding
import com.xwray.groupie.viewbinding.BindableItem


open class ProfileItem(private val profile: TaxProfile): BindableItem<ProfileItemBinding>(profile.fy.toLong()) {

    lateinit var profileItemBinding: ProfileItemBinding
    lateinit var jobRowBinding: ProfileRowJobBinding
    lateinit var revRowBinding: ProfileRowRevBinding
    lateinit var expRowBinding: ProfileRowExpBinding


    override fun bind(viewBinding: ProfileItemBinding, position: Int) {
        profileItemBinding.headerView.text = "YA" + profile.fy.toString()
        jobRowBinding.jobsList.text = profile.jobString()
        revRowBinding.revAmt.text = profile.totalRev().toString()
        expRowBinding.expAmt.text = (profile.totalAllowableExp() + profile.totalMatCost()).toString()

        jobRowBinding.editJobIcon.setOnClickListener {  }
        revRowBinding.editRevIcon.setOnClickListener {  }
        expRowBinding.editExpIcon.setOnClickListener {  }
        profileItemBinding.generateButton.setOnClickListener {  }
    }

    override fun getLayout(): Int = R.layout.profile_item

    override fun initializeViewBinding(view: View): ProfileItemBinding = ProfileItemBinding.bind(view)
}

