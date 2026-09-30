package com.yusry.smartpantrymanager.data;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// recipe table in room database
@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    // increment id for each recipe
    public int id;
    // get recipe name
    public String name;
    // Displays ingredients
    public String ingredientsDisplay;
    // Lowercase trim. ingredient name for strict matching later
    public String requiredNames;
    // Cooking instructions
    public String method;
    // Constructor that takes name and dispalys other info. Name as key
    public Recipe(String name, String ingredientsDisplay, String requiredNames, String method) {
        this.name = name;
        this.ingredientsDisplay = ingredientsDisplay;
        // convert to lowercase so matching
        this.requiredNames = requiredNames.toLowerCase();
        this.method = method;
    }
}
