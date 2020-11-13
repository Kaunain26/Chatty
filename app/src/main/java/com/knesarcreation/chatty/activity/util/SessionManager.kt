package com.knesarcreation.chatty.activity.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    object PrefsConst {
        var PRIVATE_MODE = 0
        const val PREFS_NAME = "userRegister"

    }
    var getSharedPreferences: SharedPreferences =
        context.getSharedPreferences(PrefsConst.PREFS_NAME, PrefsConst.PRIVATE_MODE)
}
