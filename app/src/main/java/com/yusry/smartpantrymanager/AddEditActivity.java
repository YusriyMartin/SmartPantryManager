package com.yusry.smartpantrymanager;
import android.app.DatePickerDialog; import android.content.SharedPreferences; import android.os.Bundle;
import android.widget.*; import androidx.appcompat.app.AppCompatActivity;
import com.yusry.smartpantrymanager.data.*;
import java.util.Calendar; import java.util.concurrent.Executors;
public class AddEditActivity extends AppCompatActivity {
    EditText edtName, edtQty, edtExpiry; Spinner spinner; Button btnSave, btnDelete;
    AppDao dao; int editId=-1; Ingredient editing;

    @Override protected void onCreate(Bundle b){

        super.onCreate(b); setContentView(R.layout.activity_add_edit);

        edtName = findViewById(R.id.edtName);
        edtQty = findViewById(R.id.edtQty);
        edtExpiry = findViewById(R.id.edtExpiry);
        spinner = findViewById(R.id.spinnerUnit);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        dao = AppDatabase.getInstance(this).dao();
        SharedPreferences p=getSharedPreferences("settings", MODE_PRIVATE);

        String pref = p.getString("unit_pref","Kg");

        String[] units = new String[]{"Select unit", "Kg", "g", "Lbs", "cups", "pcs"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, units);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(ad);

        // keeps user preference, when change - spinner
        int pos = ad.getPosition(pref);
        if(pos >= 0) spinner.setSelection(pos);
        else spinner.setSelection(0); // Display hint: Select unot


        editId = getIntent().getIntExtra("id",-1);
        edtExpiry.setOnClickListener(v->{ Calendar c=Calendar.getInstance();

            new DatePickerDialog(this,(view,y,m,d)->
                    edtExpiry.setText(y+"-"+(m+1)+"-"+d),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)).show();
        });
        if(editId!=-1){
            btnDelete.setVisibility(Button.VISIBLE);
            Executors.newSingleThreadExecutor().execute(()->{
                editing=dao.getIngredientById(editId);
                runOnUiThread(()->{
                    edtName.setText(editing.name);
                    edtQty.setText(String.valueOf(editing.quantity));
                    edtExpiry.setText(editing.expiryDate);
                    spinner.setSelection(ad.getPosition(editing.unit));
                });
            });
        }
        btnSave.setOnClickListener(v->{

            String name = edtName.getText().toString().trim();
            String qtyStr = edtQty.getText().toString().trim();
            String unit = spinner.getSelectedItem().toString();
            // If user did not select measurement
            if(unit.equals("Unit")){
                Toast.makeText(this,"Hey chef, you forgot to select a measurement unit",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String exp = edtExpiry.getText().toString().trim();

            if(name.isEmpty()||qtyStr.isEmpty()||exp.isEmpty()){
                Toast.makeText(this,"Fill all field.",Toast.LENGTH_SHORT).show(); return; }

            double qty = Double.parseDouble(qtyStr);
            Executors.newSingleThreadExecutor().execute(()->{

                if(editId ==- 1) dao.insertIngredient(new Ingredient(name,qty,unit,exp));

                else { editing.name=name.toLowerCase();
                    editing.quantity=qty;
                    editing.unit=unit;
                    editing.expiryDate=exp;
                    dao.updateIngredient(editing); }

                runOnUiThread(()->{
                    Toast.makeText(
                            this,
                            "Saved",Toast.LENGTH_SHORT).show(); finish(); });
            });
        });
        btnDelete.setOnClickListener(
                v-> Executors.newSingleThreadExecutor().execute(()->
                { dao.deleteIngredient(editing);
                    runOnUiThread(()->
                            finish()); }));
    }
}