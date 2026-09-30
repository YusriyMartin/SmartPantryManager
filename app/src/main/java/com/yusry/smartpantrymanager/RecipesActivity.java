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
    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_recipes);
        RecyclerView rv  =findViewById(R.id.rvRecipes);
        TextView txtNo = findViewById(R.id.txtNoMatch);
        rv.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btnBack).setOnClickListener(v-> finish());

        Executors.newSingleThreadExecutor().execute(()->{
            AppDao dao = AppDatabase.getInstance(this).dao();
            List<Ingredient> pantry = dao.getAllIngredients();
            List<Recipe> all = dao.getAllRecipes();
            List<Recipe> matched=new ArrayList<>();

            // Build normalized pantry set + keep full objects for qty check
            Set<String> pantryNames = new HashSet<>();
            for(Ingredient ing: pantry) {
                pantryNames.add(Ingredient.normalize(ing.name));
            }

            for(Recipe r: all){
                if(isStrict(r.requiredNames, pantry, pantryNames)){
                    matched.add(r);
                }
            }

            runOnUiThread(()->{
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

    // 2.3 STRICT-MATCHING CORE LOGIC
    private boolean isStrict(String requiredNames, List<Ingredient> pantryList, Set<String> pantryNames){
        if(requiredNames==null || requiredNames.isEmpty())
            return false;
        String[] neededRaw = requiredNames.split(",");

        for(String raw : neededRaw){
            String req = raw.trim();
            if(req.isEmpty()) continue;

            // Parse "name:500 g" or just "name"
            String reqName;
            Double reqQty = null;
            String reqUnit = null;

            if(req.contains(":")){
                String[] parts = req.split(":",2);
                reqName = Ingredient.normalize(parts[0]);
                String[] qParts = parts[1].trim().split("\\s+");
                try{ reqQty = Double.parseDouble(qParts[0]);
                    if(qParts.length>1) reqUnit = qParts[1].toLowerCase().trim();
                }catch(Exception e){ reqQty=null; }
            } else {
                reqName = Ingredient.normalize(req);
            }

            // 1. Strict name check (handles tomato/tomatoes)
            if(!pantryNames.contains(reqName)) return false;

            // 2. If recipe specifies qty + unit, check qty
            if(reqQty!=null && reqUnit!=null){
                boolean enough = false;
                for(Ingredient p : pantryList){
                    if(Ingredient.normalize(p.name).equals(reqName)){
                        if(UnitConverter.canCompare(p.unit, reqUnit)){
                            double have = UnitConverter.isWeight(p.unit)?
                                    UnitConverter.toGrams(p.quantity, p.unit) : p.quantity;
                            double need = UnitConverter.isWeight(reqUnit)?
                                    UnitConverter.toGrams(reqQty, reqUnit) : reqQty;
                            if(have >= need){ enough=true; break; }
                        }
                    }
                }
                if(!enough) return false; // If not enough qty, then don't suggest
            }
        }
        return true;
    }
}