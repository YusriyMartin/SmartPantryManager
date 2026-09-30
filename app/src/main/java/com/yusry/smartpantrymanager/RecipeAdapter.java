package com.yusry.smartpantrymanager;
//imoirts resources
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.data.Recipe;
import java.util.List;
// Adapter for RecyclerView
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {
    Context ctx;
    List<Recipe> list;
    public RecipeAdapter(Context ctx, List<Recipe> list) {
        this.ctx = ctx;
        this.list = list;
    }
    // ViewHolder has recipe name
    public static class VH extends RecyclerView.ViewHolder {
        TextView name;
        public VH(View v) {
            super(v);
            name = v.findViewById(R.id.txtRecipeName);
        }
    }
    // Inflate recipe item layout
    @Override
    public VH onCreateViewHolder(ViewGroup p, int t) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_recipe, p, false));
    }
    // Binds recipe data to views
    @Override
    public void onBindViewHolder(VH h, int pos) {
        Recipe r = list.get(pos);
        h.name.setText((pos + 1) + ". " + r.name); // Display recipe, and adds a number, like 1. Malva Pudding
        // Cick on rown to view details
        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, RecipeDetailActivity.class);
            i.putExtra("recipeId", r.id);
            ctx.startActivity(i);
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
