package com.yusry.smartpantrymanager.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    // This variable holds the connection to our SQLite database
    // Think of it like a telephone line to the database—we use it to send commands
    private SQLiteDatabase db;

    // Constructor: This gets called when we create a new IngredientDAO object
    // We pass in the database connection and store it so we can use it later
    public IngredientDAO(SQLiteDatabase database) {
        this.db = database;
    }

    // ========== CREATE METHOD ==========
    // This method ADDS a new ingredient to the pantry
    // We give it the ingredient's name, quantity (how much), and unit (cups, grams, etc.)
    // It returns the ID of the newly created ingredient (or -1 if it fails)
    public long addIngredient(String name, String quantity, String unit) {
        // ContentValues is like a shopping bag where we put key-value pairs
        // Think of it as: {"name": "tomato", "quantity": "5", "unit": "kg"}
        // SQLite needs this format to understand what data we're inserting
        ContentValues values = new ContentValues();

        // Now we're filling the shopping bag with our ingredient's information
        values.put("name", name);           // Put the ingredient name in the bag
        values.put("quantity", quantity);   // Put the quantity in the bag
        values.put("unit", unit);           // Put the measurement unit in the bag

        // Now we send this to the database
        // db.insert() says "Hey database, add this data to the 'ingredients' table"
        // It returns the ID of the new row (so we know it was added successfully)
        return db.insert("ingredients", null, values);
    }

    // ========== READ METHOD (Get All) ==========
    // This method GETS ALL ingredients from the pantry
    // It returns a list of ingredient names like ["tomato", "onion", "garlic"]
    public List<String> getAllIngredients() {
        // Create an empty bag to hold all the ingredient names we find
        List<String> ingredients = new ArrayList<>();

        // db.query() is like asking the database a question
        // "Give me all data from the 'ingredients' table"
        // The 'null' values mean "I want ALL rows, ALL columns, no filters"
        Cursor cursor = db.query("ingredients", null, null, null, null, null, null);

        // Now we have a cursor (think of it as a finger pointing at database results)
        // moveToFirst() moves that finger to the first result
        // If there ARE results, it returns true. If the table is empty, it returns false
        if (cursor.moveToFirst()) {
            // This loop keeps going through each ingredient one by one
            // It's like reading a list from top to bottom
            do {
                // getColumnIndex("name") finds which column number has the ingredient name
                // Then getString() gets the actual name from that column
                String name = cursor.getString(cursor.getColumnIndex("name"));

                // Add this ingredient name to our shopping bag (the List)
                ingredients.add(name);

                // moveToNext() moves our finger to the next ingredient in the results
                // If there are no more ingredients, the loop stops
            } while (cursor.moveToNext());
        }

        // IMPORTANT: We're done with the cursor, so we close it
        // This frees up database resources (like closing a book after reading)
        cursor.close();

        // Return the list of all ingredients we found
        return ingredients;
    }

    // ========== READ METHOD (Get One) ==========
    // This method GETS ONE specific ingredient by its ID
    // Think of it like looking up a person's phone number by their ID
    public String getIngredientById(int id) {
        // We're asking the database: "Give me data WHERE id equals this specific number"
        // The "id = ?" means we're filtering for a specific ID
        // The String array with the ID is the actual value we're searching for
        Cursor cursor = db.query("ingredients", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        // Did we find an ingredient with this ID?
        if (cursor.moveToFirst()) {
            // Yes! Get the name from the first (and only) result
            String name = cursor.getString(cursor.getColumnIndex("name"));

            // Close the cursor before we return (don't forget this!)
            cursor.close();

            // Give back the ingredient name we found
            return name;
        }

        // If we get here, we didn't find an ingredient with that ID
        // Close the cursor anyway (good practice)
        cursor.close();

        // Return null to say "I didn't find anything"
        return null;
    }

    // ========== UPDATE METHOD ==========
    // This method CHANGES an ingredient's information
    // We give it the ingredient's ID (so we know which one to change),
    // and the new name, quantity, and unit
    // It returns how many ingredients were successfully updated (usually 1 or 0)
    public int updateIngredient(int id, String name, String quantity, String unit) {
        // Create a shopping bag with the NEW information we want to store
        ContentValues values = new ContentValues();

        // Fill the bag with the updated ingredient data
        values.put("name", name);           // New ingredient name
        values.put("quantity", quantity);   // New quantity
        values.put("unit", unit);           // New unit

        // Now tell the database to update
        // db.update() says: "In the 'ingredients' table, find the row WHERE id equals this value,
        // and replace its data with what's in our shopping bag"
        // It returns how many rows were actually updated (1 = success, 0 = ingredient not found)
        return db.update("ingredients", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // ========== DELETE METHOD ==========
    // This method REMOVES an ingredient from the pantry forever
    // We give it the ingredient's ID (so we know which one to delete)
    // It returns how many ingredients were successfully deleted (usually 1 or 0)
    public int deleteIngredient(int id) {
        // Tell the database to delete
        // db.delete() says: "From the 'ingredients' table, remove the row WHERE id equals this value"
        // It returns how many rows were actually deleted (1 = success, 0 = ingredient not found)
        return db.delete("ingredients", "id = ?", new String[]{String.valueOf(id)});
    }
}
