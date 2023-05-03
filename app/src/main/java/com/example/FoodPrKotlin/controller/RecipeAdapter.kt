package com.example.FoodPrKotlin.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.FoodPrKotlin.model.MyResponse
import com.example.FoodPrKotlin.model.Recipe
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class RecipeAdapter(private val onboardingItems: MyResponse) :
    RecyclerView.Adapter<RecipeAdapter.OnboardingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.setOnboardingData(onboardingItems.hits!![position].recipe)
    }

    override fun getItemCount(): Int {
        return onboardingItems.hits!!.size
    }

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textZagolovok: TextView
        private val textOpisanie: TextView
        private val textCalorii: TextView
        private val textRating: TextView
        private val image: ImageView

        init {
            textZagolovok = itemView.findViewById(R.id.text_title)
            textOpisanie = itemView.findViewById(R.id.text_descr)
            textCalorii = itemView.findViewById(R.id.text_calorii)
            textRating = itemView.findViewById(R.id.text_rating)
            image = itemView.findViewById(R.id.image)
        }

        fun setOnboardingData(recipe: Recipe?) {
            textZagolovok.text = recipe!!.label
            val stringBuilder = StringBuilder()
            stringBuilder.append("ингредиенты:\n")
            for (s in recipe.ingredientLines!!) {
                stringBuilder.append(s).append("\n")
            }
            textOpisanie.text = stringBuilder
            textCalorii.text = """калории:
 ${recipe.calories}"""
            textRating.text = """
                   рейтинг: 
                   ${recipe.co2EmissionsClass}
                   """.trimIndent()
            //здесь нужна библиотека Picasso для загрузки картинки по url в наш imageView
            Picasso.get().load(recipe.image).into(image)
        }
    }
}