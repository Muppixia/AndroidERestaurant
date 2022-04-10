package fr.isen.turcotti.androiderestaurant.cart

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.File
import java.io.Serializable

class ItemCart(var cartItem: Cart, var quantity:Int) : Serializable

class CartDetail(val items: MutableList<ItemCart>): Serializable {
    private var itemsCount: Int = 0
        get() {
            return items.map {
                it.quantity
            }.reduceOrNull { acc, i -> acc + i } ?: 0
        }



    fun removeItem(cartItem: ItemCart) {
        items.remove(cartItem)
    }

    fun clear() {
        items.removeAll{true}
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
        jsonFile.writeText(toJson())
        updateCount(context)
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

    private fun updateCount(context: Context) {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemsCount)
        editor.apply()
    }

    companion object {
        fun getCart(context: Context): Cart? {
            val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
            return if(jsonFile.exists()) {
                val json = jsonFile.readText()
                GsonBuilder().create().fromJson(json, Cart::class.java)
            } else {
                return null
            }
        }


        const val CART_FILE = "cart.json"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}