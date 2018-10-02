package com.vsantander.blockchaininfochallenge.domain.usecase.base

import io.reactivex.Completable

abstract class CompletableUseCase<in T> :
        RxUseCase<T, Completable>()