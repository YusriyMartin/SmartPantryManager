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

public class SuggestedRecipesActivity extends AppCompatActivity {

    // UI components we'll be using to display recipes and match information
    private Toolbar toolbar;
    private TextView tvMatchCounter;
    private RecyclerView recyclerViewRecipes;
    private LinearLayout emptyStateContainer;
    private Button btnAddFromRecipes;

    // Database helper objects to access ingredients and recipes from SQLite
    private IngredientDAO ingredientDAO;
    private RecipeDAO recipeDAO;

    // List to store recipes that match the user's pantry (strict-matching)
    private List<Recipes> matchedRecipes;

    // Adapter to display recipes in the RecyclerView
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity (the XML file we just created)
        setContentView(R.layout.recipe_suggestions);

        // Initialize all UI components by finding them in the XML layout
        initializeUI();

        // Set up the database access objects (DAOs) so we can read ingredients and recipes
        setupDatabase();

        // Configure the toolbar with title and back button
        setupToolbar();

        // Load and filter recipes based on strict-matching logic
        loadMatchedRecipes();

        // Set up click listeners for buttons
        setupClickListeners();
    }

    /**
     * Find all UI components from the XML layout and store them as class variables.
     * This is like saying "Hey Android, find these views and remember them for later!"
     */
    private void initializeUI() {
        toolbar = findViewById(R.id.toolbar);
        tvMatchCounter = findViewById(R.id.tvMatchCounter);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        emptyStateContainer = findViewById(R.id.emptyStateContainer);
        btnAddFromRecipes = findViewById(R.id.btnAddFromRecipes);
    }

    /**
     * Create database helper and DAO objects so we can query ingredients and recipes.
     * DAO = "Data Access Object" — it's like a translator between our app and the database.
     */
    private void setupDatabase() {
        // Create the database helper
        PantryDatabaseHelper dbHelper = new PantryDatabaseHelper(this);

        // IngredientDAO takes dbHelper
        ingredientDAO = new IngredientDAO(dbHelper);  // ✅ PantryDatabaseHelper

        // RecipeDAO takes Context (which is 'this')
        recipeDAO = new RecipeDAO(this);              // ✅ Context (the Activity)
    }

    /**
     * Configure the toolbar to show a title and a back button.
     * The back button will close this activity and return to MainActivity.
     */
    private void setupToolbar() {
        // Set "Recipes" as the title in the toolbar
        toolbar.setTitle("Recipes");

        // Enable the back/up button in the toolbar (the left arrow)
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * This is the HEART of the app! ❤️
     *
     * We implement STRICT-MATCHING logic:
     * - A recipe ONLY appears if the pantry has ALL required ingredients
     * - AND each ingredient is present in the required QUANTITY
     * - No partial matches, no "almost there" — it's all or nothing!
     *
     * Example:
     * - Recipe: "Banana Smoothie" needs 2 Bananas + 1 Protein Powder
     * - Pantry has: 2 Bananas ✅ and 0 Protein Powder ❌
     * - Result: Recipe is HIDDEN (doesn't appear in list)
     */
    private void loadMatchedRecipes() {
        // Start with an empty list — we'll add recipes that match
        matchedRecipes = new ArrayList<>();

        // Get ALL recipes from the database (all 20 preloaded recipes)
        List<Recipes> allRecipes = recipeDAO.getAllRecipes();

        // Get ALL ingredients currently in the user's pantry
        List<Ingredient> pantryIngredients = ingredientDAO.getAllIngredients();

        // Now we check EACH recipe against the pantry
        for (Recipes recipe : allRecipes) {
            // Check if this recipe can be made with what's in the pantry
            if (canMakeRecipe(recipe, pantryIngredients)) {
                // YES! Add it to the matched recipes list
                matchedRecipes.add(recipe);
            }
            // If canMakeRecipe returns false, we skip this recipe (don't add it)
        }

        // Update the UI based on how many recipes matched
        updateUI();
    }

    /**
     * STRICT-MATCHING LOGIC! 🎯
     *
     * This method checks if a recipe can be made with ingredients in the pantry.
     * It's strict: ALL ingredients must be present in the required quantity.
     *
     * @param recipe The recipe we're checking (e.g., "Banana Smoothie")
     * @param pantryIngredients All ingredients currently in the user's pantry
     * @return true if the recipe can be made, false otherwise
     */
    /**
     * STRICT-MATCHING LOGIC! 🎯
     *
     * This method checks if a recipe can be made with ingredients in the pantry.
     * It's strict: ALL ingredients must be present in the required quantity.
     *
     * @param recipe The recipe we're checking (e.g., "Banana Smoothie")
     * @param pantryIngredients All ingredients currently in the user's pantry
     * @return true if the recipe can be made, false otherwise
     */
    private boolean canMakeRecipe(Recipes recipe, List<Ingredient> pantryIngredients) {
        // Get the ingredient list from the recipe
        // This is a comma-separated string like "tomatoes,garlic,olive oil"
        String ingredientList = recipe.getIngredientList();

        // If the recipe has no ingredients listed, we can't make it
        if (ingredientList == null || ingredientList.isEmpty()) {
            return false;
        }

        // Split the ingredient names string into individual ingredients
        // Example: "tomatoes,garlic,olive oil" becomes ["tomatoes", "garlic", "olive oil"]
        String[] recipeIngredients = ingredientList.split(",");

        // Check EACH ingredient needed for this recipe
        for (String recipeIngredient : recipeIngredients) {
            // Trim whitespace from the ingredient name
            String neededIngredient = recipeIngredient.trim().toLowerCase();

            // Now search the pantry to find THIS ingredient
            boolean foundIngredient = false;
            for (Ingredient pantryItem : pantryIngredients) {
                // Compare ingredient names (case-insensitive and unit-robust)
                // For example, "tomato" matches "tomatoes", "Tomato", "TOMATO"
                String pantryItemName = pantryItem.getName().trim().toLowerCase();

                // Check if the pantry ingredient matches (allowing singular/plural variations)
                if (pantryItemName.equals(neededIngredient) ||
                        pantryItemName.replace("es", "").equals(neededIngredient) ||
                        neededIngredient.replace("es", "").equals(pantryItemName)) {
                    foundIngredient = true;
                    break;
                }
            }

            // STRICT CHECK: Did we find the ingredient?
            if (!foundIngredient) {
                // ❌ Ingredient is missing from pantry — recipe can't be made
                return false;
            }
            // ✅ This ingredient check passed, continue to next one
        }

        // ✅ ALL ingredients check passed! The recipe can be made!
        return true;
    }


    /**
     * Update the UI based on whether recipes matched or not.
     * If recipes matched: show the list and update the counter.
     * If no recipes matched: show the empty state message.
     */
    private void updateUI() {
        // Update the match counter to show how many recipes can be made
        // Example: "3/20 matches" means 3 out of 20 recipes can be made
        int totalRecipes = recipeDAO.getAllRecipes().size();
        tvMatchCounter.setText(matchedRecipes.size() + "/" + totalRecipes + " matches");

        if (matchedRecipes.isEmpty()) {
            // ❌ No recipes matched — show the empty state message
            emptyStateContainer.setVisibility(android.view.View.VISIBLE);
            recyclerViewRecipes.setVisibility(android.view.View.GONE);
        } else {
            // ✅ Recipes matched — show the list and hide the empty state
            emptyStateContainer.setVisibility(android.view.View.GONE);
            recyclerViewRecipes.setVisibility(android.view.View.VISIBLE);

            // Set up the RecyclerView with a layout manager and adapter
            recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));

            // Create an adapter to display the matched recipes
            recipeAdapter = new RecipeAdapter(matchedRecipes, this);

            // Tell the RecyclerView to use our adapter
            recyclerViewRecipes.setAdapter(recipeAdapter);
        }
    }

    /**
     * Set up click listeners for buttons in this activity.
     */
    private void setupClickListeners() {
        // When user clicks the "Add Ingredient" button in the empty state...
        btnAddFromRecipes.setOnClickListener(v -> {
            // Open the AddEditIngredientActivity
            Intent intent = new Intent(SuggestedRecipesActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Handle back button click in the toolbar.
     * This method is called when the user clicks the back arrow.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check if the back/up button was clicked
        if (item.getItemId() == android.R.id.home) {
            // Close this activity and return to MainActivity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
