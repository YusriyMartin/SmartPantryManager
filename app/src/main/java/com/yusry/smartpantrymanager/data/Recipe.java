package com.yusry.smartpantrymanager.data;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)

    public int id;

    public String name;
    public String ingredientsDisplay;
    public String requiredNames;
    public String method;
    public Recipe(String name, String ingredientsDisplay, String requiredNames, String method) {
        this.name = name;
        this.ingredientsDisplay = ingredientsDisplay;
        this.requiredNames = requiredNames.toLowerCase(); // comma list for strict containsAll
        this.method = method;
    }
}