package com.yusry.smartpantrymanager.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.yusry.smartpantrymanager.R;

// Settings screen for user prefs
public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button btnNotifyYes, btnNotifyNo;
    private Spinner spinnerUnits;
    private Button btnSaveSettings;
    private boolean notificationsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // init SharedPreferences
        sharedPreferences = getSharedPreferences("SmartPantryPrefs", MODE_PRIVATE);

        // find views
        btnNotifyYes = findViewById(R.id.btnNotifyYes);
        btnNotifyNo = findViewById(R.id.btnNotifyNo);
        spinnerUnits = findViewById(R.id.spinnerUnits);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // load saved prefs
        notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true);
        String savedUnit = sharedPreferences.getString("unit_preference", "Kg");

        // set notification button states
        updateNotificationButtons(notificationsEnabled);

        // setup spinner with unit options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.unit_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnits.setAdapter(adapter);

        // set spinner to saved unit
        int unitPosition = savedUnit.equals("Lbs") ? 1 : 0;
        spinnerUnits.setSelection(unitPosition);

        // notification button listeners
        btnNotifyYes.setOnClickListener(v -> updateNotificationButtons(true)); // click yes, set true
        btnNotifyNo.setOnClickListener(v -> updateNotificationButtons(false)); // click no, set false

        // save button listener
        btnSaveSettings.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // get current notification state
            editor.putBoolean("notifications_enabled", notificationsEnabled);

            // get selected unit from spinner
            String selectedUnit = spinnerUnits.getSelectedItem().toString();
            editor.putString("unit_preference", selectedUnit);

            editor.apply(); // save to SharedPreferences
            Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show(); // show confirmation
        });
    }

    // toggle notification button states based on selection
    private void updateNotificationButtons(boolean notifEnabled) {
        notificationsEnabled = notifEnabled;

        // set button colors to show selection
        if (notifEnabled) {
            btnNotifyYes.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark)); // yes highlighted
            btnNotifyNo.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white)); // no normal
        } else {
            btnNotifyYes.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white)); // yes normal
            btnNotifyNo.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark)); // no highlighted
        }
    }
}


