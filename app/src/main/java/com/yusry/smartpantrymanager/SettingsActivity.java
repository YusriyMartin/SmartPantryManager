package com.yusry.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

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
        btnNotifyYes.setOnClickListener(v -> updateNotificationButtons(true));
        btnNotifyNo.setOnClickListener(v -> updateNotificationButtons(false));

        // save button listener
        btnSaveSettings.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // get current notification state
            editor.putBoolean("notifications_enabled", notificationsEnabled);

            // get selected unit from spinner
            String selectedUnit = spinnerUnits.getSelectedItem().toString();
            editor.putString("unit_preference", selectedUnit);

            editor.apply(); // save to SharedPreferences
        });
    }

    // toggle notification button states based on selection
    private void updateNotificationButtons(boolean notifEnabled) {
        notificationsEnabled = notifEnabled;
        btnNotifyYes.setSelected(notifEnabled);
        btnNotifyNo.setSelected(!notifEnabled);
    }
}
