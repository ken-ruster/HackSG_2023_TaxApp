package com.example.myapplication.data

import android.os.Parcelable
import com.github.shamil.Xid
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    var jobType: Int,
    var jobName: String,
    val id: String = Xid.string(),
) : Parcelable








