package com.timmitof.cityreport.ui.fragments.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmitof.cityreport.core.services.Repository
import com.timmitof.cityreport.core.utils.Resource
import com.timmitof.cityreport.core.utils.isNetworkCheck
import com.timmitof.cityreport.models.Complaint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: Repository,
    @ApplicationContext private val context: Context
): ViewModel() {

    val reportComplaints = MutableLiveData<Resource<List<Complaint>>>()

    init {
        getAllComplaints()
    }

    fun getAllComplaints() {
        reportComplaints.postValue(Resource.loading(null))
        if (isNetworkCheck(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getAllComplaints().let {
                        if (it.isSuccessful) {
                            reportComplaints.postValue(Resource.success(it.body()))
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    fun sendLike() {

    }
}