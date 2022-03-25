package fr.isen.turcotti.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import fr.isen.turcotti.androiderestaurant.databinding.ActivityHomeBinding
import android.widget.Toast as Toast

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding //Etape 1 : déclaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater) //Etape 2
        val view = binding.root //setContentView(binding.root)
        setContentView(view) //setContentView(R.layout.activity_home) -- Etape 3
        //val text = findViewById<TextView>(R.id.homeStarters)

        binding.homeStarters.setOnClickListener{
            goToCategory(getString(R.string.home_starters))
        }

        binding.homeDishes.setOnClickListener{
            goToCategory(getString(R.string.home_dishes))
        }

        binding.homeDeserts.setOnClickListener{
            goToCategory(getString(R.string.home_deserts))
        }
    }

    private fun goToCategory(category:String) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

    private fun toastMsg(msg: String?) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayEntreesMsg(view: View) {
        toastMsg("Entrées")
    }

    fun displayPlatsMsg(view: View) {
        toastMsg("Plats")
    }

    fun displayDessertsMsg(view: View) {
        toastMsg("Desserts")
    }

    private val tag = "LogHomeActivity"

    override fun onStop() {
        super.onStop()
        Log.d(tag, "Sortie de la page d'acceuil")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Page d'acceuil détruite")
    }


}