package com.vsantander.blockchaininfochallenge.data.repository

import com.vsantander.blockchaininfochallenge.data.remote.RestClient
import com.vsantander.blockchaininfochallenge.data.remote.mapper.TransactionPerSecondTOMapper
import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
        private val restClient: RestClient,
        private val mapper: TransactionPerSecondTOMapper) : TransactionRepository {

    override fun getTransactionsPerSecond(timespan: String, rollingAverage: String): Single<List<TransactionPerSecond>> {
        return restClient.getTransactionsPerSecond(timespan, rollingAverage)
                .map { mapper.toEntity(it.data) }
    }

}