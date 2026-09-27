package com.yusry.smartpantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * PantryDatabaseHelper - Manages all SQLite database operations for Smart Pantry Manager.
 *
 * Think of this class as the "database janitor" - it sets up the database structure
 * (tables and columns), handles creation, and manages updates. Android calls the methods
 * in this class automatically when the app first runs or when the database version changes.
 *
 * We extend SQLiteOpenHelper because it's the recommended Android way to handle SQLite.
 * It prevents common issues like database corruption and ensures thread safety.
 */
public class PantryDatabaseHelper extends SQLiteOpenHelper {

    // === DATABASE CONFIGURATION ===
    private static final String DATABASE_NAME = "SmartPantry.db"; // File name stored on device
    private static final int DATABASE_VERSION = 1; // Version number - increment when schema changes

    // === TABLE NAMES ===
    // These are the three main tables we need:
    public static final String TABLE_INGREDIENTS = "ingredients";           // Stores what's in the pantry
    public static final String TABLE_RECIPES = "recipes";                   // Stores recipe metadata
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients"; // Stores recipe ingredients (links recipes to ingredients)

    // === INGREDIENTS TABLE COLUMNS ===
    // Example row: [1, "tomato", 5, "kg", "2025-12-31"]
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";      // Unique ID (Primary Key)
    public static final String COLUMN_INGREDIENT_NAME = "name";             // Ingredient name (must be unique - can't have two "tomatoes")
    public static final String COLUMN_QUANTITY = "quantity";                // How much we have (e.g., 5)
    public static final String COLUMN_UNIT = "unit";                        // Unit of measurement (kg, liters, pieces, etc.)
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";          // When it expires (YYYY-MM-DD format)

    // === RECIPES TABLE COLUMNS ===
    // Example row: [1, "Tomato Soup", "Boil tomatoes, blend...", "url_to_image"]
    public static final String COLUMN_RECIPE_ID = "recipe_id";              // Unique ID (Primary Key)
    public static final String COLUMN_RECIPE_NAME = "recipe_name";          // Recipe name (must be unique)
    public static final String COLUMN_INSTRUCTIONS = "instructions";        // Step-by-step cooking instructions
    public static final String COLUMN_IMAGE_URL = "image_url";              // URL or file path to recipe image

    // === RECIPE_INGREDIENTS TABLE COLUMNS ===
    // This is a "junction table" - it connects recipes to their required ingredients
    // Example row: [1, 1, "tomato", 2, "kg"] means Recipe #1 needs 2kg of tomato
    public static final String COLUMN_RECIPE_INGREDIENT_ID = "recipe_ingredient_id"; // Unique ID (Primary Key)
    public static final String COLUMN_RECIPE_FK = "recipe_id";              // Foreign Key pointing to recipes table
    public static final String COLUMN_RECIPE_ING_NAME = "ingredient_name";  // Which ingredient this recipe needs
    public static final String COLUMN_RECIPE_ING_QUANTITY = "quantity";     // How much of this ingredient (e.g., 2)
    public static final String COLUMN_RECIPE_ING_UNIT = "unit";             // Unit (kg, liters, pieces, etc.)

    /**
     * Constructor - Called when the app creates a database helper instance.
     *
     * @param context - App context (needed to access device storage)
     *
     * We pass:
     * - context: to tell Android where to store the database
     * - DATABASE_NAME: the filename
     * - null: for cursor factory (we don't need custom cursors)
     * - DATABASE_VERSION: triggers onUpgrade if version changes
     */
    public PantryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate() - Called ONCE when database doesn't exist yet (first app launch).
     *
     * This is where we define the structure of our three tables. Think of it like
     * designing the blueprint for a building - we specify what rooms exist and
     * what furniture goes in each room.
     *
     * @param db - The SQLiteDatabase object we write CREATE TABLE statements to
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // === CREATE INGREDIENTS TABLE ===
        // Stores the pantry inventory
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Auto-incrementing ID (1, 2, 3...)
                COLUMN_INGREDIENT_NAME + " TEXT UNIQUE NOT NULL, " +             // Name must be unique & not null (no duplicate "tomato" entries)
                COLUMN_QUANTITY + " REAL NOT NULL, " +                           // Amount (using REAL to support decimals like 2.5)
                COLUMN_UNIT + " TEXT NOT NULL, " +                              // Unit of measurement (kg, liters, pieces, etc.)
                COLUMN_EXPIRY_DATE + " TEXT)";                                  // Expiry date as text (flexible format)
        db.execSQL(CREATE_INGREDIENTS_TABLE);

        // === CREATE RECIPES TABLE ===
        // Stores recipe metadata (name, instructions, image)
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +     // Auto-incrementing recipe ID
                COLUMN_RECIPE_NAME + " TEXT UNIQUE NOT NULL, " +                // Recipe name (must be unique - no duplicate recipes)
                COLUMN_INSTRUCTIONS + " TEXT, " +                               // Cooking instructions (optional, can be null)
                COLUMN_IMAGE_URL + " TEXT)";                                    // Image URL (optional)
        db.execSQL(CREATE_RECIPES_TABLE);

        // === CREATE RECIPE_INGREDIENTS TABLE ===
        // This is the "bridge" table - connects recipes to their required ingredients
        // Why separate? Because one recipe can have many ingredients, and one ingredient
        // can be used in many recipes. This avoids storing recipe data multiple times.
        String CREATE_RECIPE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COLUMN_RECIPE_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Unique ID for each requirement
                COLUMN_RECIPE_FK + " INTEGER NOT NULL, " +                            // Foreign Key to recipes table (links to recipe_id)
                COLUMN_RECIPE_ING_NAME + " TEXT NOT NULL, " +                         // Ingredient name (e.g., "tomato")
                COLUMN_RECIPE_ING_QUANTITY + " REAL NOT NULL, " +                     // Amount needed (e.g., 2)
                COLUMN_RECIPE_ING_UNIT + " TEXT NOT NULL, " +                         // Unit (kg, liters, pieces)
                "FOREIGN KEY(" + COLUMN_RECIPE_FK + ") REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "))"; // Constraint: recipe_id must exist in recipes table
        db.execSQL(CREATE_RECIPE_INGREDIENTS_TABLE);
    }

    /**
     * onUpgrade() - Called when DATABASE_VERSION is incremented.
     *
     * Why do we need this? When we want to add new features (like a new column),
     * we increase DATABASE_VERSION. Android detects this and calls onUpgrade.
     *
     * Current strategy: Drop old tables and recreate (simple but loses user data).
     * For production apps, you'd migrate data carefully instead.
     *
     * @param db - The SQLiteDatabase object
     * @param oldVersion - Previous database version
     * @param newVersion - New database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables in reverse order of creation (to respect foreign key constraints)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS); // Drop first (has foreign key)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);            // Drop second
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);        // Drop third

        // Recreate fresh tables with new schema
        onCreate(db);
    }
}
