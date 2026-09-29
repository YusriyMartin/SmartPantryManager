package com.yusry.smartpantrymanager.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PantryDatabaseHelper extends SQLiteOpenHelper {
    // Configuring database
    private static final String DATABASE_NAME = "SmartPantry.db"; // File name stored on device
    private static final int DATABASE_VERSION = 1; // version 1


    // Declaring tables for ingredients, recipes, and ingredients
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";


    // Ingredient table: stores Ingredient ID, Ingredient name, qty, unit and expiry date
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_INGREDIENT_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";


    // Recipe table: stores Recipe ID, Recipe name, Instruction, and recipe image
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_RECIPE_NAME = "recipe_name";
    public static final String COLUMN_INSTRUCTIONS = "method";
    public static final String COLUMN_IMAGE_URL = "image_url";


    // Recipe Ingredient table (juntion table): stores recipe ingredient ID, recipe ID as foreign key, ingredient name, quantity, and unit
    public static final String COLUMN_RECIPE_INGREDIENT_ID = "recipe_ingredient_id";
    public static final String COLUMN_RECIPE_FK = "recipe_id";
    public static final String COLUMN_RECIPE_ING_NAME = "ingredient_name";
    public static final String COLUMN_RECIPE_ING_QUANTITY = "quantity";
    public static final String COLUMN_RECIPE_ING_UNIT = "unit";
    // Constructor for when the app creates a database helper instance

    public PantryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // To start database, lauch app
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating ingredient table, stores ingredients in pantry
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + " " +
                "(" + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INGREDIENT_NAME + " TEXT UNIQUE NOT NULL, " +
                COLUMN_QUANTITY + " REAL NOT NULL, " +
                COLUMN_UNIT + " TEXT NOT NULL, " +
                COLUMN_EXPIRY_DATE + " TEXT)";
        db.execSQL(CREATE_INGREDIENTS_TABLE);

// Updated error: Added descirption text colum so it matches Recipes.java
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT UNIQUE NOT NULL, " +
                "description TEXT, " +
                "method TEXT, " +
                "ingredient_list TEXT, " +
                COLUMN_IMAGE_URL + " TEXT)";

        /**
         * Creating recipe_ingredient table. To connects the recipes to ingredients
         * contains recipe_ingredient ID, contains recipe ID as foreign key, recipe ingredient name, recipe ingredient qty, recipe ingredient unit
         * **/
        String CREATE_RECIPE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COLUMN_RECIPE_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_FK + " INTEGER NOT NULL, " +
                COLUMN_RECIPE_ING_NAME + " TEXT NOT NULL, " +
                COLUMN_RECIPE_ING_QUANTITY + " REAL NOT NULL, " +
                COLUMN_RECIPE_ING_UNIT + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_RECIPE_FK + ") REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "))";
        db.execSQL(CREATE_RECIPE_INGREDIENTS_TABLE);
    }
    // For incrementation of database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables in reverse to accomodate Foreign Key
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);

        // Recreate new table
        onCreate(db);
    }
}

