package fr.isen.turcotti.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.turcotti.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.turcotti.androiderestaurant.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titreDetail.text = intent.getStringExtra(CategoryActivity.ITEM_KEY)
    }
}