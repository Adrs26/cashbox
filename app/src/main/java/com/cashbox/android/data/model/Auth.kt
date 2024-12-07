package com.cashbox.android.data.model

data class LoginGoogleBody(
    val idToken: String
)

data class LoginResponse(
    val token: String,
    val user: UserData
)

data class UserData(
    val uid: String,
    val email: String,
    val name: String
)

data class AuthError(
    val message: String
)