package com.example.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


data class UploadResponse(
    val error: Boolean,
    val message: String
)

interface MyAPI {
    @Multipart
    @POST("ordenes.php")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("Id_usuario") id_user: RequestBody,
        @Part("Descripcion") descri: RequestBody,
        @Part("Titulo") tit: RequestBody,
        @Part("Direccion") dire: RequestBody,
        @Part("Tipo_anuncio") type: RequestBody,
        @Part("Longitud") lon: RequestBody,
        @Part("Latitud") lat: RequestBody,
        @Part("Fecha") fec: RequestBody,
        @Part("opcion") opci: RequestBody
    ): Call<UploadResponse>

    companion object {
        operator fun invoke(): MyAPI {
            return Retrofit.Builder()
                .baseUrl("http://192.168.0.108/APIAyudaAnimal/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyAPI::class.java)
        }
    }

}