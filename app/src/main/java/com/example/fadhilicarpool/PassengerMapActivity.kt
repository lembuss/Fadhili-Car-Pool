package com.example.fadhilicarpool

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.directions.route.Route
import com.directions.route.RouteException
import com.directions.route.RoutingListener
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.LocationCallback
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.Builder
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_driver_map.goBack
import kotlinx.android.synthetic.main.activity_passenger_map.*
import java.util.*
import java.util.stream.DoubleStream.builder
import android.location.Location as Location

open class PassengerMapActivity : AppCompatActivity(), OnMapReadyCallback{


    // connect to authentication and realtime database
    lateinit var passengerRequest : DatabaseReference
    lateinit var fadhiliUsers : DatabaseReference

    var myAuth = FirebaseAuth.getInstance()

    private lateinit var map: GoogleMap

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var userLocation: Location


    //    PERMISSION REQUEST VARIABLE FOR CURRENT LOCATION
    private val REQUEST_LOCATION_PERMISSION = 1


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // to leave the map area and back to dashboard
        goBack.setOnClickListener{
            startActivity(Intent(this, MainActivity:: class.java))
            finish()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        findRide.setOnClickListener{
            getLocation()
            findRide.text = "Searching for a ride"
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

//        CALL THE ONLONGCLICK METHOD
        setMapLongClick(map)
//        CALL THE POI METHOD
        setPoiClick(map)
//        CALL THE LOCATION ENABLER
        enableMyLocation()

    }



    //    ALLOW USER TO SET MARKER WITH LONG PRESS
    //    SNIPPET ADDS THE INFO WINDOW
    private fun setMapLongClick(map:GoogleMap){
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat:%1$.5f, Long %2$.5f",
                latLng.latitude,
                latLng.longitude
            )

//            ADD AN INFOTITLE TO THE MARKER
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
            )
        }
    }

    //    SET MARKERS AT POINTS OF INTEREST
    private fun setPoiClick(map:GoogleMap){
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
    private fun isPermissionGranted() : Boolean{
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    //    ENABLE LOCATION TRACKING
    private fun enableMyLocation(){
        if (isPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ){
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
        } else{
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
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION){
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                enableMyLocation()
            }
        }
    }

    private fun getLocation() {
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000 // 5 seconds
        locationRequest.fastestInterval = 2000 // 2 seconds

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


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
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,
            Looper.myLooper())


    }

    private val locationCallback = object : com.google.android.gms.location.LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            userLocation = locationResult.lastLocation
//            updateUIWithLocation(userLocation)
            saveLocation(userLocation)
        }
    }

    private fun saveLocation(location: Location){

        // create child for storage of the location
        passengerRequest = FirebaseDatabase.getInstance().getReference("Passenger Request")

        //        target database with data
        val user = myAuth.currentUser
        val uid = user!!.uid

        var passengerLat = location.latitude.toString()
        var passengerLong = location.longitude.toString()

        passengerRequest.child(uid).child("Latitude").setValue(passengerLat)
        passengerRequest.child(uid).child("Longitude").setValue(passengerLong)

        val userPosition = LatLng(userLocation.latitude, userLocation.longitude)

        fadhiliUsers = FirebaseDatabase.getInstance().getReference("Users")

        fadhiliUsers.child(uid).child("Name").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                // import user name and display it
                val result = snapshot.value.toString()
                map.addMarker(
                    MarkerOptions()
                        .position(userPosition)
                        .title("$result - Passenger")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                )
            }

        })


    }

}


