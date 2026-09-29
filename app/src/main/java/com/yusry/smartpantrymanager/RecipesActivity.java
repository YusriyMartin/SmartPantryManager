package com.yusry.smartpantrymanager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.data.*;
import java.util.*; import java.util.concurrent.Executors;
public class RecipesActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_recipes);
        RecyclerView rv=findViewById(R.id.rvRecipes); TextView txtNo=findViewById(R.id.txtNoMatch);
        rv.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btnBack).setOnClickListener(v-> finish());
        Executors.newSingleThreadExecutor().execute(()->{
            AppDao dao=AppDatabase.getInstance(this).dao();
            List<Ingredient> pantry=dao.getAllIngredients();
            List<String> pantryNames=new ArrayList<>();
            for(Ingredient ing: pantry) pantryNames.add(ing.name.toLowerCase().trim());
            List<Recipe> all=dao.getAllRecipes(); List<Recipe> matched=new ArrayList<>();
            for(Recipe r: all){
                List<String> needed=Arrays.asList(r.requiredNames.toLowerCase().split(","));
                List<String> trimmed=new ArrayList<>(); for(String s: needed) trimmed.add(s.trim());
                if(pantryNames.containsAll(trimmed)) matched.add(r); // strict mode
            }
            runOnUiThread(()->{
                if(matched.isEmpty()){ txtNo.setVisibility(View.VISIBLE); rv.setVisibility(View.GONE); }
                else { txtNo.setVisibility(View.GONE); rv.setVisibility(View.VISIBLE); rv.setAdapter(new RecipeAdapter(this, matched)); }
            });
        });
    }
}