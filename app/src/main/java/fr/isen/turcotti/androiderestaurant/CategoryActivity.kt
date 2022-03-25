package fr.isen.turcotti.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.turcotti.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.turcotti.androiderestaurant.model.DataResult
import org.json.JSONObject

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var items: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        items = arrayListOf()

        val nomCategorie = intent.getStringExtra("category")
        binding.categoryTitle.text = nomCategorie

        //val entreesList = resources.getStringArray(R.array.entreesList).toList()
        val recyclerView: RecyclerView = binding.categoryList
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = CategoryAdapter(arrayListOf()) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ITEM_KEY, it)
            startActivity(intent)
        }
        getDataFromApi(intent.getStringExtra("categorie") ?: "")
        Log.d("LogApi",items.toString())
    }

    private fun getDataFromApi(category: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        jsonObject.toString()
        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonObject,
            { response ->
                val strResp = response.toString()
                val dataResult = Gson().fromJson(strResp, DataResult::class.java)



                val items = dataResult.data.firstOrNull { it.name_fr == category }?.items ?: arrayListOf()
                binding.categoryList.adapter = CategoryAdapter(items) {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(ITEM_KEY, it)
                    startActivity(intent)
                }

            }, {
                Log.e(tag, "Log Volley error: $it")
            })

        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    companion object {
        val ITEM_KEY = "item"
    }

    private val tag = "LogCategoryActivity"

    override fun onStop() {
        super.onStop()
        Log.d(tag, "Sortie de la page des catégories")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Page des catégories détruite")
    }
}