package com.yusry.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.models.Ingredient;
import com.yusry.smartpantrymanager.adapters.IngredientAdapter;
import com.yusry.smartpantrymanager.AddEditIngredientActivity;
import com.yusry.smartpantrymanager.SuggestedRecipesActivity;
import com.yusry.smartpantrymanager.SettingsActivity;
import com.yusry.smartpantrymanager.database.PreloadedRecipes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // preload recipes on first launch
        PreloadedRecipes.loadRecipesIntoDB(this);

        // init db & daos
        PantryDatabaseHelper dbHelper = new PantryDatabaseHelper(this);
        IngredientDAO ingredientDAO = new IngredientDAO(dbHelper);

        // init recyclerview & adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Ingredient> ingredientList = ingredientDAO.getAllIngredients();
        IngredientAdapter adapter = new IngredientAdapter(ingredientList, ingredientDAO, this);
        recyclerView.setAdapter(adapter);

        // add ingredient button
        Button btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });

        // bottom navigation setup - wire tabs to activities
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {
                // already on pantry, do nothing
                return true;
            } else if (itemId == R.id.nav_recipes) {
                // launch suggested recipes activity
                startActivity(new Intent(MainActivity.this, SuggestedRecipesActivity.class));
                return true;
            } else if (itemId == R.id.nav_settings) {
                // launch settings activity
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }
}
