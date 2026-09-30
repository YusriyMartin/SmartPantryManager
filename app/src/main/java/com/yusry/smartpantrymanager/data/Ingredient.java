package com.yusry.smartpantrymanager.data;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "ingredients")
public class Ingredient {
    @PrimaryKey(autoGenerate = true) public int id;
    public String name;
    public double quantity;
    public String unit;
    public String expiryDate;

    public Ingredient(String name, double quantity, String unit, String expiryDate) {
        this.name = normalize(name);
        this.quantity = quantity;
        this.unit = unit.toLowerCase().trim();
        this.expiryDate = expiryDate;
    }

    public static String normalize(String s){
        if(s==null) return "";
        s = s.toLowerCase().trim();
        if(s.endsWith("oes")) return s.substring(0, s.length()-2); // tomatoes -> tomato
        if(s.endsWith("s") && !s.endsWith("ss") && s.length()>2) return s.substring(0, s.length()-1);
        return s;
    }
}