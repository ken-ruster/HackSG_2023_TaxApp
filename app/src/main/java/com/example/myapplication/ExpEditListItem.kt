package com.example.myapplication

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myapplication.data.Job
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ExpEditBinding
import com.example.myapplication.databinding.ExpEditRowBinding
import com.example.myapplication.fragments.ExpEditFragment
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.viewbinding.BindableItem


open class ExpEditListItem(
    private var map: MutableMap<String, Float>,
    var job: Job,
): BindableItem<ExpEditRowBinding>() {

    override fun bind(viewBinding: ExpEditRowBinding, position: Int) {
        with(viewBinding) {

            checkBox.isChecked = map.containsKey(job.id)
            checkBox.text = job.jobName
            if(map.containsKey(job.id)){
                portionAmt.setText(map[job.id].toString())
            } else {
              portionAmt.setText("0.0")
            }
            
            checkBox.setOnCheckedChangeListener { _, isChecked -> if(isChecked){
                var temp = portionAmt.text.toString().toFloat()
                if(temp == null){
                    temp = 0.0F
                    portionAmt.setText("0.0")
                }
                map.put(job.id, temp)
            } else {
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
                        map.replace(job.id, s.toString().toFloat())
                        checkBox.isChecked = (s.toString().toFloat() != 0.0F)
                    }
                }

                override fun afterTextChanged(s: Editable?) {}

            })
        }
    }

    override fun getLayout(): Int = R.layout.exp_edit_row

    override fun initializeViewBinding(view: View): ExpEditRowBinding = ExpEditRowBinding.bind(view)
}