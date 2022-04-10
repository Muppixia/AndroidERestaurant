package fr.isen.turcotti.androiderestaurant.model

import java.io.Serializable

data class Item(val name_fr: String, val images: ArrayList<String>,val prices: ArrayList<Price>,val ingredients: ArrayList<Ingredient>) :Serializable
