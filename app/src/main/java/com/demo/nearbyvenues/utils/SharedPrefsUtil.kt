package com.demo.nearbyvenues.utils

import android.content.Context
import android.content.SharedPreferences


object SharedPrefsUtil {

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        sharedPreferences?.let {
            return it.getBoolean(key, defaultValue)
        }
        return false
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key, value)?.apply()
    }
}