package com.yusry.smartpantrymanager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.adapters.IngredientAdapter;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.models.Ingredient;
import com.yusry.smartpantrymanager.database.PreloadedRecipes;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngredientAdapter adapter;
    private IngredientDAO ingredientDAO;
    private List<Ingredient> ingredientList;
    private Button btnAddIngredient;
    private ImageButton btnEdit, btnAddAction, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load recipes on first launch
        PreloadedRecipes.loadRecipesIntoDB(this);

        // init views
        recyclerView = findViewById(R.id.recyclerView);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnEdit = findViewById(R.id.btnEdit);
        btnAddAction = findViewById(R.id.btnAddAction);
        btnDelete = findViewById(R.id.btnDelete);

        // init DAO FIRST, before adapter (adapter needs it)
        PantryDatabaseHelper dbHelper = new PantryDatabaseHelper(this);
        ingredientDAO = new IngredientDAO(dbHelper);

        // setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientList = new ArrayList<>();
        adapter = new IngredientAdapter(ingredientList, ingredientDAO, this);
        recyclerView.setAdapter(adapter);

        // load ingredients from db
        loadIngredients();

        // add ingredient button
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            intent.putExtra("ingredient_id", -1);
            startActivity(intent);
        });

        // pill action bar buttons
        btnAddAction.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            intent.putExtra("ingredient_id", -1);
            startActivity(intent);
        });

        btnEdit.setOnClickListener(v -> {
            // edit selected ingredient (can add selection logic later)
        });

        btnDelete.setOnClickListener(v -> {
            // delete selected ingredient (can add selection logic later)
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients(); // refresh list when coming back
    }

    private void loadIngredients() {
        ingredientList.clear();
        ingredientList.addAll(ingredientDAO.getAllIngredients());
        adapter.notifyDataSetChanged();
    }
}
