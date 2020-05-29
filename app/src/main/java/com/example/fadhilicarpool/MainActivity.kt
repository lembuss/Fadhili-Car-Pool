package com.example.fadhilicarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // fragments
    lateinit var profileFragment: ProfileFragment
    lateinit var dashboardFragment: DashboardFragment
    lateinit var notificationFragment: NotificationFragment
    lateinit var messagesFragment: MessagesFragment
    lateinit var ridesFragment: RidesFragment
    lateinit var settingsFragment: SettingsFragment

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
//        toolbar.title = null

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, findViewById(R.id.main_toolbar), 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        // default fragment is the dashboard fragment
        dashboardFragment = DashboardFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, dashboardFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myHome -> {
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
                dashboardFragment = DashboardFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, dashboardFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.myProfile -> {
                // open profile page
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                profileFragment = ProfileFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, profileFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.myNotifications -> {
                Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show()
                notificationFragment = NotificationFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, notificationFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.myMessages -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
                messagesFragment = MessagesFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, messagesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.myRides -> {
                Toast.makeText(this, "Recent rides clicked", Toast.LENGTH_SHORT).show()
                ridesFragment = RidesFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, ridesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.mySettings -> {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                settingsFragment = SettingsFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, settingsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.signOut -> {
                myAuth.signOut()
                Toast.makeText(this, "You have been signed out", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, FadhiliLanding::class.java))
                finish()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}