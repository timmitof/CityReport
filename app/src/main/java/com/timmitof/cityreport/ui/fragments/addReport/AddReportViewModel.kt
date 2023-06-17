package com.timmitof.cityreport.ui.fragments.addReport

import android.content.ClipDescription
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.timmitof.cityreport.core.services.Repository
import com.timmitof.cityreport.core.utils.Constants.Companion.DATE_FORMAT
import com.timmitof.cityreport.core.utils.Resource
import com.timmitof.cityreport.core.utils.SharedPreferencesManager
import com.timmitof.cityreport.core.utils.isNetworkCheck
import com.timmitof.cityreport.models.ComplaintRequest
import com.timmitof.cityreport.models.Coordinate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddReportViewModel @Inject constructor (
    private val repository: Repository,
    @ApplicationContext private val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager
): ViewModel() {

    val reportSend = MutableLiveData<Resource<Unit>>()

    fun sendComplaint(coordinate: Coordinate, description: String?, name: String, image: String) {
        reportSend.postValue(Resource.loading(null))
        if (isNetworkCheck(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val model = ComplaintRequest(
                        coordinate = coordinate,
                        date = SimpleDateFormat(DATE_FORMAT).format(Date()),
                        description = description,
                        name = name,
                        userId = sharedPreferencesManager.userID?.toInt()!!,
                        image = image
                    )
                    repository.sendComplaint(model).let {
                        if (it.isSuccessful) {
                            reportSend.postValue(Resource.success(null))
                        }
                    }
                } catch (e: Exception) {}
            }
        } else Toast.makeText(context, "Нет интернет соединения!", Toast.LENGTH_SHORT).show()
    }
}