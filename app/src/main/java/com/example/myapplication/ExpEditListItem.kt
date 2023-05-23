package com.example.myapplication

import android.view.View
import com.example.myapplication.data.Job
import com.example.myapplication.databinding.ExpEditRowBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.math.RoundingMode
import kotlin.math.max
import kotlin.math.min


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

                if (isChecked) {
                    val remainder: Float = max(100f - map.values.sum(), 0f).round()
                    map[job.id] = remainder
                    portionAmt.setText(remainder.toString())
                    root.requestFocus()
                } else {
                    map.remove(job.id)
                    portionAmt.setText("0.0")
                }
            }

            portionAmt.setOnFocusChangeListener { _, on ->
                if (on) return@setOnFocusChangeListener

                val fv = portionAmt.text.toString().toFloatOrNull()?.round() ?: 0.0f
                val remainder: Float = max(100f - map.values.sum() + (map[job.id] ?: 0f), 0f).round()
                val minV = min(remainder, fv).round()
                when {
                    minV == 0f -> {
                        checkBox.isChecked = false
                    }
                    minV < fv -> {
                        map[job.id] = minV
                        portionAmt.setText(minV.toString())
                    }
                    else -> {
                        map[job.id] = minV
                    }

                }
            }

            /*portionAmt.addTextChangedListener(object: TextWatcher{

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(!s.isNullOrBlank()) {
                        checkBox.isChecked = s.toString().toFloatOrNull()?.round()?.let {
                            map.replace(job.id, it)
                            //portionAmt.setText(it.toString())
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

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}

            })*/
        }
    }

    private fun Float.round(): Float = toBigDecimal().setScale(1, RoundingMode.UP).toFloat()

    override fun getLayout(): Int = R.layout.exp_edit_row

    override fun initializeViewBinding(view: View): ExpEditRowBinding = ExpEditRowBinding.bind(view)
}
