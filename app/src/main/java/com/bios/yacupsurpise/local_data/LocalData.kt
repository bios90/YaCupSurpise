package com.bios.yacupsurpise.local_data

import com.bios.yacupsurpise.data.models.ModelUser
import java.io.Serializable

sealed class LocalData : Serializable {
    data class LdLoggedUser(val user: ModelUser) : LocalData()
}

inline fun <reified T : LocalData> getLocalDataKey(): String {
    val stateMap = LocalData::class.sealedSubclasses.asSequence()
        .map { it to it.simpleName }
        .toMap()
    return stateMap.get(T::class)!!
}

