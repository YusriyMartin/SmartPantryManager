package com.yusry.smartpantrymanager;
// Import resources
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.yusry.smartpantrymanager.data.*;
import java.util.*;
import java.util.concurrent.Executors;
// Class for Recipe Details
public class RecipeDetailActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_detail);
        // Gets id from intent
        int id = getIntent().getIntExtra("recipe_id", -1);
        // Binds UI elements
        TextView name = findViewById(R.id.txtDetailName);
        TextView ing = findViewById(R.id.txtDetailIngredients);
        TextView method = findViewById(R.id.txtDetailMethod);
        TextView result = findViewById(R.id.txtCheckResult);
        Button btnCheck = findViewById(R.id.btnCheckPantry);
        // Back button to close
        findViewById(R.id.btnBackRecipes).setOnClickListener(v-> finish());
        // Loads recipe async to not block interface
        Executors.newSingleThreadExecutor().execute(()->{
            AppDatabase db = AppDatabase.getInstance(this);
            Recipe r = db.dao().getRecipeById(id);
            List<Ingredient> pantry = db.dao().getAllIngredients();
            // Updates UI on main thread
            runOnUiThread(()->{
                if(r!= null){
                    name.setText(r.name);
                    ing.setText(r.ingredientsDisplay);
                    method.setText(r.method);
                    // Checks pantry buttin listener
                    btnCheck.setOnClickListener(v -> {
                        String check = checkPantry(r.ingredientsDisplay, pantry);
                        result.setText(check);
                    });
                }
            });
        });
    }

    // Compare recipe ingredients and pantry list
    private String checkPantry(String recipeIngs, List<Ingredient> pantry) {

        // Map checks for name and quantity
        Map<String, Double> pantryMap = new HashMap<>();
        Map<String, String> pantryUnit = new HashMap<>();

        // for loop over ingredient and check for match. Trim
        for(Ingredient p : pantry){
            if(p.name == null || p.name.isEmpty()) continue;
            String key = p.name.toLowerCase().trim();
            pantryMap.put(key, p.quantity);
            if(p.unit!= null) pantryUnit.put(key,
                    p.unit.toLowerCase().trim());
        }

        StringBuilder sb = new StringBuilder();
        boolean allOk = true;

        if(recipeIngs == null || recipeIngs.isEmpty()){
            return "Hey chef! No ingredients listed for this recipe.";
        }
        // Parses recipe ingredients
        String[] items = recipeIngs.split(",");
        for(String item : items){
            try{
                // Split ingredient to get actual
                String[] parts = item.split(":");
                if(parts.length < 2) continue;
                String needName = parts[0].toLowerCase().trim();
                String needQtyUnit = parts[1].trim();
                // Get quantity from ingredient string and trim
                String[] qU = needQtyUnit.split(" ");
                double needQty = Double.parseDouble(qU[0].trim());
                String needUnit = qU[1].toLowerCase().trim();
                // check if ingredient exists in pantry - validation
                if(!pantryMap.containsKey(needName)){
                    sb.append("This is missing: ").append(needName).append("\n");
                    allOk = false;
                    continue;
                }
                double haveQty = pantryMap.get(needName);
                String haveUnit = pantryUnit.get(needName);
                if(haveUnit == null) haveUnit = needUnit;
                // Converts to grams if other weight
                double haveInGrams = toGrams(haveQty, haveUnit);
                double needInGrams = toGrams(needQty, needUnit);
                if(isWeight(needUnit) && isWeight(haveUnit)){
                    // Compare weights - for partial macth
                    if(haveInGrams < needInGrams - 0.001){
                        sb.append("More needed: ").
                                append(needName).append(": have ")
                                .append(haveQty).append(" ")
                                .append(haveUnit)
                                .append(" need ").append(needQty)
                                .append(" ")
                                .append(needUnit).append("\n");
                        allOk = false;
                    }
                } else {
                    // Non weight units must match
                    if(!haveUnit.equals(needUnit) || haveQty < needQty){
                        sb.append("More needed: ")
                                .append(needName)
                                .append(": have ")
                                .append(haveQty).append(" ")
                                .append(haveUnit)
                                .append(" need ")
                                .append(needQty)
                                .append(" ")
                                .append(needUnit).append("\n");
                        allOk = false;
                    }
                }
            } catch(Exception e){ }
        }
        if(allOk) return "Hey chef! You have all ingredients, let's cookc.";
        else return sb.toString();
    }
    // Converts quantity to grams - for strcit macth requirements
    private double toGrams(double qty, String unit){
        if(unit == null) return qty;
        switch(unit){
            case "kg": return qty * 1000.0;
            case "g": return qty;
            case "lbs": case "lb": case "lbs.": return qty * 453.6;
            default: return qty;
        }
    }
    // Check if kg or other wieght , not piece
    private boolean isWeight(String u){
        if(u == null) return false;
        return u.equals("kg") || u.equals("g") || u.equals("lbs") || u.equals("lb");
    }
}
