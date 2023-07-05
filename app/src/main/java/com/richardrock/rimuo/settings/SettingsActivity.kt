package com.richardrock.rimuo.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.*
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.richardrock.rimuo.R
import com.richardrock.rimuo.databinding.ActivitySettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val VOLUME_LVL = "volume_lvl"
        const val KEY_DARKMODE = "key_dark_mode"
        const val KEY_BLUETOOTH = "key_bluetooth"
        const val KEY_VIBRATION = "key_vibration"
    }

    private lateinit var binding: ActivitySettingsBinding
    private var firstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getBack = binding.icGetBack

        CoroutineScope(Dispatchers.IO).launch {
            getSetting().filter { firstTime }.collect { settingsModel ->
                runOnUiThread {
                    if (settingsModel != null) {
                        binding.switchDarkMode.isChecked = settingsModel.darkMode
                        binding.switchBluetooth.isChecked = settingsModel.bluetooth
                        binding.rsVolume.setValues(settingsModel.volume.toFloat())
                        binding.switchVibrate.isChecked = settingsModel.vibration
                        firstTime = !firstTime
                    }
                }
            }
        }
        initUI()

        getBack.setOnClickListener { navigateBack() }

    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener { _, value, _ ->
            Log.i("RICH", "El valor de volume es $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            if (value) {
                enableDarkMode()
            } else {
                disableDarkMode()
            }

            CoroutineScope(Dispatchers.IO).launch {
                saveChecks(KEY_DARKMODE, value)
            }
        }
        binding.switchBluetooth.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveChecks(KEY_BLUETOOTH, value)
            }
        }
        binding.switchVibrate.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveChecks(KEY_VIBRATION, value)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun navigateBack() {
        onBackPressed()
    }

    private suspend fun saveVolume(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(VOLUME_LVL)] = value
        }
    }

    private suspend fun saveChecks(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSetting(): Flow<SettingsModel?> {
        return dataStore.data.map { preferences ->
            SettingsModel(
                darkMode = preferences[booleanPreferencesKey(KEY_DARKMODE)] ?: false,
                bluetooth = preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
                volume = preferences[intPreferencesKey(VOLUME_LVL)] ?: 45,
                vibration = preferences[booleanPreferencesKey(KEY_VIBRATION)] ?: true
            )
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}