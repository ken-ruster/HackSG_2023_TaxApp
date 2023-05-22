package com.example.myapplication

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.myapplication.data.Job
import com.example.myapplication.databinding.ExpEditRowBinding
import com.xwray.groupie.viewbinding.BindableItem


open class ExpEditListItem(
    private var map: MutableMap<String, Float>,
    var job: Job,
): BindableItem<ExpEditRowBinding>() {

    override fun bind(viewBinding: ExpEditRowBinding, position: Int) {
        with(viewBinding) {

            checkBox.text = job.jobName
            checkBox.isChecked = map[job.id]?.let {
                portionAmt.setText(it.toString())
                true
            } ?: run {
                portionAmt.setText("0.0")
                false
            }
            portionAmt.isEnabled = checkBox.isChecked

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                portionAmt.isEnabled = isChecked

                if(isChecked) {
                    val remainder = map.values.sum()
                    val temp = portionAmt.text.toString().toFloatOrNull() ?: run {
                        portionAmt.setText("0.0")
                        0.0f
                    }
                    map.put(job.id, temp)
                } else {
                    portionAmt.setText("0.0")
                    map.remove(job.id)
                }
            }

            portionAmt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(!s.isNullOrBlank()) {
                        checkBox.isChecked = s.toString().toFloatOrNull()?.let {
                            map.replace(job.id, s.toString().toFloat())
                            it != 0.0f
                        } ?: run {
                            map.remove(job.id)
                            portionAmt.setText("0.0")
                            false
                        }
                    } else {
                        map.remove(job.id)
                        portionAmt.setText("0.0")
                        checkBox.isChecked = false
                    }
                }

                override fun afterTextChanged(s: Editable?) {}

            })
        }
    }

    override fun getLayout(): Int = R.layout.exp_edit_row

    override fun initializeViewBinding(view: View): ExpEditRowBinding = ExpEditRowBinding.bind(view)
}
