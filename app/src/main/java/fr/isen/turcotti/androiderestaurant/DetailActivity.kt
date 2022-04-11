package fr.isen.turcotti.androiderestaurant

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import fr.isen.turcotti.androiderestaurant.DetailActivity
import fr.isen.turcotti.androiderestaurant.cart.Cart
import fr.isen.turcotti.androiderestaurant.cart.CartActivity
import fr.isen.turcotti.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.turcotti.androiderestaurant.model.Item
import fr.isen.turcotti.androiderestaurant.cart.CartDetail.Companion.getCart
import java.io.File

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var nbProduct: TextView
    lateinit var plusButton: Button
    lateinit var minusButton: Button
    var selected : Float = 0F
    lateinit var totalButton: Button
    private lateinit var cartButton: Button


    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(CategoryActivity.ITEM_KEY) as Item
        binding.titleDetail.text = item.name_fr

        val carouselAdapter = CarouselAdapter(this, item.images)
        binding.detailSlider.adapter = carouselAdapter

        val ingredients = item.ingredients.joinToString { it.name_fr }
        binding.ingredientView.text = ingredients


        plusButton = findViewById(R.id.plusButton)
        minusButton = findViewById(R.id.minusButton)
        totalButton = findViewById(R.id.totalButton)
        cartButton = findViewById(R.id.cartButton)
        nbProduct = findViewById(R.id.nbProduct)
        nbProduct.text = selected.toString()
        doTotal(item, selected)

        plusButton.setOnClickListener {
            if (selected >= 0) {
                selected++
                nbProduct.text = selected.toString()
                doTotal(item, selected)
            }
        }
        minusButton.setOnClickListener {
            if (selected > 0) {
                selected--
                nbProduct.text = selected.toString()
                doTotal(item, selected)
            }
        }

        cartButton.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            })

        /*totalButton.setOnClickListener(
            addToCart()
        )*/


    }


    private fun doTotal(item: Item, selected: Float) {
        val totalPrice: String = item.prices[0].price
        val total: Float = totalPrice.toFloat() * selected
        val totalString: String = "Total : " + total.toString() + "€"


        binding.totalButton.text = totalString
    }

    override fun onRestart() {
        super.onRestart()
    }

    /*private fun addToCart(): View.OnClickListener? {
        currentDish?.let { dish ->
            val cart = Cart.getCart(this)
            cart.addItem(dish, count.toInt())
            cart.save(this)
            Toast.makeText(this, R.string.added, Toast.LENGTH_SHORT).show()
            invalidateOptionsMenu()
        }

     */

}




