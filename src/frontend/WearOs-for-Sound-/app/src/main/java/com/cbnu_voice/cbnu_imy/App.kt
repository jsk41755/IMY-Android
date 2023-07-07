package com.cbnu_voice.cbnu_imy

import android.app.Application
import androidx.room.Room

class App : Application() {
    lateinit var datastore: DataStoreModule
    lateinit var messageDatabase: AppDatabase

    companion object {
        private lateinit var instance: App

        fun getInstance(): App = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        datastore = DataStoreModule(this)

        messageDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "message-db"
        ).build()
    }

    fun getDataStore(): DataStoreModule = datastore
}
