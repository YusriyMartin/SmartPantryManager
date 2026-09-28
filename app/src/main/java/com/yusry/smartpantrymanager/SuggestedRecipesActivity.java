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
import java.util.List;

// Suggested recipes based on ingredients in pantry
public class SuggestedRecipesActivity extends AppCompatActivity {
    // UI components used to display recipes and match information
    private Toolbar toolbar;
    private TextView tvMatchCounter;
    private RecyclerView recyclerViewRecipes;
    private LinearLayout emptyStateContainer;
    private Button btnAddFromRecipes;
    // db helper objects to access ingredients and recipes from db
    private IngredientDAO ingredientDAO;
    private RecipeDAO recipeDAO;
    private List<Recipes> matchedRecipes; // List to store the recipes for strict matching
    // Adapter to display recipes in the RecyclerView
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity (the XML file we just created)
        setContentView(R.layout.recipe_suggestions);
        initializeUI(); // Initialize all UI components
        setupDatabase(); // Set up db access objects (Date Access Object) to read ingredients and recipes
        setupToolbar(); // Configure toolbare with title and back button
        loadMatchedRecipes(); // load and filter recipes based on strict mode
        setupClickListeners(); // Set up click listeners for buttons
    }
    // Find all UI components and store as class variables
    private void initializeUI() {
        toolbar = findViewById(R.id.toolbar);
        tvMatchCounter = findViewById(R.id.tvMatchCounter);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        emptyStateContainer = findViewById(R.id.emptyStateContainer);
        btnAddFromRecipes = findViewById(R.id.btnAddFromRecipes);
    }
    // Create db helper and Data Access Object to query ingredients and recipes
    private void setupDatabase() {
        PantryDatabaseHelper dbHelper = new PantryDatabaseHelper(this); // Create db helper
        ingredientDAO = new IngredientDAO(dbHelper);
        recipeDAO = new RecipeDAO(this);
    }

    // Configure toolbar to show title + back button
    private void setupToolbar() {
        toolbar.setTitle("Recipes"); // Set recipes as title in toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    // Implementing strict matching logic
    private void loadMatchedRecipes() {
        matchedRecipes = new ArrayList<>();

        List<Recipes> allRecipes = recipeDAO.getAllRecipes(); // Get all recipes from db
        List<Ingredient> pantryIngredients = ingredientDAO.getAllIngredients(); // Get all ingredients in pantry

        // Check each recipe against what's available in pantry
        for (int i = 0; i < allRecipes.size(); i++) {
            Recipes recipe = allRecipes.get(i);
            if (canMakeRecipe(recipe, pantryIngredients)) {
                matchedRecipes.add(recipe);
            }
        }
        // Update the UI based on how many recipes matched
        updateUI();
    }

    // Checks if recipe can be made with available ingredients
    private boolean canMakeRecipe(Recipes recipe, List<Ingredient> pantryIngredients) {
        String ingredientList = recipe.getIngredientList(); // Get ingreident list from recipe

        if (ingredientList == null || ingredientList.isEmpty()) {
            return false;
        }
        // Split ingredient names string into individual ingredients
        String[] recipeIngredients = ingredientList.split(",");

        // Check each ingredient needed to make a recipe
        for (int i = 0; i < recipeIngredients.length; i++) {
            String neededIngredient = recipeIngredients[i].trim().toLowerCase();
            boolean foundIngredient = false;

            for (int j = 0; j < pantryIngredients.size(); j++) {
                Ingredient pantryItem = pantryIngredients.get(j);
                String pantryItemName = pantryItem.getName().trim().toLowerCase();

                // Check for ingredient match, adding in suffix checks for name variations
                if (pantryItemName.equals(neededIngredient) ||
                        pantryItemName.replace("es", "").equals(neededIngredient) ||
                        neededIngredient.replace("es", "").equals(pantryItemName)) {
                    foundIngredient = true;
                    break;
                }
            }
            // If ingredient not found, can't make recipe
            if (!foundIngredient) {
                return false;
            }
        }
        // returns if ingredients to make recipe is available
        return true;
    }

    // Update UI based on recipe matching
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
            // Create an adapter to display the matched recipes
            recipeAdapter = new RecipeAdapter(matchedRecipes, this);
            recyclerViewRecipes.setAdapter(recipeAdapter);
        }
    }

    // Set up click listeners for buttons
    private void setupClickListeners() {
        btnAddFromRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(SuggestedRecipesActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }
    // back button click functionality in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
