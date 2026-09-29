package com.yusry.smartpantrymanager.database;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.ArrayList;
import java.util.List;

// handle db CRUD operations for recipes
public class RecipeDAO {
    private PantryDatabaseHelper dbHelper;

    // constructor takes db helper instance
    public RecipeDAO(PantryDatabaseHelper helper) {
        dbHelper = helper;
    }

    // add new recipe to db
    public long addRecipe(Recipes recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

            values.put("recipe_name", recipe.getName());
            values.put("description", recipe.getDescription());
            values.put("ingredient_list", recipe.getIngredientList());
            values.put("instructions", recipe.getMethod());

        long recipeId = db.insert("recipes", null, values);
        db.close();
        return recipeId;
    }

    // Get all recipes from db
    public List<Recipes> getAllRecipes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Recipes> recipesList = new ArrayList<>();
        Cursor cursor = db.query("recipes", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("recipe_id");
                int nameIndex = cursor.getColumnIndex("recipe_name");
                int descriptionIndex = cursor.getColumnIndex("description");
                int ingredientListIndex = cursor.getColumnIndex("ingredient_list");
                int methodIndex = cursor.getColumnIndex("instructions"); // FIXED: was "method"
                int id = cursor.getInt(idIndex);

                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descriptionIndex);
                String ingredientList = cursor.getString(ingredientListIndex);
                String method = cursor.getString(methodIndex);

                Recipes recipe = new Recipes(id, name, description, ingredientList, method);
                recipesList.add(recipe);

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return recipesList;
    }

    // get single recipe by id
    public Recipes getRecipeById(int recipeId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "recipe_id = ?";
        String[] selectionArgs = {String.valueOf(recipeId)};

        Cursor cursor = db.query("recipes", null, selection, selectionArgs, null, null, null);
        Recipes recipe = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("recipe_id");
            int nameIndex = cursor.getColumnIndex("recipe_name");
            int descriptionIndex = cursor.getColumnIndex("description");
            int ingredientListIndex = cursor.getColumnIndex("ingredient_list");
            int methodIndex = cursor.getColumnIndex("instructions"); // FIXED: was "method"

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            String ingredientList = cursor.getString(ingredientListIndex);
            String method = cursor.getString(methodIndex);

            recipe = new Recipes(id, name, description, ingredientList, method);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return recipe;
    }

    // update existing recipe in db
    public int updateRecipe(Recipes recipe) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("recipe_name", recipe.getName());
        values.put("description", recipe.getDescription());
        values.put("ingredient_list", recipe.getIngredientList());
        values.put("instructions", recipe.getMethod());

        String selection = "recipe_id = ?";
        String[] selectionArgs = {String.valueOf(recipe.getId())};

        int rowsAffected = db.update("recipes", values, selection, selectionArgs);
        db.close();
        return rowsAffected;
    }

    // delete recipe from db
    public int deleteRecipe(int recipeId) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "recipe_id = ?";
        String[] selectionArgs = {String.valueOf(recipeId)};

        int rowsDeleted = db.delete("recipes", selection, selectionArgs);
        db.close();
        return rowsDeleted;
    }
}
