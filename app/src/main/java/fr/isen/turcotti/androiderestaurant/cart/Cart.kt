package fr.isen.turcotti.androiderestaurant.cart

import fr.isen.turcotti.androiderestaurant.model.Price
import java.io.Serializable

data class Cart(val id:String,
                val name_fr: String,
                val images: ArrayList<String>,
                val prices: ArrayList<Price>) : Serializable
