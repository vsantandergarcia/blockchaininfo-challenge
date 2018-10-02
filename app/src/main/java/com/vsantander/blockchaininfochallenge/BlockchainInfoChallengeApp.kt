package com.vsantander.blockchaininfochallenge

import android.app.Activity
import android.app.Application
import com.vsantander.blockchaininfochallenge.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BlockchainInfoChallengeApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()

        // Dagger 2 injection
        AppInjector.init(this)
    }
}