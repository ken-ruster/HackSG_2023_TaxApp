package com.example.myapplication

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun View.flowClicked() = callbackFlow<View> {
    setOnClickListener { v -> trySend(v) }
    awaitClose { setOnClickListener(null) }
}