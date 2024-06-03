package com.drg.shlapp.ReciclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.drg.shlapp.databinding.ItemSuperheroBinding
import com.drg.shlapp.retrofit.SuperheroItemResponse
import com.squareup.picasso.Picasso

class SuperHeroViewHolder (view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, onItemSelected: (String) -> Unit){
        binding.tvSuperheroName.text = superheroItemResponse.name
        Picasso.get().load(superheroItemResponse.superheroImage.url).into(binding.ivSuperhero)
        //Con root coge toda la vista (cada vez que alguien pulse en el elemento de la lista)
        binding.root.setOnClickListener{
            onItemSelected(superheroItemResponse.superheroId)
        }
    }
}