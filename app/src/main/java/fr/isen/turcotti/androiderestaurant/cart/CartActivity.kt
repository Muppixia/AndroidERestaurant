package fr.isen.turcotti.androiderestaurant.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.turcotti.androiderestaurant.HomeActivity
import fr.isen.turcotti.androiderestaurant.R
import fr.isen.turcotti.androiderestaurant.databinding.ActivityCartBinding
import java.io.File

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var orderButton: Button
    private lateinit var cartList: CartList
    private lateinit var monRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = File(cacheDir.absolutePath + "cart.json")

        monRecyclerView = binding.cartRecyclerView
        monRecyclerView.layoutManager = LinearLayoutManager(this)

        cartList = if(file.exists()){
            Gson().fromJson(file.readText(), CartList::class.java)
        } else {
            CartList(arrayListOf())
        }

        orderButton = findViewById(R.id.orderButton)
        orderButton.setOnClickListener{
            order()
        }
    }

    private fun order() {
        cartList = CartList(arrayListOf())
        val strCart = Gson().toJson(cartList, CartList::class.java)
        File(cacheDir.absolutePath + "cart.json").writeText(strCart)

        val intent = Intent(this, HomeActivity::class.java)
        Toast.makeText(applicationContext, R.string.toast, Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

    /*fun removeItem(cartItem: ItemCart) {
        cartItem.remove()
    }*/

}