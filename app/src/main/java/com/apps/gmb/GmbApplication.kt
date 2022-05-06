package com.apps.gmb

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class GmbApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun setAppContext(con: Context) {
            context = con
        }

        fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        setAppContext(this)
    }
}