package com.ruangkajian.android.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ruangkajian.android.R
import com.ruangkajian.android.home.entity.Content
import com.ruangkajian.android.home.entity.Section
import com.ruangkajian.android.presenter.MainContract
import com.ruangkajian.android.utils.toPx
import com.ruangkajian.android.watch.WatchActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_headline.view.*
import javax.inject.Inject

class MainFragment : Fragment(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.requestSections()
        initList()
    }

    private fun initList() {
        headlineRecycler.layoutManager = GridLayoutManager(context, 2)
        headlineRecycler.adapter = HeadlineAdapter { content ->
            openWatchActivity(content)
        }
    }


    private fun initHeadlineSection(section: Section) {
        val contents = section.contents
        if (contents.isNotEmpty()) {
            val grandContent = contents[0]
            initGrandHeadlineContent(grandContent)
            val headlineAdapter = headlineRecycler.adapter as HeadlineAdapter
            headlineAdapter.contents = contents - grandContent
            headlineAdapter.notifyDataSetChanged()
        }
    }

    private fun initGrandHeadlineContent(content: Content) {
        context?.run {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(RoundedCorners(4.toPx()))
                .error(R.drawable.ic_launcher_foreground)

            Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(content.cover)
                .into(grandHeadlineImage)
        }
        grandHeadlineTitle.text = content.title
        grandHeadlineSubTitle.text = content.title

        grandHeadline.setOnClickListener {
            openWatchActivity(content)
        }

    }

    private fun openWatchActivity(content: Content) {
        context?.run {
            activity?.startActivity(
                WatchActivity.createIntent(
                    this,
                    content.id,
                    content.title,
                    content.cover,
                    content.url
                )
            )
        }
    }

    override fun showSections(sections: List<Section>) {
        sections.forEachIndexed { index, section ->
            when (index) {
                0 -> initHeadlineSection(section)
            }
        }
    }
}

class HeadlineAdapter(private val onClick: (Content) -> Unit) :
    RecyclerView.Adapter<HeadlineViewHolder>() {
    var contents: List<Content>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        return HeadlineViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_headline,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contents?.size ?: 0
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        contents?.get(position)?.run {
            holder.bind(this, onClick)
        }
    }

}

class HeadlineViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        content: Content,
        onClick: (Content) -> Unit
    ) {
        itemView.setOnClickListener { onClick.invoke(content) }

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .transform(RoundedCorners(4.toPx()))
            .error(R.drawable.ic_launcher_foreground)

        Glide.with(itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(content.cover)
            .into(itemView.headlineImage)


        itemView.headlineTitle.text = content.title
        itemView.headlineSubTitle.text = content.title
    }
}
