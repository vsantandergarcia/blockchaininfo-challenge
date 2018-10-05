package com.vsantander.blockchaininfochallenge.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionPerSecond(
        val timestamp: Float,
        val numberBitcoins: Float
) : Parcelable