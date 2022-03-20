package com.uclan.MealManagement;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MealAlgorithmManager {
	// Params
	public static int mealPlanDays = 7;

	// Data
	public static ArrayList<String> fridgeIngredients = new ArrayList<String>();
	public static ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
	public static ArrayList<Recipe> possibleRecipes = new ArrayList<Recipe>();
	public static ArrayList<Recipe> mealList = new ArrayList<Recipe>();

	// Main event
	public static ArrayList<Recipe> CalculateMealPlan(String UserId, ArrayList<String> BreakfastList,
			ArrayList<String> LunchList, ArrayList<String> DinnerList) throws Exception {

		// Initialise data
		InitialiseRecipeList();
		InitialiseFridge(UserId);
		mealList = new ArrayList<Recipe>();

		// Do initial update of possible recipes
		UpdatePossibleRecipes(UserId);
		System.out.println("I can make " + possibleRecipes.size() + " different recipes");

		// Get list of recipes for different meal times
		ArrayList<Recipe> breakfastRecipeList = makeMealTimeList("breakfast", UserId);
		ArrayList<Recipe> lunchRecipeList = makeMealTimeList("lunch", UserId);
		ArrayList<Recipe> dinnerRecipeList = makeMealTimeList("dinner", UserId);

		// Declare errors
		if (breakfastRecipeList == null) {
			System.out.print("Could not make enough breakfast meals");
		}
		if (lunchRecipeList == null) {
			System.out.print("Could not make enough lunch meals");
		}
		if (dinnerRecipeList == null) {
			System.out.print("Could not make enough dinner meals");
		}

		// Update string lists for JTable population
		for (int i = 0; i < breakfastRecipeList.size(); i++) {
			BreakfastList.add(breakfastRecipeList.get(i).mName);
			mealList.add(breakfastRecipeList.get(i));
		}
		for (int i = 0; i < lunchRecipeList.size(); i++) {
			LunchList.add(lunchRecipeList.get(i).mName);
			mealList.add(lunchRecipeList.get(i));
		}
		for (int i = 0; i < dinnerRecipeList.size(); i++) {
			DinnerList.add(dinnerRecipeList.get(i).mName);
			mealList.add(dinnerRecipeList.get(i));
		}

		// Return
		return mealList;
	}

	public static void InitialiseRecipeList() throws Exception {
		allRecipes = new ArrayList<Recipe>();
		ResultSet rsRecipeQuery = SQLManager.RecipeQuery();
		while (rsRecipeQuery.next()) {
			String recipeName = rsRecipeQuery.getString(2);
			String mealTime = rsRecipeQuery.getString(3);
			ArrayList<String> Ingredients = SQLManager.getIngredientsOfRecipe(rsRecipeQuery.getString(1));
			Recipe recipe = new Recipe();
			recipe.IngredientList = Ingredients;
			recipe.mName = recipeName;
			recipe.mMealTime = mealTime;
			allRecipes.add(recipe);
		}
	}

	public static void InitialiseFridge(String UserId) throws Exception {
		fridgeIngredients = new ArrayList<String>();
		ResultSet rsFridgeQuery = SQLManager.FridgeQuery(UserId);
		while (rsFridgeQuery.next()) {
			String itemName = rsFridgeQuery.getString(1);
			int quantity = Integer.parseInt(rsFridgeQuery.getString(3));
			for (int i = 0; i < quantity; i++) {
				fridgeIngredients.add(itemName);
			}
		}
	}

	// This function gives 7 recipes, all for one time (eg lunch, breakfast, dinner)
	public static ArrayList<Recipe> makeMealTimeList(String mealTime, String UserId) throws Exception {
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
		Recipe recipe;
		for (int i = 0; i < possibleRecipes.size(); i++) {
			recipe = possibleRecipes.get(i);
			if (recipe.mMealTime.equals(mealTime)) {
				return recipe;
			}
		}
		return null;
	}

	public static void removeRecipeIngredientsFromFridge(Recipe recipe) {
		// Remove ingredients from fridge
		for (int i = 0; i < recipe.IngredientList.size(); i++) {
			// Ingredient name
			String ingredient = recipe.IngredientList.get(i);

			// Find in fridge
			for (int j = 0; j < fridgeIngredients.size(); j++) {
				// Fridge ingredient
				String fridgeIngredient = fridgeIngredients.get(j);

				// Remove and finish iteration if found
				if (ingredient.equals(fridgeIngredient)) {
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
				String requiredIngredient = recipe.IngredientList.get(j);

				// Bool for if ingredient is found
				boolean ingredientFound = false;

				// Look for ingredient in fridge
				for (int k = 0; k < fridgeIngredients.size(); k++) {
					// Get fridge ingredient
					String fridgeIngredient = fridgeIngredients.get(k);

					// Find if ingredient is missing
					if (fridgeIngredient.equals(requiredIngredient)) {
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
				newMealPlanList.add(recipe);
			}
		}

		// add non favourites
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

			// Add if not favourite
			if (!recipeIsFavourite) {
				newMealPlanList.add(recipe);
			}
		}

		// Set new meal plan
		return newMealPlanList;
	}
}
