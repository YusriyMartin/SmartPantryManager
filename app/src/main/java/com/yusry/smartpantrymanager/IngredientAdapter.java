package com.yusry.smartpantrymanager;

//Importing resources needed
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.data.Ingredient;
import java.util.List;

// Adapter bridges ingredient list to RecyclerView
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.VH> {
    Context ctx;
    List<Ingredient> list;
    boolean showExpiry;

    public IngredientAdapter(Context ctx, List<Ingredient> list, boolean showExpiry) {
        this.ctx = ctx;
        this.list = list;
        this.showExpiry = showExpiry;
    }

    // Hold references to views in each row
    public static class VH extends RecyclerView.ViewHolder {
        TextView name, qty, unit, expiry;

        public VH(View v) {
            super(v);
            name = v.findViewById(R.id.txtName);
            qty = v.findViewById(R.id.txtQty);
            unit = v.findViewById(R.id.txtUnit);
            expiry = v.findViewById(R.id.txtExpiry);
        }
    }

    // Refresh list on data changes
    public void setIngredients(List<Ingredient> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup p, int t) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_ingredient, p, false));
    }

    @Override
    public void onBindViewHolder(VH h, int pos) {
        Ingredient i = list.get(pos);
        h.name.setText(i.name);
        h.qty.setText(String.valueOf(i.quantity));
        h.unit.setText(i.unit);

        // Show expiry date if user selected
        if (showExpiry) {
            h.expiry.setVisibility(View.VISIBLE);
            h.expiry.setText("Use by " + i.expiryDate);
        } else {
            h.expiry.setVisibility(View.GONE);
        }

        // Making itemView clickable to edit
        h.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, AddEditActivity.class);
            intent.putExtra("id", i.id);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
