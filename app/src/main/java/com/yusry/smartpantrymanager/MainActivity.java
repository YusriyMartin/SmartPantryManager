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

// Main screen shows list
public class MainActivity extends AppCompatActivity {

    // Declare RecyclerView
    private RecyclerView recyclerView;
    // Adapter that connects ingredient data to the RecyclerView display
    private IngredientAdapter ingredientAdapter;
    // Database helper to manage db conncetion
    private PantryDatabaseHelper databaseHelper;
    // Creating DAO (Data Access Object) for CRUD operations
    private IngredientDAO ingredientDAO;
    private Button btnAddIngredient; // Declare button for adding new ingredients to pantry

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState); // Call parent onCreate

        setContentView(R.layout.activity_main); // Sets layout file to display screen
        PreloadedRecipes.loadRecipesIntoDB(this); // Loads preloaded recipes

        databaseHelper = new PantryDatabaseHelper(this); // Initialize db helper to access db

        ingredientDAO = new IngredientDAO(databaseHelper); // Initialize DAO to pass the database helper

        recyclerView = findViewById(R.id.recyclerView); // Find RecyclerView, store in recycleview variable

        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // Create LinearLayoutManager to display ingredients in vertical list

        recyclerView.setLayoutManager(layoutManager); //Attach layout manager to RecyclerView

        ingredientAdapter = new IngredientAdapter(ingredientDAO.getAllIngredients(), ingredientDAO, this); // Get all ingredients from db and create adapter to display it

        recyclerView.setAdapter(ingredientAdapter); // Attach adapter to RecycleView

        btnAddIngredient = findViewById(R.id.btnAddIngredient); // Find Add Ingredient button

        // Launch AddEditIngredientActivity when add ingredient is clicked
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class); // Create intent to navigate to AddEditIngredientActivity

            intent.putExtra("ingredient_id", -1); // Add extra ingredient_id, with -1 to show that adding new ingredient and not editing

            startActivity(intent); // Start Activtiy
        });
    }
    @Override
    protected void onResume() {
        super.onResume(); // Call parent onResume
        //  Refresh ingredient list after editing or adding ingredient
        ingredientAdapter = new IngredientAdapter(ingredientDAO.getAllIngredients(), ingredientDAO, this);
        // Update the adapter so RecyclerView shows the refreshed data
        recyclerView.setAdapter(ingredientAdapter);
    }

}
