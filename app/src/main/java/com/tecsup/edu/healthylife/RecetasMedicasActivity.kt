package com.tecsup.edu.healthylife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.adapter.RecipeAdapter
import com.tecsup.edu.healthylife.view_model.RecipeViewModel


class RecetasMedicasActivity : AppCompatActivity() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetasmedicas)

        supportActionBar?.hide()

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        val userId = 1 // ID del usuario logueado (en este caso, 2)

        recipeViewModel.getRecipesForUser(userId)

        recyclerView = findViewById(R.id.recyclerViewRecipes)
        recipeAdapter = RecipeAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@RecetasMedicasActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = recipeAdapter
        }

        recipeViewModel.getRecipesLiveData().observe(this, Observer { recipeList ->
            recipeAdapter.submitList(recipeList)
        })
    }

}
