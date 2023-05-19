package com.example.myapplication

import android.view.View
import android.view.View.OnClickListener
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.JobRowBinding
import com.xwray.groupie.viewbinding.BindableItem


open class JobListItem(
    private val profile: TaxProfile,
    private val listener: OnClickListener,
    private val jobTypeArray: Array<String>
): BindableItem<JobRowBinding>(profile.fy.toLong()) {

    lateinit var binding: JobRowBinding

    override fun bind(viewBinding: JobRowBinding, position: Int) {
        with(viewBinding) {
            if(profile.jobs.isNotEmpty()) {
                itemName.text = profile.jobs[position].jobName
                jobType.text = "Type: ${jobTypeArray[profile.jobs[position].jobType]}"
                editIcon.setOnClickListener(listener)
            }
        }
    }

    override fun getLayout(): Int = R.layout.job_row

    override fun initializeViewBinding(view: View): JobRowBinding = JobRowBinding.bind(view)
}