package com.yusry.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.adapters.RecipeAdapter;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.database.RecipeDAO;
import com.yusry.smartpantrymanager.models.Ingredient;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Shows recipes that match what's in the pantry - strict matching only
public class SuggestedRecipesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvMatchCounter;
    private RecyclerView recyclerViewRecipes;
    private LinearLayout emptyStateContainer;
    private Button btnAddFromRecipes;
    private IngredientDAO ingredientDAO;
    private RecipeDAO recipeDAO;
    private List<Recipes> matchedRecipes;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        initializeUI();
        setupDatabase();
        setupToolbar();
        loadMatchedRecipes();
        setupClickListeners();
    }

    private void initializeUI() {
        toolbar = findViewById(R.id.toolbar);
        tvMatchCounter = findViewById(R.id.tvMatchCounter);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        emptyStateContainer = findViewById(R.id.emptyStateContainer);
        btnAddFromRecipes = findViewById(R.id.btnAddFromRecipes);
    }

    private void setupDatabase() {
        PantryDatabaseHelper dbHelper = new PantryDatabaseHelper(this);
        ingredientDAO = new IngredientDAO(dbHelper);
        recipeDAO = new RecipeDAO(this);
    }

    private void setupToolbar() {
        toolbar.setTitle("Recipes");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // grab all recipes, filter by strict matching, update UI
    private void loadMatchedRecipes() {
        matchedRecipes = new ArrayList<>();

        List<Recipes> allRecipes = recipeDAO.getAllRecipes();
        List<Ingredient> pantryIngredients = ingredientDAO.getAllIngredients();

        // convert pantry list to map for faster lookups
        Map<String, Double> pantryMap = buildPantryMap(pantryIngredients);

        // loop thru each recipe and check if we can make it
        for (int i = 0; i < allRecipes.size(); i++) {
            Recipes recipe = allRecipes.get(i);
            if (canMakeRecipe(recipe, pantryMap)) {
                matchedRecipes.add(recipe);
            }
        }

        updateUI();
    }

    // store pantry ingredients as map (name -> qty) for easy lookup
    private Map<String, Double> buildPantryMap(List<Ingredient> pantryIngredients) {
        Map<String, Double> pantryMap = new HashMap<>();
        for (int i = 0; i < pantryIngredients.size(); i++) {
            Ingredient ingredient = pantryIngredients.get(i);
            String normalizedName = ingredient.getName().toLowerCase().trim();
            pantryMap.put(normalizedName, ingredient.getQuantity());
        }
        return pantryMap;
    }

    // check if all recipe ingredients exist in pantry with enough qty
    private boolean canMakeRecipe(Recipes recipe, Map<String, Double> pantryMap) {
        String ingredientList = recipe.getIngredientList();

        if (ingredientList == null || ingredientList.isEmpty()) {
            return false;
        }

        String[] recipeIngredients = ingredientList.split(",");

        // verify each ingredient requirement
        for (int i = 0; i < recipeIngredients.length; i++) {
            String ingredient = recipeIngredients[i].trim();

            // format: "flour (2)" or "tomato (500g)"
            int bracketIndex = ingredient.indexOf("(");
            if (bracketIndex == -1) {
                return false; // skip malformed entries
            }

            String ingredientName = ingredient.substring(0, bracketIndex).trim().toLowerCase();
            String quantityStr = ingredient.substring(bracketIndex + 1, ingredient.lastIndexOf(")")).trim();

            // extract number from qty string
            double requiredQty = 0;
            try {
                String[] parts = quantityStr.split("\\s+");
                requiredQty = Double.parseDouble(parts[0]);
            } catch (Exception e) {
                return false; // couldnt parse, skip recipe
            }

            // pantry has enough of this ingredient?
            Double pantryQty = pantryMap.get(ingredientName);
            if (pantryQty == null || pantryQty < requiredQty) {
                return false; // nope, missing or insuficient
            }
        }

        return true; // all good
    }

    private void updateUI() {
        int totalRecipes = recipeDAO.getAllRecipes().size();
        tvMatchCounter.setText(matchedRecipes.size() + "/" + totalRecipes + " matches");

        if (matchedRecipes.isEmpty()) {
            emptyStateContainer.setVisibility(android.view.View.VISIBLE);
            recyclerViewRecipes.setVisibility(android.view.View.GONE);
        } else {
            emptyStateContainer.setVisibility(android.view.View.GONE);
            recyclerViewRecipes.setVisibility(android.view.View.VISIBLE);
            recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
            recipeAdapter = new RecipeAdapter(matchedRecipes, this);
            recyclerViewRecipes.setAdapter(recipeAdapter);
        }
    }

    private void setupClickListeners() {
        btnAddFromRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(SuggestedRecipesActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
