package com.yusry.smartpantrymanager.data;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "ingredients")
public class Ingredient {
    @PrimaryKey(autoGenerate = true) public int id;
    public String name; public double quantity; public String unit; public String expiryDate;
    public Ingredient(String name, double quantity, String unit, String expiryDate) {
        this.name = name.toLowerCase().trim(); // lower for strict matching
        this.quantity = quantity; this.unit = unit; this.expiryDate = expiryDate;
    }
}