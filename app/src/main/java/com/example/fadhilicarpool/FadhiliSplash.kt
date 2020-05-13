package com.example.fadhilicarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class FadhiliSplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_splash)

//        hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


//        load animation
        var logoanim = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        var txtanim = AnimationUtils.loadAnimation(this, R.anim.txt_animation)

        var logo = findViewById<View>(R.id.imageLogo)
        var txt = findViewById<View>(R.id.textLogo)

        logo.setAnimation(logoanim)
        txt.setAnimation(txtanim)

//        send the intent to the Dashboard
        Handler().postDelayed({
            startActivity(Intent(this, FadhiliLanding::class.java))
            finish()
        }, 4000)
    }



}
