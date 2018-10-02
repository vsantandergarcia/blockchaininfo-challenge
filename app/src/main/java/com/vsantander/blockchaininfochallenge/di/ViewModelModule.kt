package com.vsantander.blockchaininfochallenge.di

import android.arch.lifecycle.ViewModelProvider
import com.vsantander.blockchaininfochallenge.presentation.base.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}