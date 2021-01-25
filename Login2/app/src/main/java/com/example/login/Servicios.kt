package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Servicios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
    }

    fun nuevo_anuncio(view: View){
        val forma= Intent( this@Servicios,CrearAnuncio::class.java)
        startActivity(forma)
    }

    fun ver_anuncios(view: View){
        val forma= Intent( this@Servicios,VerAnuncios::class.java)
        startActivity(forma)
    }

    fun mis_anuncios(view: View){
        val forma= Intent( this@Servicios,CrearAnuncio::class.java)
        startActivity(forma)
    }

    fun salir(view: View){
        val forma= Intent( this@Servicios,MainActivity::class.java)
        startActivity(forma)
    }
}