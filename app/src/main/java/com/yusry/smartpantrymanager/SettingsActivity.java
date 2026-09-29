package com.yusry.smartpantrymanager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_settings);
        RadioButton rbYes=findViewById(R.id.rbYes);
        RadioButton rbNo=findViewById(R.id.rbNo);
        Spinner spinner=findViewById(R.id.spinnerPref);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Kg","Lbs"}));
        SharedPreferences prefs=getSharedPreferences("settings", MODE_PRIVATE);
        boolean notify=prefs.getBoolean("notify_expiry", true);
        if(notify) rbYes.setChecked(true); else rbNo.setChecked(true);
        String unit=prefs.getString("unit_pref","Kg"); spinner.setSelection(unit.equals("Kg")?0:1);
        findViewById(R.id.btnSaveSettings).setOnClickListener(v->{
            prefs.edit().putBoolean("notify_expiry", rbYes.isChecked()).putString("unit_pref", spinner.getSelectedItem().toString()).apply();
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show(); finish();
        });
    }
}