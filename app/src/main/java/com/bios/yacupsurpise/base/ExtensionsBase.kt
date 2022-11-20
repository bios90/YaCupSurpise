package com.bios.yacupsurpise.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.location.Location
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.provider.Settings
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

fun Toast(text: String) {
    android.widget.Toast.makeText(App.app, text, android.widget.Toast.LENGTH_LONG).show()
}

fun Boolean?.safe(): Boolean = this == true

fun <T> T.toSet() = setOf(this)

val gsonGlobal by lazy {
    GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()
}

fun makeOnBackgroundGlobal(action: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(
    context = Dispatchers.IO,
    block = action
)

fun Intent.makeClearAllPrevious() {
    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?,
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}


fun getColorApp(id: Int): Int {
    return ContextCompat.getColor(App.app, id)
}

fun getStringApp(id: Int): String {
    return App.app.getResources().getString(id)
}

fun getStringApp(id: Int, text: String): String {
    return App.app.getResources().getString(id, text)
}

fun String.Companion.random(count: Int = 20) = UUID.randomUUID().toString().take(count)

fun uriToPartBody(uri: String, field_name: String): MultipartBody.Part? {
    var file = File(uri)

    if (file == null) {
        return null
    }

    return fileToPartBody(file, field_name)
}

fun fileToPartBody(file: File?, field_name: String): MultipartBody.Part? {
    var bodyFile: MultipartBody.Part? = null

    if (file != null && file.exists()) {
        val b = file.asRequestBody()
        val requestBody = file.asRequestBody("multipart/form-baby_diary".toMediaTypeOrNull())
        bodyFile = MultipartBody.Part.createFormData(field_name, file.getName(), requestBody)
    }

    return bodyFile
}

fun Date.formatToString(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this).toString()
}

fun isNetworkAvailable(): Boolean {
    val connectivityManager =
        App.app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun dp2pxInt(dp: Float): Int {
    return dp2px(dp).toInt()
}

fun dp2px(dp: Float): Float {
    val r = Resources.getSystem()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics)
}

fun Boolean.toVisibility(): Int = if (this) View.VISIBLE else View.GONE

