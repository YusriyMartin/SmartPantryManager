package com.yusry.smartpantrymanager.adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yusry.smartpantrymanager.R;
import com.yusry.smartpantrymanager.RecipeDetailActivity;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.List;


// For displaying recipes in RecycleView
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipes> recipeList; // Recipe List

    private Context context; //Context required to lauch oyher activities

    // Constructor initialize adapter with recipes
    public RecipeAdapter(List<Recipes> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    // Called when recylerview needs new viewholder
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(context) // Template for recipe row
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    // Method for displaying a recipe at specific position in Recyclerview
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipes recipe = recipeList.get(position); // Get recipe from list
        holder.recipeNameTextView.setText(recipe.getName()); // Add recipe name into text view.
        holder.itemView.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId()); // Passes recipe ID, so recipe detailed activity can know which recipe to show
            context.startActivity(intent);
        });
    }

    // Returns total number of recipes in list
    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Helper class for holding references in a single recipe row
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        // Showa recipe name
        public TextView recipeNameTextView;

        // Constructor to find views inside row and store them
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.tvRecipeName); // find recipe name
        }
    }
}
