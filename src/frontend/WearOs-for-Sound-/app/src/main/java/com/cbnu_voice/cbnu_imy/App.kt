package com.cbnu_voice.cbnu_imy

import android.app.Application

class App : Application() {
    lateinit var datastore: DataStoreModule

    companion object {
        private lateinit var instance: App

        fun getInstance(): App = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        datastore = DataStoreModule(this)
    }

    fun getDataStore(): DataStoreModule = datastore
}
