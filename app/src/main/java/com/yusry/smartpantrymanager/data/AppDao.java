package com.yusry.smartpantrymanager.data;
import androidx.room.*;
import java.util.List;
@Dao
public interface AppDao {
    @Query("SELECT * FROM ingredients") List<Ingredient> getAllIngredients();
    @Query("SELECT * FROM ingredients WHERE id = :id LIMIT 1") Ingredient getIngredientById(int id);
    @Insert void insertIngredient(Ingredient i);
    @Update void updateIngredient(Ingredient i);
    @Delete void deleteIngredient(Ingredient i);
    @Query("SELECT * FROM recipes") List<Recipe> getAllRecipes();
    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1") Recipe getRecipeById(int id);
    @Insert void insertRecipe(Recipe r);
}