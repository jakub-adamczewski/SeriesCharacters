package com.example.util

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.callOnEmit(action: (elem: T) -> Unit) = this.map {
    action(it)
    it
}

fun <T> Flow<T>.logItems(tag: String, flowName: String) = this.callOnEmit {
    Log.d(tag, "$flowName emits: $it")
}