package com.example.fadhilicarpool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    // connect to authentication and realtime database
    lateinit var fadhiliUsers : DatabaseReference
    var myAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.title = "My Settings"

        //        refer to all the user details
        val nameTxt = view.findViewById(R.id.userName) as TextView

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


    }

}
