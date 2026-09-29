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
                "Marinated beef kebabs grilled on coals.",
                "beef cubed, onion, soy sauce, worcestershire sauce, honey, garlic, ginger, black pepper, salt, bay leaves, thyme, skewers.",
                "Combine soy sauce (30ml), worcestershire (30ml), honey (60ml), garlic (4 cloves), ginger (1tsp), pepper, salt, bay leaves (3) and thyme (1tsp) in a bowl. Add beef cubes and marinate for minimum 4 hours or overnight. Thread beef onto skewers alternating with onion slices. Braai on medium-hot coals for 10-12 minutes, turning frequently. Baste with remaining marinade. Serve hot with pap and tomato relish."
                ));
        // 2 Buttermilk Rusks
        recipes.add(new Recipes(
                "Buttermilk Rusks",
                "Crunchy South African twice-baked biscuits.",
                "self raising flour, sugar, butter, eggs, buttermilk, baking powder, salt, vanilla extract, aniseed.",
                "Cream butter (250g) and sugar (250g) until light and fluffy. Beat in eggs (3) one at a time. Combine flour (4 cups), baking powder (2 teaspoons), salt (1 teaspoon) and aniseed (1 tablespoon, optional) in separate bowl. Alternate adding flour mixture and buttermilk (1 cup) to butter mixture, starting and ending with flour. Pour into greased loaf tin and bake at 180°C for 50-60 minutes until golden. Cool, slice diagonally and bake slices at 160°C for 20-25 minutes until dry and crispy, turning halfway through."
                ));
        // 3.Fatcake Burger
        recipes.add(new Recipes(
                "Fatcake Burger",
                "Deep fried dough patties filled with spiced beef and fresh toppings",
                "Mself raising flour, sugar, salt, baking powder, water, oil, ground beef, onion, garlic, tomato sauce, worcestershire sauce, lettuce, tomato, cheese.",
                "Mix flour (2 cups), sugar (1 tablespoon), salt (1 teaspoon) and baking powder (2 teaspoons). Add water (1.5 cups) gradually to form thick batter. Heat oil and fry spoonfuls of batter until golden and puffy (fatcakes). Brown ground beef (500g) with onion (1, diced) and garlic (2 cloves, minced), add tomato sauce (30ml), worcestershire sauce (15ml), salt and pepper. Simmer 10 minutes. Split warm fatcakes and fill with beef mixture, lettuce (1 bunch), tomato (2, sliced) and cheese (200g, sliced). Serve immediately"
                ));
        // 4.Boerewors Corn Dog
        recipes.add(new Recipes(
                "Boerewors Corn Dog",
                "A crispy cornmeal-battered sausage on a stick, South African style.",
                "boerewors, cornmeal, self raising flour, sugar, baking powder, salt, milk, egg, mustard, oil",
                "Mix cornmeal (1 cup), flour (1 cup), sugar (2 tablespoons), baking powder (2 teaspoons) and salt (1 teaspoon). Whisk milk (1 cup) and egg (1) together, add to dry ingredients with mustard (2 tablespoons) until smooth batter forms. Heat oil in pot. Pierce boerewors (500g) with stick. Dip boerewors in batter until fully coated. Fry 5-6 minutes until golden brown, turning occasionally. Drain on paper towel. Serve hot with tomato sauce and mustard."
                ));
        // 5.Malva Pudding
        recipes.add(new Recipes(
                "Malva Pudding",
                "Sweet spongy dessert with warm toffee sauce, a South African classic.",
                "self raising flour, sugar, butter, egg, apricot jam, baking soda, vanilla extract, salt, hot water, cream, brown sugar.",
                "Cream butter (75g) and sugar (150g). Beat in egg (1) and vanilla extract (1 teaspoon). Mix flour (1.5 cups), baking soda (1 teaspoon) and salt (pinch), fold into wet ingredients with apricot jam (150ml). Pour into greased baking dish. For sauce: dissolve brown sugar (150g) and butter (75g) in hot water (250ml), pour over batter carefully (don't stir). Bake at 180°C for 35-40 minutes until spongy. Heat cream (250ml), pour warm sauce over pudding before serving. Serve warm with custard."
                ));

        // 6.Milk Tart (Melktert)
        recipes.add(new Recipes(
                "Milk Tart (Melktert)",
                "Creamy custard tart with spiced cinnamon topping, a beloved South African dessert.",
                "puff pastry, milk, cornstarch, sugar, egg, butter, vanilla extract, cinnamon, salt.",
                "Line tart tin with puff pastry (500g), prick with fork and bake blind at 200°C for 10 minutes. Whisk cornstarch (60g) with 50ml cold milk. Heat remaining milk (950ml) with sugar (200g) and salt (pinch) until steaming. Add cornstarch slurry, stirring constantly until thick. Cool slightly, whisk in egg (1), butter (30g) and vanilla extract (1 teaspoon). Pour filling into pastry case. Sprinkle cinnamon (1 tablespoon) generously on top. Bake at 180°C for 20-25 minutes until cinnamon sugar caramelizes slightly. Chill before serving."
                ));
        // 7.Inkomasi Scones
        recipes.add(new Recipes(
                "Inkomasi Scones",
                "Light and fluffy scones perfect with jam and cream, a traditional treat.",
                "self raising flour, sugar, baking powder, salt, butter, milk, egg, vanilla extract.",
                "Mix flour (2 cups), sugar (2 tablespoons), baking powder (1 teaspoon) and salt (1 teaspoon). Rub in cold butter (100g) until breadcrumb texture. Make well in center, pour in milk (750ml) and vanilla extract (1 teaspoon), mix gently until just combined. Turn onto floured surface, handle minimally. Pat to 2cm thickness, cut into circles. Place on baking tray, brush with beaten egg (1). Bake at 200°C for 12-15 minutes until golden. Serve warm with jam and cream."
                ));
        //8. Microwave Pap
        recipes.add(new Recipes(
                "Microwave Pap",
                "Quick cornmeal porridge made in microwave with butter and sugar",
                "cornmeal, water, salt, butter, sugar",
                "Pour water (4 cups) into microwave safe bowl and microwave 5 minutes until boiling. Slowly add cornmeal (1 cup) while stirring constantly to avoid lumps. Stir in salt (1 teaspoon). Microwave uncovered 10-12 minutes, stirring every 2-3 minutes, until smooth and thick. Add butter (30g) and sugar (1 tablespoon optional) if desired. Stir well and serve hot."
                ));
        //9. Simple Fatcake Recipe
        recipes.add(new Recipes(
                "Simple Fatcake Recipe",
                "Fluffy deep-fried dough cakes filled with jam and dusted with cinnamon sugar",
                "self raising flour, sugar, salt, baking powder, water, oil, jam, cinnamon",
                "Mix self raising flour (2 cups), sugar (2 tablespoons), salt (1 teaspoon) and baking powder (2 teaspoons). Add water (1.5 cups) gradually, stirring until thick batter forms (don't overmix). Heat oil in deep pan. Drop spoonfuls of batter and fry 2-3 minutes each side until golden. Drain on paper towel. While warm, split fatcake and fill with jam. Roll in cinnamon sugar (cinnamon + sugar mixture) while still warm. Serve hot."
                ));
        // 10.Magwinya
        recipes.add(new Recipes(
                "Traditional Magwina's",
                "Golden fried dough balls filled with jam and rolled in cinnamon sugar",
                "self raising flour, sugar, salt, baking powder, water, oil, jam, cinnamon",
                "Mix flour (2 cups), sugar (2 tablespoons), salt (1 teaspoon) and baking powder (2 teaspoons). Add water (1.5 cups) gradually, stirring until thick batter forms (don't overmix). Heat oil in deep pan. Drop spoonfuls of batter and fry 2-3 minutes each side until golden. Drain on paper towel. While warm, split fatcake and fill with jam. Roll in cinnamon sugar mixture while still warm. Serve hot."
                ));
        // 11.Dombolo with Sweet Corn
        recipes.add(new Recipes(
                "Dombolo with Sweet Corn",
                "Fluffy steamed dumplings in a savory sweet corn and chicken broth.",
                "self raising flour, sugar, baking powder, salt, water, butter, sweet corn, chicken stock, onion, garlic, thyme.",
                "Heat butter (30g), saute onion (1, diced) and garlic (2 cloves, minced) until soft. Add sweet corn (400g, canned) and chicken stock (1 liter), bring to boil. Mix flour (2 cups), sugar (2 tablespoons), baking powder (1 teaspoon) and salt (1 teaspoon). Add water (1.5 cups) gradually until dough forms. Drop spoonfuls of dough into boiling liquid. Simmer covered 20-25 minutes until dumplings are cooked through. Add thyme (1 teaspoon) and season to taste. Serve hot."
                ));
        // 12.South African Potato Salad
        recipes.add(new Recipes(
                "South African Potato Salad",
                "Creamy cold potato salad with gherkins, apples and fresh parsley",
                "potatoes, mayonnaise, gherkins, celery, apple, onion, salt, black pepper, fresh parsley.",
                "Boil potatoes (1kg, cubed) until tender, drain and cool. Combine mayonnaise (500ml), gherkins (300g, sliced), celery (2 stalks, diced), apple (2, diced) and onion (1, finely diced) in large bowl. Add cooled potatoes and toss gently. Season with salt (1 teaspoon) and black pepper (1 teaspoon). Refrigerate for at least 30 minutes. Garnish with fresh parsley (2 tablespoons, chopped) before serving. Serve cold as side dish."
                 ));
        // 13.Easy Boerewors Stew
        recipes.add(new Recipes(
                "Easy Boerewors Stew",
                "Hearty stew with boerewors, vegetables and rich tomato broth.",
                "boerewors, onions, potatoes, carrots, tomato sauce, beef stock, worcestershire sauce, bay leaves, thyme, salt, black pepper.",
                "Brown boerewors (500g, sliced) in large pot, remove and set aside. Saute onions (2, sliced) until soft. Add potatoes (4, cubed), carrots (3, sliced), tomato sauce (400ml), beef stock (500ml), worcestershire sauce (15ml), bay leaves (2) and thyme (1 teaspoon). Return boerewors to pot. Simmer covered 30-40 minutes until vegetables are tender. Season with salt and pepper. Serve hot with pap or bread."
                ));
        // 14. Polony, Atchar and Cheese Fat Cake
        recipes.add(new Recipes(
                "Polony, Atchar and Cheese Fat Cake",
                "Savory fatcakes filled with sliced polony, tangy atchar and melted cheese",
                "self raising flour, sugar, salt, baking powder, water, oil, polony, atchar, cheddar cheese, lettuce, tomato",
                "Mix flour (2 cups), sugar (1 tablespoon), salt (1 teaspoon) and baking powder (2 teaspoons). Add water (1.5 cups) gradually for thick batter. Fry spoonfuls in hot oil until golden on both sides. Split warm fatcakes, fill with polony slices (300g), spoon atchar (200ml) over top, add cheddar cheese slices (200g), lettuce (1 bunch) and tomato slices (2). Serve immediately while fatcakes are still warm."
                ));
        // 15. Chicken Nugget Salad
        recipes.add(new Recipes(
                "Chicken Nugget Salad",
                "Hearty salad with crispy chicken nuggets, fresh vegetables and creamy mayo dressing",
                "chicken nuggets, lettuce, gherkins, cheddar cheese, tomatoes, mayonnaise, olives, salt, black pepper, worcestershire braai spice, avocado, oil",
                "Heat oil (750ml) and fry nuggets (500g) until golden brown and cooked through. Drain on paper towel and cut into chunks. Chop lettuce (1 large) and place in salad bowl. Add nugget chunks, sliced gherkins (380g), cubed cheddar cheese (400g), tomato cubes (4) and olives (200g). Mix in mayonnaise (730g), add avocado slices (1-2). Season with salt, pepper and worcestershire braai spice (pinch). Toss gently and refrigerate until serving."
                ));
        // 16. Chicken Curry with Sweet Potatoes and Homemade Dombolo
        recipes.add(new Recipes(
                "Chicken Curry with Sweet Potatoes and Homemade Dombolo",
                "Spiced chicken and sweet potato curry topped with homemade dombolo dumplings",
                "chicken pieces, sweet potatoes, carrots, onions, salt, black pepper, turmeric, curry powder, mixed dried herbs, self raising flour, sugar, water",
                "Dice onions and carrots, add to hot pan with oil. Season with herbs, spices, turmeric (1 teaspoon), salt and pepper. Add sweet potatoes (4, cubed) and chicken pieces (800g), mix well. Cover and simmer 10 minutes. For dombolo: mix self raising flour (2 cups), sugar (2 tablespoons) and salt, add lukewarm water (1 cup), form ball and knead slightly. Rest 15 minutes. Divide into 4 balls and carefully drop into curry. Cover pot tightly and simmer 25 minutes without opening until dombolo is firm and cooked through. Serve hot."
                ));
        // 17. Easy Beef Stew
        recipes.add(new Recipes(
                "Easy Beef Stew",
                "Simple braised beef with root vegetables and warm spices",
                "beef cubed, onion, oreganum, ground cumin, ground coriander, beef stock cubes, Aromat seasoning, water, potatoes, carrots",
                "Fry onion (1, sliced) with oreganum (1 teaspoon), cumin (1 teaspoon) and coriander (1 teaspoon) until fragrant. Add cubed beef (500g) and fry briefly. Add water (2 cups) and simmer 25 minutes. Add sliced carrots (2 large), cubed potatoes (4 medium) and beef stock cubes (2). Simmer 20 minutes until potatoes are tender. Add Aromat seasoning (1 teaspoon) to taste. Simmer until vegetables are fully cooked. Serve hot with rice, dumplings, pap or bread."
                ));
        // 18. Cape Malay Pickled Fish With A Twist
        recipes.add(new Recipes(
                "Cape Malay Pickled Fish With A Twist",
                "Tangy spiced hake fillets preserved in aromatic vinegar and served cold",
                "hake fillet, white sugar, brown vinegar, cumin seeds, coriander powder, bay leaves, turmeric powder, curry powder, onions, garlic, salt, black pepper, flour, oil, worcestershire sauce",
                "Cut hake fillet (1kg) into portions. Mix flour (1 cup), salt (1 teaspoon) and curry powder (1 teaspoon). Coat fish in mixture. Fry fish 5 minutes each side over medium heat, set aside on glass plate. Slice onions (4 large) into rings. Fry onions 7 minutes in stainless steel pan. Add garlic (3 cloves, minced), cumin seeds (2 teaspoons), coriander powder (1 tablespoon), turmeric powder (2 teaspoons), curry powder (5 teaspoons), bay leaves (5) and worcestershire sauce (1 tablespoon), stir a few minutes. Pour in brown vinegar (750ml) and white sugar (500g), stir 5 minutes. Reduce heat and simmer 30 minutes. Layer fish and vinegar sauce in glass bowl. Cool to room temperature, cover and refrigerate for 2 days before serving cold."
                ));
        // 19. Pea Salad with Bacon
        recipes.add(new Recipes(
                "Pea Salad with Bacon",
                "Creamy pea salad with crispy bacon, cheese and tangy yogurt dressing",
                "frozen peas, olive oil, bacon, cheddar cheese, plain yogurt, mayonnaise, mixed herbs, english mustard, salt, black pepper, red onion",
                "Boil water in medium pot, add frozen peas (1kg) and cook 5 minutes (don't overcook). Drain and wash with cold water, set aside to dry. Heat olive oil (1 teaspoon) and cook minced bacon (500g) until crisp. Mix dressing ingredients in small bowl: plain yogurt (3/4 cups), mayonnaise (1/4 cups), mixed herbs (1 teaspoon), english mustard (1 teaspoon), salt (1 teaspoon) and black pepper (1 teaspoon). Layer salad in bowl: peas, bacon, cheddar cheese cubes (400g), dressing and red onion (1, finely chopped). Mix before serving. Serve as salad or side dish."
        ));
        // 20. Sugar Bean Curry
        recipes.add(new Recipes(
                "Sugar Bean Curry",
                "Warming legume curry with potatoes, aromatic spices and fresh coriander",
                "sugar beans, potatoes, ginger and garlic paste, curry leaves, mixed masala, cinnamon sticks, star anise, salt, water, vegetable oil, onion, coriander",
                "Soak sugar beans (500g) 2 hours then boil until soft. Heat vegetable oil (2 tablespoons) in pot, fry onion (1, diced) until light brown. Add cinnamon sticks (2) and star anise (2), fry briefly. Remove from heat, add ginger and garlic paste (1 teaspoon), mixed masala (2 tablespoons) and potatoes (4 medium, quartered). Return to heat, simmer on low to fry spices. Add water gradually to avoid burning. Season with salt (1 teaspoon) and simmer until potatoes are semi-soft. Add cooked beans, curry leaves (1 sprig) and more water (1 cup). Simmer 20 minutes until potatoes are fully soft. Garnish with coriander (2 tablespoons, chopped) and serve with basmati rice or as bunny chow."
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
