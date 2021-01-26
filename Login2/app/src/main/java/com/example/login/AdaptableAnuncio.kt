package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray

data class Anuncio(val titulo: String, val descripcion: String, val fecha:String, val direccion: String, val tipo:String, val fotos:String)

class AdaptableAnuncio(
    private val datos: JSONArray,
    private val clickListener: (Anuncio) -> Unit):
    RecyclerView.Adapter<AdaptableAnuncio.UserViewHolder>() {

    class UserViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val lblTitulo = item.findViewById(R.id.lblTItulo) as TextView
        val lblDescripcion = item.findViewById(R.id.lblDescripcion) as TextView
        val lblFecha = item.findViewById(R.id.lblFecha) as TextView
        val lblDireccion = item.findViewById(R.id.lblDireccion) as TextView
        val lblTipo = item.findViewById(R.id.lblTipo_anuncio) as TextView
        val foto = item.findViewById(R.id.foto_anuncio) as ImageView

        fun bindUser(anuncio: Anuncio, holder: View) {
            lblDescripcion.text = anuncio.descripcion
            lblTitulo.text = anuncio.titulo
            lblFecha.text = anuncio.fecha
            lblDireccion.text = anuncio.direccion
            lblTipo.text = anuncio.tipo

            val charset = Charsets.UTF_8
            Glide.with(item).load("http://192.168.0.108/APIAyudaAnimal/" + anuncio.fotos).into(foto)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.anuncio_item, parent, false) as LinearLayout
        return UserViewHolder(item)
    }

    override fun getItemCount() = datos.length()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val anuncio = datos.getJSONObject(position)
        val conj_anuncio = Anuncio(anuncio.getString("titulo"), anuncio.getString("descripcion"), anuncio.getString("fecha"), anuncio.getString("direccion"), anuncio.getString("tipo_anunico"), anuncio.getString("foto"))
        holder.bindUser(conj_anuncio, holder.item)
        holder.item.setOnClickListener{clickListener(conj_anuncio)};

        //val user = datos[position]
        //holder.bindUser(user, holder.item)
        //holder.item.setOnClickListener {
            //val detail = Intent(  holder.item.context, activityCita::class.java)

            //holder.item.context.startActivity(detail)

        }
    }

