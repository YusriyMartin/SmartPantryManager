package com.yusry.smartpantrymanager.database;
import android.content.Context;
import com.yusry.smartpantrymanager.models.Recipes;
import java.util.ArrayList;
import java.util.List;

// Loads preloaded recups
public class PreloadedRecipes {

    // Helper method that will return list of recipes
    public static List<Recipes> getPreloadedRecipes() {
        // Create list to hold all recipes
        List<Recipes> recipes = new ArrayList<>();

        // 1.Traditional South African Sosaties
        recipes.add(new Recipes(
                "Traditional South African Sosaties",
                "beef cubed (500g), onion (2 large, sliced), marinade soy sauce (75ml), worcestershire sauce (30ml), honey (60ml), garlic (4 cloves, minced), ginger (1 tablespoon, minced), black pepper, salt, bay leaves (3), thyme (1 teaspoon), skewers (soaked)",
                "Combine soy sauce, worcestershire, honey, garlic, ginger, pepper, salt, bay leaves and thyme in a bowl. Add beef cubes and marinate for minimum 4 hours or overnight. Thread beef onto skewers alternating with onion slices. Braai on medium-hot coals for 10-12 minutes, turning frequently. Baste with remaining marinade. Serve hot with pap and tomato relish.",
                "Traditional South African Sosaties"
        ));
        // 2 Buttermilk Rusks
        recipes.add(new Recipes(
                "Buttermilk Rusks",
                "self raising flour (4 cups), sugar (250g), butter (250g), eggs (3), buttermilk (1 cup), baking powder (2 teaspoons), salt (1 teaspoon), vanilla extract (1 teaspoon), aniseed (1 tablespoon, optional)",
                "Cream butter and sugar until light and fluffy. Beat in eggs one at a time. Combine flour, baking powder, salt and aniseed in separate bowl. Alternate adding flour mixture and buttermilk to butter mixture, starting and ending with flour. Pour into greased loaf tin and bake at 180°C for 50-60 minutes until golden. Cool, slice diagonally and bake slices at 160°C for 20-25 minutes until dry and crispy, turning halfway through.",
                "Buttermilk Rusks"
        ));
        // 3.Fatcake Burger
        recipes.add(new Recipes(
                "Fatcake Burger",
                "self raising flour (2 cups), sugar (1 tablespoon), salt (1 teaspoon), baking powder (2 teaspoons), water (1.5 cups), oil (for frying), ground beef (500g), onion (1, diced), garlic (2 cloves, minced), tomato sauce (30ml), worcestershire sauce (15ml), salt & pepper, lettuce (1 bunch), tomato (2, sliced), cheese (200g, sliced)",
                "Mix flour, sugar, salt and baking powder. Add water gradually to form thick batter. Heat oil and fry spoonfuls of batter until golden and puffy (fatcakes). Brown ground beef with onion and garlic, add tomato sauce, worcestershire, salt and pepper. Simmer 10 minutes. Split warm fatcakes and fill with beef mixture, lettuce, tomato and cheese. Serve immediately.",
                "Fatcake Burger"
        ));
        // 4.Boerewors Corn Dog
        recipes.add(new Recipes(
                "Boerewors Corn Dog",
                "boerewors (500g), cornmeal (1 cup), self raising flour (1 cup), sugar (2 tablespoons), baking powder (2 teaspoons), salt (1 teaspoon), milk (1 cup), egg (1), mustard (2 tablespoons), oil (for frying), wooden sticks",
                "Mix cornmeal, flour, sugar, baking powder and salt. Whisk milk and egg together, add to dry ingredients with mustard until smooth batter forms. Heat oil in pot. Pierce boerewors with stick. Dip boerewors in batter until fully coated. Fry 5-6 minutes until golden brown, turning occasionally. Drain on paper towel. Serve hot with tomato sauce and mustard.",
                "Boerewors Corn Dog"
        ));
        // 5.Malva Pudding
        recipes.add(new Recipes(
                "Malva Pudding",
                "self raising flour (1.5 cups), sugar (150g), butter (75g), egg (1), apricot jam (150ml), baking soda (1 teaspoon), vanilla extract (1 teaspoon), salt (pinch), hot water (250ml), cream (250ml), brown sugar (150g), butter (75g)",
                "Cream butter and sugar. Beat in egg and vanilla. Mix flour, baking soda and salt, fold into wet ingredients with jam. Pour into greased baking dish. For sauce: dissolve brown sugar and butter in hot water, pour over batter carefully (don't stir). Bake at 180°C for 35-40 minutes until spongy. Heat cream, pour warm sauce over pudding before serving. Serve warm with custard.",
                "Malva Pudding"
        ));
        // 6.Milk Tart (Melktert)
        recipes.add(new Recipes(
                "Milk Tart (Melktert)",
                "puff pastry (500g), milk (1 liter), cornstarch (60g), sugar (200g), egg (1), butter (30g), vanilla extract (1 teaspoon), cinnamon (1 tablespoon), salt (pinch)",
                "Line tart tin with puff pastry, prick with fork and bake blind at 200°C for 10 minutes. Whisk cornstarch with 50ml cold milk. Heat remaining milk with sugar and salt until steaming. Add cornstarch slurry, stirring constantly until thick. Cool slightly, whisk in egg, butter and vanilla. Pour filling into pastry case. Sprinkle cinnamon generously on top. Bake at 180°C for 20-25 minutes until cinnamon sugar caramelizes slightly. Chill before serving.",
                "Milk Tart (Melktert)"
        ));
        // 7.Inkomasi Scones
        recipes.add(new Recipes(
                "Inkomasi Scones",
                "self raising flour (2 cups), sugar (2 tablespoons), baking powder (1 teaspoon), salt (1 teaspoon), butter (100g), milk (750ml), egg (1, beaten), vanilla extract (1 teaspoon)",
                "Mix flour, sugar, baking powder and salt. Rub in cold butter until breadcrumb texture. Make well in center, pour in milk and vanilla, mix gently until just combined. Turn onto floured surface, handle minimally. Pat to 2cm thickness, cut into circles. Place on baking tray, brush with beaten egg. Bake at 200°C for 12-15 minutes until golden. Serve warm with jam and cream.",
                "Inkomasi Scones"
        ));
        //8.Microwave Pap
        recipes.add(new Recipes(
                "Microwave Pap",
                "cornmeal (1 cup), water (4 cups), salt (1 teaspoon), butter (30g), sugar (optional, 1 tablespoon)",
                "Pour water into microwave safe bowl and microwave 5 minutes until boiling. Slowly add cornmeal while stirring constantly to avoid lumps. Stir in salt. Microwave uncovered 10-12 minutes, stirring every 2-3 minutes, until smooth and thick. Add butter and sugar if desired. Stir well and serve hot.",
                "Microwave Pap"
        ));
        // 9.Simple Fatcake Recipe
        recipes.add(new Recipes(
                "Simple Fatcake Recipe",
                "self raising flour (2 cups), sugar (2 tablespoons), salt (1 teaspoon), baking powder (2 teaspoons), water (1.5 cups), oil (for frying), jam (for filling), cinnamon sugar (cinnamon + sugar mixture)",
                "Mix flour, sugar, salt and baking powder. Add water gradually, stirring until thick batter forms (don't overmix). Heat oil in deep pan. Drop spoonfuls of batter and fry 2-3 minutes each side until golden. Drain on paper towel. While warm, split fatcake and fill with jam. Roll in cinnamon sugar while still warm. Serve hot.",
                "Simple Fatcake Recipe"
        ));
        // 10.Magwinya
        recipes.add(new Recipes(
                "Magwinya",
                "self raising flour (2 cups), sugar (3 tablespoons), salt (1 teaspoon), water (1.5 cups), oil (for frying), mixed spice (1 teaspoon)",
                "Sift flour, sugar, salt and mixed spice together. Add water gradually, stirring until thick batter forms. Heat oil in deep pan or wok. Drop spoonfuls of batter and fry until golden brown on both sides (2-3 minutes total). Drain on paper towel. Serve warm as snack or with sweet sauce. Can be eaten plain or with jam.",
                "Magwinya"
        ));
        // 11.Dombolo with Sweet Corn
        recipes.add(new Recipes(
                "Dombolo with Sweet Corn",
                "self raising flour (2 cups), sugar (2 tablespoons), baking powder (1 teaspoon), salt (1 teaspoon), water (1.5 cups), butter (30g), sweet corn (400g, canned), chicken stock (1 liter), onion (1, diced), garlic (2 cloves, minced), thyme (1 teaspoon)",
                "Heat butter, saute onion and garlic until soft. Add sweet corn and stock, bring to boil. Mix flour, sugar, baking powder and salt. Add water gradually until dough forms. Drop spoonfuls of dough into boiling liquid. Simmer covered 20-25 minutes until dumplings are cooked through. Add thyme and season to taste. Serve hot.",
                "Dombolo with Sweet Corn"
        ));
        // 12.South African Potato Salad
        recipes.add(new Recipes(
                "South African Potato Salad",
                "potatoes (1kg, cubed), mayonnaise (500ml), gherkins (300g, sliced), celery (2 stalks, diced), apple (2, diced), onion (1, finely diced), salt (1 teaspoon), black pepper (1 teaspoon), fresh parsley (2 tablespoons, chopped)",
                "Boil potatoes until tender, drain and cool. Combine mayonnaise, gherkins, celery, apple and onion in large bowl. Add cooled potatoes and toss gently. Season with salt and pepper. Refrigerate for at least 30 minutes. Garnish with fresh parsley before serving. Serve cold as side dish.",
                "South African Potato Salad"
        ));
        // 13.Easy Boerewors Stew
        recipes.add(new Recipes(
                "Easy Boerewors Stew",
                "boerewors (500g, sliced), onions (2, sliced), potatoes (4, cubed), carrots (3, sliced), tomato sauce (400ml), beef stock (500ml), worcestershire sauce (15ml), bay leaves (2), thyme (1 teaspoon), salt & pepper",
                "Brown boerewors in large pot, remove and set aside. Saute onions until soft. Add potatoes, carrots, tomato sauce, stock, worcestershire, bay leaves and thyme. Return boerewors to pot. Simmer covered 30-40 minutes until vegetables are tender. Season with salt and pepper. Serve hot with pap or bread.",
                "Easy Boerewors Stew"
        ));
        // 14.Polony, Atchar and Cheese Fat Cake
        recipes.add(new Recipes(
                "Polony, Atchar and Cheese Fat Cake",
                "self raising flour (2 cups), sugar (1 tablespoon), salt (1 teaspoon), baking powder (2 teaspoons), water (1.5 cups), oil (for frying), polony (300g, sliced), atchar (200ml), cheddar cheese (200g, sliced), lettuce (1 bunch), tomato (2, sliced)",
                "Mix flour, sugar, salt and baking powder. Add water gradually for thick batter. Fry spoonfuls in hot oil until golden on both sides. Split warm fatcakes, fill with polony slices, spoon atchar over top, add cheese, lettuce and tomato. Serve immediately while fatcakes are still warm.",
                "Polony, Atchar and Cheese Fat Cake"
        ));
        // 15.Chicken Nugget Salad
        recipes.add(new Recipes(
                "Chicken Nugget Salad",
                "chicken nuggets (500g), lettuce (1 large, chopped), gherkins (380g, sliced), cheddar cheese (400g, cubed), tomatoes (4, cubed), mayonnaise (730g), olives (200g), salt & pepper, worcestershire braai spice (pinch), avocado (1-2, sliced), oil (750ml)",
                "Heat oil and fry nuggets until golden brown and cooked through. Drain on paper towel and cut into chunks. Chop lettuce and place in salad bowl. Add nugget chunks, sliced gherkins, cubed cheese, tomato cubes and olives. Mix in mayonnaise, add avocado slices. Season with salt, pepper and braai spice. Toss gently and refrigerate until serving.",
                "Chicken Nugget Salad"
        ));
        // 16.Chicken Curry with Sweet Potatoes and Homemade Dombolo
        recipes.add(new Recipes(
                "Chicken Curry with Sweet Potatoes and Homemade Dombolo",
                "chicken pieces (800g), sweet potatoes (4, cubed), carrots (3, sliced), onions (2, diced), salt & pepper, turmeric (1 teaspoon), curry powder (2 tablespoons), mixed dried herbs (1 teaspoon), self raising flour (2 cups), sugar (2 tablespoons), lukewarm water (1 cup)",
                "Dice onions and carrots, add to hot pan with oil. Season with herbs, spices, turmeric, salt and pepper. Add sweet potatoes and chicken pieces, mix well. Cover and simmer 10 minutes. For dombolo: mix flour, sugar and salt, add lukewarm water, form ball and knead slightly. Rest 15 minutes. Divide into 4 balls and carefully drop into curry. Cover pot tightly and simmer 25 minutes without opening until dombolo is firm and cooked through. Serve hot.",
                "Chicken Curry with Sweet Potatoes and Homemade Dombolo"
        ));
        // 17.Easy Beef Stew
        recipes.add(new Recipes(
                "Easy Beef Stew",
                "beef cubed (500g), onion (1, sliced), organium (1 teaspoon), ground cumin (1 teaspoon), ground coriander (1 teaspoon), knorr beef stock (2 cubes), Aromat original (1 teaspoon), water (2 cups), potatoes (4 medium, cubed), carrots (2 large, sliced)",
                "Fry onion with organium, cumin and coriander until fragrant. Add cubed beef and fry briefly. Add water and simmer 25 minutes. Add sliced carrots, cubed potatoes and beef stock cubes. Simmer 20 minutes until potatoes are tender. Add Aromat seasoning to taste. Simmer until vegetables are fully cooked. Serve hot with rice, dumplings, pap or bread.",
                "Easy Beef Stew"
        ));
        // 18.Cape Malay Pickled Fish With A Twist
        recipes.add(new Recipes(
                "Cape Malay Pickled Fish With A Twist",
                "hake fillet (1kg), white sugar (500g), brown vinegar (750ml), cumin seeds (2 teaspoons), coriander powder (1 tablespoon), bay leaves (5), turmeric powder (2 teaspoons), curry powder (5 teaspoons), onions (4 large, sliced), garlic (3 cloves, minced), salt (2 teaspoons), pepper powder (1 teaspoon), flour (1 cup), oil (100ml), worcestershire sauce (1 tablespoon)",
                "Cut fish into portions. Mix flour, 1 teaspoon salt and 1 teaspoon curry powder. Coat fish in mixture. Fry fish 5 minutes each side over medium heat, set aside on glass plate. Slice onions into rings. Fry onions 7 minutes in stainless steel pan. Add garlic, spices and worcestershire sauce, stir a few minutes. Pour in vinegar and sugar, stir 5 minutes. Reduce heat and simmer 30 minutes. Layer fish and vinegar sauce in glass bowl. Cool to room temperature, cover and refrigerate for 2 days before serving cold.",
                "Cape Malay Pickled Fish With A Twist"
        ));
        // 19.Pea Salad with Bacon
        recipes.add(new Recipes(
                "Pea Salad with Bacon",
                "frozen peas (1kg), olive oil (1 teaspoon), minced bacon (500g), cheddar cheese (400g, cubed), plain yogurt (3/4 cups), mayonnaise (1/4 cups), mixed herbs (1 teaspoon), english mustard (1 teaspoon), salt (1 teaspoon), black pepper (1 teaspoon), red onion (1, finely chopped)",
                "Boil water in medium pot, add peas and cook 5 minutes (don't overcook). Drain and wash with cold water, set aside to dry. Heat olive oil and cook minced bacon until crisp. Mix dressing ingredients in small bowl: yogurt, mayonnaise, herbs, mustard, salt and pepper. Layer salad in bowl: peas, bacon, cheese cubes, dressing and red onion. Mix before serving. Serve as salad or side dish.",
                "Pea Salad with Bacon"
        ));
        // 20.Sugar Bean Curry
        recipes.add(new Recipes(
                "Sugar Bean Curry",
                "sugar beans soaked and boiled (500g), medium potatoes (4, quartered), ginger and garlic paste (1 teaspoon), curry leaves (1 sprig), mixed masala (2 tablespoons), cinnamon sticks (2), star anise (2), salt (1 teaspoon), water (as needed), vegetable oil (2 tablespoons), onion (1, diced), coriander (2 tablespoons, chopped)",
                "Soak sugar beans 2 hours then boil until soft. Heat oil in pot, fry onions until light brown. Add cinnamon and star anise, fry briefly. Remove from heat, add ginger/garlic paste, masala and potatoes. Return to heat, simmer on low to fry spices. Add water gradually to avoid burning. Season with salt and simmer until potatoes are semi-soft. Add cooked beans, curry leaves and more water (1 cup). Simmer 20 minutes until potatoes are fully soft. Garnish with coriander and serve with basmati rice or as bunny chow.",
                "Sugar Bean Curry"
        ));
        return recipes;
    }

    // Creating method to load preloaded recipes into the database using ReceipeDAO (Data Access Object)
    public static boolean loadRecipesIntoDB(Context context) {

        RecipeDAO recipeDAO = new RecipeDAO(context); // Creating an instance of RecipeDAO using context
        List<Recipes> existingRecipes = recipeDAO.getAllRecipes(); // Get all recipes

        // Check if recipes exist, whether empty, return if true
        if (existingRecipes != null && !existingRecipes.isEmpty()) {
            return true;
        }

        // Get list of recups
        List<Recipes> preloadedRecipes = getPreloadedRecipes();

        // Loop through recipes
        for (Recipes recipe : preloadedRecipes) {

            long result = recipeDAO.addRecipe(recipe); // Use recipeDAO to inesrt to db

            // Check if insertion was successful
            if (result == -1) {
                return false;
            }
        }
        return true;
    }
}
