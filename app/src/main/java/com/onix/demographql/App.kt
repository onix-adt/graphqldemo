package com.onix.demographql

import android.app.Application
import com.onix.demographql.data.Repositoty


class App : Application() {

    companion object {
        lateinit var repository: Repositoty
    }

    override fun onCreate() {
        super.onCreate()
        repository = Repositoty()
    }
}