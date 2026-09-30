package com.yusry.smartpantrymanager;
// Imports resources
import com.yusry.smartpantrymanager.data.Ingredient;
import java.util.*;
public class RecipeUtils {

    // Check if pantry has enough ingredents
    public static String checkPantry(String recipeIngs, List<Ingredient> pantry) {
        // Using lookup maps to check for name, qty, unit
        Map<String, Double> pantryMap = new HashMap<>();
        Map<String, String> pantryUnit = new HashMap<>();
        for (Ingredient p : pantry) {
            if (p.name == null) continue;
            String key = p.name.toLowerCase().trim();
            pantryMap.put(key, p.quantity);
            if (p.unit != null) pantryUnit.put(key, p.unit.toLowerCase().trim());
        }
        // Collect error message if anything missing
        StringBuilder sb = new StringBuilder();
        boolean allOk = true;
        // Validation, returns when no ingredients available
        if (recipeIngs == null || recipeIngs.isEmpty()) {
            return "No ingredients listed for this recipe.";
        }
        // Splits recipe string like
        String[] items = recipeIngs.split(",");
        for (String item : items) {
            try {
                // Parse name, qty, unit format
                String[] parts = item.split(":");
                if (parts.length < 2) continue;
                String needName = parts[0].toLowerCase().trim();
                String needQtyUnit = parts[1].trim();
                // Extract qty and unit
                String[] qU = needQtyUnit.split(" ");
                double needQty = Double.parseDouble(qU[0].trim());
                String needUnit = qU[1].toLowerCase().trim();
                // Check if ingredient exist in pantry
                if (!pantryMap.containsKey(needName)) {
                    sb.append("This is missing: ").append(needName).append("\n");
                    allOk = false;
                    continue;
                }
                // Grabs infor from pantry
                double haveQty = pantryMap.get(needName);
                String haveUnit = pantryUnit.get(needName);
                // Fallback to recipe unit if pantry unit not set
                if (haveUnit == null) haveUnit = needUnit;
                // Normalize weights
                double haveInGrams = toGrams(haveQty, haveUnit);
                double needInGrams = toGrams(needQty, needUnit);
                // If both are weights, compare grams handles wieghts
                if (isWeight(needUnit) && isWeight(haveUnit)) {
                    //0.001g buffer for floating point errors
                    if (haveInGrams < needInGrams - 0.001) {
                        sb.append("Need more: ").append(needName)
                                .append(": have ").append(haveQty).append(" ").append(haveUnit)
                                .append(" need ").append(needQty).append(" ").append(needUnit).append("\n");
                        allOk = false;
                    }
                } else {
                    // For non weight unit. need exact match
                    if (!haveUnit.equals(needUnit) || haveQty < needQty) {
                        sb.append("Need more: ").append(needName)
                                .append(": have ").append(haveQty).append(" ").append(haveUnit)
                                .append(" need ").append(needQty).append(" ").append(needUnit).append("\n");
                        allOk = false;
                    }
                }
            } catch (Exception e) {

            }
        }
        // Return the allOk
        if (allOk) return "Hey chef! You have all ingredients! You can cook this.";
        else return sb.toString();
    }
    // convert qty to grams for normalized comparison
    private static double toGrams(double qty, String unit) {
        if (unit == null) return qty;
        switch (unit) {
            case "kg": return qty * 1000.0;
            case "g": return qty;
            case "lbs": case "lb": case "lbs.": return qty * 453.6;
            default: return qty;
        }
    }
    // Check if unit is a weight or not
    private static boolean isWeight(String u) {
        if (u == null) return false;
        return u.equals("kg") || u.equals("g") || u.equals("lbs") || u.equals("lb");
    }
}
