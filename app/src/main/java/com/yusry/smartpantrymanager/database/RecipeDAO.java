package com.yusry.smartpantrymanager.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.ArrayList;
import java.util.List;

// To handle database CRUD operations for recipes
public class RecipeDAO {
    private PantryDatabaseHelper dbHelper;// db helper to access db
    // Constructor that take app context and creates db helper
    public RecipeDAO(Context context) {
        dbHelper = new PantryDatabaseHelper(context); // Initialize db helper
    }
    // Add new recipe to the database
    public long addRecipe(Recipes recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Opening db to add data

        ContentValues values = new ContentValues(); // Container to hold info
        // Adding recipe name, description, ingredient list and recipe method to container
        values.put("recipe_name", recipe.getName());
        values.put("description", recipe.getDescription());
        values.put("ingredient_list", recipe.getIngredientList());
        values.put("method", recipe.getMethod());
        // Insert recipe into recipes table and get row ID, -1 if fails
        long recipeId = db.insert("recipes", null, values);
        db.close(); //Closes db
        return recipeId; //Return ID on new inserted recipe
    }
    // Retrieve all recipes from db
    public List<Recipes> getAllRecipes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();// Connect to db

        List<Recipes> recipesList = new ArrayList<>(); // Creating an empty list to store all recipes
        // Using cursor to iterate over reeciper and retrive all rows
        Cursor cursor = db.query("recipes", null, null, null, null, null, null);
        // Check if recipes are not empty
        if (cursor != null && cursor.moveToFirst()) {
            // loop over reecipes until end
            do {
                // Get index each required ingredient requirements
                int idIndex = cursor.getColumnIndex("recipe_id");
                int nameIndex = cursor.getColumnIndex("recipe_name");
                int descriptionIndex = cursor.getColumnIndex("description");
                int ingredientListIndex = cursor.getColumnIndex("ingredient_list");
                int methodIndex = cursor.getColumnIndex("method");

                // Get actual values from required recipe
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String description =cursor.getString(descriptionIndex);
                String ingredientList = cursor.getString(ingredientListIndex);
                String method = cursor.getString(methodIndex);

                // Creating a new recipe object with information required
                Recipes recipe = new Recipes(id, name, description, ingredientList, method);

                // Add recipe to our list
                recipesList.add(recipe);

            } while (cursor.moveToNext()); // Helps with iteration
        }

        //Close cusror to close memory
        if (cursor != null) {
            cursor.close();
        }
        db.close(); // Close db conncetion

        return recipesList; // return list with recipes found
    }

    // Get single recipe
    public Recipes getRecipeById(int recipeId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Open db connection

        // A where clause to find recipr with specific ID
        String selection = "recipe_id = ?";

        //Add the actual recipe id into an array for the query
        String[] selectionArgs = {String.valueOf(recipeId)};

        // Querying the recipes table with our WHERE clause and getting back a cursor
        Cursor cursor = db.query("recipes", null, selection, selectionArgs, null, null, null);
        // If nothing is found
        Recipes recipe = null;

        // Checking whether recipe found
        if (cursor != null && cursor.moveToFirst()) {
            // Getting the column indexes for data
            int idIndex = cursor.getColumnIndex("recipe_id");
            int nameIndex = cursor.getColumnIndex("recipe_name");
            int descriptionIndex = cursor.getColumnIndex("description");
            int ingredientListIndex = cursor.getColumnIndex("ingredient_list");
            int methodIndex = cursor.getColumnIndex("method");

            // Getting values from the row
            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            String ingredientList = cursor.getString(ingredientListIndex);
            String method = cursor.getString(methodIndex);

            // Create recipe object with data that we collected
            recipe = new Recipes(id, name, description, ingredientList, method);
        }

        if (cursor != null) {
            cursor.close(); // Close cursor to save memory
        }

        db.close(); // Closing db conncetion

        // return recipe
        return recipe;
    }

    // Update existing recipe in the database
    public int updateRecipe(Recipes recipe) {

        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Opening db connection

        // Create container for holding updated data
        ContentValues values = new ContentValues();

        //Adding updated recipe name, description, ingredients, and method into a container
        values.put("recipe_name", recipe.getName());
        values.put("description", recipe.getDescription());
        values.put("ingredient_list", recipe.getIngredientList());
        values.put("method", recipe.getMethod());

        String selection = "recipe_id = ?"; // A where clause to update the recipe with a unique id only

        String[] selectionArgs = {String.valueOf(recipe.getId())}; // Add recipe id into array for update query

        int rowsAffected = db.update("recipes", values, selection, selectionArgs); // Update recipe table for update row only
        db.close(); // Close db connection
        return rowsAffected; // Return number of rows updated
    }

    // Delete recipe from the db
    public int deleteRecipe(int recipeId) {

        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Opening db connection

        String selection = "recipe_id = ?"; // Setting up a where clause to delete recipe with specific id

        String[] selectionArgs = {String.valueOf(recipeId)}; // Add recipe id into array for the delete query

        int rowsDeleted = db.delete("recipes", selection, selectionArgs); // Deleting recipes table and getting the number of rows deleted

        db.close(); // Close the db connection
        return rowsDeleted;
    }
}
