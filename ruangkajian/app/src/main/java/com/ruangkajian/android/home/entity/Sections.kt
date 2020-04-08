package com.ruangkajian.android.home.entity

data class Sections(val sections: List<Section>)

data class Section(
    val id: Long,
    val title: String,
    val position: Int,
    val cover: String,
    val contents: List<Content>
)

data class Content(
    val id: Long,
    val type: String,
    val title: String,
    val cover: String,
    val url: String
)
