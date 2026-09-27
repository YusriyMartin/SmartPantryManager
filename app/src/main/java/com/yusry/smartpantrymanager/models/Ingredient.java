package com.yusry.smartpantrymanager.models;
// 👆 LOCATION: This file lives in com.yourname.smartpantrymanager.models package
//    It's the BLUEPRINT for ingredients used throughout the entire app

/**
 * INGREDIENT MODEL CLASS
 *
 * 📍 LOCATION IN PROJECT:
 *    - File: models/Ingredient.java
 *    - Purpose: This is the data model (blueprint) for ingredients
 *
 * 🔗 CONNECTIONS TO OTHER PARTS:
 *    1. PantryDatabaseHelper (database/PantryDatabaseHelper.java)
 *       - PantryDatabaseHelper creates the INGREDIENTS table in SQLite
 *       - Each row in that table represents ONE Ingredient object
 *
 *    2. IngredientDAO (database/IngredientDAO.java)
 *       - IngredientDAO converts database rows INTO Ingredient objects
 *       - IngredientDAO takes Ingredient objects and SAVES them to database
 *       - Methods like: addIngredient(Ingredient ing), getAllIngredients() returns List<Ingredient>
 *
 *    3. Activities (MainActivity, Add/Edit screens)
 *       - Activities CREATE Ingredient objects from user input
 *       - Activities PASS Ingredient objects to IngredientDAO to save
 *       - Activities DISPLAY Ingredient objects in RecyclerView
 *
 * 💡 THINK OF IT THIS WAY:
 *    Ingredient.java = The RECIPE CARD TEMPLATE
 *    PantryDatabaseHelper = The FILING CABINET (where data is stored)
 *    IngredientDAO = The LIBRARIAN (retrieves/saves recipe cards from/to the filing cabinet)
 *    Activities = The USER (creates recipe cards and asks the librarian to file them)
 */
public class Ingredient {

    // ========== PROPERTIES (Fields) ==========
    // 📍 These are stored in the INGREDIENTS table in PantryDatabaseHelper
    //    Column names in database:
    //    - Column: "id" (Primary Key, auto-increments)
    //    - Column: "name" (TEXT)
    //    - Column: "quantity" (TEXT)
    //    - Column: "unit" (TEXT)

    /**
     * INGREDIENT ID
     *
     * 📍 DATABASE: Stored as PRIMARY KEY in INGREDIENTS table
     *    - PantryDatabaseHelper defines: _ID INTEGER PRIMARY KEY AUTOINCREMENT
     *    - When you call IngredientDAO.addIngredient(), database auto-generates this ID
     *
     * This is a unique number that identifies each ingredient in the database.
     * Think of it like an ID card number - no two ingredients have the same ID.
     * We use this to find, update, or delete a specific ingredient quickly.
     * The database auto-generates this when we add a new ingredient.
     */
    private int id;

    /**
     * INGREDIENT NAME
     *
     * 📍 DATABASE: Stored as "name" TEXT column in INGREDIENTS table
     *    - PantryDatabaseHelper defines: name TEXT NOT NULL
     *    - IngredientDAO.addIngredient() stores this via ContentValues.put("name", name)
     *
     * This is the name of the ingredient (e.g., "Tomato", "Onion", "Salt").
     * It's stored as text (String) because ingredient names are always words.
     * Users will see and interact with this name in the app.
     */
    private String name;

    /**
     * INGREDIENT QUANTITY
     *
     * 📍 DATABASE: Stored as "quantity" TEXT column in INGREDIENTS table
     *    - PantryDatabaseHelper defines: quantity TEXT NOT NULL
     *    - IngredientDAO.addIngredient() stores this via ContentValues.put("quantity", quantity)
     *
     * This is how MUCH of the ingredient we have.
     * We store it as a String (text) to be flexible. Why?
     * Because quantities can be whole numbers (5) or decimals (2.5).
     * By using String, we avoid problems with converting between formats.
     * Examples: "5", "2.5", "0.75"
     *
     * 🔗 CRITICAL FOR RECIPES:
     *    The strict-matching algorithm in RecipeDAO will COMPARE this quantity
     *    against recipe ingredient requirements to determine if we can make the recipe.
     */
    private String quantity;

    /**
     * INGREDIENT UNIT
     *
     * 📍 DATABASE: Stored as "unit" TEXT column in INGREDIENTS table
     *    - PantryDatabaseHelper defines: unit TEXT NOT NULL
     *    - IngredientDAO.addIngredient() stores this via ContentValues.put("unit", unit)
     *
     * This is the MEASUREMENT TYPE for the quantity.
     * It tells us what the quantity number means.
     * Examples: "kg" (kilograms), "g" (grams), "cups", "tablespoons", "pieces"
     * Without the unit, "5" could mean 5 kg or 5 grams - totally different!
     *
     * 🔗 CRITICAL FOR RECIPES:
     *    The strict-matching algorithm will also CHECK the unit to ensure
     *    we're comparing apples to apples (not kg to grams without conversion).
     */
    private String unit;


    // ========== CONSTRUCTORS ==========
    // These are special methods that CREATE new Ingredient objects

    /**
     * CONSTRUCTOR 1: EMPTY CONSTRUCTOR
     *
     * 📍 USAGE LOCATIONS:
     *    1. In Add Ingredient Activity: When user opens the form to add a new ingredient
     *       - Activities call: Ingredient newIngredient = new Ingredient();
     *       - Then fill in the fields as user types
     *
     *    2. In Edit Ingredient Activity: Sometimes we create empty then populate
     *
     * This creates an Ingredient with no data yet.
     * We use this when we want to create an empty ingredient object first,
     * then fill in the details later.
     *
     * Example use: When the user opens the "Add Ingredient" screen,
     * we create an empty Ingredient, then the user fills it in.
     */
    public Ingredient() {
        // Java automatically initializes everything to default values:
        // - id becomes 0 (not saved to database yet)
        // - name, quantity, unit become null (empty)
        // This is like getting a blank recipe card with no information filled in yet
    }

    /**
     * CONSTRUCTOR 2: FULL CONSTRUCTOR (with ID)
     *
     * 📍 USAGE LOCATIONS:
     *    1. In IngredientDAO.getAllIngredients():
     *       - Cursor cursor = database.query(...);
     *       - while(cursor.moveToNext()) {
     *           Ingredient ing = new Ingredient(
     *               cursor.getInt(0),        // id from database
     *               cursor.getString(1),     // name from database
     *               cursor.getString(2),     // quantity from database
     *               cursor.getString(3)      // unit from database
     *           );
     *       - }
     *
     *    2. In IngredientDAO.getIngredientById(int id):
     *       - Similar pattern - retrieve ONE ingredient from database
     *
     *    3. In RecyclerView Adapter:
     *       - When displaying ingredients in the list
     *
     * This creates an Ingredient with ALL information at once.
     * We use this when we RETRIEVE an ingredient from the database.
     * The database gives us the id, name, quantity, and unit all together.
     *
     * @param id - The unique identifier (usually from the database, auto-generated)
     * @param name - The ingredient name (e.g., "Tomato")
     * @param quantity - How much we have (e.g., "5")
     * @param unit - The measurement type (e.g., "kg")
     *
     * Example: When we fetch a tomato from the database, it comes with all 4 values.
     *          The database already assigned it an ID (like 1, 2, 3, etc.)
     */
    public Ingredient(int id, String name, String quantity, String unit) {
        // We're assigning (copying) all the information we received
        // into this ingredient object's properties so we can use them later
        // This is like filling in all fields on a recipe card at once
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * CONSTRUCTOR 3: PARTIAL CONSTRUCTOR (without ID)
     *
     * 📍 USAGE LOCATIONS:
     *    1. In Add Ingredient Activity:
     *       - After user fills form and clicks "Save", we do:
     *       - String name = editTextName.getText().toString();
     *       - String quantity = editTextQuantity.getText().toString();
     *       - String unit = spinnerUnit.getSelectedItem().toString();
     *       - Ingredient newIngredient = new Ingredient(name, quantity, unit);
     *
     *    2. Then pass to IngredientDAO:
     *       - ingredientDAO.addIngredient(newIngredient);
     *       - IngredientDAO.addIngredient() will extract name/quantity/unit
     *         and send to database (database auto-generates the ID)
     *
     *    3. In Test/Demo code:
     *       - Quick way to create test ingredients without database
     *
     * This creates an Ingredient with name, quantity, and unit,
     * but NO id (because it's new and hasn't been saved to the database yet).
     * We use this when the user creates a NEW ingredient.
     * The database will auto-generate the id when we save it.
     *
     * @param name - The ingredient name (e.g., "Tomato")
     * @param quantity - How much we have (e.g., "5")
     * @param unit - The measurement type (e.g., "kg")
     *
     * Example: User types "Tomato", "5", "kg" in the Add Ingredient form.
     * We create: new Ingredient("Tomato", "5", "kg")
     * The database will add the id when we save it.
     */
    public Ingredient(String name, String quantity, String unit) {
        // We're setting the name, quantity, and unit.
        // The id stays 0 because it doesn't exist yet (no database id)
        // This is like filling in most of a recipe card, but leaving the ID field blank
        // (because the filing cabinet will assign the ID when we file it)
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }


    // ========== GETTERS ==========
    // These methods let other parts of the app READ the ingredient's data

    /**
     * GET ID METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In IngredientDAO.updateIngredient(Ingredient ing):
     *       - We need the ID to know WHICH row in the database to update
     *       - database.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(ing.getId())})
     *
     *    2. In IngredientDAO.deleteIngredient(int id):
     *       - We need the ID to know WHICH ingredient to delete
     *
     *    3. In Edit Ingredient Activity:
     *       - When user clicks "Edit", we get the ingredient ID to fetch from database
     *       - ingredientDAO.getIngredientById(ingredient.getId())
     *
     *    4. In RecyclerView Adapter onClick listeners:
     *       - When user clicks an ingredient, we get its ID to open detail screen
     *
     * This method returns the ingredient's unique ID number.
     * We use this when we need to find, update, or delete a specific ingredient.
     *
     * @return The ingredient's ID as an integer
     *
     * Example: int tomato_id = tomato.getId(); // Gets the tomato's ID number
     */
    public int getId() {
        // We're simply handing back (returning) the id value
        // This is like reading the ID number from the recipe card
        return id;
    }

    /**
     * GET NAME METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In RecyclerView Adapter (when displaying ingredient list):
     *       - textViewName.setText(ingredient.getName());
     *
     *    2. In Ingredient Detail Screen:
     *       - textViewName.setText(ingredient.getName());
     *
     *    3. In strict-matching algorithm (RecipeDAO):
     *       - Comparing recipe ingredient names against pantry ingredient names
     *       - Need to match "tomato" vs "Tomato", "tomatoes", etc.
     *
     *    4. In Edit Ingredient Activity:
     *       - Pre-filling the name field when editing
     *       - editTextName.setText(ingredient.getName());
     *
     * This method returns the ingredient's name.
     * We use this to display the ingredient name on screens, in lists, etc.
     *
     * @return The ingredient's name as a String
     *
     * Example: String name = tomato.getName(); // Gets "Tomato"
     */
    public String getName() {
        // We're simply handing back the name value
        // This is like reading the ingredient name from the recipe card
        return name;
    }

    /**
     * GET QUANTITY METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In RecyclerView Adapter (when displaying ingredient list):
     *       - textViewQuantity.setText(ingredient.getQuantity() + " " + ingredient.getUnit());
     *
     *    2. In Ingredient Detail Screen:
     *       - Shows "5 kg of Tomato"
     *
     *    3. 🔗 CRITICAL IN strict-matching algorithm (RecipeDAO):
     *       - When checking if we can make a recipe:
     *         Recipe needs: "2 kg tomato"
     *         We have: "5 kg tomato"
     *         Code: if (Double.parseDouble(pantryQuantity) >= Double.parseDouble(recipeQuantity))
     *       - We convert this String "5" to double 5.0 to compare numbers
     *
     *    4. In Edit Ingredient Activity:
     *       - Pre-filling when editing
     *       - editTextQuantity.setText(ingredient.getQuantity());
     *
     * This method returns how much of the ingredient we have.
     * We use this in the strict-matching logic to check if we have enough
     * of this ingredient to make a recipe.
     *
     * @return The ingredient's quantity as a String
     *
     * Example: String qty = tomato.getQuantity(); // Gets "5"
     */
    public String getQuantity() {
        // We're simply handing back the quantity value
        // This is like reading the amount from the recipe card
        return quantity;
    }

    /**
     * GET UNIT METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In RecyclerView Adapter (when displaying ingredient list):
     *       - textViewQuantity.setText(ingredient.getQuantity() + " " + ingredient.getUnit());
     *
     *    2. In Ingredient Detail Screen:
     *       - Shows "5 kg of Tomato"
     *
     *    3. 🔗 CRITICAL IN strict-matching algorithm (RecipeDAO):
     *       - When checking if we can make a recipe, we verify BOTH quantity AND unit:
     *         Recipe needs: "2 kg tomato"
     *         We have: "5 kg tomato" ✅ (same unit, more quantity)
     *         We have: "5000 g tomato" ⚠️ (different unit! Need conversion logic)
     *       - For now, we do simple comparison: if (pantryUnit.equals(recipeUnit))
     *         Later versions can add unit conversion (kg ↔ g, cups ↔ tablespoons, etc.)
     *
     *    4. In Edit Ingredient Activity:
     *       - Pre-selecting when editing
     *       - spinnerUnit.setSelection(unitList.indexOf(ingredient.getUnit()));
     *
     * This method returns the measurement unit of the ingredient.
     * We use this to understand what the quantity means (kg, g, cups, etc.).
     * Critical for strict-matching: we need to know if "5 kg" matches the recipe requirement.
     *
     * @return The ingredient's unit as a String
     *
     * Example: String unit = tomato.getUnit(); // Gets "kg"
     */
    public String getUnit() {
        // We're simply handing back the unit value
        // This is like reading the measurement unit from the recipe card
        return unit;
    }


    // ========== SETTERS ==========
    // These methods let other parts of the app CHANGE the ingredient's data

    /**
     * SET ID METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In IngredientDAO.addIngredient(Ingredient ing):
     *       - After inserting into database, we get the new ID:
     *       - long id = database.insert(TABLE_NAME, null, values);
     *       - ingredient.setId((int) id);
     *       - Now the Ingredient object knows its database ID
     *       - We return this updated Ingredient to the Activity
     *
     *    2. In rare cases where we need to manually update the ID
     *
     * This method changes the ingredient's ID number.
     * We use this when the database assigns an auto-generated ID to a newly saved ingredient.
     *
     * @param id - The new ID number to assign
     *
     * Example: After saving to database, the database returns an ID like 5.
     * We call: tomato.setId(5); // Now tomato.id is 5
     */
    public void setId(int id) {
        // We're taking the id value that was passed in and storing it
        // in this ingredient's id property
        // This is like the filing cabinet saying "Your recipe card #5 is filed"
        this.id = id;
    }

    /**
     * SET NAME METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In Add Ingredient Activity:
     *       - User types ingredient name in EditText
     *       - String name = editTextName.getText().toString();
     *       - ingredient.setName(name);
     *
     *    2. In Edit Ingredient Activity:
     *       - User modifies the name
     *       - String newName = editTextName.getText().toString();
     *       - ingredient.setName(newName);
     *       - ingredientDAO.updateIngredient(ingredient);
     *
     *    3. In database population (pre-loading recipes):
     *       - When adding sample ingredients during app setup
     *       - ingredient.setName("Tomato");
     *       - ingredient.setQuantity("5");
     *       - etc.
     *
     * This method changes the ingredient's name.
     * We use this when the user edits an ingredient's name.
     *
     * @param name - The new name to assign
     *
     * Example: User changes "Tomato" to "Roma Tomato"
     * We call: tomato.setName("Roma Tomato");
     */
    public void setName(String name) {
        // We're taking the new name and storing it in this ingredient's name property
        // This is like updating the ingredient name on the recipe card
        this.name = name;
    }

    /**
     * SET QUANTITY METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In Add Ingredient Activity:
     *       - User types quantity in EditText
     *       - String quantity = editTextQuantity.getText().toString();
     *       - ingredient.setQuantity(quantity);
     *
     *    2. In Edit Ingredient Activity:
     *       - User modifies the quantity
     *       - String newQuantity = editTextQuantity.getText().toString();
     *       - ingredient.setQuantity(newQuantity);
     *       - ingredientDAO.updateIngredient(ingredient);
     *
     *    3. In database population (pre-loading ingredients):
     *       - ingredient.setQuantity("5");
     *
     *    4. When user consumes an ingredient:
     *       - ingredient.setQuantity("4.5"); // Used 0.5 from original 5
     *       - ingredientDAO.updateIngredient(ingredient);
     *
     * This method changes the ingredient's quantity.
     * We use this when the user edits how much of the ingredient they have.
     *
     * @param quantity - The new quantity to assign
     *
     * Example: User changes quantity from "5" to "10"
     * We call: tomato.setQuantity("10");
     */
    public void setQuantity(String quantity) {
        // We're taking the new quantity and storing it in this ingredient's quantity property
        // This is like updating the amount on the recipe card
        this.quantity = quantity;
    }

    /**
     * SET UNIT METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In Add Ingredient Activity:
     *       - User selects unit from Spinner dropdown
     *       - String unit = spinnerUnit.getSelectedItem().toString();
     *       - ingredient.setUnit(unit);
     *
     *    2. In Edit Ingredient Activity:
     *       - User changes the unit
     *       - String newUnit = spinnerUnit.getSelectedItem().toString();
     *       - ingredient.setUnit(newUnit);
     *       - ingredientDAO.updateIngredient(ingredient);
     *
     *    3. In database population (pre-loading ingredients):
     *       - ingredient.setUnit("kg");
     *
     *    4. When converting units (future feature):
     *       - ingredient.setUnit("g"); // Convert from kg to grams
     *       - ingredient.setQuantity("5000"); // Adjust quantity accordingly
     *
     * This method changes the ingredient's unit.
     * We use this when the user edits the measurement unit.
     *
     * @param unit - The new unit to assign
     *
     * Example: User changes unit from "kg" to "g"
     * We call: tomato.setUnit("g");
     */
    public void setUnit(String unit) {
        // We're taking the new unit and storing it in this ingredient's unit property
        // This is like updating the measurement unit on the recipe card
        this.unit = unit;
    }


    // ========== toString() METHOD ==========
    // This method creates a readable text representation of the ingredient

    /**
     * TO STRING METHOD
     *
     * 📍 USAGE LOCATIONS:
     *    1. In Android Studio Logcat (debugging):
     *       - Log.d("Ingredient", ingredient.toString());
     *       - Output: "Ingredient{id=1, name='Tomato', quantity='5', unit='kg'}"
     *
     *    2. When printing to console for testing:
     *       - System.out.println(ingredient);
     *
     *    3. In RecyclerView Adapter (optional):
     *       - Sometimes used to create quick debug strings
     *
     *    4. In error handling:
     *       - Helps display what ingredient was being processed when error occurred
     *
     * This method converts the ingredient into a readable text format.
     * We use this for debugging and logging (seeing what's happening behind the scenes).
     * It's automatically called when we print the ingredient object.
     *
     * @return A readable string showing all ingredient details
     *
     * Example: System.out.println(tomato);
     * Output: "Ingredient{id=1, name='Tomato', quantity='5', unit='kg'}"
     */
    @Override
    public String toString() {
        // We're creating a sentence-like text that shows all the ingredient's data
        // This helps us understand what's stored inside the object when debugging
        // Format: "Ingredient{id=X, name='Y', quantity='Z', unit='U'}"
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
