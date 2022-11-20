package com.bios.yacupsurpise.local_data

import android.content.Context
import com.bios.yacupsurpise.base.App
import com.bios.yacupsurpise.base.Consts
import com.bios.yacupsurpise.base.gsonGlobal
import com.bios.yacupsurpise.networking.toObjOrNullGson
import java.io.Serializable

object SharedPrefsManager {

    inline fun <reified T : LocalData> saveLocalData(
        ld: T,
        key: String = getLocalDataKey<T>(),
    ) {
        val editor =
            App.app.getSharedPreferences(Consts.SHARED_PREFS_KEY, Context.MODE_PRIVATE).edit()
        with(editor) {
            val json = gsonGlobal.toJson(ld)
            putString(key, json)
            commit()
        }
    }

    inline fun <reified T : LocalData> getLocalData(key: String = getLocalDataKey<T>()): T? =
        getData(key = key)

    inline fun <reified T : Serializable> getData(key: String): T? {
        val prefs = App.app.getSharedPreferences(Consts.SHARED_PREFS_KEY,
            Context.MODE_PRIVATE)
        val dataStr = prefs.getString(key, null)
        return dataStr.toObjOrNullGson(T::class.java)
    }

    fun getLoggedUser() = getLocalData<LocalData.LdLoggedUser>()?.user
}