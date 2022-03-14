
package com.uclan.MealManagement;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIManager {

	// Formatting function for unusual characters in API descriptions
	public static String removeNonAlphanumeric(String str) {
		// replace the given string
		// with empty string
		// except the pattern "[^a-zA-Z0-9]"
		str = str.replaceAll("[^a-zA-Z0-9 ,.\r\n]", "");

		// return string
		return str;
	}

	public static void getAPI(String textField, JLabel label) throws Exception {

		// Make generalised ingredient list
		ArrayList<String> generalIngredients = new ArrayList<String>();
		generalIngredients.add("butter");
		generalIngredients.add("onion");
		generalIngredients.add("sugar");
		generalIngredients.add("carrots");
		generalIngredients.add("spinach");
		generalIngredients.add("salt");
		generalIngredients.add("carrot");
		generalIngredients.add("potato");
		generalIngredients.add("turmeric");
		generalIngredients.add("curry powder");
		generalIngredients.add("parsley");
		generalIngredients.add("black pepper");
		generalIngredients.add("bell pepper");
		generalIngredients.add("chicken breast");
		generalIngredients.add("thyme");
		generalIngredients.add("cheddar");
		generalIngredients.add("basil");
		generalIngredients.add("parmesian");
		generalIngredients.add("olive oil");
		generalIngredients.add("cumin");
		generalIngredients.add("cilantrio");
		generalIngredients.add("cranberries");
		generalIngredients.add("balsamic vinegar");
		generalIngredients.add("turkey");
		generalIngredients.add("water");
		generalIngredients.add("rosemary");
		generalIngredients.add("chives");
		generalIngredients.add("saffron");
		generalIngredients.add("milk");
		generalIngredients.add("cream");
		generalIngredients.add("egg");
		generalIngredients.add("cinnamon");
		generalIngredients.add("scallions");
		generalIngredients.add("ginger");
		generalIngredients.add("garlic");
		generalIngredients.add("cocoa powder");

		// Get response from API
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes"))
				.header("x-rapidapi-host", "tasty.p.rapidapi.com")
				.header("x-rapidapi-key", "1fe57c4133msh7efab93e804ef92p1e2bb3jsneafe38f2b6dd")
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		label.setText(response.body());

		// Get root JSON
		String jsonString = response.body();
		JSONObject rootJSON = new JSONObject(jsonString);

		// Get recipe array
		JSONArray recipeArray = rootJSON.getJSONArray("results");

		// Iterate through recipe array
		if (recipeArray != null) {
			for (int i = 0; i < recipeArray.length(); i++) {
				// Get recipe JSON
				JSONObject recipeJson = recipeArray.getJSONObject(i);
				JSONObject recipeNutrition = recipeJson.getJSONObject("nutrition");

				// Declare details
				String recipeName = "";
				String recipeDescription = "";
				String recipeTime = "0";
				String recipeDietCategory = "";
				String recipeCalories = "0";
				String recipeDifficulty = "";
				String recipeServings = "0";

				// Get details
				recipeName = recipeJson.getString("name");
				recipeDescription = recipeJson.getString("description");
				if (!recipeJson.isNull("total_time_minutes")) {
					recipeTime = String.valueOf(recipeJson.getInt("total_time_minutes"));
				}
				recipeDietCategory = "none";
				if (!recipeNutrition.isNull("calories")) {
					recipeCalories = String.valueOf(recipeNutrition.getInt("calories"));
				}
				recipeDifficulty = "";
				if (!recipeJson.isNull("num_servings")) {
					recipeServings = String.valueOf(recipeJson.getInt("num_servings"));
				}

				// Get instructions
				String recipeInstructions = "";
				JSONArray recipeInstructionsJSON = recipeJson.getJSONArray("instructions");
				for (int j = 0; j < recipeInstructionsJSON.length(); j++) {
					recipeInstructions += recipeInstructionsJSON.getJSONObject(j).getString("display_text") + "\n";
				}

				// Format description
				recipeDescription = removeNonAlphanumeric(recipeDescription);
				recipeInstructions = removeNonAlphanumeric(recipeInstructions);

				// Get tags
				JSONArray sections = recipeJson.getJSONArray("sections");
				JSONObject section = sections.getJSONObject(0);
				JSONArray components = section.getJSONArray("components");
				JSONArray tags = recipeJson.getJSONArray("tags");

				// Iterate through tags
				String mealTime = "";
				for (int j = 0; j < tags.length(); j++) {
					JSONObject tag = tags.getJSONObject(j);
					String tagType = tag.getString("type");
					String tagName = tag.getString("name");

					// What mealtime? (lunch/breakfast/etc.)
					if (tagType.equals("meal")) {
						mealTime = tagName;
					}

					// Difficulty
					if (tagType.equals("difficulty")) {
						recipeDifficulty = tagName;
					}
				}

				// Add recipe to SQL database
				SQLManager.AddRecipeQuery(recipeName, mealTime, recipeDescription, recipeTime, recipeDietCategory,
						recipeCalories, recipeDifficulty, recipeServings, recipeInstructions);

				// Look at each ingredient/component
				for (int j = 0; j < components.length(); j++) {
					// Get ingredient name
					JSONObject component = components.getJSONObject(j);
					JSONObject ingredient = component.getJSONObject("ingredient");
					String ingredientName = ingredient.getString("name");

					// Make into general ingredient
					for (int k = 0; k < generalIngredients.size(); k++) {
						String generalIngredient = generalIngredients.get(k);
						if (ingredientName.contains(generalIngredient)) {
							ingredientName = generalIngredient;
						}
					}

					// Add ingredient to SQL database
					SQLManager.AddIngredientsFromList(ingredientName, SQLManager.RecipeID());
				}

			}
		}

//		if (response.statusCode() == 200) {
//            System.out.println("The API has connected succssfully");
//        } else {
//        	throw new RuntimeException("HttpResponseCode: " + response.statusCode());
//        }

	}
}
