package com.vsantander.blockchaininfochallenge.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsantander.blockchaininfochallenge.data.repository.TransactionRepositoryImpl
import com.vsantander.blockchaininfochallenge.domain.usecase.GetTransactionsPerSecond
import com.vsantander.blockchaininfochallenge.utils.RxImmediateSchedulerRule
import com.vsantander.blockchaininfochallenge.utils.factory.DataFactory
import com.vsantander.blockchaininfochallenge.utils.factory.TransactionsPerSecondFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTransactionsPerSecondTest {

    companion object {
        private const val NUMBER_ITEMS = 5
    }

    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var repository: TransactionRepositoryImpl

    lateinit var getTransactionsPerSecond: GetTransactionsPerSecond

    @Before
    fun setUp() {
        getTransactionsPerSecond = GetTransactionsPerSecond(repository)
    }

    @Test
    fun getTransactionsPerSecondReturnTransactions() {
        val transactionList = TransactionsPerSecondFactory.makeTransactionPerSecondList(NUMBER_ITEMS)
        Mockito.`when`(repository.getTransactionsPerSecond(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Single.just(transactionList))

        val params = GetTransactionsPerSecond.RequestValues(
                DataFactory.randomUuid(),  DataFactory.randomUuid())
        val testObserver = getTransactionsPerSecond.buildUseCase(params).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result == transactionList)
    }

    @Test
    fun getTransactionsPerSecondErrorReturnError() {
        Mockito.`when`(repository.getTransactionsPerSecond(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Single.error { throw RuntimeException() })

        val params = GetTransactionsPerSecond.RequestValues(
                DataFactory.randomUuid(),  DataFactory.randomUuid())
        val testObserver = getTransactionsPerSecond.buildUseCase(params).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }
    
}