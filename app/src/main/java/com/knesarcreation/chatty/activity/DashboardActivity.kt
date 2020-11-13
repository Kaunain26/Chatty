package com.knesarcreation.chatty.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.knesarcreation.chatty.R
import com.knesarcreation.chatty.activity.fragment.ContactFragment
import com.knesarcreation.chatty.activity.fragment.MessageFragment
import com.knesarcreation.chatty.activity.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bottomNav = findViewById(R.id.bottomNavigation)

        openMessageFragment()

        onClickBottomNavItems()
    }

    private fun openMessageFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MessageFragment()).commit()
    }

    private fun onClickBottomNavItems() {
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_chat -> openMessageFragment()

                R.id.ic_contact -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ContactFragment()).commit()

                R.id.ic_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment()).commit()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.fragment_container)) {
            !is MessageFragment -> {
                openMessageFragment()
                bottomNav.menu.getItem(0).isChecked = true
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}
