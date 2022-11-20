package com.bios.yacupsurpise.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bios.yacupsurpise.base.App
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

fun getVideoSourceFromUri(url: String): MediaSource {
    val factory = DefaultDataSourceFactory(App.app, "ActVideoFullscreen")
    val item = MediaItem.fromUri(url)
    val progressive_source = ProgressiveMediaSource.Factory(factory).createMediaSource(item)
    val looped_source = LoopingMediaSource(progressive_source)
    return looped_source
}

fun simpleImageLoad(
    imageView: ImageView,
    url: String,
    onLoaded: () -> Unit
) {
    Glide.with(App.app)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .addListener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onLoaded.invoke()
                    return false
                }
            }
        )
        .transition(DrawableTransitionOptions.withCrossFade(250))
        .into(imageView)
}
