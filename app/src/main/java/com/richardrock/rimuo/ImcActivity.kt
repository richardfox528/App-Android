package com.richardrock.rimuo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class ImcActivity : AppCompatActivity() {

    private var isMenSelected: Boolean = false
    private var isWomenSelected: Boolean = true
    private var currentWeight: Int = 70
    private var currentHeight: Int = 120
    private var currentAge: Int = 30

    private lateinit var cvMen: CardView
    private lateinit var cvWomen: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubstractWeight: FloatingActionButton
    private lateinit var btnplusWeight: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnSubstractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalcule: AppCompatButton

    companion object{
        const val IMC_KEY= "Result_IMC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        cvMen = findViewById(R.id.cvMen)
        cvWomen = findViewById(R.id.cvWomen)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubstractWeight = findViewById(R.id.btnSubstractWeight)
        btnplusWeight = findViewById(R.id.btnPlusWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubstractAge = findViewById(R.id.btnSubstractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalcule = findViewById(R.id.btnCalcule)

    }

    private fun initListeners() {
        cvMen.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        cvWomen.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }
        btnSubstractWeight.setOnClickListener {
            currentWeight -= 1
            setWeight()
        }
        btnplusWeight.setOnClickListener {
            currentWeight += 1
            setWeight()
        }
        btnSubstractAge.setOnClickListener {
            currentAge -= 1
            setAge()
        }
        btnPlusAge.setOnClickListener {
            currentAge += 1
            setAge()
        }
        btnCalcule.setOnClickListener {
            val result = calculeIMC()
            navigateToResult(result)
        }
    }

    private fun navigateToResult(result: Double) {
        val intent = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun calculeIMC(): Double {
        var df = DecimalFormat("#.##")
        val imc = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100)
        return df.format(imc).toDouble()
        // Log.i("RICH", "TU IMC ES $result")
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun changeGender() {
        isMenSelected = !isMenSelected
        isWomenSelected = !isWomenSelected
    }

    private fun setGenderColor() {
        cvMen.setCardBackgroundColor(getBackgroundColor(isMenSelected))
        cvWomen.setCardBackgroundColor(getBackgroundColor(isWomenSelected))
    }

    private fun getBackgroundColor(isSelectComponent: Boolean): Int {
        val colorReference = if (isSelectComponent) {
            R.color.MidnightBlue
        } else {
            R.color.MediumBlue
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }
}