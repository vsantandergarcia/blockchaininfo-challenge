package com.vsantander.blockchaininfochallenge.di

import com.vsantander.blockchaininfochallenge.presentation.transactioninfo.TimeAxisValueFormatter
import dagger.Module
import dagger.Provides

@Module
class TransactionsInfoModule {

    @Provides
    fun providesTimeAxisValueFormatter(): TimeAxisValueFormatter {
        return TimeAxisValueFormatter()
    }
}