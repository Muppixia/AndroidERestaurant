package fr.isen.turcotti.androiderestaurant.cart

import java.io.Serializable

data class ItemCart(var cartItem: Cart, var quantity:Int) : Serializable