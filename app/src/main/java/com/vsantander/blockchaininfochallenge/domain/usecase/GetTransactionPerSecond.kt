package com.vsantander.blockchaininfochallenge.domain.usecase

import com.vsantander.blockchaininfochallenge.data.repository.TransactionRepositoryImpl
import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond
import com.vsantander.blockchaininfochallenge.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetTransactionPerSecond @Inject constructor(
        private val repository: TransactionRepositoryImpl
) : SingleUseCase<GetTransactionPerSecond.RequestValues, List<TransactionPerSecond>>() {

    class RequestValues constructor(
            val timespan: String,
            val rollingAverage: String
    )

    override fun buildUseCase(params: RequestValues?): Single<List<TransactionPerSecond>> {
        return repository.getTransactionsPerSecond(params!!.timespan,
                params.rollingAverage)
    }

}
