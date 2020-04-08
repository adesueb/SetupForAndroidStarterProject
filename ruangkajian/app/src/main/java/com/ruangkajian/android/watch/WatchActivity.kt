package com.ruangkajian.android.watch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ruangkajian.android.R
import com.ruangkajian.android.di.DaggerFragmentActivity

class WatchActivity : DaggerFragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val watchFragment = WatchFragment.createInstanceFromWatchActivity(intent.extras)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, watchFragment).commit()
    }

    companion object {
        fun createIntent(
            context: Context,
            id: Long,
            title: String,
            cover: String,
            url: String
        ): Intent {
            return Intent(context, WatchActivity::class.java).apply {
                putExtra(INTENT_EXTRA_ID, id)
                putExtra(INTENT_EXTRA_TITLE, title)
                putExtra(INTENT_EXTRA_COVER, cover)
                putExtra(INTENT_EXTRA_URL, url)
            }
        }

        const val INTENT_EXTRA_ID = ".extra_id"
        const val INTENT_EXTRA_TITLE = ".extra_title"
        const val INTENT_EXTRA_COVER = ".extra_cover"
        const val INTENT_EXTRA_URL = ".extra_url"
    }
}