package com.bios.yacupsurpise.base

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable


fun AppCompatActivity.addLifeCycleObserver(
    onCreate: (LifecycleOwner?) -> Unit = { },
    onStart: (LifecycleOwner?) -> Unit = { },
    onResume: (LifecycleOwner?) -> Unit = { },
    onPause: (LifecycleOwner?) -> Unit = { },
    onStop: (LifecycleOwner?) -> Unit = { },
    onDestroy: (LifecycleOwner?) -> Unit = { },
) = lifecycle.addObserver(
    object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) = onCreate.invoke(owner)
        override fun onStart(owner: LifecycleOwner) = onStart.invoke(owner)
        override fun onResume(owner: LifecycleOwner) = onResume.invoke(owner)
        override fun onPause(owner: LifecycleOwner) = onPause.invoke(owner)
        override fun onStop(owner: LifecycleOwner) = onStop.invoke(owner)
        override fun onDestroy(owner: LifecycleOwner) = onDestroy.invoke(owner)
    }
)

fun AppCompatActivity.makeActionDelayed(delayTime: Long, action: () -> Unit): Job {
    return lifecycleScope.launch {
        delay(delayTime)
        action.invoke()
    }
}

fun AppCompatActivity.runOnUiCompose(action: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launch(
        context = Dispatchers.Main,
        block = action
    )
}

fun Intent.putArgs(args: Serializable) {
    putExtra(Consts.ARGS, args)
}

fun <T : Serializable> Intent.getArgs(): T? {
    return getSerializableExtra(Consts.ARGS) as? T
}

fun <T : Serializable> AppCompatActivity.getArgs(): T? = intent?.getArgs()

fun Intent.addClearAllPreviousFlags() {
    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
}

fun <State, Effects> subscribeState(
    act: BaseActivity,
    vm: BaseViewModel<State, Effects>,
    stateConsumer: (State) -> Unit,
    effectsConsumer: (Set<Effects>) -> Unit,
) {
    vm.stateResult.observe(act, { resultEvent ->
        val stateResult = resultEvent.getIfNotHandled()
        stateConsumer.invoke(stateResult.first)
        effectsConsumer.invoke(stateResult.second)
    })
    act.addLifeCycleObserver(
        onCreate = { vm.onCreate(act) },
        onResume = { vm.onResume(act) },
        onStart = { vm.onStart(act) },
        onPause = { vm.onPause(act) },
        onStop = { vm.onStop(act) },
        onDestroy = { vm.onDestroy(act) },
    )
}

fun Dialog.setNavigationBarColor(color: Int) {
    val window = this.window ?: return
    val metrics = DisplayMetrics()
    window.windowManager.getDefaultDisplay().getMetrics(metrics)

    val dimDrawable = GradientDrawable()

    val navigationBarDrawable = GradientDrawable()
    navigationBarDrawable.shape = GradientDrawable.RECTANGLE
    navigationBarDrawable.setColor(color)

    val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)

    val windowBackground = LayerDrawable(layers)

    windowBackground.setLayerInsetTop(1, metrics.heightPixels)

    window.setBackgroundDrawable(windowBackground)
}
