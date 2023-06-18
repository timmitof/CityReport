package com.timmitof.cityreport.ui.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmitof.cityreport.core.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    val reportLogout = MutableLiveData<Unit>()

    fun logout() {
        sharedPreferencesManager.clear()
        reportLogout.postValue(Unit)
    }

}