package com.vsantander.blockchaininfochallenge.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond
import com.vsantander.blockchaininfochallenge.domain.usecase.GetTransactionsPerSecond
import com.vsantander.blockchaininfochallenge.presentation.model.Resource
import com.vsantander.blockchaininfochallenge.presentation.model.Status
import com.vsantander.blockchaininfochallenge.presentation.transactioninfo.TransactionsInfoViewModel
import com.vsantander.blockchaininfochallenge.utils.RxImmediateSchedulerRule
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
class TransactionsInfoViewModelTest {

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
    lateinit var getTransactionsPerSecond: GetTransactionsPerSecond

    @Mock
    lateinit var observer: Observer<Resource<List<TransactionPerSecond>>>

    lateinit var viewModel: TransactionsInfoViewModel

    @Before
    fun setUp() {
        viewModel = TransactionsInfoViewModel(getTransactionsPerSecond)
    }

    @Test
    fun loadInfoTransactionsLoadingState() {
        val transactionList = TransactionsPerSecondFactory.makeTransactionPerSecondList(NUMBER_ITEMS)
        Mockito.`when`(getTransactionsPerSecond.buildUseCase(Mockito.any()))
                .thenReturn(Single.just(transactionList))
        viewModel.resource.observeForever(observer)

        viewModel.loadInfoTransactions()

        Mockito.verify(observer).onChanged(Resource(Status.LOADING))
    }

    @Test
    fun loadInfoTransactionsSuccessState() {
        val transactionList = TransactionsPerSecondFactory.makeTransactionPerSecondList(NUMBER_ITEMS)
        Mockito.`when`(getTransactionsPerSecond.buildUseCase(Mockito.any()))
                .thenReturn(Single.just(transactionList))

        viewModel.loadInfoTransactions()

        assert(viewModel.resource.value?.status == Status.SUCCESS)
    }

    @Test
    fun loadInfoTransactionsErrorState() {
        Mockito.`when`(getTransactionsPerSecond.buildUseCase(Mockito.any()))
                .thenReturn(Single.error { throw RuntimeException() })

        viewModel.loadInfoTransactions()

        assert(viewModel.resource.value?.status == Status.FAILED)
    }

    @Test
    fun loadInfoTransactionsReturnTransactions() {
        val transactionList = TransactionsPerSecondFactory.makeTransactionPerSecondList(NUMBER_ITEMS)
        Mockito.`when`(getTransactionsPerSecond.buildUseCase(Mockito.any()))
                .thenReturn(Single.just(transactionList))

        viewModel.loadInfoTransactions()

        assert(viewModel.resource.value?.data == transactionList)
    }
}