package com.example.fadhilicarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_fadhili_splash.*

class FadhiliSplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_splash)

//        hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


//        load animations
        var logoanim = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        var txtanim = AnimationUtils.loadAnimation(this, R.anim.txt_animation)
        var fadeanim = AnimationUtils.loadAnimation(this, R.anim.fade_in)

//        get the elements to be animated
        var logo = findViewById<View>(R.id.imageLogo)
        var txt = findViewById<View>(R.id.textLogo)
        var boundary = findViewById<View>(R.id.imageDivider)
        var slogan = findViewById<View>(R.id.textSlogan)

//        set animations to the elements
        logo.animation = logoanim
        txt.animation = txtanim
        boundary.animation = fadeanim
        slogan.animation = fadeanim


//        send the intent to the Dashboard
        Handler().postDelayed({
            startActivity(Intent(this, FadhiliLanding::class.java))
            finish()
        }, 4000)
    }



}
