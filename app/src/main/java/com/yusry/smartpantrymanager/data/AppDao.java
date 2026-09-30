package com.yusry.smartpantrymanager.data;

import androidx.room.*;
import java.util.List;
@Dao
public interface AppDao {
    // Gets ingredients from db to use for pantry list
    @Query("SELECT * FROM ingredients")
    List<Ingredient> getAllIngredients();
    // Select id to load data
    @Query("SELECT * FROM ingredients WHERE id = :id LIMIT 1")
    Ingredient getIngredientById(int id);
    // Add user ingredient to db
    @Insert
    void insertIngredient(Ingredient i);
    // Updates ingredient a part of Room CRUD
    @Update
    void updateIngredient(Ingredient i);
    // Deletes ingredient from db
    @Delete
    void deleteIngredient(Ingredient i);
    // Selects all recipes. To check for matches with ingredients
    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();
    // Select one recipe using id
    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    Recipe getRecipeById(int id);
    // Insert new recipe into the db
    @Insert
    void insertRecipe(Recipe r);
}
