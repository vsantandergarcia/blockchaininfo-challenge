package com.vsantander.blockchaininfochallenge.presentation.transactioninfo

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.vsantander.blockchaininfochallenge.extension.formatToString
import java.util.*


class TimeAxisValueFormatter : IAxisValueFormatter {

    companion object {
        private const val FORMAT_DATE = "dd/MM"
    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     */

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
       return Date(value.toLong()).formatToString(FORMAT_DATE)
    }

}