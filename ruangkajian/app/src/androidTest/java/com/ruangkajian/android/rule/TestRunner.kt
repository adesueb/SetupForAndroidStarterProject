package com.ruangkajian.android.rule

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.ruangkajian.android.RuangKajianApplication

class TestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application = super.newApplication(cl, RuangKajianApplication::class.java.canonicalName, context)
}
