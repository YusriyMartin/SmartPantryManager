package com.yusry.smartpantrymanager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import com.yusry.smartpantrymanager.data.*;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView txtEmpty;
    AppDao dao;
    IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.rvIngredients);
        txtEmpty=findViewById(R.id.txtEmpty);
        rv.setLayoutManager(new LinearLayoutManager(this));

        dao=AppDatabase.getInstance(this).dao();

        // empty adapter first so we can update it later
        adapter = new IngredientAdapter(this, new java.util.ArrayList<>(), true);
        rv.setAdapter(adapter);

        findViewById(R.id.btnAdd).setOnClickListener(v-> startActivity(new Intent(this, AddEditActivity.class)));
        findViewById(R.id.btnRecipes).setOnClickListener(v-> startActivity(new Intent(this, RecipesActivity.class)));
        findViewById(R.id.btnSettings).setOnClickListener(v-> startActivity(new Intent(this, SettingsActivity.class)));
    }

    @Override
    protected void onResume(){
        super.onResume();
        load(); // only ONE onResume
    }

    void load(){
        Executors.newSingleThreadExecutor().execute(()->{
            List<Ingredient> list=dao.getAllIngredients();
            SharedPreferences p=getSharedPreferences("settings", MODE_PRIVATE);
            boolean showExpiry=p.getBoolean("notify_expiry", true);

            runOnUiThread(()->{
                if(list.isEmpty()){
                    txtEmpty.setVisibility(TextView.VISIBLE);
                    rv.setVisibility(RecyclerView.GONE);
                } else {
                    txtEmpty.setVisibility(TextView.GONE);
                    rv.setVisibility(RecyclerView.VISIBLE);
                    rv.setAdapter(new IngredientAdapter(this, list, showExpiry));
                    // if setIngredients doesn't exist, use:
                    // rv.setAdapter(new IngredientAdapter(this, list, showExpiry));
                }
            });
        });
    }
}