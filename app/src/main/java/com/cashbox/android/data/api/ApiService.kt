package com.cashbox.android.data.api

import com.cashbox.android.data.model.LoginGoogleBody
import com.cashbox.android.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/google-login")
    suspend fun userLoginWithGoogle(
        @Body loginGoogleBody: LoginGoogleBody
    ): Response<LoginResponse>
}