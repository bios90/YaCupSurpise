package com.bios.yacupsurpise.base.view_models

import androidx.lifecycle.MutableLiveData

class StateResultLiveData<State, Effect> : MutableLiveData<StateResultEvent<State, Effect>>()
