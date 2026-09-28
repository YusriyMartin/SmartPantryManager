package com.yusry.smartpantrymanager.models;

/**
 * Ingredient - represents a single ingredient in the pantry.
 * This is like a template/blueprint for what an ingredient should have: name, quantity, unit, date.
 */
public class Ingredient {

    // The unique ID for this ingredient in the database (auto-generated)
    private int id;

    // The name of the ingredient (e.g., "Tomato", "Banana")
    private String name;

    // How much of this ingredient we have (e.g., 2, 5, 0.5)
    private double quantity;

    // The unit of measurement (e.g., "kg", "grams", "liters")
    private String unit;

    // The date this ingredient was added to the pantry
    private String dateAdded;

    /**
     * Constructor 1 - Empty constructor (no parameters).
     * Used when creating a new ingredient before we know all the details.
     */
    public Ingredient() {
    }

    /**
     * Constructor 2 - Full constructor with all parameters.
     * Used when we already have all the ingredient details.
     */
    public Ingredient(int id, String name, double quantity, String unit, String dateAdded) {
        // Store the unique ID for this ingredient
        this.id = id;
        // Store the ingredient name
        this.name = name;
        // Store how much we have
        this.quantity = quantity;
        // Store the unit of measurement
        this.unit = unit;
        // Store when it was added
        this.dateAdded = dateAdded;
    }

    /**
     * Constructor 3 - constructor without ID (for new ingredients).
     * Used when adding a new ingredient that doesn't have an ID yet.
     */
    public Ingredient(String name, double quantity, String unit, String dateAdded) {
        // Store the ingredient name
        this.name = name;
        // Store how much we have
        this.quantity = quantity;
        // Store the unit of measurement
        this.unit = unit;
        // Store when it was added
        this.dateAdded = dateAdded;
    }

    // GETTER METHODS - these allow us to read the ingredient data

    /**
     * getId - returns the unique ID of this ingredient.
     */
    public int getId() {
        return id;
    }

    /**
     * getName - returns the name of this ingredient (e.g., "Tomato").
     */
    public String getName() {
        return name;
    }

    /**
     * getQuantity - returns how much of this ingredient we have.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * getUnit - returns the unit of measurement (e.g., "kg").
     */
    public String getUnit() {
        return unit;
    }

    /**
     * getDateAdded - returns when this ingredient was added to pantry.
     */
    public String getDateAdded() {
        return dateAdded;
    }

    // SETTER METHODS - these allow us to change the ingredient data

    /**
     * setId - sets the unique ID of this ingredient.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setName - sets the name of this ingredient (e.g., "Tomato").
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setQuantity - sets how much of this ingredient we have.
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * setUnit - sets the unit of measurement (e.g., "kg").
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * setDateAdded - sets when this ingredient was added to pantry.
     */
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * toString - converts the ingredient to a readable text format.
     * Useful for debugging or printing.
     */
    @Override
    public String toString() {
        return name + " (" + quantity + " " + unit + ")";
    }
}
