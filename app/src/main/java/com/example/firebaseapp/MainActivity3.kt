package com.example.firebaseapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity

class MainActivity3 : AppCompatActivity() {

    lateinit var WeatherBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var tvLoggedInUserName: TextView
        WeatherBtn = findViewById(R.id.goToWeatherBtn)
        tvLoggedInUserName = this.findViewById(R.id.tvLoggedInUserName)

        var intent = intent

        var name = intent.getStringExtra("userName")

        tvLoggedInUserName.setText("Välkommen " + name)

        WeatherBtn.setOnClickListener() {
            Log.d("TAG", "clicked")
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

    }
}