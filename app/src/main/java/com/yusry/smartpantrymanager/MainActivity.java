package com.yusry.smartpantrymanager;

// Import resources
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import com.yusry.smartpantrymanager.data.*;
import java.util.List;
import java.util.concurrent.Executors;

// Main actvity for all functionality
public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    TextView txtEmpty;
    AppDao dao;
    IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        // Gets RecyclerView and empty state textview
        rv = findViewById(R.id.rvIngredients);
        txtEmpty = findViewById(R.id.txtEmpty);
        rv.setLayoutManager(new LinearLayoutManager(this));
        // Connects to database
        dao = AppDatabase.getInstance(this).dao();
        // Adapter empty, populates on load
        adapter = new IngredientAdapter(this, new java.util.ArrayList<>(), true);
        rv.setAdapter(adapter);
        // Add new ingredient
        findViewById(R.id.btnAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddEditActivity.class))
        );
        // Go to recipe screen - supposed to show matching recipes
        findViewById(R.id.btnRecipes).setOnClickListener(v ->
                startActivity(new Intent(this, RecipesActivity.class))
        );
        // Navigation to settings on click of setting icon
        findViewById(R.id.btnSettings).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class))
        );
        // bottom navigation recipe button take to recipe detail
        findViewById(R.id.btnRecipesBottom).setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", 1);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Refreshed list after editing
        load();
    }
    void load() {
        // Get from db main thread
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Ingredient> list = dao.getAllIngredients();
            SharedPreferences p = getSharedPreferences("settings", MODE_PRIVATE);
            // Check if user wants to see expiry dates
            boolean showExpiry = p.getBoolean("notify_expiry", true);
            // Updates user interface
            runOnUiThread(() -> {
                if (list.isEmpty()) {
                    // If empty display Chef message...
                    txtEmpty.setVisibility(TextView.VISIBLE);
                    rv.setVisibility(RecyclerView.GONE);
                } else {
                    // Add ingedient to RecycelerView
                    txtEmpty.setVisibility(TextView.GONE);
                    rv.setVisibility(RecyclerView.VISIBLE);
                    rv.setAdapter(new IngredientAdapter(this, list, showExpiry));
                }
            });
        });
    }
}

