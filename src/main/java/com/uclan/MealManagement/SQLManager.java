package com.uclan.MealManagement;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SQLManager {

	private static String User;
	private static String Pass;

	// a member function to connect the sql database
	public static Connection getConnection() throws Exception {

		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/store_db?serverTimezone=UTC";
			// String url = "jdbc:mysql://localhost:3306/store?serverTimezone=UTC";
			String username = "root";
			String password = "root";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected successfully Man");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			System.out.println(ex);
		} catch (Throwable ex) {
			System.out.println(ex);
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
			// LogFileManager.logError("If connection is valid -- psc");
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
			// String query = "SELECT Username, Password FROM store.customer WHERE Username
			// = '" + Username + "';";

			Statement st = getConnection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// Map<String, String> loginDetail = new HashMap<String, String>();
				loginDetail.put("Username", rs.getString(1));
				loginDetail.put("Password", rs.getString(2));
				// loginDetails.add(loginDetail);
			}

			if (Username.equals(loginDetail.get("Username")) && Password.equals(loginDetail.get("Password"))) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage());
			e.printStackTrace();
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
//			String query = "SELECT * FROM store.customer WHERE Username = '" + Username + "';";
			Statement st = getConnection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// Map<String, String> Detail = new HashMap<String, String>();
				Details.put("idcustomer", rs.getString(1));
//				Details.put("Username", rs.getString(12));
				Details.put("Username", rs.getString(4));
				// Details.add(Detail);
			}

			return Details;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet FridgeQuery(String customerID) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();
			query = "Select p.itemName, p.quantity, p.calories from store_db.items p LEFT JOIN store_db.customer a ON p.idcustomer = a.idcustomer where a.idcustomer = '"
					+ customerID + "';";
			// query = "Select p.itemName, p.quantity, p.calories from store.item p LEFT
			// JOIN store.customer a ON p.idcustomer = a.idcustomer where a.idcustomer = '"
			// + customerID + "';";
			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static void AddFridgeQuery(String customerID, String itemName, String quantity, String calories)
			throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();

			query = "INSERT INTO store_db.items (idcustomer, itemName, quantity, calories) VALUES ('" + customerID
					+ "', '" + itemName + "', '" + quantity + "', '" + calories + "');";

//			query = "INSERT INTO store.item (idcustomer, itemName, quantity, calories) VALUES ('" + customerID
//					+ "', '" + itemName + "', '" + quantity + "', '" + calories + "');";
			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);

		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
		}
	}

	public static ResultSet RecipeQuery() throws Exception {
		String query = "";
		try {
			Connection getConnection = getConnection();
			query = "SELECT * FROM store_db.recipe;";
//			query = "SELECT * FROM store.recipe;";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static void AddRecipeQuery(String recipeName, String mealTime, String recipeDescription, String recipeTime,
			String recipeDietCategory, String recipeCalories, String recipeDifficulty, String recipeServings,
			String recipeInstructions) throws Exception {
		String query = "";
		try {
			// Connection
			Connection getConnection = getConnection();

			query = "INSERT INTO store_db.recipe (recipeName, mealTime, recipeDescription, recipeTime, dietCategory, recipeCalories, recipeDifficulty, recipeServings, recipeInstructions) VALUES ('"
					+ recipeName + "', '" + mealTime + "', '" + recipeDescription + "', '" + recipeTime + "', '"
					+ recipeDietCategory + "', '" + recipeCalories + "', '" + recipeDifficulty + "', '" + recipeServings
					+ "', '" + recipeInstructions + "');";
//			query = "INSERT INTO store.recipe (recipeName, mealTime) VALUES ('" + RecipeName + "', '" + mealTime + "');";

			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);

		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage() + "(" + query + " )");
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
//			query = "INSERT INTO store.ingredient (ingredientName,  idrecipe) VALUES ('" + ingredientName + "', '"
//					+ idrecipe + "')";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String RecipeID() throws Exception {
		String query = "";
		String id = null;
		try {
			Connection getConnection = getConnection();
			query = "SELECT MAX(idrecipe) FROM store_db.recipe;";
//			query = "SELECT MAX(idrecipe) FROM store.recipe;";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				id = rs.getString(1);
			}
			return id;
		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getIngredientsOfRecipe(String recipeId) throws Exception {
		String query = "";
		ArrayList<String> IngredientList = new ArrayList<String>();
		try {
			// Connection
			Connection getConnection = getConnection();
			query = "Select p.ingredientName from store_db.ingredients p LEFT JOIN store_db.recipe a ON p.idrecipe = a.idrecipe where a.idrecipe = '"
					+ recipeId + "';";
//			query = "Select p.ingredientName from store.ingredient p LEFT JOIN store.recipe a ON p.idrecipe = a.idrecipe where a.idrecipe = '"
//					+ recipeId + "';";
			// it allows to reset the result set
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// Map<String, String> Detail = new HashMap<String, String>();
				IngredientList.add(rs.getString(1));
			}

			return IngredientList;
		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage() + "(" + query + " )");
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
//			query = "Select p.recipeName from store.recipe p LEFT JOIN store.favourite a ON p.idrecipe = a.recipeId where a.idcustomer = '"
//					+ customerId + "';";
			Statement st = getConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				FavouriteRecipeList.add(rs.getString(1));
			}

			return FavouriteRecipeList;
		} catch (SQLException e) {
			// LogFileManager.logError(e.getMessage() + "(" + query + " )");
			e.printStackTrace();
			return null;
		}
	}

	public static void populateTableWithResultSet(JTable table, ResultSet rs) throws SQLException {

		// Resultset metadata
		ResultSetMetaData metaData = rs.getMetaData();

		// Table columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// Table data
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		rs.beforeFirst(); // the populatetablewithresultset it will end up puting the cursor at the end so
		// we need before first to reset the cursor in the beginning

		// Create model
		DefaultTableModel model = new DefaultTableModel(data, columnNames);

		Color black = Color.getHSBColor(0.0f, 0.0f, 0.0f);
		Color green = Color.decode("#A6FFCB");
		// Set table model
		table.setModel(model);
		JTableHeader Header = table.getTableHeader();
		Header.setBackground(green);
		Header.setForeground(black);
		Header.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		// Resize table
		// resizeTable(table);

		// Set resize mode
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}

	public static void populateTableWithResultSetWithCheckBox(JTable table, ResultSet rs, String customerId)
			throws Exception {

		// Resultset metadata
		ResultSetMetaData metaData = rs.getMetaData();

		// Table columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// Table data
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		rs.beforeFirst(); // the populatetablewithresultset it will end up puting the cursor at the end so
		// we need before first to reset the cursor in the beginning

		// Create model
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int columnIndex) {
				if (columnIndex == 3) {
					return Boolean.class;
				} else {
					return Object.class;
				}
			}
		};
		// model.addRow(new Object[] { false });

		ArrayList<String> favouriteList = getFavouriteRecipes(customerId);
		Vector<Boolean> toggleList = new Vector<Boolean>();
		for (int i = 0; i < model.getRowCount(); i++) {
			Object getValue = model.getValueAt(i, 1);
			if (favouriteList.contains(getValue)) {
				toggleList.add(true);
			} else {
				toggleList.add(false);
			}
		}
		model.addColumn("Favourite", toggleList);
		Color black = Color.getHSBColor(0.0f, 0.0f, 0.0f);
		Color green = Color.decode("#A6FFCB");
		// Set table model
		table.setModel(model);
		JTableHeader Header = table.getTableHeader();
		Header.setBackground(green);
		Header.setForeground(black);
		Header.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		// Resize table
		// resizeTable(table);

		// Set resize mode
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}

	public static ArrayList<HashMap<String, String>> findSimilarUsers(String customerId)
			throws Exception, SQLException {
		// give arraylist of string with each of their ids
		String query = "";
		ArrayList<HashMap<String, String>> SimilarUserList = new ArrayList<HashMap<String, String>>();

		Connection getConnection = getConnection();
		query = "SELECT cus.customerId AS current_user_id, ous.customerId AS other_user_id, COUNT(*) AS same_recipe_count FROM store_db.favourites AS ous JOIN store_db.favourites AS cus ON cus.recipeId = ous.recipeId AND cus.customerId <> ous.customerId WHERE cus.customerId = '"
				+ customerId
				+ "' GROUP BY cus.customerId, ous.customerId ORDER BY cus.customerId, same_recipe_count DESC";

//		query = "SELECT cus.idcustomer AS current_user_id, ous.idcustomer AS other_user_id, COUNT(*) AS same_recipe_count FROM store.favourite AS ous JOIN store.favourite AS cus ON cus.recipeId = ous.recipeId AND cus.idcustomer <> ous.idcustomer WHERE cus.idcustomer = '"
//				+ customerId
//				+ "' GROUP BY cus.idcustomer, ous.idcustomer ORDER BY cus.idcustomer, same_recipe_count DESC";

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
}
