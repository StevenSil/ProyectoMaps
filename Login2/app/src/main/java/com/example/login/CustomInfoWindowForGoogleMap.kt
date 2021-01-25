package com.example.login

import android.app.Activity
import android.content.Context
import android.media.Image
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject

class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_marker_layout, null)


    private fun rendowWindowText(marker: Marker, view: View){

        val lblTitulo = view.findViewById<TextView>(R.id.lblTitulo)
        val lblDescripcion = view.findViewById<TextView>(R.id.lblDescripcion)
        val lblFecha =  view.findViewById<TextView>(R.id.lblFecha)
        val lblDireccion =  view.findViewById<TextView>(R.id.lblDireccion)
        val lblTipo =  view.findViewById<TextView>(R.id.lblTipo)
        val imagen =  view.findViewById<ImageView>(R.id.foto_anuncio)

        lblTitulo.text = marker.title

        val anuncio = JSONObject(marker.snippet)
        val fotoURL = "http://192.168.0.108/APIAyudaAnimal/" + anuncio.getString("foto").replace("%","/")
        lblDescripcion.text = "Descripcion: " + anuncio.getString("descripcion")
        lblTitulo.text = "Titulo: " + anuncio.getString("titulo")
        lblFecha.text = "Fecha: " + anuncio.getString("fecha")
        lblDireccion.text = "Descripcion: " + anuncio.getString("direccion")
        lblTipo.text = "Tipo: " + type(anuncio.getString("tipo_anuncio"))
        Glide.with(mWindow).load(fotoURL)
            .signature(ObjectKey(System.currentTimeMillis()))
            .into(imagen)


    }

    fun type(type: String) : String{
        return when (type) {
            "1" -> {
                "Animal perdido"
            }
            "2" -> {
                "Animal encontrado"
            }
            else -> {
                "Animal callejero / En adopción"
            }
        }
    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}