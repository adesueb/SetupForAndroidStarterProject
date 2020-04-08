package com.ruangkajian.android.home

import android.os.Bundle
import com.ruangkajian.android.R
import com.ruangkajian.android.di.DaggerFragmentActivity

class MainActivity : DaggerFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment,
            MainFragment()
        ).commit()
    }
}
