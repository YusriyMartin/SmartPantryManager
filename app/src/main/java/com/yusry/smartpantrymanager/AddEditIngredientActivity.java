package com.yusry.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.models.Ingredient;

/**
 * AddEditIngredientActivity - allows user to add a new ingredient or edit an existing one.
 * This screen has input fields for ingredient name, quantity, and unit (e.g., kg, lbs, etc.).
 */
public class AddEditIngredientActivity extends AppCompatActivity {

    // Reference to the database helper so we can access the database
    private PantryDatabaseHelper dbHelper;

    // Reference to the DAO so we can save/update ingredients
    private IngredientDAO ingredientDAO;

    // Reference to the ingredient ID we're editing (-1 means we're adding a new one)
    private int ingredientId;

    // UI References - these are the input fields user will type into
    private EditText etIngredientName;
    private EditText etQuantity;
    private Spinner spinnerUnit;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call parent onCreate to initialize the activity
        super.onCreate(savedInstanceState);
        // Load the layout for this activity from activity_main.xml
        setContentView(R.layout.activity_add_edit_ingredient);

        // Initialize the database helper so we can work with the database
        dbHelper = new PantryDatabaseHelper(this);
        // Initialize the DAO so we can save/update ingredient data
        ingredientDAO = new IngredientDAO(dbHelper);

        // Find and store reference to the ingredient name input field
        etIngredientName = findViewById(R.id.etIngredientName);
        // Find and store reference to the quantity input field
        etQuantity = findViewById(R.id.etQuantity);
        // Find and store reference to the unit dropdown spinner
        spinnerUnit = findViewById(R.id.spinnerUnit);
        // Find and store reference to the Save button
        btnSave = findViewById(R.id.btnSave);
        // Find and store reference to the Cancel button
        btnCancel = findViewById(R.id.btnCancel);

        // Set up the unit spinner with common measurement units
        setupUnitSpinner();

        // Get the ingredient ID from the intent that launched this activity
        // The MainActivity passes "ingredient_id" extra: -1 for new, or the ID for editing
        ingredientId = getIntent().getIntExtra("ingredient_id", -1);

        // If ingredientId is NOT -1, we're editing an existing ingredient, so load its data
        if (ingredientId != -1) {
            // Load the ingredient from database and populate the form fields
            loadIngredientData();
        }

        // When user clicks Save button, save the ingredient to database
        btnSave.setOnClickListener(v -> saveIngredient());

        // When user clicks Cancel button, just go back without saving
        btnCancel.setOnClickListener(v -> finish());
    }

    /**
     * setupUnitSpinner - sets up the dropdown list of measurement units.
     * Common units like kg, grams, liters, etc.
     */
    private void setupUnitSpinner() {
        // Create an array of measurement units that user can choose from
        String[] units = {"kg", "grams", "liters", "ml", "cups", "tablespoons", "teaspoons", "pieces", "lbs"};

        // Create an adapter that will display these units in the spinner dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        // Tell the adapter what layout to use when showing the dropdown list
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner so it displays the units
        spinnerUnit.setAdapter(adapter);
    }

    /**
     * loadIngredientData - loads existing ingredient data from database and fills the form fields.
     * This only runs when user is EDITING an ingredient (not adding a new one).
     */
    private void loadIngredientData() {
        // Get the ingredient from database using its ID
        Ingredient ingredient = ingredientDAO.getIngredientById(ingredientId);

        // If we found the ingredient (not null), fill the form with its data
        if (ingredient != null) {
            // Display the ingredient name in the name input field
            etIngredientName.setText(ingredient.getName());
            // Display the quantity in the quantity input field
            etQuantity.setText(String.valueOf(ingredient.getQuantity()));

            // Find the index of the current unit in the spinner dropdown and select it
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerUnit.getAdapter();
            int unitPosition = adapter.getPosition(ingredient.getUnit());
            // Set the spinner to show the current unit
            spinnerUnit.setSelection(unitPosition);
        }
    }

    /**
     * saveIngredient - saves the ingredient data to database.
     * This runs when user clicks the Save button.
     */
    private void saveIngredient() {
        // Get the ingredient name that user typed into the input field
        String name = etIngredientName.getText().toString().trim();
        // Get the quantity that user typed (convert from text to number)
        String quantityText = etQuantity.getText().toString().trim();
        // Get the unit that user selected from the dropdown
        String unit = spinnerUnit.getSelectedItem().toString();

        // Check if user left the name field empty - if so, show error and don't save
        if (name.isEmpty()) {
            // Show error message to user (they didn't enter a name)
            etIngredientName.setError("Please enter ingredient name");
            return; // Stop the save operation
        }

        // Check if user left the quantity field empty - if so, show error and don't save
        if (quantityText.isEmpty()) {
            // Show error message to user (they didn't enter a quantity)
            etQuantity.setError("Please enter quantity");
            return; // Stop the save operation
        }

        // Convert the quantity text to a number (double) so we can store it
        double quantity = Double.parseDouble(quantityText);

        // If ingredientId is -1, we're ADDING a new ingredient
        if (ingredientId == -1) {
            // Create a new Ingredient object with the user's input
            Ingredient newIngredient = new Ingredient();
            // Set the name property to what user typed
            newIngredient.setName(name);
            // Set the quantity property to the number they entered
            newIngredient.setQuantity(quantity);
            // Set the unit property to what they selected from dropdown
            newIngredient.setUnit(unit);

            // Save the new ingredient to database using the DAO
            ingredientDAO.addIngredient(newIngredient);
        } else {
            // We're EDITING an existing ingredient, so load it, update it, and save it back
            // Get the ingredient from database using its ID
            Ingredient existingIngredient = ingredientDAO.getIngredientById(ingredientId);

            // Make sure we found the ingredient (it should exist if we're editing)
            if (existingIngredient != null) {
                // Update the name with what user typed
                existingIngredient.setName(name);
                // Update the quantity with what user entered
                existingIngredient.setQuantity(quantity);
                // Update the unit with what user selected
                existingIngredient.setUnit(unit);

                // Save the updated ingredient back to database
                ingredientDAO.updateIngredient(existingIngredient);
            }
        }

        // Close this activity and go back to MainActivity
        finish();
    }
}
