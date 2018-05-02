package com.onix.demographql.ui.view_model

import FeedQuery
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.res.AssetManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.google.gson.Gson
import com.onix.demographql.App
import com.onix.demographql.data.pojo.FeedEntry
import com.onix.demographql.data.pojo.ResponseData
import type.FeedType
import java.io.InputStream

class MainActivityVM : ViewModel() {

    private lateinit var githuntFeedCall: ApolloCall<FeedQuery.Data>
    lateinit var unFilteredEntries: ArrayList<FeedEntry>
    val filteredEntries = ArrayList<FeedEntry>()
    var liveData: MutableLiveData<ArrayList<FeedEntry>> = MutableLiveData()
    var errorData: MutableLiveData<Throwable> = MutableLiveData()
    var gson = Gson()
    private lateinit var inputStreamEmergency: InputStream
    private lateinit var assetManager: AssetManager

    fun createAndCallApi(assetManager: AssetManager) {
        this.assetManager = assetManager
        val feedQuery = fetchFeed()
        createCallBack(feedQuery)
        callApi()
    }

    private fun fetchFeed(): FeedQuery {
        return FeedQuery.builder()
                .type(FeedType.HOT)
                .build()
    }

    private fun createCallBack(feedQuery: FeedQuery) {
        githuntFeedCall = App.repository.apolloClient
                .query(feedQuery)
    }

    private fun callApi() {
        githuntFeedCall.enqueue(object : ApolloCall.Callback<FeedQuery.Data>() {
            override fun onResponse(response: Response<FeedQuery.Data>) {
                val json = gson.toJson(response)
                val myResponse = gson.fromJson<ResponseData>(json, ResponseData::class.java)
                if (myResponse.data.feedEntries[0].repository != null) {
                    unFilteredEntries = myResponse.data.feedEntries
                } else {
                    unFilteredEntries = workOnEmergencyData()
                }
                for (i in 0 until unFilteredEntries.size) {
                    if (unFilteredEntries[i].repository?.fragments != null) {
                        filteredEntries.add(unFilteredEntries[i])
                    }
                }
                liveData.postValue(filteredEntries)
            }

            override fun onFailure(e: ApolloException) {
                e.printStackTrace()
                try {
                    unFilteredEntries = workOnEmergencyData()
                    for (i in 0 until unFilteredEntries.size) {
                        if (unFilteredEntries[i].repository?.fragments != null) {
                            filteredEntries.add(unFilteredEntries[i])
                        }
                    }
                    liveData.postValue(filteredEntries)
                } catch (e: Exception) {
                    errorData.postValue(e)
                }
            }
        })
    }

    private fun workOnEmergencyData(): ArrayList<FeedEntry> {
        inputStreamEmergency = assetManager.open("response_copy.txt")
        val resultCurrencies: ResponseData? = gson.fromJson(inputStreamEmergency.bufferedReader(), ResponseData::class.java)
        inputStreamEmergency.close()
        return resultCurrencies?.data?.feedEntries!!
    }
}