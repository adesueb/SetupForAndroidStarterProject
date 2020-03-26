package com.ruangkajian.android

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

class MainFragment: Fragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}