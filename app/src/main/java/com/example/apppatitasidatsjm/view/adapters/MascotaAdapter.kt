package com.example.apppatitasidatsjm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppatitasidatsjm.databinding.ItemMascotaBinding
import com.example.apppatitasidatsjm.retrofit.response.MascotaResponse

class MascotaAdapter(private var listamascota: List<MascotaResponse>)
    : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMascotaBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaAdapter.ViewHolder {
        val binding = ItemMascotaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MascotaAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(listamascota[position]) {
                binding.tvNomMascota.text = nommascota
                binding.tvContactoMascota.text = contacto
                binding.tvUbicacionMascota.text = lugar
                Glide.with(itemView.context).load(urlimagen).into(binding.ivMascota)
            }
        }
    }

    override fun getItemCount() = listamascota.size
}