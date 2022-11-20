package com.bios.yacupsurpise.di

import com.bios.yacupsurpise.networking.BaseNetworker
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurprise
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurpriseVm
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm
import com.bios.yacupsurpise.utils.MediaPickerManager
import com.bios.yacupsurpise.utils.PermissionsManager

class ActCreateSurpriseInjector {
    private var mediaPickerManager: MediaPickerManager? = null
    private var permissionsManager: PermissionsManager? = null
    private var baseNetworker: BaseNetworker? = null

    fun provideMediaPickerManager(act: ActCreateSurprise) =
        mediaPickerManager ?: MediaPickerManager(act)
            .also { this.mediaPickerManager = it }

    fun providePermissionsManager(act: ActCreateSurprise) = permissionsManager
        ?: PermissionsManager(act)
            .also { this.permissionsManager = it }

    fun provideBaseNetworker(vm: ActCreateSurpriseVm) =
        baseNetworker ?: BaseInjector.provideBaseNetworker(vm)
            .also { this.baseNetworker = it }
}
