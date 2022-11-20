package com.bios.yacupsurpise.di

import com.bios.yacupsurpise.networking.BaseNetworker
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm

class ActWelcomeInjector {

    private var baseNetworker: BaseNetworker? = null

    fun provideBaseNetworker(vm: ActWelcomeVm) =
        baseNetworker ?: BaseInjector.provideBaseNetworker(vm)
            .also { this.baseNetworker = it }
}