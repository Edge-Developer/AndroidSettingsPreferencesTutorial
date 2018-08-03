package com.edgedevstudio.androidsettingspreferencestutorial

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.*


class SettingsFragement : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    val TAG = "SettingsFragement"
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings_pref)

        val sharedPref = preferenceScreen.sharedPreferences
        sharedPref.registerOnSharedPreferenceChangeListener(this)
        val prefScreen = preferenceScreen
        val count = preferenceScreen.preferenceCount

        for (index in 0..(count - 1)) {
            val preference = prefScreen.getPreference(index)
            val prefKey = preference.key
            if (preference !is CheckBoxPreference) {
                val value = sharedPref.getString(prefKey, "")
                setPreferenceSummary(preference, value)
            }
        }
    }

    fun setPreferenceSummary(preference: Preference, value: String) {
        if (preference is ListPreference) {
            val prefIndex = preference.findIndexOfValue(value)
            if (prefIndex >= 0)
                preference.setSummary(preference.entries[prefIndex])
        } else if (preference is EditTextPreference) {
            val msg = preference.text
            preference.setSummary(msg)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val preference = findPreference(key)
        if (preference != null) {
            if (preference !is CheckBoxPreference) {
                val sharedPrefKey = sharedPreferences?.getString(preference.key, "")
                setPreferenceSummary(preference, sharedPrefKey!!)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}