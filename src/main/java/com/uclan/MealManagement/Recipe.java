package com.uclan.MealManagement;

import java.util.ArrayList;
import java.util.Calendar;

class Ingredient {
	public String name;
	public Calendar expiryDate = Calendar.getInstance();
}

public class Recipe {
// list of strings array
	ArrayList<Ingredient> IngredientList = new ArrayList<Ingredient>();

	String mName;
	String mMealTime;

	public Recipe() {
	}

	public Recipe(String name, String mealTime) {
		this.mName = name;
		this.mMealTime = mealTime;
	}

}
