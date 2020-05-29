package com.example.fadhilicarpool

import android.Manifest
import android.Manifest.*
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.SyncStateContract.Helpers.insert
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkPermission
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class EditProfileFragment : Fragment() {

//   for capturing the image
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null

    //connect to database
    var myAuth =  FirebaseAuth.getInstance()
    lateinit var fadhiliUsers: DatabaseReference

    // connect to profilefragment
    lateinit var profileFragment: ProfileFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.title = "Edit Profile"

//        return back to profile
        backProfile.setOnClickListener {
            profileFragment = ProfileFragment()
            fragmentManager!!
                .beginTransaction()
                .replace(R.id.fragment_container, profileFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }

        // add image to profile
        editImage.setOnClickListener {
//                View -> addImage()
        }
//        initiate the save button
        userSave.setOnClickListener {
            View -> saveData()
        }

    }

    private fun saveData(){
        //        target database and user data
        fadhiliUsers = FirebaseDatabase.getInstance().getReference("Users")
        val user = myAuth.currentUser
        val uid = user!!.uid


//        import the edit text views
        val nameTxt = view!!.findViewById(R.id.editName) as EditText
        val emailTxt = view!!.findViewById(R.id.editEmail) as EditText
        val phoneTxt = view!!.findViewById(R.id.editPhone) as EditText
        val addressTxt = view!!.findViewById(R.id.editAddress) as EditText

//        replace new user inputs into the database
        val name = nameTxt.text.toString()
        val email = emailTxt.text.toString()
        val phone = phoneTxt.text.toString()
        val address = addressTxt.text.toString()

        if(name.isEmpty() or email.isEmpty() or phone.isEmpty() or address.isEmpty()){
            Toast.makeText(activity, "Please fill out all inputs", Toast.LENGTH_LONG).show()
        } else{
//            replace user data
            fadhiliUsers.child(uid).child("Name").setValue(name)
            fadhiliUsers.child(uid).child("Email").setValue(email)
            fadhiliUsers.child(uid).child("Number").setValue(phone)
            fadhiliUsers.child(uid).child("Address").setValue(address)

            Toast.makeText(activity, "User details updated successfully!", Toast.LENGTH_LONG).show()

//            return user back to profile fragment
            profileFragment = ProfileFragment()
            fragmentManager!!
                .beginTransaction()
                .replace(R.id.fragment_container, profileFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

    }

/*
    //TRY TO ADD A USER IMAGE TO THE PROFILE USING THE CAMERA
    private fun addImage(){
        //if system os is Marshmallow or Above, we need to request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
            }
            else{
                //permission already granted
                openCamera()
            }
        }
        else{
            //system os is < marshmallow
            openCamera()
        }

    }

    //Function to open camera
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri =contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


    //Requesting permision from the user
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            editImage.setImageURI(image_uri)
        }
    }

    */

}
