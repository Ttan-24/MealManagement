
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

import javax.swing.JLabel;

import org.json.*;

public class APIManager {
	public static void getAPI(String textField, JLabel label) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes"))
				.header("x-rapidapi-host", "tasty.p.rapidapi.com")
				.header("x-rapidapi-key", "1fe57c4133msh7efab93e804ef92p1e2bb3jsneafe38f2b6dd")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		label.setText(response.body());
		
		String displayName = "Recipes: ";
		
		String jsonString = response.body();
	    JSONObject jsonObject = new JSONObject(jsonString);  
		JSONArray jArray = jsonObject.getJSONArray("results");
		if (jArray != null) { 
		   for (int i=0; i<jArray.length(); i++){ 
		    JSONObject recipeJson = jArray.getJSONObject(i);
		    String name = recipeJson.getString("name");
		    displayName += name + ", ";
		    
		    System.out.print(name + ": ");
		    
		    // Get recipe details
		    JSONArray sections = recipeJson.getJSONArray("sections");
		    JSONObject section = sections.getJSONObject(0);
	    	JSONArray components = section.getJSONArray("components");
	    	
	    	// Look at each ingredient/component
		    for (int j = 0; j < components.length(); j++) {
		    	JSONObject component = components.getJSONObject(j);
			    JSONObject ingredient = component.getJSONObject("ingredient");
			    String ingredientName = ingredient.getString("name");
			    System.out.print(ingredientName + ", ");
		    }
		    
		    System.out.println();
		    
		   } 
		} 
		
		label.setText(displayName);
		
//		if (response.statusCode() == 200) {
//            System.out.println("The API has connected succssfully");
//        } else {
//        	throw new RuntimeException("HttpResponseCode: " + response.statusCode());
//        }
	   
	}
}
