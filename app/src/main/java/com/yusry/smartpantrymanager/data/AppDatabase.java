package com.yusry.smartpantrymanager.data;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;
@Database(entities = {Ingredient.class, Recipe.class}, version = 5, exportSchema = false) // V-control,5th attempt at recipes
/*
* App deletes old
* Runs onCreat
* loads 20 recipes
* */
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao dao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "smart_pantry.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(seedCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static final Callback seedCallback = new Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDao dao = INSTANCE.dao();
                dao.insertRecipe(new Recipe(
                        "Traditional South African Sosaties",
                        "Beef Cubed (500g)\nOnion (1)\nSoy Sauce (30ml)\nWorcestershire Sauce (30ml)\nHoney (60ml)\nGarlic (4 cloves)\nGinger (1tsp)\nBlack Pepper\nSalt\nBay Leaves (3)\nThyme (1tsp)\nSkewers",
                        "beef cubed,onion,soy sauce,worcestershire sauce,honey,garlic,ginger,black pepper,salt,bay leaves,thyme,skewers",
                        "Combine soy sauce, worcestershire, honey, garlic, ginger, pepper, salt, bay leaves and thyme. Add beef cubes and marinate 4 hours or overnight. Thread onto skewers with onion. Braai on medium-hot coals 10-12 mins, turning and basting. Serve with pap."
                ));
                dao.insertRecipe(new Recipe(
                        "Fatcake Burger",
                        "Self Raising Flour (2 cups)\nSugar (1 tbsp)\nSalt (1 tsp)\nBaking Powder (2 tsp)\nWater (1.5 cups)\nOil\nGround Beef (500g)\nOnion (1)\nGarlic (2 cloves)\nTomato Sauce (30ml)\nWorcestershire Sauce (15ml)\nLettuce\nTomato (2)\nCheddar Cheese (200g)",

                        "self raising flour,sugar,salt,baking powder,water,oil,ground beef,onion,garlic,tomato sauce,worcestershire sauce,lettuce,tomato,cheddar cheese",
                        "Mix flour, sugar, salt, baking powder and water to thick batter. Fry spoonfuls until golden. Brown beef with onion and garlic, add sauces and simmer 10 mins. Split fatcakes and fill with beef, lettuce, tomato and cheese."
                ));
                dao.insertRecipe(new Recipe(
                        "Easy Boerewors Stew",
                        "Boerewors (500g)\nOnions (2)\nPotatoes (4)\nCarrots (3)\nTomato Sauce (400ml)\nBeef Stock (500ml)\nWorcestershire Sauce (15ml)\nBay Leaves (2)\nThyme (1 tsp)\nSalt\nBlack Pepper",
                        "boerewors,onion,potato,carrot,tomato sauce,beef stock,worcestershire sauce,bay leaves,thyme,salt,black pepper",
                        "Brown boerewors sliced, remove. Saute onions, add potatoes, carrots, tomato sauce, beef stock, worcestershire, bay leaves and thyme. Return boerewors and simmer covered 30-40 mins until tender."
                ));
                dao.insertRecipe(new Recipe(
                        "Easy Beef Stew",
                        "Beef Cubed (500g)\nOnion (1)\nOreganum (1 tsp)\nGround Cumin (1 tsp)\nGround Coriander (1 tsp)\nBeef Stock Cubes (2)\nAromat Seasoning (1 tsp)\nWater (2 cups)\nPotatoes (4)\nCarrots (2)",
                        "beef cubed,onion,oregano,cumin,coriander,beef stock cube,aromat,water,potato,carrot",
                        "Fry onion with oreganum, cumin and coriander. Add beef and fry briefly, add water and simmer 25 mins. Add carrots, potatoes and stock cubes, simmer 20 mins until tender, add Aromat."
                ));
                dao.insertRecipe(new Recipe(
                        "Malva Pudding",
                        "Self Raising Flour (1.5 cups)\nSugar (150g)\nButter (75g)\nEgg (1)\nApricot Jam (150ml)\nBaking Soda (1 tsp)\nVanilla Extract (1 tsp)\nSalt\nHot Water (250ml)\nCream (250ml)\nBrown Sugar (150g)",
                        "self raising flour,sugar,butter,egg,apricot jam,baking soda,vanilla extract,salt,hot water,cream,brown sugar",
                        "Cream butter and sugar, beat in egg and vanilla. Fold in flour, baking soda, salt and jam. Pour into dish. Dissolve brown sugar and butter in hot water, pour over batter. Bake 180C 35-40 mins. Pour warm cream over before serving."
                ));
                dao.insertRecipe(new Recipe(
                        "Milk Tart (Melktert)",
                        "Puff Pastry (500g)\nMilk (1L)\nCornstarch (60g)\nSugar (200g)\nEgg (1)\nButter (30g)\nVanilla Extract (1 tsp)\nCinnamon (1 tbsp)\nSalt",
                        "puff pastry,milk,cornstarch,sugar,egg,butter,vanilla extract,cinnamon,salt",
                        "Line tin with pastry and bake blind 200C 10 mins. Whisk cornstarch with cold milk. Heat rest of milk with sugar and salt, add slurry until thick. Whisk in egg, butter and vanilla. Pour into case, sprinkle cinnamon and bake 180C 20-25 mins. Chill."
                ));
                dao.insertRecipe(new Recipe(
                        "Inkomasi Scones",
                        "Self Raising Flour (2 cups)\nSugar (2 tbsp)\nBaking Powder (1 tsp)\nSalt (1 tsp)\nButter (100g)\nMilk (750ml)\nEgg (1)\nVanilla Extract (1 tsp)",
                        "self raising flour,sugar,baking powder,salt,butter,milk,egg,vanilla extract",
                        "Mix flour, sugar, baking powder and salt. Rub in butter. Add milk and vanilla, mix gently. Pat to 2cm thick, cut circles, brush with egg. Bake 200C 12-15 mins until golden."
                ));
                dao.insertRecipe(new Recipe(
                        "Microwave Pap",
                        "Cornmeal (1 cup)\nWater (4 cups)\nSalt (1 tsp)\nButter (30g)\nSugar (1 tbsp)",
                        "cornmeal,water,salt,butter,sugar",
                        "Boil water 5 mins in microwave. Add cornmeal while stirring to avoid lumps, add salt. Microwave 10-12 mins stirring every 2-3 mins until thick. Add butter and sugar, serve hot."
                ));
                dao.insertRecipe(new Recipe(
                        "Simple Fatcake Recipe",
                        "Self Raising Flour (2 cups)\nSugar (2 tbsp)\nSalt (1 tsp)\nBaking Powder (2 tsp)\nWater (1.5 cups)\nOil\nJam\nCinnamon",
                        "self raising flour,sugar,salt,baking powder,water,oil,jam,cinnamon",
                        "Mix flour, sugar, salt and baking powder, add water to thick batter. Fry spoonfuls 2-3 mins each side until golden. Drain, split and fill with jam, roll in cinnamon sugar while warm."
                ));
                dao.insertRecipe(new Recipe(
                        "Traditional Magwinya's",
                        "Self Raising Flour (2 cups)\nSugar (2 tbsp)\nSalt (1 tsp)\nBaking Powder (2 tsp)\nWater (1.5 cups)\nOil\nJam\nCinnamon",
                        "self raising flour,sugar,salt,baking powder,water,oil,jam,cinnamon",
                        "Mix flour, sugar, salt and baking powder, add water to thick batter. Fry spoonfuls 2-3 mins each side until golden. Drain, split and fill with jam, roll in cinnamon sugar."
                ));
                dao.insertRecipe(new Recipe(
                        "Dombolo with Sweet Corn",
                        "Self Raising Flour (2 cups)\nSugar (2 tbsp)\nBaking Powder (1 tsp)\nSalt (1 tsp)\nWater (1.5 cups)\nButter (30g)\nSweet Corn (400g)\nChicken Stock (1L)\nOnion (1)\nGarlic (2 cloves)\nThyme (1 tsp)",
                        "self raising flour,sugar,baking powder,salt,water,butter,sweet corn,chicken stock,onion,garlic,thyme",
                        "Saute onion and garlic in butter, add sweet corn and stock and bring to boil. Mix flour, sugar, baking powder and salt, add water to form dough. Drop spoonfuls into boiling liquid, cover and simmer 20-25 mins. Add thyme."
                ));
                dao.insertRecipe(new Recipe(
                        "South African Potato Salad",
                        "Potatoes (1kg)\nMayonnaise (500ml)\nGherkins (300g)\nCelery (2 stalks)\nApple (2)\nOnion (1)\nSalt (1 tsp)\nBlack Pepper (1 tsp)\nFresh Parsley (2 tbsp)",
                        "potatoes,mayonnaise,gherkins,celery,apple,onion,salt,black pepper,fresh parsley",
                        "Boil potatoes cubed until tender, drain and cool. Mix mayo, gherkins, celery, apple and onion. Add potatoes and toss gently. Season with salt and pepper, refrigerate 30 mins, garnish with parsley."
                ));
                dao.insertRecipe(new Recipe(
                        "South African Potato Salad",
                        "Potatoes (1kg)\nMayonnaise (500ml)\nGherkins (300g)\nCelery (2 stalks)\nApple (2)\nOnion (1)\nSalt (1 tsp)\nBlack Pepper (1 tsp)\nFresh Parsley (2 tbsp)",
                        "potato,mayonnaise,gherkin,celery,apple,onion,salt,black pepper,parsley",
                        "Boil potatoes cubed until tender, drain and cool. Mix mayo, gherkins, celery, apple and onion. Add potatoes and toss gently. Season with salt and pepper, refrigerate 30 mins, garnish with parsley."
                ));
                dao.insertRecipe(new Recipe(
                        "Easy Boerewors Stew",
                        "Boerewors (500g)\nOnions (2)\nPotatoes (4)\nCarrots (3)\nTomato Sauce (400ml)\nBeef Stock (500ml)\nWorcestershire Sauce (15ml)\nBay Leaves (2)\nThyme (1 tsp)\nSalt\nBlack Pepper",
                        "boerewors,onion,potato,carrot,tomato sauce,beef stock,worcestershire sauce,bay leaves,thyme,salt,black pepper",
                        "Brown boerewors sliced, remove. Saute onions, add potatoes, carrots, tomato sauce, beef stock, worcestershire, bay leaves and thyme. Return boerewors and simmer covered 30-40 mins until tender."
                ));
                dao.insertRecipe(new Recipe(
                        "Polony, Atchar and Cheese Fat Cake",
                        "Self Raising Flour (2 cups)\nSugar (1 tbsp)\nSalt (1 tsp)\nBaking Powder (2 tsp)\nWater (1.5 cups)\nOil\nPolony (300g)\nAtchar (200ml)\nCheddar Cheese (200g)\nLettuce\nTomato (2)",
                        "self raising flour,sugar,salt,baking powder,water,oil,polony,atchar,cheddar cheese,lettuce,tomato",
                        "Mix flour, sugar, salt, baking powder and water to thick batter. Fry spoonfuls until golden. Split and fill with polony slices, atchar, cheese, lettuce and tomato slices."
                ));
                dao.insertRecipe(new Recipe(
                        "Chicken Nugget Salad",
                        "Chicken Nuggets (500g)\nLettuce (1 large)\nGherkins (380g)\nCheddar Cheese (400g)\nTomatoes (4)\nMayonnaise (730g)\nOlives (200g)\nSalt\nBlack Pepper\nWorcestershire Braai Spice\nAvocado (2)\nOil (750ml)",
                        "chicken nugget,lettuce,gherkin,cheddar cheese,tomato,mayonnaise,olive,salt,black pepper,worcestershire spice,avocado,oil",
                        "Fry nuggets in oil until golden, drain and cut into chunks. Chop lettuce in bowl, add nuggets, gherkins, cheese, tomatoes and olives. Mix in mayo and avocado, season and refrigerate."
                ));
                dao.insertRecipe(new Recipe(
                        "Chicken Curry with Sweet Potatoes and Homemade Dombolo",
                        "Chicken Pieces (800g)\nSweet Potatoes (4)\nCarrots\nOnions\nSalt\nBlack Pepper\nTurmeric (1 tsp)\nCurry Powder\nMixed Dried Herbs\nSelf Raising Flour (2 cups)\nSugar (2 tbsp)\nWater (1 cup)",
                        "chicken piece,sweet potato,carrot,onion,salt,black pepper,turmeric,curry powder,mixed herb,self raising flour,sugar,water",
                        "Dice onions and carrots, fry with spices, add sweet potatoes and chicken and simmer 10 mins. Mix flour, sugar and salt with water to form dough balls. Drop into curry, cover tightly and simmer 25 mins without opening."
                ));
            });
        }
    };
}