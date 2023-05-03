package com.example.FoodPrKotlin.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
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
import com.example.FoodPrKotlin.db.AppDatabase
import com.example.FoodPrKotlin.db.Database
import com.example.FoodPrKotlin.db.RecipeEntity
import com.example.FoodPrKotlin.model.Recipe
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecipeActivity : AppCompatActivity() {
    var pager: ViewPager2? = null
    var searchField: EditText? = null
    var adapter: RecipeAdapter? = null
    var progressBar: ProgressBar? = null
    var countRecords: TextView? = null


    private var database: AppDatabase? = null

    fun createDataBase(context: Context){
        database = Database.build(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        supportActionBar!!.hide()
        pager = findViewById(R.id.pager)
        countRecords = findViewById(R.id.text_count_rec)

        val itemDecorator =
            DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.item_decorator
            )!!
        )
        pager?.addItemDecoration(itemDecorator)

        createDataBase(applicationContext)


        CoroutineScope(Dispatchers.IO).launch {
            countRecords?.setText("количество записей в БД: " + database?.recipeDao()?.getCountRecords())
        }

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

                        //работаем с БД
                        for (hit in response.body()!!.hits!!){
                            hit.recipe?.let { saveData(it) }
                        }

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

   fun saveData(recipe: Recipe){
        val  recipeEntity = RecipeEntity(
            image = recipe.image,
            label =  recipe.label,
            calories = recipe.calories,
            ingredientLines = recipe.ingredientLines.toString(),
            co2EmissionsClass = recipe.co2EmissionsClass)
        CoroutineScope(Dispatchers.IO).launch {
            var id: Long? =  database?.recipeDao()?.insertRecipe(recipeEntity)
            var count: Long? = database?.recipeDao()?.getCountRecords()
            MainScope().launch {
                countRecords?.setText("количество записей в БД: " + count)
                if (count!! > 1000) {
                    database?.recipeDao()?.deleteAll()
                    countRecords?.setText(
                        "количество записей в БД: " + database?.recipeDao()?.getCountRecords())
                }
            }

           // Log.d("777", "id: " + id + " " + recipe.toString())

        }
    }

}