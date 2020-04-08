package com.ruangkajian.android.watch

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ruangkajian.android.R

class WatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_watch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(WatchActivity.INTENT_EXTRA_URL)
        val mp = MediaPlayer.create(context, Uri.parse(url))
        mp.start()
    }

    companion object{
        fun createInstanceFromWatchActivity(arguments: Bundle?): WatchFragment{
            val watchFragment = WatchFragment()
            watchFragment.arguments = arguments
            return watchFragment
        }
    }

}