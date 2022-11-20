package com.bios.yacupsurpise.di

import com.bios.yacupsurpise.networking.BaseNetworker
import com.bios.yacupsurpise.screens.act_main.subscreens.users.SubScreenUsersVm
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm

class SubScreenUsersInjector {
    private var baseNetworker: BaseNetworker? = null

    fun provideBaseNetworker(vm: SubScreenUsersVm) =
        baseNetworker ?: BaseInjector.provideBaseNetworker(vm)
            .also { this.baseNetworker = it }
}