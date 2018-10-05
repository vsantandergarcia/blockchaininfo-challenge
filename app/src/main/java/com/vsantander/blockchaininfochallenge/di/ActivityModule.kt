package com.vsantander.blockchaininfochallenge.di

import com.vsantander.blockchaininfochallenge.presentation.transactioninfo.TransactionsInfoActivity
import com.vsantander.blockchaininfochallenge.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(ViewModelModule::class)])
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [TransactionsInfoModule::class])
    internal abstract fun contributeTransactionsInfoActivity(): TransactionsInfoActivity

}