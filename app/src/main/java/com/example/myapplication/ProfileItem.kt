package com.example.myapplication

import android.view.View
import android.view.View.OnClickListener
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ProfileItemBinding
import com.xwray.groupie.viewbinding.BindableItem


open class ProfileItem(
    private val profile: TaxProfile,
    private val listener: OnClickListener,
    private val jobOverviewListener: OnClickListener,
    private val revOverviewListener: OnClickListener,
    private val expOverviewListener: OnClickListener
): BindableItem<ProfileItemBinding>(profile.fy.toLong()) {

    override fun bind(viewBinding: ProfileItemBinding, position: Int) {
        with (viewBinding) {
            headerView.text = "YA ${profile.fy}"
            profileRow.jobsList.text = profile.jobString()
            profileRowRev.revAmt.text = "$${profile.totalRev()}"
            profileRowExp.expAmt.text = "$${(profile.totalAllowableExp() + profile.totalMatCost())}"

            generateButton.setOnClickListener(listener)

            profileRow.editJobIcon.setOnClickListener(jobOverviewListener)
            profileRowRev.editRevIcon.setOnClickListener(revOverviewListener)
            profileRowExp.editExpIcon.setOnClickListener(expOverviewListener)
        }


    }

    override fun getLayout(): Int = R.layout.profile_item

    override fun initializeViewBinding(view: View): ProfileItemBinding = ProfileItemBinding.bind(view)
}

