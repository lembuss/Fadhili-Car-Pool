package com.example.fadhilicarpool

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.directions.route.Route
import com.directions.route.RouteException
import com.directions.route.RoutingListener
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_driver_map.*
import java.util.ArrayList

open class DriverMapActivity : AppCompatActivity(), OnMapReadyCallback, RoutingListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    var myAuth = FirebaseAuth.getInstance()

    lateinit var fadhiliUsers : DatabaseReference

    private lateinit var mMap: GoogleMap
    lateinit var fGoogleApiClient: GoogleApiClient
    lateinit var fLastLocation: Location
    lateinit var fRequestLocation: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // to leave the map area and back to dashboard
        goBack.setOnClickListener{
            startActivity(Intent(this, MainActivity:: class.java))
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        buildGoogleApiClient()
        mMap.isMyLocationEnabled = true
    }

    private fun buildGoogleApiClient(){
        fGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        fGoogleApiClient.connect()

    }

    override fun onConnected(p0: Bundle?) {
        // request new location every second
        fRequestLocation = LocationRequest()
        fRequestLocation.interval = 1000
        fRequestLocation.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY


        // necessary for the refreshing nature of the location
        LocationServices.FusedLocationApi.requestLocationUpdates(
            fGoogleApiClient,
            fRequestLocation,
            this
        )
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location?) {

        fLastLocation = location!!;

        var latLng: LatLng = LatLng(location.latitude, location.longitude)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11F))

        // save the location on user entry into app to the database
        fadhiliUsers = FirebaseDatabase.getInstance().getReference("Fads Available")
        var user = myAuth.currentUser
        var uid = user!!.uid

        var geoFire: GeoFire = GeoFire(fadhiliUsers)
        geoFire.setLocation(uid, GeoLocation(location.latitude, location.longitude))

    }

    override fun onStop() {
        super.onStop()

        // remove the location on user exit from app
        fadhiliUsers = FirebaseDatabase.getInstance().getReference("Fads Available")
        var user = myAuth.currentUser
        var uid = user!!.uid

        lateinit var geoFire: GeoFire
        geoFire.removeLocation(uid)
    }

//    create route
    private fun createRoute(){

}

    // routing implementation
    override fun onRoutingCancelled() {
        TODO("Not yet implemented")
    }

    override fun onRoutingStart() {
        TODO("Not yet implemented")
    }

    override fun onRoutingFailure(p0: RouteException?) {
        TODO("Not yet implemented")
    }

    override fun onRoutingSuccess(p0: ArrayList<Route>?, p1: Int) {
        TODO("Not yet implemented")
    }
}