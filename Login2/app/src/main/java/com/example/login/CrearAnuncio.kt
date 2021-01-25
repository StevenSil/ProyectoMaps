package com.example.login

import android.R.attr
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.util.*
import com.example.login.MainActivity.Companion.login_user

class CrearAnuncio : AppCompatActivity() {

    lateinit var txtTitulo: EditText
    lateinit var txtDescripcion: EditText
    lateinit var cmbTipo: Spinner
    lateinit var txtDireccion: EditText
    lateinit var btnRegistrar: Button
    lateinit var btnCancelar: Button
    lateinit var btnAbrir: Button
    lateinit var imagen_animal: ImageView
    lateinit var progress_bar: ProgressBar
    lateinit var lblEstado: TextView

    var latitude = ""
    var longitude = ""
    private var selectedImageUri: Uri? = null
    private var imageLoaded = false
    private var ImageStr = ""
    private val tiposAnuncios = arrayOf("Animal perdido", "Animal encontrado", "Animal callejero/ en adopción")

    fun load_components(){
        txtTitulo = findViewById(R.id.txtTitulo)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        cmbTipo = findViewById(R.id.cmbTipo)
        btnAbrir = findViewById(R.id.btnAbrir)
        imagen_animal = findViewById(R.id.foto_animal)
        progress_bar = findViewById(R.id.progress_upload)
        lblEstado = findViewById(R.id.Estado_ubicacion)
        txtDireccion = findViewById(R.id.txtDireccion)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnRegistrar = findViewById(R.id.btnGuardar)
    }

    fun limpiar_campos(){
        txtTitulo.setText("")
        txtDescripcion.setText("")
        cmbTipo.setSelection(tiposAnuncios.indexOf(1));
        imagen_animal.setBackgroundDrawable(null)
        progress_bar.setProgress(0);
        lblEstado.setText("Ubicación no cargada aún")
        txtDireccion.setText("")
        latitude = ""
        longitude = ""
        selectedImageUri = null
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_anuncio)
        load_components()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposAnuncios)
        cmbTipo.adapter = arrayAdapter
        getStorage()
        imagen_animal.setOnClickListener {
            openImageChooser()
        }
    }


    private fun getStorage() {
        if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
            )
        }
    }


    fun cancelar(view: View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cancelar registro")
        builder.setMessage("¿Seguro que quieres cancelar el registro?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            limpiar_campos()
            val forma= Intent(this@CrearAnuncio, Servicios::class.java)
            startActivity(forma)
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }

    fun open_map(view: View){
        val forma= Intent(this@CrearAnuncio, MapsActivity::class.java)
        startActivityForResult(forma, 2)

    }


    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2){
            longitude = data?.getStringExtra("longitud").toString()
            latitude = data?.getStringExtra("latitud").toString()
            lblEstado.setText("Ubicación cargada correctamente")
            return
        }

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data!!.data
                    imagen_animal.setImageURI(selectedImageUri)
                    val current = LocalDateTime.now()
                    var file = File(this.filesDir, 1.toString() + current.toString())
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                    val resize = resizeImage(bitmap)
                    ImageStr = BitMapToString(resize)
                }
            }
        }
    }


    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }

    fun resizeImage(bitmap: Bitmap):Bitmap {
        val resized = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false)
        return resized
    }

    private fun uploadImage() {
        val colaPeticiones = Volley.newRequestQueue(this)
        var URL_ROOT = "http://192.168.0.108/APIAyudaAnimal/v1/ordenes.php"
        val stringRequest = object : StringRequest(
                Request.Method.POST, URL_ROOT,
                com.android.volley.Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                        if (!obj.getBoolean("error")) {
                            enable_buttoms(true)
                            limpiar_campos()
                            val forma = Intent(this@CrearAnuncio, Servicios::class.java)
                            startActivity(forma)
                        } else {
                            enable_buttoms(true)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                com.android.volley.Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("Id_usuario", login_user.toString())
                params.put("Titulo", txtTitulo.text.toString())
                params.put("Descripcion", txtDescripcion.text.toString())
                params.put("Direccion", txtDireccion.text.toString())
                params.put("Tipo_anuncio", getItemCode(cmbTipo.getSelectedItem().toString()))
                params.put("Latitud", latitude)
                params.put("Foto", ImageStr)
                params.put("opcion", "insertar")
                params.put("Fecha", LocalDateTime.now().toString())
                params.put("Longitud", longitude)
                return params
            }
        }
        colaPeticiones.add(stringRequest)
    }

    fun getItemCode(name: String): String {
        return when (name) {
            "Animal perdido" -> {
                "1"
            }
            "Animal encontrado" -> {
                "2"
            }
            else -> {
                "3"
            }
        }
    }

    fun enable_buttoms(status: Boolean){
        btnAbrir.setEnabled(status)
        btnRegistrar.setEnabled(status)
        btnRegistrar.setEnabled(status)
    }

    fun guardar(view: View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Guardar anuncio")
        builder.setMessage("¿Seguro que quieres guardar?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            uploadImage()
            enable_buttoms(false)
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }


}