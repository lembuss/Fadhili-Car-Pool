package com.example.fadhilicarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_fadhili_dashboard.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //    connect to firebase authentication
    var myAuth = FirebaseAuth.getInstance()

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = null

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, findViewById(R.id.main_toolbar), 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)


        cardDrive.setOnClickListener{
            startActivity(Intent(this, DriverMapActivity:: class.java))
            Toast.makeText(this, "Offer a ride clicked", Toast.LENGTH_LONG).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myHome -> {
                // return to the dashboard
                startActivity(Intent(this, MainActivity:: class.java))
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.myProfile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.myNotifications -> {
                Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.myMessages -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.myRides -> {
                Toast.makeText(this, "Recent rides clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.mySettings -> {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.signOut -> {
                myAuth.signOut()
                Toast.makeText(this, "You have been signed out", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, FadhiliLanding::class.java))
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }
}