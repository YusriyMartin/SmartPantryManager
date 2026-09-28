package com.yusry.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.database.RecipeDAO;
import com.yusry.smartpantrymanager.models.Recipes;

/**
 * RecipeDetailActivity displays the full details of a single recipe.
 * When user clicks a recipe from the list, they see:
 * - Recipe name
 * - Ingredients needed (with quantities)
 * - Cooking method/instructions
 * - A back button to return to the recipes list
 */
public class RecipeDetailActivity extends AppCompatActivity {

    // UI components for displaying recipe details
    private Toolbar toolbar;
    private TextView tvRecipeName;
    private TextView tvIngredients;
    private TextView tvMethod;
    private Button btnBack;

    // Database helper to access the database
    private PantryDatabaseHelper dbHelper;

    // Database access object to fetch recipe details
    private RecipeDAO recipeDAO;

    // The recipe ID passed from the previous activity
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.activity_recipe_detail);

        // Initialize UI components (find them in the XML layout)
        initializeUI();

        // Set up the database
        setupDatabase();

        // Configure the toolbar
        setupToolbar();

        // Get the recipe ID from the intent that launched this activity
        getRecipeIdFromIntent();

        // Load and display the recipe details
        loadRecipeDetails();

        // Set up button click listeners
        setupClickListeners();
    }

    /**
     * Find all UI components from the XML layout and store them as class variables.
     */
    private void initializeUI() {
        toolbar = findViewById(R.id.toolbar);
        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvMethod = findViewById(R.id.tvMethod);
        btnBack = findViewById(R.id.btnBack);
    }

    /**
     * Create a database helper and RecipeDAO so we can fetch recipe details.
     * The PantryDatabaseHelper is the "key" to the database.
     * The RecipeDAO uses that key to access recipes.
     */
    private void setupDatabase() {
        // Create a database helper — this opens the database connection
        dbHelper = new PantryDatabaseHelper(this);

        // Create a RecipeDAO using the CONTEXT (this activity), not the database helper!
        // Now we can fetch recipes from the database
        recipeDAO = new RecipeDAO(this);  // ✅ CORRECT
    }

    /**
     * Configure the toolbar with a title and back button.
     */
    private void setupToolbar() {
        // Set "Recipe Details" as the title in the toolbar
        toolbar.setTitle("Recipe Details");

        // Enable the back/up button in the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Extract the recipe ID from the intent that launched this activity.
     * The recipe ID was passed from RecipeAdapter's onClick method.
     */
    private void getRecipeIdFromIntent() {
        // Get the intent that launched this activity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            // Extract the "recipe_id" value (default to 0 if not found)
            recipeId = extras.getInt("recipe_id", 0);
        }
    }

    /**
     * Load the recipe from the database and display its details.
     */
    private void loadRecipeDetails() {
        // Fetch the recipe from the database using its ID
        Recipes recipe = recipeDAO.getRecipeById(recipeId);

        if (recipe != null) {
            // Display the recipe name in the TextView
            tvRecipeName.setText(recipe.getName());

            // Display the ingredients needed for this recipe
            // The ingredients are stored in a formatted string like "tomato:2:cups,garlic:3:cloves"
            String ingredientsText = formatIngredientList(recipe.getIngredientList());
            tvIngredients.setText(ingredientsText);

            // Display the cooking method/instructions
            // This is stored in the "method" field of the Recipes model
            tvMethod.setText(recipe.getMethod());
        } else {
            // If recipe not found, show an error message
            tvRecipeName.setText("Recipe Not Found");
            tvIngredients.setText("Could not load recipe details.");
            tvMethod.setText("");
        }
    }

    /**
     * Format the ingredient list to display nicely.
     * Takes a formatted string like "tomato:2:cups,garlic:3:cloves,olive oil:2:tablespoons"
     * and converts it to a readable format like:
     * "Tomato (2 cups)
     *  Garlic (3 cloves)
     *  Olive Oil (2 tablespoons)"
     *
     * @param ingredientList Comma-separated ingredients in format "name:quantity:unit"
     * @return Formatted string with each ingredient on a new line
     */
    private String formatIngredientList(String ingredientList) {
        // If the ingredient list is empty or null, return a placeholder message
        if (ingredientList == null || ingredientList.isEmpty()) {
            return "No ingredients listed";
        }

        // Split the string by commas to get individual ingredient entries
        // Example: "tomato:2:cups,garlic:3:cloves" becomes ["tomato:2:cups", "garlic:3:cloves"]
        String[] ingredients = ingredientList.split(",");

        // Build a nicely formatted string with each ingredient on its own line
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < ingredients.length; i++) {
            // Get one ingredient entry (e.g., "tomato:2:cups")
            String ingredient = ingredients[i].trim();

            // Split by colon to get the three parts: name, quantity, unit
            // Example: "tomato:2:cups" becomes ["tomato", "2", "cups"]
            String[] parts = ingredient.split(":");

            if (parts.length == 3) {
                // Extract the three parts
                String name = parts[0].trim();
                String quantity = parts[1].trim();
                String unit = parts[2].trim();

                // Capitalize the first letter of the ingredient name for display
                // "tomato" becomes "Tomato"
                name = name.substring(0, 1).toUpperCase() + name.substring(1);

                // Add this ingredient to our formatted string
                // Format: "Tomato (2 cups)"
                formatted.append(name).append(" (").append(quantity).append(" ").append(unit).append(")");

                // Add a line break after each ingredient, except the last one
                if (i < ingredients.length - 1) {
                    formatted.append("\n");
                }
            }
        }

        return formatted.toString();
    }

    /**
     * Set up click listeners for buttons in this activity.
     */
    private void setupClickListeners() {
        // When user clicks the back button, close this activity and return to the previous one
        btnBack.setOnClickListener(v -> {
            finish(); // Go back to the previous activity (SuggestedRecipesActivity)
        });
    }

    /**
     * Clean up database resources when the activity is destroyed.
     * It's good practice to close database connections when we're done.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database helper when activity is destroyed
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
