package com.richardrock.rimuo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val tvName = findViewById<TextView>(R.id.tvName)
        val btnBuscar = findViewById<AppCompatButton>(R.id.btn)
        val tvInput = findViewById<AppCompatEditText>(R.id.textinput)

        val name: String = intent.extras?.getString("EXTRA").orEmpty()

        btnBuscar.setOnClickListener {
            var name = tvInput.text.toString()

            if (name.isNotEmpty()) {

                val intent = Intent(this, ViewActivity::class.java)
                intent.putExtra("EXTRA", name)
                startActivity(intent)

            }
        }

        tvName.text = "Hola, $name"
    }
}