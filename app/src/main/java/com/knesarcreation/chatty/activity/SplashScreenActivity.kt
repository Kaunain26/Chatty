package com.knesarcreation.chatty.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.knesarcreation.chatty.R
import android.util.Pair as UtilPair

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val loginActivity = Intent(this, LoginActivity::class.java)

            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                UtilPair.create(findViewById(R.id.imgAppLogo), "appLogo"),
                UtilPair.create(findViewById(R.id.txtAppName), "LogoText")
            )
            startActivity(loginActivity, options.toBundle())
            finish()
        }, 1000)
    }
}