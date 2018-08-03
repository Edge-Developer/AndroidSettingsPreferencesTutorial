package com.edgedevstudio.androidsettingspreferencestutorial

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var textView: TextView? = null
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        val showTextView = sharedPreferences.getBoolean(getString(R.string.textview_visibility_key), resources.getBoolean(R.bool.show_button_default))
        val textViewString = sharedPreferences.getString(getString(R.string.textview_text_key), getString(R.string.default_textview_text))
        val backgroundColorString = sharedPreferences.getString(getString(R.string.textview_backgroundcolor_key), getString(R.string.pref_bckgrnd_colour_black_value))
        val textColorString = sharedPreferences.getString(getString(R.string.textview_textcolor_key), getString(R.string.pref_txt_colour_white_value))

        setTextViewText(textViewString)
        setTextViewVisibility(showTextView)
        setTextViewBackgroundColor(backgroundColorString)
        setTextViewTextColor(textColorString)

        Log.d(TAG, "onCreate, " +
                "visibility = $showTextView, " +
                "text = $textViewString, " +
                "backgroundColor = $backgroundColorString, " +
                "textColor = $textColorString")

    }

    fun launchSettings(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences == null) return
        Log.d(TAG, "onSharedPreferenceChanged and is not null")
        val showTextView = sharedPreferences.getBoolean(getString(R.string.textview_visibility_key), resources.getBoolean(R.bool.show_button_default))
        val textViewString = sharedPreferences.getString(getString(R.string.textview_text_key), getString(R.string.default_textview_text))
        val backgroundColorString = sharedPreferences.getString(getString(R.string.textview_backgroundcolor_key), getString(R.string.pref_bckgrnd_colour_black_value))
        val textColorString = sharedPreferences.getString(getString(R.string.textview_textcolor_key), getString(R.string.pref_txt_colour_white_value))

        setTextViewText(textViewString)
        setTextViewVisibility(showTextView)
        setTextViewBackgroundColor(backgroundColorString)
        setTextViewTextColor(textColorString)
    }

    fun setTextViewText(text: String) {
        textView?.setText(text)
    }

    fun setTextViewTextColor(text_color_value: String) {
        var color_string = ""
        if (text_color_value.equals(getString(R.string.pref_txt_colour_white_value)))
            color_string = "#ffffff"
        else if (text_color_value.equals(getString(R.string.pref_txt_colour_yellow_value)))
            color_string = "#ffff00"
        else if (text_color_value.equals(getString(R.string.pref_txt_colour_sea_green_value)))
            color_string = "#2E8B57"
        textView?.setTextColor(Color.parseColor(color_string))
    }

    fun setTextViewBackgroundColor(background_color_value: String) {
        var color_string = ""
        if (background_color_value.equals(getString(R.string.pref_bckgrnd_colour_black_value)))
            color_string = "#000000"
        else if (background_color_value.equals(getString(R.string.pref_bckgrnd_colour_red_value)))
            color_string = "#ff0000"
        else if (background_color_value.equals(getString(R.string.pref_bckgrnd_colour_blue_value)))
            color_string = "#0000ff"
        textView?.setBackgroundColor(Color.parseColor(color_string))
    }

    fun setTextViewVisibility(showTextView: Boolean) {
        if (showTextView) textView?.visibility = View.VISIBLE
        else textView?.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }
}