package com.example.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.internal.zzav
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.RuntimeRemoteException
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    private lateinit var mMap: GoogleMap
    val ZOOM_LEVEL = 20f
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(-2.225897537930685, -79.92803783314244)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geofencingClient: GeofencingClient
    private val locationPermission = 1
    private var markerCount =  true
    private var coordinate = LatLng(0.0, 0.0)


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermission
            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                    MarkerOptions()
                            .position(poi.latLng)
                            .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            if(!markerCount){
                map.clear()
            }
            val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    latLng.latitude,
                    latLng.longitude
            )
            map.addMarker(MarkerOptions()
                    .position(latLng)
                    .title("Ubicación del marcador")
                    .snippet(snippet))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,ZOOM_LEVEL))
            markerCount = false
            coordinate = latLng

        }
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == locationPermission) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                mMap.isMyLocationEnabled = true
            }
            else {
                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        geofencingClient = LocationServices.getGeofencingClient(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val india = LatLng(-2.225897537930685, -79.92803783314244)
        mMap.addMarker(MarkerOptions().position(india).title("Marker in Danger zone"))
        getLocationAccess()
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationClickListener(this);
        setMapLongClick(mMap)
        getDeviceLocation()
        setPoiClick(mMap)
    }


    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG).show()
    }




    private fun getDeviceLocation() {
        try {
            if (locationPermission == 1) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            mMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), ZOOM_LEVEL.toFloat()
                                )
                            )
                        }
                    } else {
                        mMap?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, ZOOM_LEVEL.toFloat())
                        )
                        mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


}