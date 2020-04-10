package com.problemsolver.githubtrending.services

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class TrendingApiTest {

    private val mockWebServer = MockWebServer()

    private lateinit var trendingApi: TrendingApi

    @Before
    fun setUp() {
        mockWebServer.start()
        trendingApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrendingApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun apiTest() {
        // create
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Mockito.anyString())
        mockWebServer.enqueue(response)

        // test
        val product = trendingApi.getTrendingList("", "", "").isExecuted
        assertNotNull(product)
    }
}