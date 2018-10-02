package com.vsantander.blockchaininfochallenge.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.vsantander.blockchaininfochallenge.presentation.base.viewmodel.ViewModelFactory
import com.vsantander.blockchaininfochallenge.presentation.info.TransactionsInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsInfoViewModel::class)
    abstract fun bindTransactionsInfoViewModel(viewModel: TransactionsInfoViewModel): ViewModel

}