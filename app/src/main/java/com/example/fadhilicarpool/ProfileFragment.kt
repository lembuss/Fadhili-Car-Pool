package com.example.fadhilicarpool

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment(){

    // connect to authentication and realtime database

    lateinit var fadhiliUsers : DatabaseReference
    var myAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.title = "My Profile"

        //        refer to all the user details
        val nameTxt = view.findViewById(R.id.userName) as TextView
        val emailTxt = view.findViewById(R.id.userEmail) as TextView
        val phoneTxt = view.findViewById(R.id.userPhone) as TextView
        val addressTxt = view.findViewById(R.id.userAddress) as TextView

//        target database with data
        fadhiliUsers = FirebaseDatabase.getInstance().getReference("Users")
        val user = myAuth.currentUser
        val uid = user!!.uid

        // add listeners to all user details
        // name
        fadhiliUsers.child(uid).child("Name").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // import user name and display it on landing page
                val result = snapshot.value.toString()
                nameTxt.text = result
            }

        })
        // email
        fadhiliUsers.child(uid).child("Email").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // import user name and display it on landing page
                val result = snapshot.value.toString()
                emailTxt.text = result
            }

        })

        // Number
        fadhiliUsers.child(uid).child("Number").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // import user name and display it on landing page
                val result = snapshot.value.toString()
                phoneTxt.text = result
            }
        })

        /*
        Add something here for the address
         */
    }


}