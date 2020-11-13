package com.knesarcreation.chatty.activity.util

import android.util.Patterns

object Validations {

    fun validateName(name: String): Boolean {
        return name.length >= 3
    }

    fun matchPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun validateEmail(email: String): Boolean {
        return (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }
}