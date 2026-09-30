package com.yusry.smartpantrymanager;

// Import resources
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.data.*;
import java.util.*;
import java.util.concurrent.Executors;

// Class for Recipe Activity
public class RecipesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        RecyclerView rv = findViewById(R.id.rvRecipes);
        rv.setLayoutManager(new LinearLayoutManager(this));
        // load all recipes, filter by strict matching
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Recipe> allRecipes = db.dao().getAllRecipes();
            List<Ingredient> pantry = db.dao().getAllIngredients();
            // For loop for iterating over available recips
            List<Recipe> possible = new ArrayList<>();
            for (int i = 0; i < allRecipes.size(); i++) {
                Recipe r = allRecipes.get(i);
                // CheckPantry returns full
                String result = RecipeUtils.checkPantry(r.ingredientsDisplay, pantry);
                // if nothing found then display
                if (!result.contains("It's still empty!")) {
                    possible.add(r);
                }
            }
        });
    }
}
