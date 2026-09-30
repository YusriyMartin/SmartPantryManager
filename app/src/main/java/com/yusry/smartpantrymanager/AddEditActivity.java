package com.yusry.smartpantrymanager;

// Import resources
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.yusry.smartpantrymanager.data.*;
import java.util.Calendar;
import java.util.concurrent.Executors;

// Class to Edit ingredient
public class AddEditActivity extends AppCompatActivity {
    EditText edtName, edtQty, edtExpiry;
    Spinner spinner;
    Button btnSave, btnDelete;
    AppDao dao;
    int editId = -1;
    Ingredient editing;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_add_edit);

        edtName = findViewById(R.id.edtName);
        edtQty = findViewById(R.id.edtQty);
        edtExpiry = findViewById(R.id.edtExpiry);
        spinner = findViewById(R.id.spinnerUnit);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        dao = AppDatabase.getInstance(this).dao();

        // Gets user measurement from settings
        SharedPreferences p = getSharedPreferences("settings", MODE_PRIVATE);
        String pref = p.getString("unit_pref", "Kg");

        // Dropdown for unit selection
        String[] units = new String[]{"Kg", "g", "Lbs", "cups", "pcs"};

        // Array adapter to bridge to data, context to indicate unit running
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, R.layout.spinner_item, units);
        ad.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(ad);

        // Keeps user preference
        int pos = ad.getPosition(pref);
        if (pos >= 0) spinner.setSelection(pos);
        else spinner.setSelection(0);

        // Checks if editing ingredient
        editId = getIntent().getIntExtra("id", -1);

        // Date picker for expiry (yyyy,mm,dd)
        edtExpiry.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) ->
                    edtExpiry.setText(y + "-" + (m + 1) + "-" + d),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // if editing, load ingredient and show delete button
      if (editId != -1) {
            btnDelete.setVisibility(Button.VISIBLE);
            Executors.newSingleThreadExecutor().execute(() -> {
                editing = dao.getIngredientById(editId);
                runOnUiThread(() -> {
                    edtName.setText(editing.name);
                    edtQty.setText(String.valueOf(editing.quantity));
                    edtExpiry.setText(editing.expiryDate);
                    spinner.setSelection(ad.getPosition(editing.unit));
                });
            });
        }

        // Save button
        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String qtyStr = edtQty.getText().toString().trim();
            String unit = spinner.getSelectedItem().toString();

            // Validating user selection
            if (unit.equals("Select unit")) {
                Toast.makeText(this, "Hey chef, you forgot to select a measurement unit",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String exp = edtExpiry.getText().toString().trim();

            // Validate all feilds
            if (name.isEmpty() || qtyStr.isEmpty() || exp.isEmpty()) {
                Toast.makeText(this, "Hey chef! You need to add more than that...", Toast.LENGTH_SHORT).show();
                return;
            }

            double qty = Double.parseDouble(qtyStr);

            // Async to db
            Executors.newSingleThreadExecutor().execute(() -> {
                if (editId == -1) {
                    // Loads new ingredient
                    dao.insertIngredient(new Ingredient(name, qty, unit, exp));
                } else {
                    // Updating to existing and making lowercase
                    editing.name = name.toLowerCase();
                    editing.quantity = qty;
                    editing.unit = unit;
                    editing.expiryDate = exp;
                    dao.updateIngredient(editing);
                }
                runOnUiThread(() -> {
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });

        //Delete from db
        btnDelete.setOnClickListener(v ->
                Executors.newSingleThreadExecutor().execute(() -> {
                    dao.deleteIngredient(editing);
                    runOnUiThread(this::finish);
                }));
    }
    @Override
    protected void onPause() {
        super.onPause();
        // FIX for WindowLeaked - closes spinner popup
        try {
            findViewById(R.id.spinnerPref).clearFocus();
        } catch (Exception e) {}
    }
}