package com.richardrock.rimuo.UserLocation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.richardrock.rimuo.R
import com.richardrock.rimuo.databinding.ActivityGetUserLocationBinding
import kotlinx.coroutines.launch

class GetUserLocationActivity : AppCompatActivity() {
    private val location: LocationService = LocationService()
    private lateinit var binding: ActivityGetUserLocationBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetUserLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvLocation = binding.tvLocation
        val btnGetLocation = binding.btnGetUbication

        btnGetLocation.setOnClickListener {
            lifecycleScope.launch {
                val result = location.getUserLocation(this@GetUserLocationActivity)
                if (result != null) {
                    tvLocation.text = "latitude ${result.latitude} y longitude ${result.longitude}"
                }
            }
        }
    }
}