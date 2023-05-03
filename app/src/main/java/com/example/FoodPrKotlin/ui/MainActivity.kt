package com.example.FoodPrKotlin.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.FoodPrKotlin.db.Database
import com.example.myapplication.R


class MainActivity : AppCompatActivity() {
     var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()


        button = findViewById(R.id.button)
        button?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    RecipeActivity::class.java
                )
            )
        })
    }
}