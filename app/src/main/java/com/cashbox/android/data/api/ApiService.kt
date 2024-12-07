package com.cashbox.android.data.api

import com.cashbox.android.data.model.LoginBody
import com.cashbox.android.data.model.LoginGoogleBody
import com.cashbox.android.data.model.LoginResponse
import com.cashbox.android.data.model.RegisterBody
import com.cashbox.android.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/google-login")
    suspend fun userLoginWithGoogle(@Body loginGoogleBody: LoginGoogleBody): Response<LoginResponse>

    @POST("api/auth/login")
    suspend fun userLogin(@Body loginBody: LoginBody): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun userRegister(@Body registerBody: RegisterBody): Response<RegisterResponse>
}