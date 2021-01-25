package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.internal.zzav
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.RuntimeRemoteException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class listMap  : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {
    private lateinit var mMap: GoogleMap
    val ZOOM_LEVEL = 20f
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(-2.225897537930685, -79.92803783314244)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geofencingClient: GeofencingClient
    private val locationPermission = 1

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

    fun crear_puntos(){
        val colaPeticiones = Volley.newRequestQueue(this)
        val url = "http://192.168.0.108/APIAyudaAnimal/v1/ordenes.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONArray(response)
                    for (i in 0 until obj.length()) {
                        val anuncio = obj.getJSONObject(i)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(LatLng( anuncio.getString("latitud").toDouble(), anuncio.getString("longitud").toDouble()))
                                .title(anuncio.getString("titulo"))
                                .snippet("Descripcion: " + anuncio.getString("descripcion"))
                        )
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>{
                val params = HashMap<String, String>()
                params.put("opcion", "consultar")
                return params
            }
        }
        //envio a la cola mi String Request
        colaPeticiones.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_map)
        geofencingClient = LocationServices.getGeofencingClient(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        crear_puntos()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val india = LatLng(-2.225897537930685, -79.92803783314244)
        mMap.addMarker(MarkerOptions().position(india).title("Marker in Danger zone"))
        getLocationAccess()
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationClickListener(this);
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