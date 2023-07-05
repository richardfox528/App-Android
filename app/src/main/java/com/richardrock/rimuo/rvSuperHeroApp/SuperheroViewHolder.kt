package com.richardrock.rimuo.rvSuperHeroApp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.richardrock.rimuo.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemSuperheroBinding.bind(view)
    fun bind(SuperheroItemsResponse: SuperheroItemsResponse, onItemSelected: (String) -> Unit) {
        binding.superherName.text = SuperheroItemsResponse.superheroName

        Picasso.get().load(SuperheroItemsResponse.superheroImage.url).into(binding.ivSuperhero)
        binding.root.setOnClickListener { onItemSelected(SuperheroItemsResponse.superheroID) }
    }
}