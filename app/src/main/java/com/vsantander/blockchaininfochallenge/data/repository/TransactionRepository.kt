package com.vsantander.blockchaininfochallenge.data.repository

import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond
import io.reactivex.Single

interface TransactionRepository {

    /**
     * Gets a list of Transactions
     *
     * @param timespan: Duration of the chart
     * @param rollingAverage: Duration over which the data should be averaged
     *
     * @return a List of the Transactions per Second available.
     */
    fun getTransactionsPerSecond(timespan: String,
                                 rollingAverage: String): Single<List<TransactionPerSecond>>
}