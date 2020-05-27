package com.example.fadhilicarpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FadhiliProfile : AppCompatActivity() {

    // connect to authentication and realtime database

    lateinit var fadhiliUsers : DatabaseReference

    var myAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_profile)

//        refer to all the user details
        val nameTxt = findViewById<View>(R.id.userName) as TextView
        val emailTxt = findViewById<View>(R.id.userEmail) as TextView
        val phoneTxt = findViewById<View>(R.id.userPhone) as TextView
        val addressTxt = findViewById<View>(R.id.userAddress) as TextView


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

       /* // address
        fadhiliUsers.child(uid).child("Address").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // import user name and display it on landing page
                val result = snapshot.value.toString()
                addressTxt.text = result
            }

        })
        */
    }
}
