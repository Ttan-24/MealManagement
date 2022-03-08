package com.uclan.MealManagement;

import java.util.ArrayList;

public class Recipe {
// list of strings array
	ArrayList<String> IngredientList = new ArrayList<String>();

	String mName;
	String mMealTime;

	public Recipe() {
	}

	public Recipe(String name, String mealTime) {
		this.mName = name;
		this.mMealTime = mealTime;
	}

}
