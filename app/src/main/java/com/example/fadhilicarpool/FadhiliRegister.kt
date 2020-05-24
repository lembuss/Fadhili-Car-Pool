package com.example.fadhilicarpool

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_fadhili_register.*

class FadhiliRegister : AppCompatActivity() {

    // Authentication database
    var myAuth = FirebaseAuth.getInstance()

    // Realtime database
    lateinit var fadhiliUsers : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_register)

        textSignIn.setOnClickListener {
            startActivity(Intent(this, FadhiliSignIn:: class.java))
        }

        btnRegister.setOnClickListener {
            View -> register()
        }
    }

    private fun register(){
//        progress dialog created
        var progress = ProgressDialog(this)
        progress.setTitle("Register in Progress")
        progress.setMessage("Getting you set up, please wait..")

        // extract and convert inputs from user
        var nameTxt = findViewById<View>(R.id.newName) as EditText
        var phoneTxt = findViewById<View>(R.id.newNumber) as EditText
        var emailTxt = findViewById<View>(R.id.newEmail) as EditText
        var passTxt = findViewById<View>(R.id.newPassword) as EditText
        var pconfirmTxt = findViewById<View>(R.id.newPConfirm) as EditText

        var name = nameTxt.text.toString()
        var phone = phoneTxt.text.toString()
        var email = emailTxt.text.toString()
        var password = passTxt.text.toString()
        var pconfirm = pconfirmTxt.text.toString()

        // create a database to hold the user information
        fadhiliUsers = FirebaseDatabase.getInstance().getReference("Users")

        // check that the inputs are filled
        if (name.isEmpty() or phone.isEmpty() or email.isEmpty() or password.isEmpty() or pconfirm.isEmpty()){
            // show error message
            dialogBox("Empty Fields", "Please fill out the inputs.")
        } else{
            if (password != pconfirm){
                // show error message
                dialogBox("Error", "Passwords don't match")
                clear()
            } else{
                progress.show()
                myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task->
                    progress.dismiss()
                    if (task.isSuccessful){
                        // add info to the database
                        val user = myAuth.currentUser
                        val uid = user!!.uid
                        fadhiliUsers.child(uid).child("Name").setValue(name)
                        fadhiliUsers.child(uid).child("Number").setValue(phone)
                        fadhiliUsers.child(uid).child("Email").setValue(email)
                        fadhiliUsers.child(uid).child("Password").setValue(password)

                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()

                        // take user back to sign in page
                        startActivity(Intent(this, FadhiliSignIn::class.java))
                    } else{
                        dialogBox("Error", "Unable to register. Please check credentials")
                        clear()
                    }
                })
            }
        }

    }

    private fun dialogBox(title: String, message: String){
        val mydialog = AlertDialog.Builder(this)
        mydialog.setCancelable(false)
        mydialog.setTitle(title)
        mydialog.setMessage(message)
        mydialog.setPositiveButton("Ok", DialogInterface.OnClickListener{ dialog, which ->
            dialog.dismiss()
        })
        mydialog.create().show()
    }

    private fun clear(){
        var nameTxt = findViewById<View>(R.id.newName) as EditText
        var phoneTxt = findViewById<View>(R.id.newNumber) as EditText
        var emailTxt = findViewById<View>(R.id.newEmail) as EditText
        var passTxt = findViewById<View>(R.id.newPassword) as EditText
        var pconfirmTxt = findViewById<View>(R.id.newPConfirm) as EditText

        nameTxt.text = null
        phoneTxt.text = null
        emailTxt.text = null
        passTxt.text = null
        pconfirmTxt.text = null
    }
}
