package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class VerAnuncios : AppCompatActivity() {

    private lateinit var recView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_anuncios)
        recView = findViewById(R.id.recView1)

        var listar_anuncios = listar_anuncios()

        val adaptable = AdaptableAnuncio(listar_anuncios) {
            Log.i("Mis anuncios", "Click en ${it.titulo} ")
        }
        recView.setHasFixedSize(true)

        //1. Linear Layout Manager
        recView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //2. Grid Layout Manager
        //recView.layoutManager = GridLayoutManager(this, 3)

        recView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )


        recView.itemAnimator = DefaultItemAnimator()

        recView.adapter = adaptable

    }

    fun listar_anuncios(): JSONArray {
        val colaPeticiones = Volley.newRequestQueue(this)
        val url = "http://192.168.0.108/APIAyudaAnimal/v1/ordenes.php"
        var obja = JSONArray("[{}]")
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    obja = JSONArray(response)
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
        colaPeticiones.add(stringRequest)
        return obja
    }

}