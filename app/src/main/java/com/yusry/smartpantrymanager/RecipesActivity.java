package com.yusry.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.data.*;
import java.util.*;
import java.util.concurrent.Executors;

public class RecipesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_recipes);

        RecyclerView rv = findViewById(R.id.rvRecipes);
        TextView txtNo = findViewById(R.id.txtNoMatch);
        rv.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btnBack).setOnClickListener(v-> finish());

        // run DB work in background
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDao dao = AppDatabase.getInstance(this).dao();

            // Fetch pantry
            List<Ingredient> pantry = dao.getAllIngredients();
            android.util.Log.d("DEBUG_PANTRY", "Pantry items loaded: " + pantry.size()); // Confirmation to check if oantry loaded
            List<Recipe> all = dao.getAllRecipes();
            List<Recipe> matched = new ArrayList<>();

            // normalize pantry names for matching
            Set<String> pantryNames = new HashSet<>();
            for(Ingredient ing: pantry) {
                pantryNames.add(Ingredient.normalize(ing.name));
            }

            // check each recipe against pantry
            for(Recipe r: all){
                if(isStrict(r.requiredNames, pantryNames)){
                    matched.add(r); // ✅ only add if all ingredients present
                }
            }

            runOnUiThread(() -> {
                // show debug info in log/toast
                android.util.Log.d("DEBUG_PANTRY",
                        "Pantry: " + pantryNames + " | All recipes: " + all.size() + " | Matched: " + matched.size());
                android.widget.Toast.makeText(this,
                        "Pantry:"+pantryNames.size()+" All:"+all.size()+" Matched:"+matched.size(),
                        android.widget.Toast.LENGTH_LONG).show();

                // ✅ show matched recipes only
                if(matched.isEmpty()){
                    txtNo.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                } else {
                    txtNo.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    rv.setAdapter(new RecipeAdapter(this, matched));
                }
            });
        });
    }

    // strict matching → all required names must be in pantry
    private boolean isStrict(String requiredNames, Set<String> pantryNames){
        if(requiredNames == null || requiredNames.isEmpty()) return false;
        for(String raw : requiredNames.split(",")){
            String reqName = Ingredient.normalize(raw.trim());
            if(reqName.isEmpty()) continue;
            if(!pantryNames.contains(reqName)) {
                android.util.Log.d("DEBUG_MATCH", "Missing ingredient: " + reqName);
                return false;
            }
        }
        return true;
    }

}
