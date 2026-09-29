package com.yusry.smartpantrymanager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.yusry.smartpantrymanager.data.*;
import java.util.concurrent.Executors;
public class RecipeDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_detail);
        int id=getIntent().getIntExtra("recipeId",-1);
        TextView name=findViewById(R.id.txtDetailName);
        TextView ing=findViewById(R.id.txtDetailIngredients);
        TextView method=findViewById(R.id.txtDetailMethod);
        findViewById(R.id.btnBackRecipes).setOnClickListener(v-> finish());
        Executors.newSingleThreadExecutor().execute(()->{
            Recipe r=AppDatabase.getInstance(this).dao().getRecipeById(id);
            runOnUiThread(()->{ name.setText(r.name);ing.setText(r.ingredientsDisplay); method.setText(r.method); });
        });
    }
}