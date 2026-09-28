package com.yusry.smartpantrymanager.models;
// A single ingredient in the pantry
public class Ingredient {
    private int id; // Unique ID for ingredient in db
    private String name; //Ingredient name
    private double quantity; // How many of each ingredient
    private String unit; // The unit of measure
    private String dateAdded; // The date ingredient was added

    // Empty constructor to create new ingredient
    public Ingredient() {
    }

    // Constructor with all parameters for all ingreient details
    public Ingredient(int id, String name, double quantity, String unit, String dateAdded) {
        // Store the unique Id, ingredient name, qty, unit, and date
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.dateAdded = dateAdded;
    }

    // Constructor without Id. Adding new ingredient that does not have id yet
    public Ingredient(String name, double quantity, String unit, String dateAdded) {
        // Stores the ingredient name, qty, unit, and date
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.dateAdded = dateAdded;
    }

    // Declaring getter methods
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getQuantity() {
        return quantity;
    }
    public String getUnit() {
        return unit;
    }
    public String getDateAdded() {
        return dateAdded;
    }

    // Declaring setter methods
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    // Convert ingredient to text format
    @Override
    public String toString() {
        return name + " (" + quantity + " " + unit + ")";
    }
}
