package com.richardrock.rimuo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.richardrock.rimuo.ImcActivity.Companion.IMC_KEY

@Suppress("DEPRECATION")
class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvIMC: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnReCalcule: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imc)
        val result = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        initComponents()
        initUI(result)
        initListeners()
    }

    private fun initListeners() {
        btnReCalcule.setOnClickListener { onBackPressed() }
    }

    private fun initUI(result: Double) {
        tvIMC.text = result.toString()
        when (result) {
            in 0.00..18.5 -> {//BAJO PESO
                tvResult.text = getString(R.string.bajo_peso)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.GreenYellow))
                tvDescription.text = getString(R.string.description_bajo_peso)
            }

            in 18.51..24.99 -> {//PESO NORMAL
                tvResult.text = getString(R.string.peso_normal)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.LightGreen))
                tvDescription.text = getString(R.string.description_peso_normal)
            }

            in 25.0..29.99 -> {//SOBREPESO
                tvResult.text = getString(R.string.sobrepeso)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.OrangeRed))
                tvDescription.text = getString(R.string.description_sobrepeso)
            }

            in 30.00..60.0 -> {//OBESIDAD
                tvResult.text = getString(R.string.obesidad)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.Crimson))
                tvDescription.text = getString(R.string.description_obesidad)
            }

            else -> {
                tvResult.text = getString(R.string.error)
                tvIMC.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
            }
        }
    }

    private fun initComponents() {
        tvResult = findViewById(R.id.tvResult)
        tvIMC = findViewById(R.id.tvIMC)
        tvDescription = findViewById(R.id.tvDescription)
        btnReCalcule = findViewById(R.id.btnReCalcule)
    }
}