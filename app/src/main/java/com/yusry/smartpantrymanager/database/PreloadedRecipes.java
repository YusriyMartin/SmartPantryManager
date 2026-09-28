package com.yusry.smartpantrymanager.database;

import android.content.Context;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.ArrayList;
import java.util.List;

/**
 * PreloadedRecipes class - loads 20 sample recipes into the database on first app launch.
 * This ensures users see recipes immediately without a blank screen.
 */
public class PreloadedRecipes {

    // We're creating a helper method that returns a list of pre-made Recipes
    // Think of it like a cookbook—each recipe has a name, description, ingredients, and cooking method
    public static List<Recipes> getPreloadedRecipes() {
        // Create an empty list to hold all our recipes
        List<Recipes> recipes = new ArrayList<>();

        // Recipe 1: Tomato Sauce
        // The ingredientList is stored as a STRING with ingredients separated by commas
        recipes.add(new Recipes(
                "Tomato Sauce",
                "A classic Italian tomato-based sauce perfect for pasta",
                "tomatoes,garlic,olive oil,salt,pepper",
                "1. Chop tomatoes. 2. Sauté garlic in olive oil. 3. Add tomatoes, salt, and pepper. 4. Simmer for 20 minutes."
        ));

        // Recipe 2: Caesar Salad
        recipes.add(new Recipes(
                "Caesar Salad",
                "Crispy lettuce with parmesan cheese and creamy dressing",
                "lettuce,parmesan,croutons,caesar dressing,lemon",
                "1. Wash and chop lettuce. 2. Add croutons and parmesan. 3. Drizzle with caesar dressing. 4. Squeeze lemon and serve."
        ));

        // Recipe 3: Pasta Carbonara
        recipes.add(new Recipes(
                "Pasta Carbonara",
                "Creamy pasta with eggs, bacon, and parmesan cheese",
                "pasta,eggs,bacon,parmesan,salt,pepper",
                "1. Cook pasta. 2. Fry bacon until crispy. 3. Mix eggs and parmesan. 4. Combine hot pasta with eggs and bacon. 5. Season with salt and pepper."
        ));

        // Recipe 4: Garlic Bread
        recipes.add(new Recipes(
                "Garlic Bread",
                "Crispy bread with garlic and butter",
                "bread,garlic,butter,parsley,salt",
                "1. Mix butter with minced garlic and parsley. 2. Spread on bread slices. 3. Bake at 375°F for 10 minutes until golden."
        ));

        // Recipe 5: Chicken Stir Fry
        recipes.add(new Recipes(
                "Chicken Stir Fry",
                "Tender chicken with fresh vegetables in a savory soy sauce",
                "chicken,soy sauce,bell peppers,broccoli,garlic,oil",
                "1. Heat oil in a wok. 2. Stir-fry chicken until cooked. 3. Add vegetables and garlic. 4. Pour soy sauce and stir well. 5. Serve hot."
        ));

        // Recipe 6: Margherita Pizza
        recipes.add(new Recipes(
                "Margherita Pizza",
                "Fresh pizza with mozzarella, tomatoes, and basil",
                "pizza dough,tomato sauce,mozzarella,basil,olive oil",
                "1. Spread tomato sauce on dough. 2. Add mozzarella pieces. 3. Drizzle with olive oil. 4. Bake at 475°F for 12 minutes. 5. Top with fresh basil."
        ));

        // Recipe 7: Vegetable Soup
        recipes.add(new Recipes(
                "Vegetable Soup",
                "A hearty soup loaded with fresh vegetables",
                "carrots,celery,onions,potatoes,vegetable broth,salt,pepper",
                "1. Chop all vegetables. 2. Sauté onions and celery. 3. Add carrots and potatoes. 4. Pour in broth and simmer 30 minutes. 5. Season with salt and pepper."
        ));

        // Recipe 8: Fish Tacos
        recipes.add(new Recipes(
                "Fish Tacos",
                "Light tacos with seasoned fish and fresh toppings",
                "fish,tortillas,cabbage,lime,cilantro,sour cream",
                "1. Season and grill fish. 2. Shred cabbage. 3. Warm tortillas. 4. Assemble tacos with fish and cabbage. 5. Top with cilantro and sour cream. 6. Squeeze lime juice."
        ));

        // Recipe 9: Chocolate Chip Cookies
        recipes.add(new Recipes(
                "Chocolate Chip Cookies",
                "Soft and chewy cookies loaded with chocolate chips",
                "flour,butter,sugar,eggs,chocolate chips,vanilla extract,baking soda",
                "1. Mix butter and sugar. 2. Add eggs and vanilla. 3. Blend in flour and baking soda. 4. Fold in chocolate chips. 5. Bake at 350°F for 12 minutes."
        ));

        // Recipe 10: Fried Rice
        recipes.add(new Recipes(
                "Fried Rice",
                "Asian-style rice with eggs and vegetables",
                "rice,eggs,soy sauce,carrots,peas,oil,garlic",
                "1. Heat oil in wok. 2. Scramble eggs and set aside. 3. Stir-fry garlic, carrots, and peas. 4. Add rice and soy sauce. 5. Mix in eggs. 6. Serve hot."
        ));

        // Recipe 11: Beef Tacos
        recipes.add(new Recipes(
                "Beef Tacos",
                "Delicious tacos with seasoned ground beef",
                "ground beef,tortillas,lettuce,tomatoes,cheese,salsa",
                "1. Brown ground beef with taco seasoning. 2. Warm tortillas. 3. Assemble with beef, lettuce, and tomatoes. 4. Top with cheese and salsa."
        ));

        // Recipe 12: Greek Salad
        recipes.add(new Recipes(
                "Greek Salad",
                "Fresh vegetables with Feta cheese and olives",
                "lettuce,tomatoes,cucumbers,olives,feta cheese,olive oil",
                "1. Chop lettuce, tomatoes, and cucumbers. 2. Add olives and Feta chunks. 3. Drizzle with olive oil. 4. Toss and serve."
        ));

        // Recipe 13: Spaghetti Bolognese
        recipes.add(new Recipes(
                "Spaghetti Bolognese",
                "Pasta with a rich meat sauce",
                "spaghetti,ground beef,tomato sauce,garlic,onions,olive oil",
                "1. Brown ground beef. 2. Sauté onions and garlic. 3. Add tomato sauce. 4. Simmer 20 minutes. 5. Cook spaghetti and serve with sauce."
        ));

        // Recipe 14: Smoothie Bowl
        recipes.add(new Recipes(
                "Smoothie Bowl",
                "Creamy smoothie base topped with fresh fruit and granola",
                "yogurt,berries,banana,granola,honey,coconut flakes",
                "1. Blend yogurt, berries, and banana. 2. Pour into bowl. 3. Top with granola, coconut flakes, and drizzle of honey. 4. Serve immediately."
        ));

        // Recipe 15: Grilled Chicken Breast
        recipes.add(new Recipes(
                "Grilled Chicken Breast",
                "Tender and juicy grilled chicken with herbs",
                "chicken breast,olive oil,lemon,garlic,rosemary,salt,pepper",
                "1. Season chicken with salt, pepper, garlic, and rosemary. 2. Brush with olive oil. 3. Grill for 6-8 minutes per side. 4. Squeeze lemon juice. 5. Rest 5 minutes before serving."
        ));

        // Recipe 16: Mushroom Risotto
        recipes.add(new Recipes(
                "Mushroom Risotto",
                "Creamy rice dish with earthy mushrooms",
                "rice,mushrooms,white wine,vegetable broth,butter,parmesan,onions",
                "1. Sauté onions and mushrooms. 2. Add rice and toast. 3. Pour white wine. 4. Gradually add warm broth while stirring. 5. Finish with butter and parmesan."
        ));

        // Recipe 17: Lemon Cake
        recipes.add(new Recipes(
                "Lemon Cake",
                "Bright and zesty cake with a tangy lemon flavor",
                "flour,butter,sugar,eggs,lemon,baking powder,vanilla extract",
                "1. Cream butter and sugar. 2. Add eggs and vanilla. 3. Mix in flour and baking powder. 4. Fold in lemon zest and juice. 5. Bake at 350°F for 30 minutes."
        ));

        // Recipe 18: Baked Salmon
        recipes.add(new Recipes(
                "Baked Salmon",
                "Flaky salmon with fresh herbs and lemon",
                "salmon,lemon,dill,olive oil,salt,pepper,garlic",
                "1. Place salmon on baking sheet. 2. Season with salt, pepper, garlic, and dill. 3. Drizzle with olive oil. 4. Add lemon slices. 5. Bake at 400°F for 15 minutes."
        ));

        // Recipe 19: Caprese Salad
        recipes.add(new Recipes(
                "Caprese Salad",
                "Fresh tomatoes and mozzarella with basil and balsamic",
                "tomatoes,mozzarella,basil,balsamic vinegar,olive oil,salt",
                "1. Slice tomatoes and mozzarella. 2. Layer alternately. 3. Add fresh basil leaves. 4. Drizzle with olive oil and balsamic vinegar. 5. Season with salt."
        ));

        // Recipe 20: Beef Stew
        recipes.add(new Recipes(
                "Beef Stew",
                "Hearty stew with tender beef and vegetables",
                "beef,potatoes,carrots,celery,beef broth,onions,tomato paste,salt,pepper",
                "1. Brown beef cubes. 2. Sauté onions and celery. 3. Add carrots and potatoes. 4. Pour in broth and tomato paste. 5. Simmer 90 minutes until tender. 6. Season with salt and pepper."
        ));

        // Return the complete list of recipes
        return recipes;
    }

    /**
     * This method loads all preloaded recipes into the database using RecipeDAO.
     * It's called from MainActivity to populate the database on first launch.
     * We check if recipes already exist to avoid loading duplicates every time the app opens.
     */
    public static boolean loadRecipesIntoDB(Context context) {
        // Create a RecipeDAO instance using the context (not PantryDatabaseHelper!)
        RecipeDAO recipeDAO = new RecipeDAO(context);

        // Get all recipes currently in the database
        List<Recipes> existingRecipes = recipeDAO.getAllRecipes();

        // If recipes already exist in the database, don't load them again
        if (existingRecipes != null && !existingRecipes.isEmpty()) {
            // Recipes are already loaded, so return true (success)
            return true;
        }

        // Get the list of 20 preloaded recipes
        List<Recipes> preloadedRecipes = getPreloadedRecipes();

        // Loop through each recipe and add it to the database
        for (Recipes recipe : preloadedRecipes) {
            // Use RecipeDAO to insert this recipe into the SQLite database
            long result = recipeDAO.addRecipe(recipe);

            // Check if the insertion was successful
            if (result == -1) {
                // Insertion failed—return false to indicate an error occurred
                return false;
            }
        }

        // All recipes were successfully loaded into the database, so return true
        return true;
    }
}
