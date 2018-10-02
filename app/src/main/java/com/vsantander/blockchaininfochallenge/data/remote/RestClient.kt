package com.vsantander.blockchaininfochallenge.data.remote

import com.vsantander.blockchaininfochallenge.data.remote.model.DefaultResponse
import com.vsantander.blockchaininfochallenge.data.remote.model.TransactionPerSecondTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestClient {

    @GET("transactions-per-second?format=json")
    fun getTransactionsPerSecond(
            @Query("timespan") timespan: String,
            @Query("rollingAverage") rollingAverage: String
    ): Single<DefaultResponse<List<TransactionPerSecondTO>>>

}