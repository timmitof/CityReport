package com.timmitof.cityreport.ui.fragments.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class RegisterViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {
    val reportRegister = MutableLiveData<Resource<LoginResponse>>()

    fun login(phoneNumber: String, password: String) {
        reportRegister.postValue(Resource.loading(null))
        if (isNetworkCheck(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.register(phoneNumber, password).let {
                        if (it.isSuccessful) {
                            sharedPreferencesManager.userID = "${it.body()?.userId}"
                            sharedPreferencesManager.phoneNumber = phoneNumber
                            reportRegister.postValue(Resource.success(it.body()))
                        }
                    }
                } catch (e: Exception) {
                    reportRegister.postValue(Resource.failure("Не удалось соединиться к серверу"))
                }
            }
        } else Toast.makeText(context, "Нет интернет соединения!", Toast.LENGTH_SHORT).show()
    }
}