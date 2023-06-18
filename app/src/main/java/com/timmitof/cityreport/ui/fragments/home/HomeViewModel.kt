package com.timmitof.cityreport.ui.fragments.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmitof.cityreport.core.services.Repository
import com.timmitof.cityreport.core.utils.Importance
import com.timmitof.cityreport.core.utils.Resource
import com.timmitof.cityreport.core.utils.SharedPreferencesManager
import com.timmitof.cityreport.core.utils.isNetworkCheck
import com.timmitof.cityreport.models.Complaint
import com.timmitof.cityreport.models.ImportanceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: Repository,
    @ApplicationContext private val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager
): ViewModel() {

    val reportComplaints = MutableLiveData<Resource<List<Complaint>>>()
    val reportImportance = MutableLiveData<Resource<Unit>>()

    init {
        getAllComplaints()
    }

    fun getAllComplaints() {
        reportComplaints.postValue(Resource.loading(null))
        if (isNetworkCheck(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getAllComplaints(sharedPreferencesManager.userID?.toInt()).let {
                        if (it.isSuccessful) {
                            reportComplaints.postValue(Resource.success(it.body()))
                        }
                    }
                } catch (e: Exception) {
                    reportComplaints.postValue(Resource.failure(null))
                }
            }
        }
    }

    fun sendImportance(value: ImportanceRequest) {
        reportImportance.postValue(Resource.loading(null))
        if (isNetworkCheck(context)) {
            var model = value
            model = model.copy(userId = sharedPreferencesManager.userID?.toInt())
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.sendImportance(model).let {
                        if (it.isSuccessful) {
                            reportImportance.postValue(Resource.success(Unit))
                        }
                    }
                } catch (e: Exception) {
                    reportImportance.postValue(Resource.failure(null))
                }
            }
        }
    }
}