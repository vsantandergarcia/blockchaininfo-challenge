package com.vsantander.blockchaininfochallenge.presentation.info

import android.arch.lifecycle.MutableLiveData
import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond
import com.vsantander.blockchaininfochallenge.domain.usecase.GetTransactionPerSecond
import com.vsantander.blockchaininfochallenge.extension.logd
import com.vsantander.blockchaininfochallenge.extension.loge
import com.vsantander.blockchaininfochallenge.presentation.base.viewmodel.BaseViewModel
import com.vsantander.blockchaininfochallenge.presentation.model.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransactionsInfoViewModel @Inject constructor(
        private val getTransactionPerSecond: GetTransactionPerSecond
): BaseViewModel() {

    val resource = MutableLiveData<Resource<List<TransactionPerSecond>>>()

    fun loadInfo() {
        resource.value = Resource.loading()

        val params = GetTransactionPerSecond.RequestValues("4weeks", "8hours")
        disposables += getTransactionPerSecond.buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("loadInfo.onSuccess")
                            resource.value = Resource.success(it)
                        },
                        onError = {
                            loge("loadInfo.onError", it)
                            resource.value = Resource.error(it)
                        }
                )
    }
}