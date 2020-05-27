package com.example.fadhilicarpool

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
import java.util.*

class FadhiliMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

//    INITIATE THE NECESSARY VARIABLES
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location


//    PERMISSION REQUEST VARIABLE FOR CURRENT LOCATION
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadhili_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        FUSED LOCATION
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
        map = googleMap

        // values

        val latitude = -1.3671141
        val longitude = 36.7601409
        val zoomLevel = 15f

        val homeLatLng = LatLng(latitude, longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.addMarker(MarkerOptions().position(homeLatLng))

//        CALL THE ONLONGCLICK METHOD
        setMapLongClick(map)
//        CALL THE POI METHOD
        setPoiClick(map)
//        CALL THE LOCATION ENABLER
        enableMyLocation()

//        ENABLE ZOOM CONTROLS
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
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

//    ON MARKER CLICK CALLED WHEN A MARKER IS CLICKED OR TAPPED
    override fun onMarkerClick(p0: Marker?) = false
}
