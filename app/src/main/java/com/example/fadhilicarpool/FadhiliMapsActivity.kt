package com.example.fadhilicarpool

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_fadhili_maps.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class FadhiliMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    //    INITIATE THE NECESSARY VARIABLES
    private lateinit var map: GoogleMap
    lateinit var fadhiliUsers : DatabaseReference
    lateinit var passengerRequest : DatabaseReference
    lateinit var driverOffer : DatabaseReference

    var myAuth = FirebaseAuth.getInstance()

    //    PERMISSION REQUEST VARIABLE FOR CURRENT LOCATION
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // return back to dashboard
        goBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        showRides.setOnClickListener{
            showUsers()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

//        CALL THE POI METHOD
        setPoiClick(map)
//        CALL THE LOCATION ENABLER
        enableMyLocation()

//        ENABLE ZOOM CONTROLS
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
    }

    //    SET MARKERS AT POINTS OF INTEREST
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { pointOfInterest ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(pointOfInterest.latLng)
                    .title(pointOfInterest.name)
            )
            poiMarker.showInfoWindow()
        }

    }

    //    CHECKING IF PERMISSION IS GRANTED
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    //    ENABLE LOCATION TRACKING
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    //    REQUEST FOR PERMISSION ON THE APP
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun onMarkerClick(p0: Marker?) = false

    private fun showUsers() {

        passengerRequest = FirebaseDatabase.getInstance().getReference("Passenger Request")
        driverOffer = FirebaseDatabase.getInstance().getReference("Driver Offer")
        fadhiliUsers = FirebaseDatabase.getInstance().getReference("Users")


        val user = myAuth.currentUser
        val uid = user!!.uid

//        driver details

        var driverLat : Double? = null
        var driverLong : Double? = null
        // latitude
        driverOffer.child(uid).child("Latitude").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // import user latitude and append it to location
                val result = snapshot.value.toString()
                driverLat = result as Double
            }

        })

        // longitude
        driverOffer.child(uid).child("Longitude").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                // import longitude and append
                val result = snapshot.value.toString()
                driverLong = result as Double

            }

        })

        val driverPosition = driverLat?.let { driverLong?.let { it1 -> LatLng(it, it1) } }

        //name
        fadhiliUsers.child(uid).child("Name")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    // import user name and display it
                    val result = snapshot.value.toString()
                    map.addMarker(
                        driverPosition?.let {
                            MarkerOptions()
                                .position(it)
                                .title("$result - Driver")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                        }
                    )
                }

            })

//        passenger details

        var passengerLat : Double? = null
        var passengerLong : Double? = null

        // latitude
        passengerRequest.child(uid).child("Latitude").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // import user latitude and append it to location
                val result = snapshot.value
                passengerLat = result as Double
            }

        })

        // longitude
        passengerRequest.child(uid).child("Longitude").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                // import longitude and append
                val result = snapshot.value
                passengerLong = result as Double

            }
        })


        val passengerPosition = passengerLat?.let { passengerLong?.let { it1 -> LatLng(it, it1) } }

        // name
        fadhiliUsers.child(uid).child("Name")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    // import user name and display it
                    val result = snapshot.value.toString()
                    map.addMarker(
                        passengerPosition?.let {
                            MarkerOptions()
                                .position(it)
                                .title("$result - Passenger")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                        }
                    )
                }

            })

    }
}