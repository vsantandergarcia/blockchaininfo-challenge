package com.vsantander.blockchaininfochallenge.data.remote.model

import com.google.gson.annotations.SerializedName

data class TransactionPerSecondTO (
        @SerializedName("x")
        val timestamp: Double,

        @SerializedName("y")
        val numberBitcoins: Double
)