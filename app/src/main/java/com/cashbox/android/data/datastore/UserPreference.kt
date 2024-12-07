package com.cashbox.android.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {
    private val isUserLoginKey = booleanPreferencesKey("is_user_login")
    private val usernameKey = stringPreferencesKey("user_name")
    private val userEmailKey = stringPreferencesKey("user_email")

    val isUserLogin: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[isUserLoginKey] ?: false
    }
    val username: Flow<String> = dataStore.data.map { preferences ->
        preferences[usernameKey] ?: ""
    }
    val userEmail: Flow<String> = dataStore.data.map { preferences ->
        preferences[userEmailKey] ?: ""
    }

    suspend fun updateUserLoginStatus(status: Boolean) {
        dataStore.edit { preferences ->
            preferences[isUserLoginKey] = status
        }
    }

    suspend fun updateUsernameAndEmail(username: String, userEmail: String) {
        dataStore.edit { preferences ->
            preferences[usernameKey] = username
            preferences[userEmailKey] = userEmail
        }
    }
}