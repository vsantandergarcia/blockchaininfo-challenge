package com.vsantander.blockchaininfochallenge.utils.factory

import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond

/**
 * Factory class for TransactionPerSecond related instances
 */
class TransactionsPerSecondFactory {

    companion object {

        fun makeTransactionPerSecondList(count: Int): List<TransactionPerSecond> {
            val transactionList = mutableListOf<TransactionPerSecond>()
            repeat(count) {
                transactionList.add(makeTransactionPerSecondModel())
            }
            return transactionList
        }

        private fun makeTransactionPerSecondModel(): TransactionPerSecond {
            return TransactionPerSecond(
                    timestamp = DataFactory.randomDouble(),
                    numberBitcoins = DataFactory.randomDouble()
            )
        }
    }
}