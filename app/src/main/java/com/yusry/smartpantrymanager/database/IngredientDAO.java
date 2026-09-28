package com.yusry.smartpantrymanager.database;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yusry.smartpantrymanager.models.Ingredient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class IngredientDAO {
    private final PantryDatabaseHelper dbHelper; // Creating reference to database helper for db access
    // Constructor that runs when a new ingredient DAO (Data Access Object) required
    public IngredientDAO(PantryDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper; // stores db helper for later
    }
    // Function to save new ingredients to db
    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Getting access to db
        try {
            ContentValues values = new ContentValues(); // Declare ContentValues to hold data
            // Adding values into corresponding column container
            values.put("name", ingredient.getName());
            values.put("quantity", ingredient.getQuantity());
            values.put("unit", ingredient.getUnit());
            values.put("date_added", getCurrentDate());
            long result = db.insert("ingredients", null, values); //Insert value into ingredients table, retrieve ID back
            // If result is -1, display error messgae
            if (result == -1)
            {
                System.out.println("Unable to add that ingredient");
            }
        } finally {
            db.close(); //Close db connection
        }
    }
    // Gets all ingredients form db
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredientList = new ArrayList<>(); // Creating empty list to store ingredients
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Readable access to the database

        // Check the db to get all rows
        try {
            Cursor cursor = db.query("ingredients", null, null, null, null, null, null);
            // Loop through each row/ingredients
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0); // Get Ingredient ID
                String name = cursor.getString(1); // Get ingredient name
                double quantity = cursor.getDouble(2); // Get qty
                String unit = cursor.getString(3); //get unit
                String dateAdded = cursor.getString(4); // get date

                Ingredient ingredient = new Ingredient(id, name, quantity, unit, dateAdded); // Object for data called ingredient

                ingredientList.add(ingredient); // Add ingredient to list
            }
            cursor.close(); // close cursor
        } finally {
            db.close(); // close db connection
        }
        return ingredientList; // return ingredients
    }
    // Gets single ingredient from db
    public Ingredient getIngredientById(int id) {
        // Get readable access to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            // Query to find specific ingredient
            Cursor cursor = db.query("ingredients", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

            // if statement to check if founs
            if (cursor.moveToFirst()) {
                // Get ingredient, qty, unit and date from columns
                int ingredientId = cursor.getInt(0);
                String name = cursor.getString(1);
                double quantity = cursor.getDouble(2);
                String unit = cursor.getString(3);
                String dateAdded = cursor.getString(4);
                cursor.close(); //Close cursor

                // Create a new Ingredient object with this data and return it
                return new Ingredient(ingredientId, name, quantity, unit, dateAdded);
            }
            cursor.close(); // Close cursor, no ingredient found
        } finally {
            db.close(); // Close db connection
        }
        return null; // return null if no ingredients
    }
    // Updating existing ingredients in db
    public void updateIngredient(Ingredient ingredient) {

        SQLiteDatabase db = dbHelper.getWritableDatabase(); // get access to db

        try {
            // Create a ContentValues container with the updated ingredient data
            ContentValues values = new ContentValues();

            // Put the updated name, qty, unit, and date into new container
            values.put("name", ingredient.getName());
            values.put("quantity", ingredient.getQuantity());
            values.put("unit", ingredient.getUnit());
            values.put("date_added", ingredient.getDateAdded());

            // Update row in db where id equals ingredient ID
            int rowsAffected = db.update("ingredients", values, "id = ?", new String[]{String.valueOf(ingredient.getId())}  //ID that must match
            );

            // If 0, no row updated, display message
            if (rowsAffected == 0) {
                System.out.println("Unable to edit that Ingredient");
            }
        } finally {
            db.close(); // Close db
        }
    }
    // Removes ingredients from the database
    public void deleteIngredient(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Access the db

        try {
            // Delete the row from db base on ID
            int rowsAffected = db.delete("ingredients", "id = ?", new String[]{String.valueOf(id)}
            );

            // Check if o, if 0 then no row was deleted, display message
            if (rowsAffected == 0) {
                System.out.println("Ingredient not found!");
            }
        } finally {
            // Always close the database connection
            db.close();
        }
    }
    // Helper method, returns today's date, to track when ingredients were added
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); //Date formatter to get specific format
        return sdf.format(new Date());
    }
}
