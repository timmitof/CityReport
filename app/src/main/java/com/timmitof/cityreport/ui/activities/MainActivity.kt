package com.timmitof.cityreport.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timmitof.cityreport.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}