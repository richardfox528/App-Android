package com.richardrock.rimuo

import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.richardrock.rimuo.rvLeerPoemas.PoemasActivity
import com.richardrock.rimuo.rvSuperHeroApp.rvSuperHeroApp
import com.richardrock.rimuo.settings.SettingsActivity
import com.richardrock.rimuo.todoapp.ToDoAppActivity

class MainActivity : AppCompatActivity() {
    val PERMISSIONS_REQUEST_CODE = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSettings = findViewById<ImageButton>(R.id.btnSettings)
        val btnSaludar = findViewById<AppCompatButton>(R.id.menu)
        val btnIMC = findViewById<AppCompatButton>(R.id.btnIMCApp)
        val btnTodoApp = findViewById<AppCompatButton>(R.id.btnToDoApp)
        val btnSuperHero = findViewById<AppCompatButton>(R.id.btnSuperHero)
        val btnLeerPoemas = findViewById<AppCompatButton>(R.id.btnLeerPoemas)

        btnSettings.setOnClickListener { buttonSettins() }
        btnSaludar.setOnClickListener { funSaludar() }
        btnIMC.setOnClickListener { calculeIMC() }
        btnTodoApp.setOnClickListener { toDoApp() }
        btnSuperHero.setOnClickListener { superHeroApp() }
        btnLeerPoemas.setOnClickListener { leerPoemasApp() }
        requestedPermissions()
    }

    private fun requestedPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // Verificar si el permiso no está otorgado y agregarlo a la lista de permisos a solicitar
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissionsToRequest.add(Manifest.permission.CAMERA)
            permissionsToRequest.add(Manifest.permission.BODY_SENSORS)
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
            permissionsToRequest.add(Manifest.permission.RECORD_AUDIO)
            permissionsToRequest.add(Manifest.permission.CALL_PHONE)
            permissionsToRequest.add(Manifest.permission.READ_SMS)
            permissionsToRequest.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            permissionsToRequest.add(Manifest.permission.READ_CALL_LOG)
        }

        // Verificar si hay permisos para solicitar
        if (permissionsToRequest.isNotEmpty()) {

            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // Verificar si se otorgaron todos los permisos solicitados

            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Los permisos fueron otorgados
                // Realizar acciones necesarias aquí
            } else {
                // Al menos un permiso fue denegado
                // Manejar el caso de permisos denegados aquí
            }
        }
    }

    private fun buttonSettins() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun funSaludar() {
        val intent = Intent(this, ViewActivity::class.java)
        startActivity(intent)
    }

    private fun calculeIMC() {
        val intent = Intent(this, ImcActivity::class.java)
        startActivity(intent)
    }

    private fun toDoApp() {
        val intent = Intent(this, ToDoAppActivity::class.java)
        startActivity(intent)
    }

    private fun superHeroApp() {
        val intent = Intent(this, rvSuperHeroApp::class.java)
        startActivity(intent)
    }

    private fun leerPoemasApp() {
        val intent = Intent(this, PoemasActivity::class.java)
        startActivity(intent)
    }

}