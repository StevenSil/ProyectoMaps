package com.example.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import java.io.StringReader
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.VolleyError

class MainActivity : AppCompatActivity() {

    lateinit var txtUsuario: EditText
    lateinit var txtContrasena: EditText
    lateinit var btnIngresar: Button
    lateinit var btnRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtUsuario = findViewById(R.id.txtUsuario)
        txtContrasena = findViewById(R.id.txtContraseña)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrar = findViewById(R.id.btnRegistrar)
    }

     fun submit(view: View) {
        var username = txtUsuario.text.toString()
        var password = txtContrasena.text.toString()
        val colaPeticiones = Volley.newRequestQueue(this)
        var URL_ROOT = "http://192.168.0.108/APIAyudaAnimal/v1/login.php"
        val stringRequest = object :StringRequest(
                Request.Method.POST, URL_ROOT,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                        if(!obj.getBoolean("error")){
                            vaciar_campos()
                            login_user = obj.getInt("id_user")
                            Ingresar()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>{
                val params = HashMap<String, String>()
                params.put("user", username)
                params.put("password", password)
                return params
            }
        }
        colaPeticiones.add(stringRequest)
    }

    fun prueba(view: View){
        val forma2= Intent( this@MainActivity,listMap::class.java)
        startActivity(forma2)
    }

    fun Registrar(view: View){
        val forma2= Intent( this@MainActivity,RegistrarUsuario::class.java)
        startActivity(forma2)
    }

    fun Ingresar(){
        val forma2= Intent( this@MainActivity,Servicios::class.java)
        startActivity(forma2)
    }

    fun vaciar_campos(){
        txtUsuario.setText("")
        txtContrasena.setText("")
    }

    companion object {
        var login_user = 0
    }



}


