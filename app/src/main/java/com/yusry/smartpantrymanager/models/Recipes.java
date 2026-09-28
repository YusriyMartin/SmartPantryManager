package com.yusry.smartpantrymanager.models;

// Creating a recipe class
public class Recipes {

    private int id; // We store recipe's id, to find in db
    private String name; // Recipe name
    private String description; // Recipe desciption
    private String ingredientList; // Stores ingredient in string format
    private String method; // Cooking method for recipes

    // Empty constructor, to create recipe object
    public Recipes() {
    }

    // Full constrcutor, allow recipe to be creates with datails
    public Recipes(int id, String name, String description, String ingredientList, String method) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
        this.method = method;
    }

    // Constructor without ID used when adding new recipe
    public Recipes(String name, String description, String ingredientList, String method) {
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
        this.method = method;
    }

    // Declaring Getter methods
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    // Set/Update recipe description
    public void setDescription(String description) {
        this.description = description;
    }
    // Get the ingredients as a string
    public String getIngredientList() {
        return ingredientList;
    }
    // Set/Update ingredients
    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }
    // Get method
    public String getMethod() {
        return method;
    }
    // Set/Update instructions
    public void setMethod(String method) {
        this.method = method;
    }
    // toString method - to debug
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
