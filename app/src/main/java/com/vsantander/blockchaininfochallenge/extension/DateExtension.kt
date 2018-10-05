package com.vsantander.blockchaininfochallenge.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToString(pattern: String) : String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this)
}

