package com.example.fadhilicarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_fadhili_dashboard.*

class FadhiliDashboard : AppCompatActivity() {
    //    connect to firebase authentication
    var myAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_dashboard)

//        create on click listeners for the items on the dashboard

        // to offer a ride
        cardDrive.setOnClickListener{
                startActivity(Intent(this, DriverMapActivity:: class.java))
                Toast.makeText(this, "Offer a ride clicked", Toast.LENGTH_LONG).show()
            }

        // to find a ride
        cardRide.setOnClickListener{
                startActivity(Intent(this, PassengerMapActivity:: class.java))
            }


    }

}

