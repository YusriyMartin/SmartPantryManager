package com.yusry.smartpantrymanager.adapters;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.Activity.AddEditIngredientActivity;
import com.yusry.smartpantrymanager.R;
import com.yusry.smartpantrymanager.database.IngredientDAO;
import com.yusry.smartpantrymanager.models.Ingredient;
import java.util.List;

// To convert to recycleview. Handles Delete and Edit
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredientList; //Stores ingredient List
    private final IngredientDAO ingredientDAO; // Stores DAO for deleting and updating ingredients
    private final AppCompatActivity activity; //Stores activity context, for launching new activities

    // Constructor for new ingredientAdapter
    public IngredientAdapter(List<Ingredient> ingredientList, IngredientDAO ingredientDAO, AppCompatActivity activity) {
        this.ingredientList = ingredientList; // Stores ingredients in a list for later use
        this.ingredientDAO = ingredientDAO; //Store DAO for CRUD operations
        this.activity = activity; //Store activity
    }

    // For when recycleview needs to create new row
    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);// load ingredients
        return new IngredientViewHolder(view);
    }

    // For each row in list
    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position); // Gets ingredient in list
        holder.tvIngredientName.setText(ingredient.getName()); // display ingredient name
        String quantityUnitText = ingredient.getQuantity() + " " + ingredient.getUnit(); // Display for qty and unit
        holder.tvQuantityUnit.setText(quantityUnitText);

        // Handles deleting ingredients
        holder.btnDelete.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(activity)
                    .setTitle("Delete this ingredient?")
                    .setMessage("Chef, are you sure want to delete " + ingredient.getName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        ingredientDAO.deleteIngredient(ingredient.getId()); // remove from db
                        ingredientList.remove(position); // remove from list
                        notifyItemRemoved(position); // tell recycler view item gone
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Handles editing ingredients
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, AddEditIngredientActivity.class);
            intent.putExtra("ingredient_id", ingredient.getId()); // pass ingredient id to edit screen
            activity.startActivity(intent);
        });
    }

    // To tell how many rows to create
    @Override
    public int getItemCount() {
        // Return the total number of ingredients we have in the list
        return ingredientList.size();
    }

    // For new data arriving in database
    public void updateIngredients(List<Ingredient> newIngredientList) {
        this.ingredientList.clear(); // Replace old list with new list
        this.ingredientList.addAll(newIngredientList);
        notifyDataSetChanged(); // Notify if updated
    }

    public void updateData(List<Ingredient> newList) {
        this.ingredientList = newList;
        notifyDataSetChanged();
    }


    // Conatiner for ingredients
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        // Reference to the TextView that displays ingredient name
        TextView tvIngredientName; // Ingredient Name
        TextView tvQuantityUnit; // Displays Qty
        ImageButton btnDelete; // Delete reference
        ImageButton btnEdit;//Edit reference

        // Constructor for Ingredients View Holder
        public IngredientViewHolder(View itemView) {
            super(itemView); // Calls parent constuctor
            tvIngredientName = itemView.findViewById(R.id.tvIngredientName); // Find ingredient by name
            tvQuantityUnit = itemView.findViewById(R.id.tvQuantityUnit); // Find qty
            btnDelete = itemView.findViewById(R.id.btnDelete); // Find the delete button by id
            btnEdit = itemView.findViewById(R.id.btnEdit); // Find the edit button by id
        }
    }
}

