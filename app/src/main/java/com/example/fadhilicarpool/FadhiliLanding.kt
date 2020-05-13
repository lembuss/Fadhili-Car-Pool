package com.example.fadhilicarpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class FadhiliLanding : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_landing)

//             hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}
