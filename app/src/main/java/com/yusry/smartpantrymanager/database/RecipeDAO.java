package com.yusry.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.ArrayList;
import java.util.List;

// RecipeDAO handles all database operations for recipes (Create, Read, Update, Delete)
public class RecipeDAO {
    // We need the database helper to get access to the actual SQLite database
    private PantryDatabaseHelper dbHelper;

    // Constructor that takes the app context and creates a database helper
    public RecipeDAO(Context context) {
        // We're initializing the database helper so we can perform database operations
        dbHelper = new PantryDatabaseHelper(context);
    }

    // ADD a new recipe to the database
    public long addRecipe(Recipes recipe) {
        // We're opening a writable database connection to insert data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ContentValues is like a container that holds column names and their values
        // SQLite uses this to understand what data to insert
        ContentValues values = new ContentValues();

        // We're putting the recipe name into the container with the column name "recipe_name"
        values.put("recipe_name", recipe.getName());

        // We're putting the recipe description into the container
        values.put("description", recipe.getDescription());

        // We're putting the ingredient list (formatted as "ingredientName:quantity:unit") into the container
        values.put("ingredient_list", recipe.getIngredientList());

        // We're inserting the recipe into the "recipes" table and getting back the row ID
        // If insertion fails, it returns -1
        long recipeId = db.insert("recipes", null, values);

        // We're closing the database connection to free up resources
        db.close();

        // We're returning the ID of the newly inserted recipe
        return recipeId;
    }

    // GET ALL recipes from the database
    public List<Recipes> getAllRecipes() {
        // We're opening a readable database connection to fetch data
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // We're creating an empty list to store all recipes we find
        List<Recipes> recipesList = new ArrayList<>();

        // We're querying the "recipes" table and getting back all rows as a cursor
        // A cursor is like a pointer that moves through each row of data
        Cursor cursor = db.query("recipes", null, null, null, null, null, null);

        // We're checking if there are any recipes in the database
        if (cursor != null && cursor.moveToFirst()) {
            // We're looping through each recipe row until there are no more
            do {
                // We're getting the column index for each piece of data we need
                // The index tells us which position each column is at in the row
                int idIndex = cursor.getColumnIndex("recipe_id");
                int nameIndex = cursor.getColumnIndex("recipe_name");
                int descriptionIndex = cursor.getColumnIndex("description");
                int ingredientListIndex = cursor.getColumnIndex("ingredient_list");

                // We're extracting the actual values from the current row using the indexes
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descriptionIndex);
                String ingredientList = cursor.getString(ingredientListIndex);

                // We're creating a new Recipes object with the data we extracted
                Recipes recipe = new Recipes(id, name, description, ingredientList);

                // We're adding this recipe to our list
                recipesList.add(recipe);

            } while (cursor.moveToNext()); // We're moving to the next row and repeating
        }

        // We're closing the cursor to free up memory
        if (cursor != null) {
            cursor.close();
        }

        // We're closing the database connection
        db.close();

        // We're returning the list of all recipes we found
        return recipesList;
    }

    // GET a single recipe by its ID
    public Recipes getRecipeById(int recipeId) {
        // We're opening a readable database connection
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // We're setting up a WHERE clause to find only the recipe with this specific ID
        // The ? is a placeholder that prevents SQL injection attacks (a security measure)
        String selection = "recipe_id = ?";

        // We're putting the actual recipe ID into an array for the query
        String[] selectionArgs = {String.valueOf(recipeId)};

        // We're querying the "recipes" table with our WHERE clause and getting back a cursor
        Cursor cursor = db.query("recipes", null, selection, selectionArgs, null, null, null);

        // We're creating a null recipe object in case we don't find anything
        Recipes recipe = null;

        // We're checking if we found a recipe
        if (cursor != null && cursor.moveToFirst()) {
            // We're getting the column indexes for each piece of data
            int idIndex = cursor.getColumnIndex("recipe_id");
            int nameIndex = cursor.getColumnIndex("recipe_name");
            int descriptionIndex = cursor.getColumnIndex("description");
            int ingredientListIndex = cursor.getColumnIndex("ingredient_list");

            // We're extracting the values from the row
            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            String ingredientList = cursor.getString(ingredientListIndex);

            // We're creating a new Recipes object with the data we found
            recipe = new Recipes(id, name, description, ingredientList);
        }

        // We're closing the cursor to free up memory
        if (cursor != null) {
            cursor.close();
        }

        // We're closing the database connection
        db.close();

        // We're returning the recipe we found (or null if we didn't find it)
        return recipe;
    }

    // UPDATE an existing recipe in the database
    public int updateRecipe(Recipes recipe) {
        // We're opening a writable database connection to update data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ContentValues is the container holding the updated data
        ContentValues values = new ContentValues();

        // We're putting the updated recipe name into the container
        values.put("recipe_name", recipe.getName());

        // We're putting the updated description into the container
        values.put("description", recipe.getDescription());

        // We're putting the updated ingredient list into the container
        values.put("ingredient_list", recipe.getIngredientList());

        // We're setting up a WHERE clause to update only the recipe with this specific ID
        String selection = "recipe_id = ?";

        // We're putting the recipe ID into an array for the update query
        String[] selectionArgs = {String.valueOf(recipe.getId())};

        // We're updating the "recipes" table and getting back the number of rows affected
        int rowsAffected = db.update("recipes", values, selection, selectionArgs);

        // We're closing the database connection
        db.close();

        // We're returning the number of rows updated (0 if the recipe wasn't found, 1 if successful)
        return rowsAffected;
    }

    // DELETE a recipe from the database
    public int deleteRecipe(int recipeId) {
        // We're opening a writable database connection to delete data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // We're setting up a WHERE clause to delete only the recipe with this specific ID
        String selection = "recipe_id = ?";

        // We're putting the recipe ID into an array for the delete query
        String[] selectionArgs = {String.valueOf(recipeId)};

        // We're deleting from the "recipes" table and getting back the number of rows deleted
        int rowsDeleted = db.delete("recipes", selection, selectionArgs);

        // We're closing the database connection
        db.close();

        // We're returning the number of rows deleted (0 if nothing was deleted, 1 if successful)
        return rowsDeleted;
    }
}
