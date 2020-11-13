package com.knesarcreation.chatty.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.knesarcreation.chatty.R
import com.knesarcreation.chatty.activity.model.User
import com.knesarcreation.chatty.activity.util.SessionManager
import com.knesarcreation.chatty.activity.util.Validations

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var etUsername: EditText
    lateinit var etEmailAddress: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var mProgressbar: ProgressBar
    lateinit var btnSignUp: Button
    lateinit var login: TextView
    lateinit var mRef: DatabaseReference
    lateinit var sessionManager: SessionManager
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        /*initializing views*/
        init()

        /*Register new user*/
        onClickBtnRegister()

        /*for saving userdata in shared prefs*/
        sessionManager = SessionManager(this)
        sharedPreferences = sessionManager.getSharedPreferences

        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().getReference("Users")

        login.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun onClickBtnRegister() {
        btnSignUp.setOnClickListener {
            if (Validations.validateName(etUsername.text.toString())) {
                etUsername.error = null
                if (Validations.validateEmail(etEmailAddress.text.toString())) {

                    etEmailAddress.error = null

                    if (etPassword.text.toString().isNotBlank()) {
                        etPassword.error = null
                        if (Validations.matchPassword(
                                etPassword.text.toString(),
                                etConfirmPassword.text.toString()
                            )
                        ) {
                            /*validation is successful*/
                            mProgressbar.visibility = View.VISIBLE
                            btnSignUp.visibility = View.GONE
                            /*sending user credential to firebase*/
                            signUpNewUser(
                                etEmailAddress.text.toString(),
                                etPassword.text.toString()
                            )
                        } else {
                            etConfirmPassword.error = "Password didn't match"
                            Toast.makeText(this, "Password didn't match", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        etPassword.error = "Invalid password"
                        Toast.makeText(this, "Password field shouldn't empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    etEmailAddress.error = "Invalid email"
                }
            } else {
                etUsername.error = "username should be min. 3 characters "
            }
        }
    }

    private fun init() {
        etUsername = findViewById(R.id.etUserName)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        mProgressbar = findViewById(R.id.mProgressBar)
        btnSignUp = findViewById(R.id.btnRegister)
        login = findViewById(R.id.login)
    }

    private fun signUpNewUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    addUserDataToFirebase()
                    /*after a successful register going to dashboard activity*/
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "unexpected error occurred", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                mProgressbar.visibility = View.GONE
                btnSignUp.visibility = View.VISIBLE
            }
    }

    private fun addUserDataToFirebase() {
        val userData =
            User(mAuth.uid.toString(), etUsername.text.toString(), etEmailAddress.text.toString())
        mRef.child(mAuth.uid.toString()).setValue(userData)
            .addOnSuccessListener {

                /*if data is added successFully*/
                sharedPreferences.edit().putString("uid", mAuth.uid.toString()).apply()
                sharedPreferences.edit().putString("username", etUsername.text.toString()).apply()
                sharedPreferences.edit().putString("emailId", etEmailAddress.text.toString())
                    .apply()

            }.addOnFailureListener {
                Toast.makeText(this, "Unexpected error occurred", Toast.LENGTH_SHORT).show()
            }
    }
}