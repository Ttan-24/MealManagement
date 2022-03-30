package com.uclan.MealManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SQLManager {

	private static String User;
	private static String Pass;

	// a member function to connect the sql database
	public static Connection getConnection() throws Exception {

		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/store_db?serverTimezone=UTC";
			String username = "root";
			String password = "root";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected successfully");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			LogFileManager.logError(e.getMessage());
		} catch (Exception ex) {
			System.out.println(ex);
			LogFileManager.logError(ex.getMessage());
		} catch (Throwable ex) {
			System.out.println(ex);
			LogFileManager.logError(ex.getMessage());
		}
		return null; // couldn't return a connection
	}

	// if username and password is
	public static boolean setLoggingDetails(String Username, String Password) throws Exception {
		Connection getConnection = getConnection();
		if (getConnection.isValid(0)) {
			SQLManager.User = Username;
			SQLManager.Pass = Password;
			System.out.println("Connection Valid");
			return true;
		} else {
			System.out.println("Connection not valid");
			return false;
		}
	}

	// if username and password from querylogin == to the username and password from
	// the textfield than move to a different cardlayout

	public static Boolean queryLogin(String Username, String Password) throws Exception {
		try {
			Map<String, String> loginDetail = new HashMap<String, String>();

			Connection getConnection = getConnection();
			// Filtering out groups without a name or any patients.
			String query = "SELECT Username, Password FROM store_db.customer WHERE Username = '" + Username + "';";

			Statement st = getConnection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				loginDetail.put("Username", rs.getString(1));
				loginDetail.put("Password", rs.getString(2));
			}

			if (Username.equals(loginDetail.get("Username")) && Password.equals(loginDetail.get("Password"))) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			LogFileManager.logError(e.getMessage());
			return false;
		}
	}

	// query
	public static Map<String, String> UserDetails(String Username) {
		try {
			Map<String, String> Details = new HashMap<String, String>();

			Connection getConnection = getConnection();
			// Filtering out groups without a name or any patients.
			String query = "SELECT * FROM store_db.customer WHERE Username = '" + Username + "';";
			Statement st = getConnection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Details.put("idcustomer", rs.getString(1));
				Details.put("Username", rs.getString(4));
			}

			return Details;
		} catch (Exception e) {
			e.printStackTrace();
			LogFileManager.logError(e.getMessage());
			return null;
		}
	}

	public static ResultSet FridgeQuery(String customerID) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();
			query = "Select p.itemName, p.bestbefore, p.quantity, p.calories from store_db.items p LEFT JOIN store_db.customer a ON p.idcustomer = a.idcustomer where a.idcustomer = '"
					+ customerID + "';";
			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			LogFileManager.logError(e.getMessage());
			return null;
		}
	}

	public static void AddFridgeQuery(String customerID, String itemName, String bestBefore, String quantity,
			String calories) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();

			query = "INSERT INTO store_db.items (idcustomer, itemName, bestbefore, quantity, calories) VALUES ('"
					+ customerID + "', '" + itemName + "', '" + bestBefore + "', '" + quantity + "', '" + calories
					+ "');";
			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			LogFileManager.logError(e.getMessage());
		}
	}

	public static void DeleteFridgeQuery(String customerID, String itemName) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();

			query = "DELETE FROM store_db.items WHERE idcustomer = '" + customerID + "' AND itemName = '" + itemName
					+ "';";

			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);

		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
		}
	}

	public static String TotalFridgeItemsQuery() throws Exception {
		String query = "";
		String totalCount = null;
		try {
			Connection getConnection = getConnection();
			query = "SELECT COUNT(idItems) FROM store_db.items;";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				totalCount = rs.getString(1);
			}
			return totalCount;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet RecipeQuery() throws Exception {
		String query = "";
		try {
			Connection getConnection = getConnection();
			query = "SELECT idrecipe, recipeName, mealTime FROM store_db.recipe;";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet RecipeQuery(int maxCalories, int maxCookTime, String difficulty) throws Exception {
		String query = "";
		try {
			Connection getConnection = getConnection();
			query = "SELECT idrecipe, recipeName, mealTime, recipeTime, recipeCalories, recipeDifficulty FROM store_db.recipe "
					+ "WHERE recipeCalories < '" + maxCalories + "' AND recipeDifficulty = '" + difficulty
					+ "' AND recipeTime < '" + maxCookTime + "';";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> RecipeQueryCuisine(int maxCalories, int maxCookTime, String difficulty,
			String cuisine) throws Exception {
		String query = "";
		try {

			// Make recipe list
			ArrayList<String> recipeList = new ArrayList<String>();

			Connection getConnection = getConnection();
			query = "SELECT idrecipe, recipeName, mealTime, recipeTime, recipeCalories, recipeDifficulty FROM store_db.recipe "
					+ "WHERE recipeCuisine = '" + cuisine + "';";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);

			// Turn result set into arraylist
			while (rs.next()) {
				recipeList.add(rs.getString(2));
			}

			return recipeList;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static void AddRecipeQuery(String recipeName, String mealTime, String recipeDescription, String recipeTime,
			String recipeDietCategory, String recipeCalories, String recipeDifficulty, String recipeServings,
			String recipeInstructions, String recipeCuisine) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();

			query = "INSERT INTO store_db.recipe (recipeName, mealTime, recipeDescription, recipeTime, dietCategory, recipeCalories, recipeDifficulty, recipeServings, recipeInstructions, recipeCuisine) VALUES ('"
					+ recipeName + "', '" + mealTime + "', '" + recipeDescription + "', '" + recipeTime + "', '"
					+ recipeDietCategory + "', '" + recipeCalories + "', '" + recipeDifficulty + "', '" + recipeServings
					+ "', '" + recipeInstructions + "', '" + recipeCuisine + "')";

			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);

		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
		}
	}

	public static void AddIngredientsFromList(String ingredientName, String idrecipe) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();
			query = "INSERT INTO store_db.ingredients (ingredientName,  idrecipe) VALUES ('" + ingredientName + "', '"
					+ idrecipe + "')";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);
		} catch (Exception e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			System.out.println(e);
		}
	}

	public static String RecipeID() throws Exception {
		String query = "";
		String id = null;
		try {
			Connection getConnection = getConnection();
			query = "SELECT MAX(idrecipe) FROM store_db.recipe;";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				id = rs.getString(1);
			}
			return id;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Ingredient> getIngredientsOfRecipe(String recipeId) throws Exception {
		String query = "";
		ArrayList<Ingredient> IngredientList = new ArrayList<Ingredient>();
		try {
			// Connection
			Connection getConnection = getConnection();
			query = "Select p.ingredientName from store_db.ingredients p LEFT JOIN store_db.recipe a ON p.idrecipe = a.idrecipe where a.idrecipe = '"
					+ recipeId + "';";
			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Ingredient ingredient = new Ingredient();
				ingredient.name = rs.getString(1);
				IngredientList.add(ingredient);
			}

			return IngredientList;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getFavouriteRecipes(String customerId) throws Exception {
		String query = "";
		ArrayList<String> FavouriteRecipeList = new ArrayList<String>();
		try {
			Connection getConnection = getConnection();
			query = "Select p.recipeName from store_db.recipe p LEFT JOIN store_db.favourites a ON p.idrecipe = a.recipeId where a.customerID = '"
					+ customerId + "';";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				FavouriteRecipeList.add(rs.getString(1));
			}

			return FavouriteRecipeList;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet getFavouriteRecipesResultSet(String customerId) throws Exception {
		String query = "";
		try {
			Connection getConnection = getConnection();
			query = "Select p.recipeName from store_db.recipe p LEFT JOIN store_db.favourites a ON p.idrecipe = a.recipeId where a.customerID = '"
					+ customerId + "';";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static Object UpdatePassword(String customerId, String newPassword) throws Exception {
		String query = "";
		try {
			Connection getConnection = getConnection();
			query = "UPDATE store_db.customer SET Password = '" + newPassword + "' WHERE idcustomer = '" + customerId
					+ "';";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int rs = st.executeUpdate(query);
			return rs;
		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static void AddFavouriteRecipe(String customerID, String recipeID) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();

			query = "INSERT INTO store_db.favourites (customerId, recipeId) VALUES ('" + customerID + "', '" + recipeID
					+ "');";

			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);

		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
		}
	}

	public static void DeleteFavouriteRecipe(String customerID, String recipeID) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();

			query = "DELETE FROM store_db.favourites WHERE recipeId = '" + recipeID + "' AND customerId = '"
					+ customerID + "';";

			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);

		} catch (SQLException e) {
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
		}
	}

	public static ArrayList<HashMap<String, String>> findSimilarUsers(String customerId)
			throws Exception, SQLException {
		// give arraylist of string with each of their ids
		String query = "";
		ArrayList<HashMap<String, String>> SimilarUserList = new ArrayList<HashMap<String, String>>();

		Connection getConnection = getConnection();
		query = "SELECT a.customerId AS current_customer_id, b.customerId AS other_customer_id, COUNT(*) AS shared_recipe_count FROM store_db.favourites AS b JOIN store_db.favourites AS a ON a.recipeId = b.recipeId AND a.customerId <> b.customerId WHERE a.customerId = '"
				+ customerId + "' GROUP BY a.customerId, b.customerId ORDER BY a.customerId, shared_recipe_count DESC;";

		Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			HashMap<String, String> SimilarUserMap = new HashMap<String, String>();
			SimilarUserMap.put("UserID", rs.getString(2));
			SimilarUserMap.put("SimilarityScore", rs.getString(3));
			SimilarUserList.add(SimilarUserMap);
		}
		return SimilarUserList;
	}

	public static void populateSuggestedRecipes(String customerId, JLabel suggestedRecipesLabel) throws Exception {
		// give arraylist of recipe ids

		ArrayList<HashMap<String, String>> similarUsers = findSimilarUsers(customerId);
		String similarUserId = similarUsers.get(0).get("UserID");

		ArrayList<String> SuggestedRecipes = getFavouriteRecipes(similarUserId);
		String SuggestedRecipeString = "Suggested Recipes: ";
		for (int i = 0; i < SuggestedRecipes.size(); i++) {
			SuggestedRecipeString += SuggestedRecipes.get(i) + ", ";
		}
		suggestedRecipesLabel.setText(SuggestedRecipeString);
	}

	public static String GetMostPopularCuisine(String customerID) throws Exception {

		// Set query
		String query = "SELECT p.customerId, p.idFavourites, a.recipeCuisine, COUNT(a.recipeCuisine) AS cusine_count FROM store_db.favourites p LEFT JOIN store_db.recipe a ON p.recipeId = a.idrecipe WHERE p.customerID = "
				+ customerID + "\r\n" + "				GROUP BY a.recipeCuisine \r\n"
				+ "				ORDER BY cusine_count DESC;";

		// Get connection
		Connection getConnection = getConnection();

		// Execute query
		Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery(query);
		rs.next();

		// Return
		return rs.getString(3);
	}

	public static void populateSuggestedRecipesInTable(String customerId, JTable suggestedRecipesTable)
			throws Exception {
		// Find similar users
		ArrayList<HashMap<String, String>> similarUsers = findSimilarUsers(customerId);
		String similarUserId = similarUsers.get(0).get("UserID");

		// Get suggested recipes
		ArrayList<String> SuggestedRecipes = getFavouriteRecipes(similarUserId);

		// Get most popular cuisine
		String mostPopularCuisine = GetMostPopularCuisine(customerId);

		// Get recipes with most popular cuisine
		ArrayList<String> suggestedRecipesByCuisine = RecipeQueryCuisine(UserPreferenceManager.maxCalories,
				UserPreferenceManager.maxCookTime, UserPreferenceManager.difficulty, mostPopularCuisine);

		// Add cuisine suggestions (item based) onto CF/user suggested recipes
		for (int i = 0; i < suggestedRecipesByCuisine.size(); i++) {
			SuggestedRecipes.add(suggestedRecipesByCuisine.get(i));
		}

		// Filter out existing favourites
		ArrayList<String> existingFavourites = getFavouriteRecipes(customerId);
		for (int i = 0; i < existingFavourites.size(); i++) {
			// Get suggested
			String existingFavourite = existingFavourites.get(i);
			// Look for existing
			for (int j = 0; j < SuggestedRecipes.size(); j++) {
				// Get existing
				String suggestedFavourite = SuggestedRecipes.get(j);

				// Compare
				if (existingFavourite.equals(suggestedFavourite)) {
					// Remove
					SuggestedRecipes.remove(j);
					j--;
				}
			}
		}

		// Initialise table model
		DefaultTableModel suggestedRecipesTableModel = new DefaultTableModel();

		// Add rows for suggested recipes
		suggestedRecipesTableModel.setRowCount(0);
		for (int i = 0; i < SuggestedRecipes.size(); i++) {
			suggestedRecipesTableModel.addRow(new Object[] {});
		}

		// Add columns
		suggestedRecipesTableModel.addColumn("Recipe Name");

		// Populate model
		for (int i = 0; i < SuggestedRecipes.size(); i++) {
			String recipeName = SuggestedRecipes.get(i);
			suggestedRecipesTableModel.setValueAt(recipeName, i, 0);
		}

		// Set table model
		suggestedRecipesTable.setModel(suggestedRecipesTableModel);
	}

	public static HashMap<String, String> getRecipeDetails(int recipeID) throws Exception, SQLException {
		String query = "";
		HashMap<String, String> RecipeDetailMap = new HashMap<String, String>();

		Connection getConnection = getConnection();
		query = "SELECT * FROM store_db.recipe where idrecipe = '" + recipeID + "'";

		Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			RecipeDetailMap.put("RecipeID", rs.getString(1));
			RecipeDetailMap.put("RecipeName", rs.getString(2));
			RecipeDetailMap.put("mealTime", rs.getString(3));
			RecipeDetailMap.put("RecipeDescription", rs.getString(4));
			RecipeDetailMap.put("RecipeTime", rs.getString(5));
			RecipeDetailMap.put("RecipeDifficulty", rs.getString(6));
			RecipeDetailMap.put("RecipeServings", rs.getString(7));
			RecipeDetailMap.put("RecipeCalories", rs.getString(8));
			RecipeDetailMap.put("dietCategory", rs.getString(9));
			RecipeDetailMap.put("RecipeInstructions", rs.getString(10));
		}
		return RecipeDetailMap;
	}

	public static int getRecipeID(String recipeName) throws Exception, SQLException {
		String query = "";
		Connection getConnection = getConnection();
		query = "SELECT * FROM store_db.recipe where recipeName = '" + recipeName + "'";
		Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery(query);
		rs.next();
		return Integer.parseInt(rs.getString(1));
	}

	public static void decrementIngredient(String ingredientName) throws Exception {
		// Needs an SQL statement to decrement the quantity of this ingredient
		String query = "";
		String updateQuery = "";
		String quantity = null;
		try {
			// Connection
			Connection getConnection = getConnection();
			query = "SELECT quantity FROM store_db.items where itemName = '" + ingredientName + "';";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				quantity = rs.getString(1);
			}

			int newQuantity = Integer.parseInt(quantity) - 1;

			updateQuery = "UPDATE store_db.items SET quantity = '" + newQuantity + "' WHERE itemName = '"
					+ ingredientName + "';";
			Statement st2 = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int rs2 = st2.executeUpdate(updateQuery);

		} catch (SQLException e) {
			e.printStackTrace();
			LogFileManager.logError(e.getMessage() + "(" + query + " )");
		}
	}
}
