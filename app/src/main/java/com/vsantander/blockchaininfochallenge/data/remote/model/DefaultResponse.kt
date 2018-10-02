package com.vsantander.blockchaininfochallenge.data.remote.model

import com.google.gson.annotations.SerializedName

class DefaultResponse<T> (
        @SerializedName("status")
        val status: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("unit")
        val unit: String,

        @SerializedName("period")
        val period: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("values")
        var data: T
)