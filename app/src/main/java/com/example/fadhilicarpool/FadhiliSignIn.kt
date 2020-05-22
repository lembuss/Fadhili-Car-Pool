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
import kotlinx.android.synthetic.main.activity_fadhili_sign_in.*

class FadhiliSignIn : AppCompatActivity() {

//    connect to firebase authentication
    var myAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_sign_in)

//        set click listeners for the buttons
        textSignUp.setOnClickListener {
            startActivity(Intent(this, FadhiliRegister::class.java))
        }

        btnSignIn.setOnClickListener {
            View -> signIn()
        }
    }


    private fun signIn(){
//        set up a progress dialog
        var progress = ProgressDialog(this)
        progress.setTitle("Sign In")
        progress.setMessage("Signing you in, please wait..")


//        extract the inputs from the activity and convert to string
        val mailTxt = findViewById<View>(R.id.inputMail) as EditText
        val passTxt = findViewById<View>(R.id.inputPassword) as EditText

        val email = mailTxt.text.toString()
        val password = passTxt.text.toString()


//        check if inputs are filled and execute the authentication
        if (email.isEmpty() or password.isEmpty()){
//            display error message
            dialogBox("Empty Fields", "Please fill out all inputs")
        } else {
            progress.show()         // show progress bar
//            begin authentication
            myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful){
                    progress.dismiss()    // hide progress bar
                    Toast.makeText(this, "Sign in successful", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity:: class.java))
                } else{
//                    display error message
                    dialogBox("Sign In Error","Wrong credentials, please try again")
//                    clear the fields
                    mailTxt.text = null
                    passTxt.text = null
                }
            })
        }
    }


    private fun dialogBox(title: String, message: String){
//        create a dialog box to display messages

        val mydialog = AlertDialog.Builder(this)
        mydialog.setTitle(title)
        mydialog.setMessage(message)
        mydialog.setCancelable(false)
        mydialog.setPositiveButton("Ok", DialogInterface.OnClickListener{ dialog, which ->
            dialog.dismiss()
        })
        mydialog.create().show()
    }

}
