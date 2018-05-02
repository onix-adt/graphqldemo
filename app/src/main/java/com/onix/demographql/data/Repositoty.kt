package com.onix.demographql.data

import com.apollographql.apollo.ApolloClient
import com.onix.demographql.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class Repositoty {
    private val BASE_URL = "https://api.githunt.com/graphql"

    val apolloClient: ApolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(createOkHttpClient()).build()

    fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        val logLevel = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
                .build()
    }

}