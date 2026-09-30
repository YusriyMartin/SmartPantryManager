package com.yusry.smartpantrymanager;
// Import resources
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_settings);
        // Grabs the user interface references
        RadioButton rbYes = findViewById(R.id.rbYes);
        RadioButton rbNo = findViewById(R.id.rbNo);
        Spinner spinner = findViewById(R.id.spinnerPref);
        // setup spinner with custom layouts
        String[] units = new String[]{"Kg", "Lbs"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, units);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // Loads use preference (exp and unit of measure)
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean notify = prefs.getBoolean("notify_expiry", true);
        if(notify) rbYes.setChecked(true);
        else rbNo.setChecked(true);
        // Restores unit preference in spinner
        String unit = prefs.getString("unit_pref","Kg");
        int pos = adapter.getPosition(unit);
        if(pos >= 0) spinner.setSelection(pos);
        // Save button for preference and toast  show toast, close
        findViewById(R.id.btnSaveSettings).setOnClickListener(v->{
            prefs.edit()
                    .putBoolean("notify_expiry", rbYes.isChecked())
                    .putString("unit_pref", spinner.getSelectedItem().toString())
                    .apply();
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
