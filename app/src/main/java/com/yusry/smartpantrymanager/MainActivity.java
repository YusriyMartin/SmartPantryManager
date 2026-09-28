package com.yusry.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.adapters.IngredientAdapter;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.database.PreloadedRecipes;

/**
 * MainActivity - the main screen showing the list of ingredients in the pantry.
 * This is where users see all their ingredients and can add new ones.
 */
public class MainActivity extends AppCompatActivity {

    // RecyclerView for displaying the list of ingredients
    private RecyclerView recyclerView;

    // Adapter that connects ingredient data to the RecyclerView display
    private IngredientAdapter ingredientAdapter;

    // Database helper to manage SQLite connection
    private PantryDatabaseHelper databaseHelper;

    // DAO object to perform ingredient database operations (add, read, update, delete)
    private IngredientDAO ingredientDAO;

    // Button for adding new ingredients to the pantry
    private Button btnAddIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call parent's onCreate to initialize the activity properly
        super.onCreate(savedInstanceState);

        // Set the layout file (activity_main.xml) to display on screen
        setContentView(R.layout.activity_main);

        // Load 20 pre-made recipes into the database on app's first launch (only runs once)
        PreloadedRecipes.loadRecipesIntoDB(this);

        // Initialize the database helper - this gives us access to SQLite database
        databaseHelper = new PantryDatabaseHelper(this);

        // Initialize the DAO - pass the database HELPER (not the database itself)
        // The DAO will get the database connection from the helper when needed
        ingredientDAO = new IngredientDAO(databaseHelper);

        // Find the RecyclerView from the XML layout and store it in our recyclerView variable
        recyclerView = findViewById(R.id.recyclerView);

        // Create a LinearLayoutManager to display ingredients in a vertical list
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Attach the layout manager to the RecyclerView so it knows how to arrange items
        recyclerView.setLayoutManager(layoutManager);

        // Get all ingredients from the database and create an adapter to display them
        // Pass 3 parameters: ingredients list, the DAO (for delete operations), and this activity (context)
        ingredientAdapter = new IngredientAdapter(ingredientDAO.getAllIngredients(), ingredientDAO, this);

        // Attach the adapter to the RecyclerView - now the RecyclerView knows what to display
        recyclerView.setAdapter(ingredientAdapter);

        // Find the "Add Ingredient" button from the XML layout
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        // When user clicks "Add Ingredient" button, launch AddEditIngredientActivity
        btnAddIngredient.setOnClickListener(v -> {
            // Create an Intent to navigate to AddEditIngredientActivity
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);

            // Put an extra value "ingredient_id" with -1 to signal this is adding a NEW ingredient (not editing)
            intent.putExtra("ingredient_id", -1);

            // Start the activity - this opens the Add Ingredient screen
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        // Call parent's onResume to handle activity lifecycle properly
        super.onResume();

        // Refresh the ingredient list when user returns to this screen (after adding/editing)
        // This ensures we see any new ingredients that were added
        ingredientAdapter = new IngredientAdapter(ingredientDAO.getAllIngredients(), ingredientDAO, this);

        // Update the adapter so RecyclerView shows the refreshed data
        recyclerView.setAdapter(ingredientAdapter);
    }

}
