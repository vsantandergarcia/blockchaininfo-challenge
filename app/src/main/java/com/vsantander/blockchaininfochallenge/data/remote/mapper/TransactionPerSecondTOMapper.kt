package com.vsantander.blockchaininfochallenge.data.remote.mapper

import com.vsantander.blockchaininfochallenge.data.remote.model.TransactionPerSecondTO
import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond
import javax.inject.Inject

class TransactionPerSecondTOMapper @Inject constructor() {

    fun toEntity(value: TransactionPerSecondTO): TransactionPerSecond {
        return TransactionPerSecond(
                timestamp = value.timestamp * 1000, // timestamp in miliseconds
                numberBitcoins = value.numberBitcoins)
    }

    fun toEntity(values: List<TransactionPerSecondTO>): List<TransactionPerSecond> = values.map { toEntity(it) }

}