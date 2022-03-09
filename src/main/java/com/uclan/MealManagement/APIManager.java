
package com.uclan.MealManagement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;

import org.json.*;

public class APIManager {
	
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
		generalIngredients.add("coco powder");
		
		
		// Get response from API
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes"))
				.header("x-rapidapi-host", "tasty.p.rapidapi.com")
				.header("x-rapidapi-key", "1fe57c4133msh7efab93e804ef92p1e2bb3jsneafe38f2b6dd")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
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
		   for (int i=0; i<recipeArray.length(); i++){ 
			// Get recipe JSON
		    JSONObject recipeJson = recipeArray.getJSONObject(i);
		    
		    // Get recipe name
		    String recipeName = recipeJson.getString("name");
		    
		    // Get recipe details
		    JSONArray sections = recipeJson.getJSONArray("sections");
		    JSONObject section = sections.getJSONObject(0);
	    	JSONArray components = section.getJSONArray("components");
	    	JSONArray tags = recipeJson.getJSONArray("tags");
	    	
	    	// Iterate through tags
	    	String mealTime = "";
		    for (int j = 0; j < tags.length(); j++) {
		    	JSONObject tag = tags.getJSONObject(j);
		    	if (tag.getString("type").equals("meal"))
		    	{
		    		mealTime = tag.getString("name");
		    	}
		    }
		    
		    // Add recipe to SQL database
		    SQLManager.AddRecipeQuery(recipeName, mealTime);
	    	
	    	// Look at each ingredient/component
		    for (int j = 0; j < components.length(); j++) {
		    	// Get ingredient name
		    	JSONObject component = components.getJSONObject(j);
			    JSONObject ingredient = component.getJSONObject("ingredient");
			    String ingredientName = ingredient.getString("name");
			    
			    // Make into general ingredient
			    for (int k = 0; k < generalIngredients.size(); k++)
				{
					String generalIngredient = generalIngredients.get(k);
					if (ingredientName.contains(generalIngredient))
					{
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
