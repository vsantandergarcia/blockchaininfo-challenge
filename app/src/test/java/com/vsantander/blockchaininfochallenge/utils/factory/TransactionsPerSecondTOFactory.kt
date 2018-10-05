package com.vsantander.blockchaininfochallenge.utils.factory

import com.vsantander.blockchaininfochallenge.data.remote.model.TransactionPerSecondTO

/**
 * Factory class for TransactionsPerSecondTO related instances
 */
class TransactionsPerSecondTOFactory {

    companion object {

        fun makeTransactionPerSecondTOList(count: Int): List<TransactionPerSecondTO> {
            val transactionTOList = mutableListOf<TransactionPerSecondTO>()
            repeat(count) {
                transactionTOList.add(makeTransactionPerSecondTOModel())
            }
            return transactionTOList
        }

        private fun makeTransactionPerSecondTOModel(): TransactionPerSecondTO {
            return TransactionPerSecondTO(
                timestamp = DataFactory.randomFloat(),
                numberBitcoins = DataFactory.randomFloat()
            )
        }
    }
}