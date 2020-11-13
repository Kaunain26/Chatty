package com.knesarcreation.chatty.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.knesarcreation.chatty.R
import com.knesarcreation.chatty.activity.util.Validations
import android.util.Pair as UtilPair

class LoginActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var etEmailAddress: EditText
    lateinit var etPassword: EditText
    lateinit var mProgressBar: ProgressBar
    lateinit var btnLogin: Button
    lateinit var signUp: TextView

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val dashBoardActivity = Intent(this, DashboardActivity::class.java)
            startActivity(dashBoardActivity)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*initialize the FirebaseAuth instance.*/
        mAuth = FirebaseAuth.getInstance()

        /*initializing views*/
        init()

        /*sign in users */
        onClickLoginBtn()

        findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            finish()
        }

        signUp.setOnClickListener {
            val registerActivity = Intent(this, RegisterActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                UtilPair.create(findViewById(R.id.imgBack), "arrow_back"),
                UtilPair.create(findViewById(R.id.etEmailAddress), "et_email_address"),
                UtilPair.create(findViewById(R.id.etPassword), "et_password"),
                UtilPair.create(findViewById(R.id.btnLogin), "btn"),
            )
            startActivity(registerActivity, options.toBundle())
        }
    }

    private fun init() {
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        mProgressBar = findViewById(R.id.mProgressBar)
        btnLogin = findViewById(R.id.btnLogin)
        signUp = findViewById(R.id.signUp)
    }

    private fun onClickLoginBtn() {
        btnLogin.setOnClickListener {
            if (Validations.validateEmail(etEmailAddress.text.toString())) {
                etEmailAddress.error = null

                if (etPassword.text.toString().isNotBlank()) {
                    etPassword.error = null

                    /*if validation is successful*/
                    mProgressBar.visibility = View.VISIBLE
                    btnLogin.visibility = View.GONE
                    /*sending user credential to firebase*/
                    logIn(etEmailAddress.text.toString(), etPassword.text.toString())

                } else {
                    etPassword.error = "Invalid password"
                }
            } else {
                etEmailAddress.error = "Invalid email"
                Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    val dashboardActivity = Intent(this, DashboardActivity::class.java)
                    startActivity(dashboardActivity)
                    finish()

                    mProgressBar.visibility = View.GONE
                    btnLogin.visibility = View.GONE
                }
            }.addOnFailureListener {
                mProgressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                Toast.makeText(this, "No such user found", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onBackPressed() {
        finish()
    }
}