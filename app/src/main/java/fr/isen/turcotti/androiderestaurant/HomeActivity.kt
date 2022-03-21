package fr.isen.turcotti.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast as Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        button.setOnClickListener {
            Toast.makeText(this@MainActivity, "Voici les entrées", Toast.LENGTH_LONG).show()
        }
        button2.setOnClickListener {
            Toast.makeText(this@MainActivity, "Voici les plats", Toast.LENGTH_LONG).show()
        }
        button3.setOnClickListener {
            Toast.makeText(this@MainActivity, "Voici les desserts", Toast.LENGTH_LONG).show()
        }
    }


}