package com.uclan.MealManagement;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MealAlgorithmManager {
	// Params
	public static int mealCountMax = 7 * 3;

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
		UpdatePossibleRecipes();

		System.out.println("I can make " + possibleRecipes.size() + " recipes");

		// Make meal plan
		while (mealList.size() < mealCountMax && possibleRecipes.size() > 0) {
			// Get next available recipe -- can determine which is best here
			Recipe breakfastRecipe = getBreakfastRecipe();
			Recipe lunchRecipe = getLunchRecipe();
			Recipe dinnerRecipe = getDinnerRecipe();

			// Add a meal if possible - needs to be split into meal times
			System.out.println("Adding " + breakfastRecipe.mName);
			mealList.add(breakfastRecipe);
			BreakfastList.add(breakfastRecipe.mName);

			System.out.println("Adding " + lunchRecipe.mName);
			mealList.add(lunchRecipe);
			LunchList.add(lunchRecipe.mName);

			System.out.println("Adding " + dinnerRecipe.mName);
			mealList.add(dinnerRecipe);
			DinnerList.add(dinnerRecipe.mName);

			// remove meal time recipe ingredients from fridge
			removeRecipeIngredientsFromFridge(breakfastRecipe);
			removeRecipeIngredientsFromFridge(lunchRecipe);
			removeRecipeIngredientsFromFridge(dinnerRecipe);

			// Update possible recipes
			UpdatePossibleRecipes();

			// Print recipe status
			System.out.println("I can make " + possibleRecipes.size() + " recipes");
		}

		// Output end result
		if (mealList.size() == mealCountMax) {
			System.out.println("All meals generated");
		} else {
			System.out.print("Did not have enough ingredients");
		}
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

	public static Recipe getBreakfastRecipe() {
		Recipe recipe;
		for (int i = 0; i < possibleRecipes.size(); i++) {
			recipe = possibleRecipes.get(i);
			if (recipe.mMealTime.equals("breakfast")) {
				return recipe;
			}
		}
		return null;
	}

	public static Recipe getLunchRecipe() {
		Recipe recipe;
		for (int i = 0; i < possibleRecipes.size(); i++) {
			recipe = possibleRecipes.get(i);
			if (recipe.mMealTime.equals("lunch")) {
				return recipe;
			}
		}
		return null;
	}

	public static Recipe getDinnerRecipe() {
		Recipe recipe;
		for (int i = 0; i < possibleRecipes.size(); i++) {
			recipe = possibleRecipes.get(i);
			if (recipe.mMealTime.equals("dinner")) {
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

	public static void UpdatePossibleRecipes() {
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
	}
}
