package com.vsantander.blockchaininfochallenge.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsantander.blockchaininfochallenge.data.remote.RestClient
import com.vsantander.blockchaininfochallenge.data.remote.mapper.TransactionPerSecondTOMapper
import com.vsantander.blockchaininfochallenge.data.remote.model.DefaultResponse
import com.vsantander.blockchaininfochallenge.data.repository.TransactionRepositoryImpl
import com.vsantander.blockchaininfochallenge.utils.RxImmediateSchedulerRule
import com.vsantander.blockchaininfochallenge.utils.factory.TransactionsPerSecondTOFactory
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionRepositoryTest {

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
    lateinit var service: RestClient

    lateinit var mapper: TransactionPerSecondTOMapper

    lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setUp() {
        mapper = TransactionPerSecondTOMapper()
        repository = TransactionRepositoryImpl(service, mapper)
    }

    @Test
    fun getTransactionsPerSecondReturnItems() {
        val transactionTOList = TransactionsPerSecondTOFactory.makeTransactionPerSecondTOList(NUMBER_ITEMS)
        val transactionList = transactionTOList.map { mapper.toEntity(it) }
        Mockito.`when`(service.getTransactionsPerSecond(Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(Single.just(DefaultResponse(data = transactionTOList,
                        name = "", description = "", status = "", unit = "", period = "")))

        val testObserver = repository.getTransactionsPerSecond(Mockito.anyString(),
                Mockito.anyString()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(NUMBER_ITEMS))
        assert(result == transactionList)
    }

    @Test
    fun getTransactionsPerSecondErrorReturnError() {
        Mockito.`when`(service.getTransactionsPerSecond(Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = repository.getTransactionsPerSecond(Mockito.anyString(),
                Mockito.anyString()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }
}