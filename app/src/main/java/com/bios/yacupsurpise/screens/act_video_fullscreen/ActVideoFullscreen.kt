package com.bios.yacupsurpise.screens.act_video_fullscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.getArgs
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.databinding.ActVideoFullscreenBinding
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm
import com.bios.yacupsurpise.ui.common.theme.getComposeRootView
import com.bios.yacupsurpise.ui.common.utils.ScreenWindowData
import com.bios.yacupsurpise.utils.getVideoSourceFromUri
import com.google.android.exoplayer2.ExoPlayer
import com.test.yacupsurprise.ui.common.theme.AppTheme
import java.io.Serializable

class ActVideoFullscreen : BaseActivity() {
    data class Args(val videoUrl: String) : Serializable

    private val args by lazy { requireNotNull(getArgs<Args>()) }
    private val bnd by lazy { ActVideoFullscreenBinding.inflate(layoutInflater, null, false) }

    override val screenWindowData: ScreenWindowData
        get() = ScreenWindowData.fullScreenTrans(
            isLightStatusBarIcons = true,
            isLightNavBarIcons = true
        )

    private val player by lazy { ExoPlayer.Builder(this).build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(bnd.root)
        val source = getVideoSourceFromUri(args.videoUrl)
        player.setMediaSource(source)
        player.prepare()
        player.playWhenReady = true
        bnd.playerView.player = player
        bnd.playerView.useController = false
        bnd.root
    }



    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
