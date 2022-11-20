@file:Suppress("UNCHECKED_CAST")

package com.bios.yacupsurpise.base.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurprise
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurpriseVm
import com.bios.yacupsurpise.screens.act_main.ActMainVm
import com.bios.yacupsurpise.screens.act_main.subscreens.incoming.SubScreenIncoming
import com.bios.yacupsurpise.screens.act_main.subscreens.incoming.SubScreenIncomingVm
import com.bios.yacupsurpise.screens.act_main.subscreens.sended.SubScreenSended
import com.bios.yacupsurpise.screens.act_main.subscreens.sended.SubScreenSendedVm
import com.bios.yacupsurpise.screens.act_main.subscreens.users.SubScreenUsersVm
import com.bios.yacupsurpise.screens.act_reaction.ActReaction
import com.bios.yacupsurpise.screens.act_reaction.ActReactionVm
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm
import java.io.Serializable

class BaseViewModelsFactory<V : ViewModel, A : Serializable>(
    val viewModelClass: Class<V>,
    val args: A? = null,
) : ViewModelProvider.Factory {


    override fun <V : ViewModel> create(modelClass: Class<V>): V {
        return when (viewModelClass) {
            ActWelcomeVm::class.java -> ActWelcomeVm()
            ActMainVm::class.java -> ActMainVm()
            SubScreenUsersVm::class.java -> SubScreenUsersVm()
            SubScreenSendedVm::class.java -> SubScreenSendedVm()
            SubScreenIncomingVm::class.java -> SubScreenIncomingVm()
            ActCreateSurpriseVm::class.java -> ActCreateSurpriseVm(args = args as ActCreateSurprise.Args)
            ActReactionVm::class.java -> ActReactionVm(args = args as ActReaction.Args)
            else -> throw IllegalStateException("Trying to create unknown ViewModel")
        } as V
    }
}

inline fun <reified V : ViewModel> createViewModelFactory(): BaseViewModelsFactory<V, Serializable> =
    createViewModelFactory(args = null)

inline fun <reified V : ViewModel, A : Serializable> createViewModelFactory(args: A? = null): BaseViewModelsFactory<V, A> =
    BaseViewModelsFactory(
        viewModelClass = V::class.java,
        args = args
    )
