package com.example.login

import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class RegistrarUsuario : AppCompatActivity() {
    lateinit var txtNombre: EditText
    lateinit var txtApellido: EditText
    lateinit var txtContrasena: EditText
    lateinit var txtEdad: EditText
    lateinit var txtTelefono: EditText
    lateinit var txtDireccion: EditText
    lateinit var txtUsuario: EditText
    lateinit var btnRegistrar: Button
    lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtContrasena = findViewById(R.id.txtContraseña)
        txtEdad = findViewById(R.id.txtEdad)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtDireccion = findViewById(R.id.txtDireccion)
        txtUsuario = findViewById(R.id.txtUsuario)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnRegistrar = findViewById(R.id.btnGuardar)
    }

    fun guardar(view: View){
        saveDatabase()
    }

    fun saveDatabase(){
        val colaPeticiones = Volley.newRequestQueue(this)
        var URL_ROOT = "http://192.168.0.108/APIAyudaAnimal/v1/index.php"
        val stringRequest = object : StringRequest(
                Request.Method.POST, URL_ROOT,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                        if(!obj.getBoolean("error")){
                            volverPantalla()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>{
                val params = HashMap<String, String>()
                params.put("Nombre", txtNombre.text.toString())
                params.put("Apellido", txtApellido.text.toString())
                params.put("Direccion", txtDireccion.text.toString())
                params.put("Contrasena", txtContrasena.text.toString())
                params.put("Usuario", txtUsuario.text.toString())
                params.put("Edad", txtEdad.text.toString())
                params.put("Telefono", txtTelefono.text.toString())
                return params
            }
        }
        colaPeticiones.add(stringRequest)
    }

    fun volverPantalla(){
        val forma2= Intent( this@RegistrarUsuario,MainActivity::class.java)
        startActivity(forma2)
    }

    /*
    fun showDialogAlertSimple() {
        AlertDialog.Builder(this)
            .setTitle("Mensaje del sistema")
            .setMessage("Registro almacenado!!")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    val admin = UserSqliteOpenHelper(this, "Usuario", null,1)
                    val bd = admin.writableDatabase
                    val user = ContentValues()
                    val forma2 = Intent(this@RegistrarUsuario, MainActivity::class.java)
                    user.put("Nombre", txtNombre.text.toString())
                    user.put("Apellido", txtApellido.text.toString())
                    user.put("Direccion", txtDireccion.text.toString())
                    user.put("Contrasena", txtContrasena.text.toString())
                    user.put("Usuario", txtUsuario.text.toString())
                    user.put("Edad", txtEdad.text.toString())
                    user.put("Telefono", txtTelefono.text.toString())

                    if(bd.insert("Usuario", null, user)>-1){
                        Toast.makeText(this,"Pedido agregado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,"Pedido NO agregado", Toast.LENGTH_SHORT).show()
                    }
                    bd.close()
                    startActivity(forma2)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                })
            .show()
    }
    */

    fun regresar(view: View){
        Toast.makeText(applicationContext,"Cancelado...", Toast.LENGTH_SHORT).show()
        val forma2 = Intent(this@RegistrarUsuario, MainActivity::class.java)
        startActivity(forma2)
    }

}