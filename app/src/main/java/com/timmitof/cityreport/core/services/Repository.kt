package com.timmitof.cityreport.core.services

import com.timmitof.cityreport.models.Complaint
import com.timmitof.cityreport.models.ComplaintRequest
import com.timmitof.cityreport.models.LoginRequest
import com.timmitof.cityreport.models.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api
) {

    suspend fun login(phoneNumber: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(phoneNumber, password)
        return api.login(loginRequest)
    }

    suspend fun register(phoneNumber: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(phoneNumber, password)
        return api.register(loginRequest)
    }

    suspend fun getAllComplaints(): Response<List<Complaint>> {
        return api.getAllComplaints()
    }

    suspend fun sendComplaint(complaintRequest: ComplaintRequest): Response<Unit> {
        return api.sendComplaint(complaintRequest)
    }
}