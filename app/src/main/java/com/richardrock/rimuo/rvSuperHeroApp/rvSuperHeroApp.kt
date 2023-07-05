package com.richardrock.rimuo.rvSuperHeroApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.richardrock.rimuo.R
import com.richardrock.rimuo.databinding.ActivityPoemasBinding
import com.richardrock.rimuo.databinding.ActivityRvSuperHeroAppBinding
import com.richardrock.rimuo.rvSuperHeroApp.DetailSuperheroActivity.Companion.EXTRA_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class rvSuperHeroApp : AppCompatActivity() {

    private lateinit var binding: ActivityRvSuperHeroAppBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperheroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRvSuperHeroAppBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.searchHero.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        adapter = SuperheroAdapter(onItemSelected = ::navigateToDetail)
        binding.rvSuperHero.setHasFixedSize(true)
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.pregressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java).getSuperheroes(query)

            if (myResponse.isSuccessful) {

                Log.i("RICH", "Funciona")

                val response = myResponse.body()
                if (response != null) {
                    Log.i("RICH", response.toString())
                    runOnUiThread {
                        adapter.updateList(response.superHeros)
                        binding.pregressBar.isVisible = false
                    }
                }
            } else {
                Log.i("RICH", "No found")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperheroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}