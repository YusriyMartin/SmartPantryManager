package com.yusry.smartpantrymanager.data;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;
@Database(entities = {Ingredient.class, Recipe.class}, version = 1, exportSchema = false)
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
                        "Banana Shake",
                        "Banana (2)\nProtein Powder (2 scoops)\nAlmond Milk (1 cup)\nIce (5-6 cubes)",
                        "banana,protein powder,almond milk,ice",
                        "Blend all until smooth"));
                dao.insertRecipe(new Recipe(
                        "Tomato Soup",
                        "Tomato (4)\nOnion (1)\nGarlic (2)",
                        "tomato,onion,garlic",
                        "Boil and blend"));
                dao.insertRecipe(new Recipe(
                        "Boiled Eggs",
                        "Eggs (3)\nWater",
                        "eggs,water",
                        "Boil 10 mins"));
                dao.insertRecipe(new Recipe(
                        "Pancakes",
                        "Flour (1 cup)\nMilk (1 cup)\nEggs (2)",
                        "flour,milk,eggs",
                        "Mix and fry"));
                dao.insertRecipe(new Recipe(
                        "Omelette",
                        "Eggs (3)\nCheese (30g)\nTomato (1)",
                        "eggs,cheese,tomato",
                        "Beat and fry"));
                dao.insertRecipe(new Recipe(
                        "Rice and Beans",
                        "Rice (1 cup)\nBeans (1 cup)",
                        "rice,beans",
                        "Cook together"));
                dao.insertRecipe(new Recipe(
                        "Fruit Salad",
                        "Apple (1)\nBanana (1)\nOrange (1)",
                        "apple,banana,orange",
                        "Chop mix"));
                dao.insertRecipe(new Recipe(
                        "Garlic Bread",
                        "Bread (4)\nGarlic (2)\nButter (20g)",
                        "bread,garlic,butter",
                        "Toast"));
                dao.insertRecipe(new Recipe(
                        "Milk Tea",
                        "Milk (1 cup)\nTea Bag (1)",
                        "milk,tea bag",
                        "Boil"));
                dao.insertRecipe(new Recipe(
                        "Chicken Stir Fry",
                        "Chicken (200g)\nOnion (1)\nPepper (1)",
                        "chicken,onion,pepper",
                        "Fry"));
                for(int i = 11; i <= 20 ; i++)
                    dao.insertRecipe(new Recipe(
                            "Recipe "+i,
                            "Sugar (1)\nFlour (1)",
                            "sugar,flour",
                            "Mix"));
            });
        }
    };
}