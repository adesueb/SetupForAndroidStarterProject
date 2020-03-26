package com.ruangkajian.android

import android.os.Bundle
import com.ruangkajian.android.di.DaggerFragmentActivity

class MainActivity : DaggerFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
