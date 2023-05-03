package com.example.FoodPrKotlin.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.FoodPrKotlin.controller.Api
import com.example.FoodPrKotlin.model.MyResponse
import com.example.FoodPrKotlin.controller.RecipeAdapter
import com.example.FoodPrKotlin.controller.RetrofitConnection.Companion.instance
import com.example.FoodPrKotlin.controller.Utils
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecipeActivity : AppCompatActivity() {
    var pager: ViewPager2? = null
    var searchField: EditText? = null
    var adapter: RecipeAdapter? = null
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        supportActionBar!!.hide()
        pager = findViewById(R.id.pager)
        val itemDecorator =
            DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.item_decorator
            )!!
        )
        pager?.addItemDecoration(itemDecorator)

        searchField = findViewById(R.id.search_field)
        progressBar = findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE;
        val apiService = instance!!.retrofit!!.create(
            Api::class.java
        )
        searchField?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val call = apiService.getRecipes(
                    Utils.type,
                    Utils.appID,
                    Utils.appKey,
                    editable.toString()
                )
                progressBar?.setVisibility(View.VISIBLE)
                call!!.enqueue(object : Callback<MyResponse?> {
                    override fun onResponse(
                        call: Call<MyResponse?>,
                        response: Response<MyResponse?>
                    ) {
                        adapter = RecipeAdapter(response.body()!!)
                        pager?.setAdapter(adapter)
                        progressBar?.setVisibility(View.GONE)
                        //Toast.makeText(RecipeActivity.this, response.body().hits.get(0).recipe.label + "калории: " + response.body().hits.get(0).recipe.calories + " " + response.body().hits.get(0).recipe.ingredientLines.toString() + " " + response.body().hits.get(0).recipe.co2EmissionsClass, Toast.LENGTH_SHORT).show();
                    }

                    override fun onFailure(call: Call<MyResponse?>, t: Throwable) {
                        Toast.makeText(this@RecipeActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })
    }
}