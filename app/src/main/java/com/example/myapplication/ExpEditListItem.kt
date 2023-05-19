package com.example.myapplication

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myapplication.data.TaxProfile
import com.example.myapplication.databinding.ExpEditBinding
import com.example.myapplication.databinding.ExpEditRowBinding
import com.example.myapplication.fragments.ExpEditFragment
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.viewbinding.BindableItem


open class ExpEditListItem(
    private val profile: TaxProfile,
    private var map: MutableMap<String, Float>,
    var key: String,
    private val listener: OnClickListener,
    private val adapter: ArrayAdapter<String>)
    : BindableItem<ExpEditRowBinding>(), AdapterView.OnItemSelectedListener {

    override fun bind(viewBinding: ExpEditRowBinding, position: Int) {
        with(viewBinding) {
            deleteButton.setOnClickListener(listener)

            adapter.setDropDownViewResource(
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
            portionName.adapter = adapter

            portionAmt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    map.remove(key)
                    map.put(key!!, s.toString().toFloat())
                }

                override fun afterTextChanged(s: Editable?) {}

            })
        }
    }

    override fun getLayout(): Int = R.layout.exp_edit_row

    override fun initializeViewBinding(view: View): ExpEditRowBinding = ExpEditRowBinding.bind(view)

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val newKey = profile.searchJobByName(adapter.getItem(position).toString())!!

        if(!map.containsKey(newKey)) {
            val value = map[key]!!
            map.remove(key)
            key = newKey
            map.put(key, value)
        } else {

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}