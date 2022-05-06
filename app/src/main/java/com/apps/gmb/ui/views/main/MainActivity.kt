package com.apps.gmb.ui.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apps.gmb.R
import com.apps.gmb.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var views: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}