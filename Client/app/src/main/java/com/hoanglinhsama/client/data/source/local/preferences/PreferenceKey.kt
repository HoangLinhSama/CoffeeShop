package com.hoanglinhsama.client.data.source.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object PreferenceKey {
    val FIRST_TIME_ENTER_APP = booleanPreferencesKey("first_time_enter_app")
    val LOGGED_IN = booleanPreferencesKey("logged_in")
    val PHONE = stringPreferencesKey("phone")
    val CHECKED_SAVE_PHONE = booleanPreferencesKey("checked_save_phone")
}