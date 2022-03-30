package com.uclan.MealManagement;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

public class MealAlgorithmManager {
	// Params
	public static int mealPlanDays = 7;

	// Data
	public static MealPlan mealPlan;
	public static ArrayList<Ingredient> fridgeIngredients = new ArrayList<Ingredient>();
	public static ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
	public static ArrayList<Recipe> possibleRecipes = new ArrayList<Recipe>();
	public static ArrayList<Recipe> mealList = new ArrayList<Recipe>();
	public static ArrayList<Recipe> possibleFavouriteRecipes = new ArrayList<Recipe>();
	public static boolean IsMealPlanGenerated;

	// Main event
	public static MealPlan CalculateMealPlan(String UserId) throws Exception {

		// Initialise data
		InitialiseRecipeList();
		InitialiseFridge(UserId);
		mealList = new ArrayList<Recipe>();

		// Do initial update of possible recipes
		UpdatePossibleRecipes(UserId);
		System.out.println("I can make " + possibleRecipes.size() + " different recipes");

		// Iterate through meal plan making meals
		mealPlan = new MealPlan(Calendar.getInstance());
		for (int i = 0; i < 7; i++) {
			// Day
			MealPlanDay day = mealPlan.days.get(i);

			// Eliminate out of date ingredients
			UpdateFridge(day.date);

			// Update possible recipes
			UpdatePossibleRecipes(UserId);

			// Add meals
			day.breakfastRecipe = getRecipeAtMealTime("breakfast");
			day.lunchRecipe = getRecipeAtMealTime("lunch");
			day.dinnerRecipe = getRecipeAtMealTime("dinner");
		}
		// error message
		if (getRecipeAtMealTime("breakfast") != null && getRecipeAtMealTime("lunch") != null
				&& getRecipeAtMealTime("dinner") != null) {
			IsMealPlanGenerated = true;
		} else {
			IsMealPlanGenerated = false;
		}

		// Return
		return mealPlan;
	}

	public static void InitialiseRecipeList() throws Exception {
		allRecipes = new ArrayList<Recipe>();
		ResultSet rsRecipeQuery = SQLManager.RecipeQuery(UserPreferenceManager.maxCalories,
				UserPreferenceManager.maxCookTime, UserPreferenceManager.difficulty);
		while (rsRecipeQuery.next()) {
			String recipeName = rsRecipeQuery.getString(2);
			String mealTime = rsRecipeQuery.getString(3);
			ArrayList<Ingredient> Ingredients = SQLManager.getIngredientsOfRecipe(rsRecipeQuery.getString(1));
			Recipe recipe = new Recipe();
			recipe.IngredientList = Ingredients;
			recipe.mName = recipeName;
			recipe.mMealTime = mealTime;
			allRecipes.add(recipe);
		}
	}

	public static void InitialiseFridge(String UserId) throws Exception {
		fridgeIngredients = new ArrayList<Ingredient>();
		ResultSet rsFridgeQuery = SQLManager.FridgeQuery(UserId);
		while (rsFridgeQuery.next()) {
			// Get ingredient
			Ingredient ingredient = new Ingredient();
			ingredient.name = rsFridgeQuery.getString(1);
			Date date = rsFridgeQuery.getDate(2);
			ingredient.expiryDate.setTime(date);

			// Add ingredient by quantity to fridge
			int quantity = Integer.parseInt(rsFridgeQuery.getString(3));
			for (int i = 0; i < quantity; i++) {
				fridgeIngredients.add(ingredient);
			}
		}
	}

	// Update all ingredients
	public static void UpdateFridge(Calendar date) {
		// Update all ingredients
		for (int i = 0; i < fridgeIngredients.size(); i++) {
			// Get ingredient
			Ingredient fridgeIngredient = fridgeIngredients.get(i);

			// Update ingredient
			if (fridgeIngredient.expiryDate.before(date)) {
				fridgeIngredients.remove(i);
				i--;
			}
		}
	}

	// This function gives 7 recipes, all for one time (eg lunch, breakfast, dinner)
	public static ArrayList<Recipe> makePossibleRecipeListForMealTime(String mealTime, String UserId) throws Exception {
		// Make list
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

		// Make meal plan
		for (int i = 0; i < mealPlanDays; i++) {
			// Get next available recipe -- can determine which is best here
			Recipe recipe = getRecipeAtMealTime(mealTime);

			// add recipes to list
			if (recipe != null) {
				recipeList.add(recipe);
				removeRecipeIngredientsFromFridge(recipe);
			}

			// Update possible recipes
			UpdatePossibleRecipes(UserId);
		}

		// Output end result
		if (recipeList.size() == mealPlanDays) {
			return recipeList;
		} else {
			return null;
		}
	}

	public static Recipe getRecipeAtMealTime(String mealTime) {
		// Make lists
		ArrayList<Recipe> favouriteRecipesAtCorrectTime = new ArrayList<Recipe>();
		ArrayList<Recipe> nonFavouriteRecipesAtCorrectTime = new ArrayList<Recipe>();

		// Get favourite recipes at correct time
		Recipe recipe;
		for (int i = 0; i < possibleFavouriteRecipes.size(); i++) {
			recipe = possibleFavouriteRecipes.get(i);
			if (recipe.mMealTime.equals(mealTime)) {
				favouriteRecipesAtCorrectTime.add(recipe);
			}
		}

		// Get non favourite recipes at correct time
		for (int i = 0; i < possibleRecipes.size(); i++) {
			recipe = possibleRecipes.get(i);
			if (recipe.mMealTime.equals(mealTime)) {
				nonFavouriteRecipesAtCorrectTime.add(recipe);
			}
		}

		// If there is a favourite recipe return a random one
		if (favouriteRecipesAtCorrectTime.size() != 0) {
			int random = getRandomNumber(0, favouriteRecipesAtCorrectTime.size());
			return favouriteRecipesAtCorrectTime.get(random);
		}

		// If there is a non favourite recipe return a random one
		if (nonFavouriteRecipesAtCorrectTime.size() != 0) {
			int random = getRandomNumber(0, nonFavouriteRecipesAtCorrectTime.size());
			return nonFavouriteRecipesAtCorrectTime.get(random);
		}

		// Return null if there are no possible recipes
		return null;
	}

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public static void removeRecipeIngredientsFromFridge(Recipe recipe) {
		// Remove ingredients from fridge
		for (int i = 0; i < recipe.IngredientList.size(); i++) {
			// Ingredient name
			Ingredient ingredient = recipe.IngredientList.get(i);

			// Find in fridge
			for (int j = 0; j < fridgeIngredients.size(); j++) {
				// Fridge ingredient
				Ingredient fridgeIngredient = fridgeIngredients.get(j);

				// Remove and finish iteration if found
				if (ingredient.name.equals(fridgeIngredient.name)) {
					System.out.println("Removing " + fridgeIngredient);
					fridgeIngredients.remove(j);
					break;
				}
			}
		}
	}

	public static void UpdatePossibleRecipes(String userID) throws Exception {
		// Refresh possible recipes
		possibleRecipes.clear();

		// Find all possible recipes
		for (int i = 0; i < allRecipes.size(); i++) {
			// Get recipe
			Recipe recipe = allRecipes.get(i);

			// Bool for if recipe is valid
			boolean canMakeRecipe = true;

			// Look through each required ingredient
			for (int j = 0; j < recipe.IngredientList.size(); j++) {
				// Required ingredient
				Ingredient requiredIngredient = recipe.IngredientList.get(j);

				// Bool for if ingredient is found
				boolean ingredientFound = false;

				// Look for ingredient in fridge
				for (int k = 0; k < fridgeIngredients.size(); k++) {
					// Get fridge ingredient
					Ingredient fridgeIngredient = fridgeIngredients.get(k);

					// Find if ingredient is missing
					if (fridgeIngredient.name.equals(requiredIngredient.name)) {
						ingredientFound = true;
					}
				}

				// Turn recipe invalid if ingredient was not found
				if (!ingredientFound) {
					canMakeRecipe = false;
				}
			}

			// Add recipe if no ingredients missing
			if (canMakeRecipe) {
				possibleRecipes.add(recipe);
			}
		}

		// Sort recipe by favourite
		possibleRecipes = sortMealPlan(userID, possibleRecipes);
	}

	public static ArrayList<Recipe> sortMealPlan(String customerId, ArrayList<Recipe> mealPlan) throws Exception {
		// Get favourite recipe from db
		ResultSet rsFavouriteRecipeQuery = SQLManager.getFavouriteRecipesResultSet(customerId);

		// Put favourite recipes from db into favourite arraylist
		ArrayList<String> favouriteRecipeList = new ArrayList<String>();
		while (rsFavouriteRecipeQuery.next()) {
			favouriteRecipeList.add(rsFavouriteRecipeQuery.getString(1));
		}

		// Create new meal plan for sorted list
		ArrayList<Recipe> newMealPlanList = new ArrayList<Recipe>();

		// Iterate through the meal list // add favourites first
		for (int i = 0; i < mealPlan.size(); i++) {
			// Set variables
			Recipe recipe = mealPlan.get(i);
			boolean recipeIsFavourite = false;

			// Check if recipe is favourite
			for (int j = 0; j < favouriteRecipeList.size(); j++) {
				if (mealPlan.get(i).mName.equals(favouriteRecipeList.get(j))) {
					recipeIsFavourite = true;
				}
			}

			// Add if favourite
			if (recipeIsFavourite) {
				possibleFavouriteRecipes.add(recipe);
			} else {
				newMealPlanList.add(recipe);
			}
		}

//		// add non favourites
//		for (int i = 0; i < mealPlan.size(); i++) {
//			// Set variables
//			Recipe recipe = mealPlan.get(i);
//			boolean recipeIsFavourite = false;
//
//			// Check if recipe is favourite
//			for (int j = 0; j < favouriteRecipeList.size(); j++) {
//				if (mealPlan.get(i).mName.equals(favouriteRecipeList.get(j))) {
//					recipeIsFavourite = true;
//				}
//			}
//
//			// Add if not favourite
//			if (!recipeIsFavourite) {
//				newMealPlanList.add(recipe);
//			}
//		}

		// Set new meal plan
		return newMealPlanList;
	}
}
