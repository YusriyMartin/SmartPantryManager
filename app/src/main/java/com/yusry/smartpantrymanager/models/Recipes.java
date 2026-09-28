package com.yusry.smartpantrymanager.models;

/**
 * The Recipe class represents a single recipe that the user can prepare.
 * Each recipe has an ID, name, description, a list of required ingredients, and cooking method.
 * This model is the foundation for storing and managing recipes in our database.
 */
public class Recipes {

    // We store the recipe's unique identifier so we can find it in the database later
    private int id;

    // The name of the recipe (e.g., "Pasta Carbonara", "Caesar Salad")
    private String name;

    // A detailed description of what the recipe is
    private String description;

    // This stores the ingredients needed as a comma-separated string
    // For example: "tomato:2:cups,garlic:3:cloves,olive oil:2:tablespoons"
    // Format is: ingredientName:quantity:unit, ingredientName:quantity:unit, etc.
    private String ingredientList;

    // The cooking method/instructions for preparing this recipe
    // For example: "1. Heat olive oil in a pan\n2. Add garlic and fry until golden\n3. Add tomatoes..."
    private String method;

    /**
     * Empty constructor - needed by Android and SQLite to create Recipe objects
     * without requiring any parameters at first
     */
    public Recipes() {
    }

    /**
     * Full constructor - allows us to create a Recipe with all its details at once
     * This is useful when loading recipes from the database
     */
    public Recipes(int id, String name, String description, String ingredientList, String method) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
        this.method = method;
    }

    /**
     * Constructor without ID - used when adding a NEW recipe (database will auto-generate ID)
     */
    public Recipes(String name, String description, String ingredientList, String method) {
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
        this.method = method;
    }

    // ========== GETTERS & SETTERS ==========
    // These methods allow other parts of the app to read and modify recipe data

    /**
     * Get the recipe's unique ID from the database
     */
    public int getId() {
        return id;
    }

    /**
     * Set the recipe's ID (usually done automatically by the database)
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name of the recipe
     */
    public String getName() {
        return name;
    }

    /**
     * Set or update the recipe's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the full description of the recipe
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set or update the recipe's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the comma-separated ingredient list as a string
     * Format: "ingredientName:quantity:unit,ingredientName:quantity:unit"
     */
    public String getIngredientList() {
        return ingredientList;
    }

    /**
     * Set or update the ingredient list
     */
    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * Get the cooking method/instructions for this recipe
     */
    public String getMethod() {
        return method;
    }

    /**
     * Set or update the cooking method/instructions
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * A helpful toString method for debugging - shows us what the recipe looks like when printed
     */
    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredientList='" + ingredientList + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
