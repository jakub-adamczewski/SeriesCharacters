package com.example.ui_base

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: String, short: Boolean = true) =
    Toast.makeText(requireContext(), text, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)