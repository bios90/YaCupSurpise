package com.bios.yacupsurpise.screens.act_main

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.getColorApp
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.data.enums.TypeTab
import com.bios.yacupsurpise.databinding.ActMainBinding
import com.bios.yacupsurpise.screens.act_main.subscreens.incoming.SubScreenIncoming
import com.bios.yacupsurpise.screens.act_main.subscreens.sended.SubScreenSended
import com.bios.yacupsurpise.screens.act_main.subscreens.users.SubScreenUsers
import com.bios.yacupsurpise.ui.common.theme.getComposeRootView
import com.bios.yacupsurpise.ui.common.utils.setDebounceClickListener
import com.dimfcompany.akcsl.base.adapters.AdapterVp

class ActMain : BaseActivity() {

    private val vm: ActMainVm by viewModels {
        createViewModelFactory<ActMainVm>()
    }

    private val subsScreenUsers by lazy { SubScreenUsers(this, getComposeRootView(this)) }
    private val subScreenSended by lazy { SubScreenSended(this, getComposeRootView(this)) }
    private val subScreenIncomming by lazy { SubScreenIncoming(this, getComposeRootView(this)) }

    private val bndActMain by lazy { ActMainBinding.inflate(layoutInflater, null, false) }
    private val adapterVp by lazy { AdapterVp() }
    private var lastSelectedState: ActMainVm.State? = null

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
        setListeners()
    }

    private fun initTabs() {
        bndActMain.vpMain.adapter = adapterVp
        val tabViews = listOf(
            subsScreenUsers.rootView,
            subScreenSended.rootView,
            subScreenIncomming.rootView,
        )
        adapterVp.setViews(tabViews)
    }

    private fun setListeners() {
        bndActMain.vpMain.addOnPageChangeListener(
            object : SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    val tab = requireNotNull(TypeTab.values().getOrNull(position))
                    vm.Listener().onTabScrolled(tab)
                }
            }
        )
        for (index in 0 until bndActMain.laBottomNav.lalBottomNavContainer.childCount) {
            val tab = requireNotNull(TypeTab.values().getOrNull(index))
            val view = bndActMain.laBottomNav.lalBottomNavContainer.getChildAt(index)
            view.setDebounceClickListener {
                vm.Listener().onTabClicked(tab)
            }
        }
    }

    private fun consumeState(state: ActMainVm.State) {
        if (state == lastSelectedState) {
            return
        }
        bindSelectedTab(state.selectedTab)
        lastSelectedState = state
    }

    private fun handleEffects(effects: Set<ActMainVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is ActMainVm.Effect.BaseEffectWrapper -> handleBaseEffects(eff.data)
            }
        }
    }

    private fun bindSelectedTab(selectedTab: TypeTab) {
        if (selectedTab == lastSelectedState?.selectedTab) {
            return
        }
        bndActMain.vpMain.currentItem = TypeTab.values().indexOf(selectedTab)
        val selectedTabIndex = TypeTab.values().indexOf(selectedTab)
        val colorOrange = getColorApp(R.color.orange)
        val colorBlack = getColorApp(R.color.black)
        for (index in 0 until bndActMain.laBottomNav.lalBottomNavContainer.childCount) {
            val tabView = bndActMain.laBottomNav.lalBottomNavContainer.getChildAt(index) as LinearLayout
            val icon = tabView.getChildAt(0) as TextView
            val title = tabView.getChildAt(1) as TextView
            if (selectedTabIndex == index) {
                icon.setTextColor(colorOrange)
                title.setTextColor(colorOrange)
            } else {
                icon.setTextColor(colorBlack)
                title.setTextColor(colorBlack)
            }
        }
    }

}
