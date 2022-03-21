package com.uclan.MealManagement;

import java.util.ArrayList;
import java.util.Calendar;

class MealPlanDay {

	MealPlanDay(Calendar _date) {
		date = _date;
	}

	Calendar date;
	Recipe breakfastRecipe;
	Recipe lunchRecipe;
	Recipe dinnerRecipe;
}

public class MealPlan {
	ArrayList<MealPlanDay> days;

	MealPlan(Calendar startDate) {
		Calendar date = startDate;
		days = new ArrayList<MealPlanDay>();
		for (int i = 0; i < 7; i++) {
			MealPlanDay day = new MealPlanDay(date);
			date.add(Calendar.DATE, 1);

			days.add(day);
		}
	}

}

/*
 * 
 * Ideas for functions: - Set dates
 * 
 */