package com.yusry.smartpantrymanager.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yusry.smartpantrymanager.R;
import com.yusry.smartpantrymanager.adapters.RecipeAdapter;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.database.RecipeDAO;
import com.yusry.smartpantrymanager.models.Ingredient;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Shows recipes that match what's in the pantry - strict matching only
public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRecipes;
    private RelativeLayout emptyStateContainer;
    private TextView tvMatchCounter;
    private Button btnAddFromRecipes;
    private PantryDatabaseHelper dbHelper;
    private RecipeDAO recipeDAO;
    private IngredientDAO ingredientDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        // grab UI refs
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        emptyStateContainer = findViewById(R.id.emptyStateContainer);
        tvMatchCounter = findViewById(R.id.tvMatchCounter);
        btnAddFromRecipes = findViewById(R.id.btnAddFromRecipes);

        // init db and DAOs
        dbHelper = new PantryDatabaseHelper(this);
        recipeDAO = new RecipeDAO(dbHelper);
        ingredientDAO = new IngredientDAO(dbHelper);

        // setup recycler
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));

        // load and filter recipes
        loadAndFilterRecipes();

        // add button click — go back to add ingredients
        btnAddFromRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(SuggestedRecipesActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }
    private void loadAndFilterRecipes() {
        // grab all recipes from db
        List<Recipes> allRecipes = recipeDAO.getAllRecipes();
        if (allRecipes == null) {
            allRecipes = new ArrayList<>();
        }

        // get pantry ingredients, build map for lookup (name -> qty)
        List<Ingredient> pantryList = ingredientDAO.getAllIngredients();
        HashMap<String, Double> pantryMap = new HashMap<>();
        for (int i = 0; i < pantryList.size(); i++) {
            Ingredient ing = pantryList.get(i);
            pantryMap.put(ing.getName().toLowerCase(), ing.getQuantity());
        }

        // filter recipes — only show if we can make all ingredients
        List<Recipes> matchedRecipes = new ArrayList<>();
        for (int i = 0; i < allRecipes.size(); i++) {
            Recipes recipe = allRecipes.get(i);
            if (canMakeRecipe(recipe, pantryMap)) {
                matchedRecipes.add(recipe);
            }
        }
        // update UI with match count
        tvMatchCounter.setText("Hey there chef! You can make " + matchedRecipes.size() + "/20 recipes");

        // show empty state or recipes list
        if (matchedRecipes.isEmpty()) {
            emptyStateContainer.setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.GONE);
        } else {
            emptyStateContainer.setVisibility(View.GONE);
            recyclerViewRecipes.setVisibility(View.VISIBLE);
            RecipeAdapter adapter = new RecipeAdapter(matchedRecipes, this);
            recyclerViewRecipes.setAdapter(adapter);
        }
    }
    private boolean canMakeRecipe(Recipes recipe, HashMap<String, Double> pantryMap) {
        // parse ingredient list from recipe (format: "Tomato (2 kg), Onion (1 piece)")
        String ingredientStr = recipe.getIngredientList();
        if (ingredientStr == null || ingredientStr.trim().isEmpty()) {
            return false;
        }

        // split by comma and check each one
        String[] ingredients = ingredientStr.split(",");
        for (int i = 0; i < ingredients.length; i++) {
            String ingredient = ingredients[i].trim();
            // extract name only (before the opening parenthesis)
            String ingredientName = ingredient;
            if (ingredient.contains("(")) {
                ingredientName = ingredient.substring(0, ingredient.indexOf("(")).trim();
            }
            // ingredient missing from pantry, recipe fails
            if (!pantryMap.containsKey(ingredientName.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}

