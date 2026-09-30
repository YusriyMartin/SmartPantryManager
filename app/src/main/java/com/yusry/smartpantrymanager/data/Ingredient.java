package com.yusry.smartpantrymanager.data;

// Importing resources
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Use Room to create ingredients table
@Entity(tableName = "ingredients")
public class Ingredient {
    @PrimaryKey(autoGenerate = true) public int id;

    public String name;
    public double quantity;

    public String unit;
    public String expiryDate;

    public Ingredient(String name, double quantity, String unit, String expiryDate) {
        // normalize ingredient name to match strict-matching logic (handle plurals, case sensitivity)
        this.name = normalize(name);
        this.quantity = quantity;
        // unit stored lowercase to avoid "Kg" vs "kg" mismatches
        this.unit = unit.toLowerCase().trim();
        this.expiryDate = expiryDate;
    }

    // strip whitespace, convert to lowercase, strip plurals (tomatoes → tomato, carrots → carrot)
    // ensures recipe matching logic can find ingredients regardless of how user typed them
    public static String normalize(String s){
        if(s==null) return " ";
        s = s.toLowerCase().trim();

        // handle -oes suffix (tomatoes, potatoes)
        if(s.endsWith("oes")) return s.substring(0, s.length()-2);

        // strip trailing -s if not already plural (ss) and not too short
        if(s.endsWith("s") && !s.endsWith("ss") && s.length()>2) return s.substring(0, s.length()-1);

        return s;
    }
}
