package com.cashbox.android.data.repository

import com.cashbox.android.data.api.ApiService
import com.cashbox.android.data.model.LoginGoogleBody
import com.cashbox.android.data.model.LoginResponse
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {
    suspend fun userLoginWithGoogle(loginGoogleBody: LoginGoogleBody): Response<LoginResponse> {
        return apiService.userLoginWithGoogle(loginGoogleBody)
    }
}