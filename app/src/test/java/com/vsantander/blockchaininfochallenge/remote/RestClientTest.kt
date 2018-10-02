package com.vsantander.blockchaininfochallenge.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.vsantander.blockchaininfochallenge.data.remote.RestClient
import com.vsantander.blockchaininfochallenge.data.remote.model.DefaultResponse
import com.vsantander.blockchaininfochallenge.data.remote.model.TransactionPerSecondTO
import com.vsantander.blockchaininfochallenge.extension.fromJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class RestClientTest {

    private lateinit var service: RestClient

    private lateinit var mockWebServer: MockWebServer

    @Before
    @Throws(IOException::class)
    fun createService() {
        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create<RestClient>(RestClient::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    @Throws(JsonSyntaxException::class)
    fun getTransactionsPerSecondSuccess() {
        val fileContent = getFileContentAsString("response.json")
        val items = Gson().fromJson<DefaultResponse<List<TransactionPerSecondTO>>>(fileContent).data
        mockWebServer.enqueue(MockResponse().setBody(fileContent))

        val testObserver = service.getTransactionsPerSecond(Mockito.anyString(),
                Mockito.anyString()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.data.size, CoreMatchers.`is`(items.size))
        assert(result.data == items)
    }

    @Test
    fun getTransactionsPerSecondBadRequest() {
        mockWebServer.enqueue(MockResponse().setBody("{error:\"bad request\"").setResponseCode(400))

        val testObserver = service.getTransactionsPerSecond(
                Mockito.anyString(), Mockito.anyString()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(HttpException::class.java)
    }

    @Throws(IOException::class)
    private fun getFileContentAsString(fileName: String): String {
        val inputStream =
                javaClass.classLoader.getResourceAsStream("ws-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readString(StandardCharsets.UTF_8)
    }
}