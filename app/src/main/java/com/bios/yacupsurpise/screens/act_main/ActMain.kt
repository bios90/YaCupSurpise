package com.bios.yacupsurpise.screens.act_main

import android.os.Bundle
import androidx.activity.viewModels
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.databinding.ActMainBinding
import com.bios.yacupsurpise.screens.act_main.subscreens.users.SubScreenUsers
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeCompose
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm
import com.bios.yacupsurpise.ui.common.theme.getComposeRootView
import com.dimfcompany.akcsl.base.adapters.AdapterVp

class ActMain : BaseActivity() {

    private val vm: ActMainVm by viewModels {
        createViewModelFactory<ActMainVm>()
    }

    private val subscreenUsers by lazy { SubScreenUsers(this, getComposeRootView(this)) }
    private val bndActMain by lazy { ActMainBinding.inflate(layoutInflater, null, false) }
    private val adapterVp by lazy { AdapterVp() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bndActMain.root)
        initTabs()
        subscribeState(
            act = this,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }

    private fun initTabs() {
        bndActMain.vpMain.adapter = adapterVp
        val tabViews = listOf(
            subscreenUsers.rootView
        )
        adapterVp.setViews(tabViews)
    }

    private fun consumeState(state: ActMainVm.State) {

    }

    private fun handleEffects(effects: Set<ActMainVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is ActMainVm.Effect.BaseEffectWrapper -> handleBaseEffects(eff.data)
            }
        }
    }


    /*

    private val actMainViewModel: ActWelcomeVm by viewModels {
        createViewModelFactory<ActWelcomeVm>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        subscribeState(
            act = this,
            vm = actMainViewModel,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }
    * */
}