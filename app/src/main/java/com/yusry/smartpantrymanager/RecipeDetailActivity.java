package com.yusry.smartpantrymanager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.database.RecipeDAO;
import com.yusry.smartpantrymanager.models.Recipes;

// Display deyails of a (1) recipe
public class RecipeDetailActivity extends AppCompatActivity {
    // UI components for displaying recipe details
    private Toolbar toolbar;
    private TextView tvRecipeName;
    private TextView tvIngredients;
    private TextView tvMethod;
    private Button btnBack;
    private PantryDatabaseHelper dbHelper; //db helper to access database
    private RecipeDAO recipeDAO; // db access object to fetch recipe details
    private int recipeId; //Recipe Id passed from previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail); // Set layout
        initializeUI(); // Initialize UI components
        setupDatabase(); // Set up db
        setupToolbar(); // Set up toolbar
        getRecipeIdFromIntent(); // Get recipe id from intent that launched activity
        loadRecipeDetails(); // load and display recipe detailss
        setupClickListeners(); // Set up button click listeners
    }
    // Find UI components, store as class variables
    private void initializeUI() {
        toolbar = findViewById(R.id.toolbar);
        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvMethod = findViewById(R.id.tvMethod);
        btnBack = findViewById(R.id.btnBack);
    }
    // Create database helper and RecipeDAO (Data Access Object) to fetch recipe details
    private void setupDatabase() {
        dbHelper = new PantryDatabaseHelper(this); // Create db helper
        recipeDAO = new RecipeDAO(this);  // Create RecipeDAO using context, fecth recipes from db
    }
    // Configure toolbar with title and back button
    private void setupToolbar() {
        // Set Recipe Details as title in toolbar
        toolbar.setTitle("Recipe Details");
        // Enable the back/up button in the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=  null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    // Get the recipe id from intent that launched this activity
    private void getRecipeIdFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipeId = extras.getInt("recipe_id", 0); // Get recip_id value
        }
    }
    // Get the recipe from db and show it's details
    private void loadRecipeDetails() {
        Recipes recipe = recipeDAO.getRecipeById(recipeId); // Fetch recip from db using id
        if (recipe != null) {
            tvRecipeName.setText(recipe.getName()); // Display recipe name in textview
            String ingredientsText = formatIngredientList(recipe.getIngredientList()); // Display ingredients
            tvIngredients.setText(ingredientsText);
            tvMethod.setText(recipe.getMethod()); // Display cooking method
        } else {
            // Show message if recipe not found
            tvRecipeName.setText("Oops, we could not find this recipe.");
            tvIngredients.setText("Oops, it looks like there's no ingredients here. ");
            tvMethod.setText("");
        }
    }
    // Formatting ingredient list to display nicely
    private String formatIngredientList(String ingredientList) {
        // If the ingredient list is empty or null, return a placeholder message
        if (ingredientList == null || ingredientList.isEmpty()) {
            return "No ingredients listed";
        }
        // Splitting the ingredients, so it can be treated individually
        String[] ingredients = ingredientList.split(",");
        // Format string with each ingredient on its own line
        StringBuilder formatted = new StringBuilder();

        // For loop to get ingredient
        for (int i = 0; i < ingredients.length; i++) {
            String ingredient = ingredients[i].trim();
            // Splitting into ingredient name, quantity, unit
            String[] parts = ingredient.split(":");

            if (parts.length == 3) {
                // Extract name, qty, unit
                String name=parts[0].trim();
                String quantity=parts[1].trim();
                String unit=parts[2].trim();
                // Capitalize first letter of ingredient (note: not necessary can remove)
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                // Adds ingredient to formatted string
                formatted.append(name).append(" (").append(quantity).append(" ").append(unit).append(")");
                // Add a line break after each ingredient
                if (i < ingredients.length - 1) {
                    formatted.append("\n");
                }
            }
        }
        return formatted.toString();
    }
    // Set click listeners for buttons
    private void setupClickListeners() {
        // Close this activity and return to the previous onclick
        btnBack.setOnClickListener(v -> {
            finish(); // Go back to SuggestedRecipesActivity
        });
    }
    // Clean db when activity destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database helper
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
