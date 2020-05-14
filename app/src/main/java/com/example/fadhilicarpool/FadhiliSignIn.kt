package com.example.fadhilicarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fadhili_sign_in.*

class FadhiliSignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_sign_in)

//        set click listeners for the buttons
        textSignUp.setOnClickListener {
            startActivity(Intent(this, FadhiliRegister::class.java))
        }

        btnSignIn.setOnClickListener {
//            startActivity(Intent(this, FadhiliDashboard::class.jave))
        }
    }
}
