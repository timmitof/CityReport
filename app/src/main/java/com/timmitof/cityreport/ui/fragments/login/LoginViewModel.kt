package com.timmitof.cityreport.ui.fragments.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmitof.cityreport.R
import com.timmitof.cityreport.core.services.Repository
import com.timmitof.cityreport.core.utils.Resource
import com.timmitof.cityreport.core.utils.SharedPreferencesManager
import com.timmitof.cityreport.core.utils.isNetworkCheck
import com.timmitof.cityreport.models.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {
    val reportLogin = MutableLiveData<Resource<LoginResponse>>()
    val reportUser = MutableLiveData<Unit>()

    init {
        checkUser()
    }

    private fun checkUser() {
        Log.e("UserID", "${sharedPreferencesManager.userID}")
        Log.e("Phone", "${sharedPreferencesManager.phoneNumber}")
        if (sharedPreferencesManager.userID != null && sharedPreferencesManager.phoneNumber != null) {
            reportUser.postValue(Unit)
        }
    }

    fun login(phoneNumber: String, password: String) {
        reportLogin.postValue(Resource.loading(null))
        if (isNetworkCheck(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.login(phoneNumber, password).let {
                        if (it.isSuccessful) {
                            sharedPreferencesManager.userID = "${it.body()?.userId}"
                            sharedPreferencesManager.phoneNumber = phoneNumber
                            reportLogin.postValue(Resource.success(it.body()))
                        }
                    }
                } catch (e: Exception) {
                    reportLogin.postValue(Resource.failure("Не удалось соединиться к серверу"))
                }
            }
        } else Toast.makeText(context, "Нет интернет соединения!", Toast.LENGTH_SHORT).show()
    }
}