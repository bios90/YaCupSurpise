package com.bios.yacupsurpise.screens.act_reaction

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.Consts
import com.bios.yacupsurpise.base.getArgs
import com.bios.yacupsurpise.base.safe
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.toVisibility
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.data.enums.TypeFile
import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.databinding.ActReactionBinding
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm
import com.bios.yacupsurpise.ui.common.subviews.BaseProgress
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.bios.yacupsurpise.utils.getVideoSourceFromUri
import com.bios.yacupsurpise.utils.simpleImageLoad
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import java.io.Serializable

class ActReaction : BaseActivity() {
    data class Args(
        val surprise: ModelSurprise
    ) : Serializable

    private val args by lazy { getArgs<Args>() }
    private val vm: ActReactionVm by viewModels {
        createViewModelFactory<ActReactionVm, Args>(args = args)
    }

    private val bndActReaction by lazy { ActReactionBinding.inflate(layoutInflater, null, false) }
    private var lastState: ActReactionVm.State? = null
    private val player by lazy { ExoPlayer.Builder(this).build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bndActReaction.root)
        subscribeState(
            act = this,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }

    override fun onResume() {
        super.onResume()
        if (lastState?.surprise?.hasVideo().safe()) {
            player.play()
        }
    }

    override fun onPause() {
        super.onPause()
        if (lastState?.surprise?.hasVideo().safe()) {
            player.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    override fun onBackPressed() {
        vm.Listener().onBackPressed()
    }

    fun provideSurfaceView() = bndActReaction.preview

    private fun consumeState(state: ActReactionVm.State) {
        if (state == lastState) {
            return
        }
        val attachment = state.surprise.attachment
        val type = state.surprise.fileType
        if (attachment != lastState?.surprise?.attachment) {
            attachment?.url?.let { mediaUrl ->
                when (type) {
                    TypeFile.VIDEO -> {
                        val source = getVideoSourceFromUri(mediaUrl)
                        player.setMediaSource(source)
                        player.prepare()
                        bndActReaction.playerView.player = player
                        bndActReaction.playerView.useController = false
                        player.playWhenReady = true
                        setPlayerListener()
                        bndActReaction.playerView.visibility = View.VISIBLE
                        bndActReaction.img.visibility = View.GONE
                    }
                    else -> {
                        simpleImageLoad(
                            bndActReaction.img, mediaUrl,
                            onLoaded = {
                                vm.Listener().onImageLoaded()
                            }
                        )
                        bndActReaction.playerView.visibility = View.GONE
                        bndActReaction.img.visibility = View.VISIBLE
                    }
                }
            }
            bndActReaction.lafMedia.visibility = (attachment != null).toVisibility()
            bndActReaction.tvText.text = state.surprise.text
        }

        if (state.showProgress != lastState?.showProgress && state.showProgress) {
            startProgress()
        }
        if(state.screenState == ScreenState.LOADING){
            bndActReaction.composeProgressPlaceholder.setContent {
                BaseProgress()
            }
            bndActReaction.composeProgressPlaceholder.visibility = View.VISIBLE
        }else{
            bndActReaction.composeProgressPlaceholder.visibility = View.GONE
        }
        lastState = state
    }

    private fun setPlayerListener() {
        player.addListener(
            object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    if (playbackState == Player.STATE_READY) {
                        vm.Listener().onVideoStarted()
                    }
                }
            }
        )
    }

    private fun startProgress() {
        val listener = object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) = Unit

            override fun onAnimationEnd(p0: Animator?) = Unit

            override fun onAnimationCancel(p0: Animator?) = Unit

            override fun onAnimationRepeat(p0: Animator?) = Unit
        }
        val animator = ObjectAnimator.ofInt(bndActReaction.progressbar, "progress", 100)
            .setDuration(Consts.RECORD_DURATION)
        animator.addListener(listener)
        animator.start()
    }


    private fun handleEffects(effects: Set<ActReactionVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is ActReactionVm.Effect.BaseEffectWrapper -> handleBaseEffects(eff.data)
            }
        }
    }
}
