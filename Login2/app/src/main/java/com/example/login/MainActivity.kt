package com.example.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    lateinit var txtUsuario: EditText
    lateinit var txtContraseña: EditText
    lateinit var btnIngresar: Button
    lateinit var btnRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtUsuario = findViewById(R.id.txtUsuario)
        txtContraseña = findViewById(R.id.txtContraseña)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val status = conexion.activeNetworkInfo
        btnIngresar.setOnClickListener(){
            if(status!=null && status.isConnected){
                Ingresar()
            }else{
                Toast.makeText(this, "Sin conexion de red", Toast.LENGTH_LONG).show()
            }
        }

        btnRegistrar.setOnClickListener(){
            if(status!=null && status.isConnected){
                Registrar()
            }else{
                Toast.makeText(this, "Sin conexion de red", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun Registrar(){
        val forma2= Intent( this@MainActivity,RegistrarUsuario::class.java)
        startActivity(forma2)
    }

    fun Ingresar(){
        val forma2= Intent( this@MainActivity,MapsActivity::class.java)
        startActivity(forma2)
    }

}


