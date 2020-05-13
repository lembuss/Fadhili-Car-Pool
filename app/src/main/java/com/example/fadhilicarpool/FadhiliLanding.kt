package com.example.fadhilicarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_fadhili_landing.*

class FadhiliLanding : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_landing)

//             hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

//        click listeners for the buttons

        btnSignIn.setOnClickListener {
            startActivity(Intent(this, FadhiliSignIn::class.java))
        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this,  FadhiliRegister:: class.java))
        }
    }
}
