package com.yusry.smartpantrymanager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.database.PantryDatabaseHelper;
import com.yusry.smartpantrymanager.models.Ingredient;

// Allow users to add or edit ingredients with quantity controls
public class AddEditIngredientActivity extends AppCompatActivity {
    private PantryDatabaseHelper dbHelper; // reference db helper to access db
    private IngredientDAO ingredientDAO; // Reference to DAO to save and update ingredients
    private int ingredientId; // Reference to ingredients ID

    // References for UI, when adding text, values or clicking buttons
    private EditText etIngredientName;
    private EditText etQuantity;
    private Spinner spinnerUnit;
    private Button btnSave;
    private Button btnCancel;
    private Button btnQuantityMinus; // Decrease qty by 1
    private Button btnQuantityPlus; // Increase qty by 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState); // Call parent onCreate
        setContentView(R.layout.activity_add_edit_ingredient); // loads layout

        dbHelper = new PantryDatabaseHelper(this); // Initialize db helper to work with db
        ingredientDAO = new IngredientDAO(dbHelper); // Initialize DAO to save and update ingredients

        // get and store references to ingredient name, quantity, unit, save btn, cancel btn, and qty buttons
        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnQuantityMinus = findViewById(R.id.btnQuantityMinus);
        btnQuantityPlus = findViewById(R.id.btnQuantityPlus);

        setupUnitSpinner(); // Create unit spinner

        ingredientId = getIntent().getIntExtra("ingredient_id", -1); // Get ingredient ID from intent that launched this activity
        if (ingredientId != -1) {
            loadIngredientData(); // load data
        }

        // Button listeners
        btnSave.setOnClickListener(v -> saveIngredient()); // When user clicks save btn, ingredients saved to db
        btnCancel.setOnClickListener(v -> finish()); // When user clicks cancel, exit out
        btnQuantityMinus.setOnClickListener(v -> decrementQuantity()); // Decrease qty when minus clicked
        btnQuantityPlus.setOnClickListener(v -> incrementQuantity()); // Increase qty when plus clicked
    }

    // Setting up spinner. Creates drop down list of units of measurement for ingredients
    private void setupUnitSpinner() {
        // Create an array of measurement units
        String[] units = {"kg", "grams", "liters", "ml", "cups", "tablespoons", "teaspoons", "pieces", "lbs"};

        // Create an adapter to display units in the spinner dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        // Tell the adapter what layout to use when showing the dropdown list
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply adapter to spinner
        spinnerUnit.setAdapter(adapter);
    }

    // load existing ingredient data from db and fills form fields
    private void loadIngredientData() {

        Ingredient ingredient = ingredientDAO.getIngredientById(ingredientId); // Get ingredients from db by id

        // Checking whether ingredient found or not
        if (ingredient != null) {
            etIngredientName.setText(ingredient.getName()); // Display ingredient name in input field
            etQuantity.setText(String.valueOf(ingredient.getQuantity())); // Display qty of ingredient in input field

            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerUnit.getAdapter(); // finds index of current unit in spinner from dropdown, selects it
            int unitPosition = adapter.getPosition(ingredient.getUnit());
            spinnerUnit.setSelection(unitPosition); // Spinner to show current unit
        }
    }

    // Decrease qty by 1 when minus button pressed
    private void decrementQuantity() {
        String currentText = etQuantity.getText().toString().trim();
        if (!currentText.isEmpty()) {
            try {
                double current = Double.parseDouble(currentText);
                if (current > 0) {
                    etQuantity.setText(String.valueOf(current - 1)); // Set qty to one less
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Increase qty by 1 when plus button pressed
    private void incrementQuantity() {
        String currentText = etQuantity.getText().toString().trim();
        if (!currentText.isEmpty()) {
            try {
                double current = Double.parseDouble(currentText);
                etQuantity.setText(String.valueOf(current + 1)); // Set qty to one more
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
            }
        } else {
            etQuantity.setText("1"); // If field empty, set to 1
        }
    }

    // Saves ingredient data to db
    private void saveIngredient() {
        // Get the ingredient name that user typed into the input field
        String name = etIngredientName.getText().toString().trim(); // Get ingredient name from input field
        String quantityText = etQuantity.getText().toString().trim(); // Get qty from input field
        String unit = spinnerUnit.getSelectedItem().toString(); // Get unit

        // Check whether ingredient name empty, display message
        if (name.isEmpty()) {
            etIngredientName.setError("Hey Chef! Don't forget to your ingredient...");
            return;
        }
        // Check whether qty is empty, display message
        if (quantityText.isEmpty()) {
            etQuantity.setError("Hey Chef! Don't forget to add your quantity...");
            return;
        }

        try {
            double quantity = Double.parseDouble(quantityText); // Convert qty text to double

            // If statement checks whether -1, if -1 we add new ingredient
            if (ingredientId == -1) {

                // Create a new Ingredient object with the user input
                Ingredient newIngredient = new Ingredient();

                // Setting to what user added
                newIngredient.setName(name);
                newIngredient.setQuantity(quantity);
                newIngredient.setUnit(unit);

                // Save new ingredient to database using the DAO
                ingredientDAO.addIngredient(newIngredient);
                Toast.makeText(this, "Ingredient added!", Toast.LENGTH_SHORT).show();
            } else {
                // Get ingredient from db by id
                Ingredient existingIngredient = ingredientDAO.getIngredientById(ingredientId);

                // Check whether ingredient found
                if (existingIngredient != null) {
                    existingIngredient.setName(name); // Update with user input
                    existingIngredient.setQuantity(quantity); // Update qty
                    existingIngredient.setUnit(unit); // Update unit

                    // Save updated ingredient back to database
                    ingredientDAO.updateIngredient(existingIngredient);
                    Toast.makeText(this, "Ingredient updated!", Toast.LENGTH_SHORT).show();
                }
            }
            finish(); // Close, go back to MainActivity
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid quantity format", Toast.LENGTH_SHORT).show();
        }
    }
}
