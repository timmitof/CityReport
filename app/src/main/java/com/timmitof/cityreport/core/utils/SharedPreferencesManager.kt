package com.timmitof.cityreport.core.utils

import android.content.SharedPreferences

class SharedPreferencesManager(private val preference: SharedPreferences) {

    fun saveValue(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun getValue(key: String, defaultValue: String): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    var userID: String?
        get() = preference.getString("user_id", null)
        set(value) = preference.edit().putString("user_id", value).apply()

    var phoneNumber: String?
        get() = preference.getString("phone_number", null)
        set(value) = preference.edit().putString("phone_number", value).apply()

    fun clear() {
        preference.edit().clear().apply()
    }
}