package com.uclan.MealManagement;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class MealManagementInterface extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private DefaultTableModel model;
	private JPanel cardLayoutPane;
	private CardLayout cardLayout = new CardLayout(0, 0);
	private CardLayout viewCardLayout = new CardLayout(0, 0);
	private JTextField UsernameTextField;
	private JTextField PasswordTextField;
	private JPanel ViewPanel;
	private JTable FridgeTable;
	private String mUsername; // member variable
	private String mUserID;
	private ArrayList<Recipe> mRecipeList = new ArrayList<Recipe>(); // this is the recipe list from the api
	private JTable MealPlanTable;
	public ArrayList<String> FridgeIngredientList = new ArrayList<String>();
	public ArrayList<String> RecipeQueryList = new ArrayList<String>();
	private DefaultTableModel MealPlanTableModel;
	ArrayList<Recipe> mPossibleRecipes;
	private String[] MealPlanColumns;
	private Object[] RecipeColumns;
	private DefaultTableModel RecipeTableModel;
	private JTable RecipeTable;
	private JTextField RecipeNameTextField;
	private JTextField IngredientNameTextField;
	private JTable IngredientTable;
	private JScrollPane IngredientScrollPane;
	private DefaultTableModel IngredientTableModel;
	private DefaultTableModel FavouriteRecipesTableModel;
	private String[] IngredientColumns;
	private String[] FavouriteRecipesColumns;
	private JTextField ItemNameTextField;
	private JTextField QuantityTextField;
	private JTextField CaloriesTextField;
	public static ArrayList<String> IngredientTableList = new ArrayList<String>();
	private JTable FavouriteRecipesTable;
	private JScrollPane FavouriteRecipesScrollPane;
	private JLabel SuggestedRecipesLabel;
	private JTextField SearchRecipeTextField;
	private JPanel RecipeFinderPanel;
	private JLabel SearchedRecipesLabel;
	private JTextField MealTimeTextField;
	private JTable RecipeRecommendationsTable;
	private JTextArea RecipeDescriptionTextArea;
	private JLabel RecipeDetailsTitle;
	private JTextArea RecipeProcessDetailsTextArea;
	private JTextArea NutritionTextArea;
	private JTextArea RecipeIngredientsTextArea;
	private JTextArea RecipeInstructionsTextArea;

	public static void deleteAllRows(final DefaultTableModel model) {
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	// recipe manager
	public ArrayList<Recipe> detectRecipe(ArrayList<String> FridgeItemList, ArrayList<Recipe> RecipeList) {
		// creating possible recipe list
		ArrayList<Recipe> PossibleRecipes = new ArrayList<Recipe>();

		// looking through the recipes
		for (int i = 0; i < RecipeList.size(); i++) {
			Recipe EachRecipe = RecipeList.get(i);
			System.out.println("Checking if we can make" + EachRecipe.mName);
			boolean canMakeRecipe = true;

			boolean StopHere = false;
			if (EachRecipe.mName == "Hawaii Rice Dish") {
				boolean stopHere = true;
				StopHere = true;
			}

			// looking through each ingredients in the each recipe
			for (int j = 0; j < EachRecipe.IngredientList.size(); j++) {

				String EachIngredient = EachRecipe.IngredientList.get(j);

				System.out.println("do I have?: " + EachIngredient);
				boolean foundItem = false;

				// looking through each fridge ingredients
				for (int k = 0; k < FridgeItemList.size(); k++) {
					String EachFridgeItem = FridgeItemList.get(k);
					System.out.println("Checking each fridge item");

					// looking if recipe ingredient is in fridge
					if (EachFridgeItem.equals(EachIngredient)) {
						System.out.println("matched : " + EachIngredient);
						System.out.println("I will put that in found ingredients list");
						foundItem = true;
					} else {
						System.out.println("this isnt what i am looking for" + EachFridgeItem);
					}

				}
				if (foundItem == false) {
					canMakeRecipe = false;
				}
				System.out.println();
			}
			// looking at end of each recipe
			if (canMakeRecipe) {
				PossibleRecipes.add(EachRecipe);
			}
		}

		return PossibleRecipes;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MealManagementInterface frame = new MealManagementInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MealManagementInterface() {
		initialize();
	}

	private void initialize() {
		// The parent of all the cards
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 890, 560);
		cardLayoutPane = new JPanel();
		cardLayoutPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cardLayoutPane);
		cardLayoutPane.setLayout(cardLayout);

		Color white = Color.getHSBColor(0.0f, 0.0f, 1.0f);
		Color black = Color.getHSBColor(0.0f, 0.0f, 0.0f);
		Color green = Color.decode("#A6FFCB");

//		// The register panel added to the parent
//		JPanel RegisterLoggingPanel = new JPanel();
//		cardLayoutPane.add(RegisterLoggingPanel, "name_panel");
//		RegisterLoggingPanel.setLayout(null);
//
//		JLabel NewUsernameLabel = new JLabel("Username");
//		NewUsernameLabel.setBounds(181, 150, 119, 30);
//		RegisterLoggingPanel.add(NewUsernameLabel);
//
//		JLabel NewPasswordLabel = new JLabel("Password");
//		NewPasswordLabel.setBounds(181, 201, 119, 30);
//		RegisterLoggingPanel.add(NewPasswordLabel);
//
//		JLabel ConfirmPasswordLabel = new JLabel("Confirm Password");
//		ConfirmPasswordLabel.setBounds(181, 253, 119, 30);
//		RegisterLoggingPanel.add(ConfirmPasswordLabel);

		// The logging panel added to the parent
		JPanel LoggingPanel = new JPanel();
		cardLayoutPane.add(LoggingPanel, "LoggingPanel");
		LoggingPanel.setLayout(null);

		JLabel UsernameLabel = new JLabel("Username");
		UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		UsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UsernameLabel.setBounds(224, 139, 100, 40);
		LoggingPanel.add(UsernameLabel);

		JButton card1Button = new JButton("Login");
		card1Button.setFont(new Font("Tahoma", Font.BOLD, 11));
		card1Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if (username and password != 0)
				// if username and password from querylogin == to the username and password from
				// the textfield than move to a different cardlayout
				// then invalid details
				if (UsernameTextField.getText().length() != 0 && PasswordTextField.getText().length() != 0) {
					try {
						if (SQLManager.setLoggingDetails(UsernameTextField.getText(), PasswordTextField.getText())) {
							String user = UsernameTextField.getText();
							String pass = PasswordTextField.getText();
							mUsername = user;
							if (SQLManager.queryLogin(user, pass)) {
								cardLayout.show(cardLayoutPane, "MainPanel");
								// passing the details from one function to another
								Map<String, String> userdetails = SQLManager.UserDetails(user);
								mUserID = userdetails.get("idcustomer");
								System.out.println("user details");

								// populate fridge data
								ResultSet rsFridgeQuery;
								try {
									rsFridgeQuery = SQLManager.FridgeQuery(mUserID);

									// make fridge ingredient list

									while (rsFridgeQuery.next()) {
										FridgeIngredientList.add(rsFridgeQuery.getString(1));
									}
								} catch (Exception e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
							} else {
								System.out.println("Invalid username and password. Try Again?");
							}
							// cardLayout.show(cardLayoutPane, "MainPanel");
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Valid details");
				} else {
					System.out.println("Invalid details");
				}

			}
		});
		card1Button.setToolTipText("take me to card 2");
		card1Button.setBounds(327, 289, 89, 31);
		LoggingPanel.add(card1Button);

		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PasswordLabel.setBounds(224, 201, 100, 40);
		LoggingPanel.add(PasswordLabel);

		UsernameTextField = new JTextField();
		UsernameTextField.setBounds(327, 139, 138, 40);
		LoggingPanel.add(UsernameTextField);
		UsernameTextField.setColumns(10);

		PasswordTextField = new JTextField();
		PasswordTextField.setColumns(10);
		PasswordTextField.setBounds(327, 201, 138, 40);
		LoggingPanel.add(PasswordTextField);

		// The main panel added to the parent
		JPanel MainPanel = new JPanel();
		cardLayoutPane.add(MainPanel, "MainPanel");
		MainPanel.setLayout(null);

		JPanel MenuPanel = new JPanel();
		MenuPanel.setBounds(0, 0, 78, 511);
		MainPanel.add(MenuPanel);
		MenuPanel.setLayout(null);

		JButton FridgeMenuButton = new JButton("Fridge");
		// FridgeMenuButton.setIcon(new
		// ImageIcon(ShoppingManagementInterface.class.getResource("/package
		// (1).png")));
		FridgeMenuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "FridgeViewPanel");
				ResultSet rsFridgeQuery;
				try {
					rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
					SQLManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery);

					// make fridge ingredient list

					while (rsFridgeQuery.next()) {
						FridgeIngredientList.add(rsFridgeQuery.getString(1));
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		FridgeMenuButton.setBounds(0, 81, 78, 49);
		MenuPanel.add(FridgeMenuButton);

		JButton MealPlanButton = new JButton("Meal");
		// MealPlanButton.setIcon(new
		// ImageIcon(ShoppingManagementInterface.class.getResource("/calendar.png")));
		MealPlanButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "MealPlanViewPanel");

				//
				try {
					String recipeName = null;
					String recipeMealTime = null;
					ResultSet rsRecipeQuery = SQLManager.RecipeQuery();
					while (rsRecipeQuery.next()) {
						recipeName = rsRecipeQuery.getString(2);
						recipeMealTime = rsRecipeQuery.getString(3);
						Recipe recipe = new Recipe(recipeName, recipeMealTime);
						recipe.IngredientList = SQLManager.getIngredientsOfRecipe(rsRecipeQuery.getString(1));
						mRecipeList.add(recipe);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// algorithm in which things can be made
				mPossibleRecipes = detectRecipe(FridgeIngredientList, mRecipeList);

				ArrayList<String> BreakfastList = new ArrayList<String>();
				ArrayList<String> LunchList = new ArrayList<String>();
				ArrayList<String> DinnerList = new ArrayList<String>();
				// finds all breakfast and makes them into new list
				for (int i = 0; i < mPossibleRecipes.size(); i++) {
					String RecipeName = mPossibleRecipes.get(i).mName;
					String mealTime = mPossibleRecipes.get(i).mMealTime;
					if ("breakfast".equals(mealTime)) {
						BreakfastList.add(RecipeName);
					}
					if ("lunch".equals(mealTime)) {
						LunchList.add(RecipeName);
					}
					if ("dinner".equals(mealTime)) {
						DinnerList.add(RecipeName);
					}
				}
				MealPlanTableModel.setRowCount(0);

				for (int j = 0; j < 3; j++) {

					MealPlanTableModel.addRow(new Object[] {});
				}

				// for (int j = 0; j < 8; j++) {
				// for (int i = 0; i < 8; i++) {
				// MealPlanTable.setValueAt("Breakfast", i, j);
				// MealPlanTable.setValueAt("Lunch", i, j);
				// MealPlanTable.setValueAt("Dinner", i, j);
				// }
				// }
				MealPlanTable.setValueAt("breakfast", 0, 0);
				MealPlanTable.setValueAt("lunch", 1, 0);
				MealPlanTable.setValueAt("dinner", 2, 0);
				int maxColumn = 8;
				int maxRow = 3;

				for (int i = 1; i < maxColumn; i++) {
					for (int j = 0; j < maxRow; j++) {

						int breakfastIndex = (int) (Math.random() * BreakfastList.size());
						int lunchIndex = (int) (Math.random() * LunchList.size());
						int dinnerIndex = (int) (Math.random() * DinnerList.size());
						if (j == 0) {
							MealPlanTable.setValueAt(BreakfastList.get(breakfastIndex), j, i);
						}

						if (j == 1) {
							MealPlanTable.setValueAt(LunchList.get(lunchIndex), j, i);
						}
						if (j == 2) {
							MealPlanTable.setValueAt(DinnerList.get(dinnerIndex), j, i);
						}
					}
				}

			}
		});
		MealPlanButton.setBounds(0, 129, 78, 49);
		MenuPanel.add(MealPlanButton);

		JButton RecipeButton = new JButton("Recipe");
		// RecipeButton.setIcon(new
		// ImageIcon(ShoppingManagementInterface.class.getResource("/book-open.png")));
		RecipeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "RecipeViewPanel");
				ResultSet rsRecipeQuery;
				try {
					rsRecipeQuery = SQLManager.RecipeQuery();
					SQLManager.populateTableWithResultSetWithCheckBox(RecipeTable, rsRecipeQuery, mUserID);

					SQLManager.populateSuggestedRecipes(mUserID, SuggestedRecipesLabel);

					// make fridge ingredient list

					while (rsRecipeQuery.next()) {
						RecipeQueryList.add(rsRecipeQuery.getString(2));
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		RecipeButton.setBounds(0, 177, 78, 49);
		MenuPanel.add(RecipeButton);

		JButton FavouritesButton = new JButton("Favourites");
		// FavouritesButton.setIcon(new
		// ImageIcon(ShoppingManagementInterface.class.getResource("/star.png")));
		FavouritesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		FavouritesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// query of the favourite recipes
			}
		});
		FavouritesButton.setBounds(0, 226, 78, 49);
		MenuPanel.add(FavouritesButton);

		JButton RecipeFinderButton = new JButton("Finder");
		RecipeFinderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "RecipeFinderPanel");
			}
		});
		RecipeFinderButton.setBounds(0, 275, 78, 49);
		MenuPanel.add(RecipeFinderButton);

		JButton RecipeDetailsButton = new JButton("Details");
		RecipeDetailsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "RecipeDetailsPanel");

				// Fill in details
				try {
					// Get recipe details
					HashMap<String, String> recipeDetails = SQLManager.getRecipeDetails(1);
					String recipeDescription = recipeDetails.get("RecipeDescription");
					String recipeName = recipeDetails.get("RecipeName");
					String recipeDifficulty = recipeDetails.get("RecipeDifficulty");
					String recipeTime = recipeDetails.get("recipeTime");
					String mealTime = recipeDetails.get("mealTime");
					String recipeServings = recipeDetails.get("RecipeServings");
					String recipeCalories = recipeDetails.get("RecipeCalories");
					String recipeDietCategory = recipeDetails.get("dietCategory");
					ArrayList<String> recipeIngredients = SQLManager.getIngredientsOfRecipe("1");
					String recipeIngredientsString = "";
					String recipeInstructions = recipeDetails.get("RecipeInstructions");

					// Create string from ingredients
					for (int i = 0; i < recipeIngredients.size(); i++) {
						recipeIngredientsString += recipeIngredients.get(i) + "\n";
					}

					// Set recipe details in UI
					RecipeDescriptionTextArea.setText(recipeDescription);
					RecipeDetailsTitle.setText(recipeName);
					RecipeProcessDetailsTextArea.setText("Prep time: " + recipeTime + "\n" + "Difficulty: "
							+ recipeDifficulty + "\n" + "Servings: " + recipeServings);
					NutritionTextArea.setText("Diet: " + recipeDietCategory + "\n" + "Calories: " + recipeCalories);
					RecipeIngredientsTextArea.setText(recipeIngredientsString);
					RecipeInstructionsTextArea.setText(recipeInstructions);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		RecipeDetailsButton.setBounds(0, 324, 78, 49);
		MenuPanel.add(RecipeDetailsButton);

		ViewPanel = new JPanel();
		ViewPanel.setBounds(79, 0, 785, 511);
		MainPanel.add(ViewPanel);
		ViewPanel.setLayout(viewCardLayout);

		JPanel FridgePanel = new JPanel();
		ViewPanel.add(FridgePanel, "FridgeViewPanel");
		FridgePanel.setLayout(null);

		JButton btnNewButton = new JButton("  ");
		btnNewButton.setBounds(483, 37, 262, 37);
		btnNewButton.setForeground(Color.GREEN);
		FridgePanel.add(btnNewButton);

		JScrollPane FridgeScrollPane = new JScrollPane();
		FridgeScrollPane.setBounds(34, 85, 711, 355);
		// FridgeScrollPane.getViewport().setBackground(white);
		FridgePanel.add(FridgeScrollPane);

		FridgeTable = new JTable();
		FridgeTable.setBounds(20, 81, 744, 404);
		FridgeTable.setBackground(white);
		FridgeTable.setForeground(black); // the text colour will change
		FridgeTable.setRowHeight(32);
		FridgeTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
//		// removes grid lines from the done table
		FridgeTable.setShowGrid(false);
		FridgeTable.setShowVerticalLines(false);
		FridgeTable.setShowHorizontalLines(true);
		FridgeScrollPane.setViewportView(FridgeTable);

		JButton AddFridgeIngredientsButton = new JButton("Add Ingredients");
		AddFridgeIngredientsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		AddFridgeIngredientsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ItemNameTextField.getText().length() > 0 && QuantityTextField.getText().length() > 0
						&& CaloriesTextField.getText().length() > 0) {
					try {
						SQLManager.AddFridgeQuery(mUserID, ItemNameTextField.getText(), QuantityTextField.getText(),
								CaloriesTextField.getText());
						ResultSet rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
						SQLManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery);
						ItemNameTextField.setText("");
						QuantityTextField.setText("");
						CaloriesTextField.setText("");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		AddFridgeIngredientsButton.setBounds(615, 449, 130, 51);
		FridgePanel.add(AddFridgeIngredientsButton);

		ItemNameTextField = new JTextField();
		ItemNameTextField.setBounds(33, 474, 122, 37);
		FridgePanel.add(ItemNameTextField);
		ItemNameTextField.setColumns(10);

		QuantityTextField = new JTextField();
		QuantityTextField.setColumns(10);
		QuantityTextField.setBounds(230, 474, 122, 37);
		FridgePanel.add(QuantityTextField);

		CaloriesTextField = new JTextField();
		CaloriesTextField.setColumns(10);
		CaloriesTextField.setBounds(413, 474, 122, 37);
		FridgePanel.add(CaloriesTextField);

		JLabel ItemNameLabel = new JLabel("Ingredient Name");
		ItemNameLabel.setBounds(34, 449, 122, 24);
		FridgePanel.add(ItemNameLabel);

		JLabel QuantityLabel = new JLabel("Quantity");
		QuantityLabel.setBounds(230, 449, 122, 24);
		FridgePanel.add(QuantityLabel);

		JLabel CaloriesLabel = new JLabel("Calories");
		CaloriesLabel.setBounds(413, 449, 122, 24);
		FridgePanel.add(CaloriesLabel);

		JPanel MealPlanPanel = new JPanel();
		ViewPanel.add(MealPlanPanel, "MealPlanViewPanel");
		MealPlanPanel.setLayout(null);

		JScrollPane MealPlanScrollPane = new JScrollPane();
		MealPlanScrollPane.setBounds(34, 85, 711, 391);
		MealPlanScrollPane.getViewport().setBackground(white);
		MealPlanPanel.add(MealPlanScrollPane);

		MealPlanColumns = new String[] { "Meal", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
				"Sunday" };
		Object[][] MealPlanData = {};
		MealPlanTableModel = new DefaultTableModel(MealPlanData, MealPlanColumns);
		MealPlanTable = new JTable(MealPlanTableModel);
		MealPlanTable.setBounds(32, 99, 709, 378);
//		MealPlanTable.setBackground(white);
//		MealPlanTable.setForeground(black); // the text colour will change
//		MealPlanTable.setRowHeight(32);
//		MealPlanTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
//		// removes grid lines from the done table
//		MealPlanTable.setShowGrid(false);
//		MealPlanTable.setShowVerticalLines(false);
//		MealPlanTable.setShowHorizontalLines(true);
		MealPlanScrollPane.setViewportView(MealPlanTable);

//		JTableHeader MealPlanHeader = MealPlanTable.getTableHeader();
//		MealPlanHeader.setBackground(green);
//		MealPlanHeader.setForeground(black);
//		MealPlanHeader.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		JPanel RecipePanel = new JPanel();
		ViewPanel.add(RecipePanel, "RecipeViewPanel");
		RecipePanel.setLayout(null);

		JScrollPane RecipeScrollPane = new JScrollPane();
		RecipeScrollPane.setBounds(34, 85, 711, 156);
		// FridgeScrollPane.getViewport().setBackground(white);
		RecipePanel.add(RecipeScrollPane);

		RecipeColumns = new Object[] { false, "Recipe Name" };
		Object[][] RecipeData = {};
		RecipeTableModel = new DefaultTableModel(RecipeData, RecipeColumns);

		RecipeTable = new JTable(RecipeTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		RecipeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// If a user in the recipe table is double clicked then that recipes details are
				// displayed opening the recipe details panel

				// need to see why -4 works later on
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1
						&& RecipeTable.getSelectedRows().length == 1 && RecipeTable.getSelectedColumn() - 4 != 0) {

					viewCardLayout.show(ViewPanel, "RecipeDetailsPanel");

					// Fill in details
					try {
						// Get recipe details
//						RecipeTable.getSelectedRow() + 1
						int recipeID = (Integer) RecipeTable.getModel().getValueAt(RecipeTable.getSelectedRow(), 0);
						HashMap<String, String> recipeDetails = SQLManager.getRecipeDetails(recipeID); // need to change
																										// this

						String recipeDescription = recipeDetails.get("RecipeDescription");
						String recipeName = recipeDetails.get("RecipeName");
						String recipeDifficulty = recipeDetails.get("RecipeDifficulty");
						String recipeTime = recipeDetails.get("recipeTime");
						String mealTime = recipeDetails.get("mealTime");
						String recipeServings = recipeDetails.get("RecipeServings");
						String recipeCalories = recipeDetails.get("RecipeCalories");
						String recipeDietCategory = recipeDetails.get("dietCategory");
						ArrayList<String> recipeIngredients = SQLManager
								.getIngredientsOfRecipe(Integer.toString(recipeID)); // need to
																						// change
						// this
						String recipeIngredientsString = "";
						String recipeInstructions = recipeDetails.get("RecipeInstructions");

						// Create string from ingredients
						for (int i = 0; i < recipeIngredients.size(); i++) {
							recipeIngredientsString += recipeIngredients.get(i) + "\n";
						}

						// Set recipe details in UI
						RecipeDescriptionTextArea.setText(recipeDescription);
						RecipeDetailsTitle.setText(recipeName);
						RecipeProcessDetailsTextArea.setText("Prep time: " + recipeTime + "\n" + "Difficulty: "
								+ recipeDifficulty + "\n" + "Servings: " + recipeServings);
						NutritionTextArea.setText("Diet: " + recipeDietCategory + "\n" + "Calories: " + recipeCalories);
						RecipeIngredientsTextArea.setText(recipeIngredientsString);
						RecipeInstructionsTextArea.setText(recipeInstructions);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		RecipeTable.setBounds(32, 99, 709, 378);
		// RecipeTable.getColumnModel().getColumn(0).setHeaderRenderer(new
		// CheckboxCellRenderer());
		RecipeScrollPane.setViewportView(RecipeTable);

		JLabel RecipeNameLabel = new JLabel("Recipe Name");
		RecipeNameLabel.setBounds(34, 419, 115, 29);
		RecipePanel.add(RecipeNameLabel);

		RecipeNameTextField = new JTextField();
		RecipeNameTextField.setColumns(10);
		RecipeNameTextField.setBounds(34, 459, 115, 25);
		RecipePanel.add(RecipeNameTextField);

		JButton AddRecipeButton = new JButton("Add Recipe");
		AddRecipeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (RecipeNameTextField.getText().length() > 0 && IngredientTable.getRowCount() > 0) {

					try {
						// add recipe
						SQLManager.AddRecipeQuery(RecipeNameTextField.getText(), MealTimeTextField.getText(), "", "",
								"", "", "", "", "");
						// get the recipeid of the new recipe

						// add ingredients with the recipeid of the new recipe
						for (int i = 0; i < IngredientTableList.size(); i++) {
							SQLManager.AddIngredientsFromList(IngredientTableList.get(i), SQLManager.RecipeID());
						}
						// update the recipe table
						ResultSet rsRecipeQuery = SQLManager.RecipeQuery();
						SQLManager.populateTableWithResultSetWithCheckBox(RecipeTable, rsRecipeQuery, mUserID);

						// add a new column dynamically
						// RecipeTableModel.addColumn("Favorite", new Object[] { false, false, true });
						RecipeTable.setModel(RecipeTableModel);
						RecipeNameTextField.setText("");
						// delete all row in the ingredient jtable and arraylist
						IngredientTableList.clear();
						deleteAllRows(IngredientTableModel);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		AddRecipeButton.setBounds(543, 458, 172, 26);
		RecipePanel.add(AddRecipeButton);

		IngredientNameTextField = new JTextField();
		IngredientNameTextField.setColumns(10);
		IngredientNameTextField.setBounds(165, 459, 115, 25);
		RecipePanel.add(IngredientNameTextField);

		JLabel IngredientNameLabel = new JLabel("Ingredient Name");
		IngredientNameLabel.setBounds(165, 419, 115, 29);
		RecipePanel.add(IngredientNameLabel);

		IngredientScrollPane = new JScrollPane();
		IngredientScrollPane.setBounds(34, 252, 711, 156);
		RecipePanel.add(IngredientScrollPane);

		IngredientColumns = new String[] { "Ingredient Name" };
		Object[][] IngredientData = {};
		IngredientTableModel = new DefaultTableModel(IngredientData, IngredientColumns) {
			public boolean isCellEditable(int row, int col) {
				return true;
			}
		};
		IngredientTable = new JTable(IngredientTableModel);
		IngredientScrollPane.setColumnHeaderView(IngredientTable);
		IngredientScrollPane.setViewportView(IngredientTable);

		JButton AddIngredientsButton = new JButton("Add Ingredients");
		AddIngredientsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (IngredientNameTextField.getText().length() > 0) {

					// add it to the jtable
					IngredientTableModel.addRow(new Object[] { IngredientNameTextField.getText() });
					IngredientTableList.add(IngredientNameTextField.getText());
					IngredientNameTextField.setText("");
				}
			}
		});
		AddIngredientsButton.setBounds(289, 460, 121, 23);
		RecipePanel.add(AddIngredientsButton);

		SuggestedRecipesLabel = new JLabel("Suggested Recipes");
		SuggestedRecipesLabel.setBounds(34, 26, 711, 29);
		RecipePanel.add(SuggestedRecipesLabel);

		MealTimeTextField = new JTextField();
		MealTimeTextField.setBounds(420, 461, 113, 23);
		RecipePanel.add(MealTimeTextField);
		MealTimeTextField.setColumns(10);

		JLabel MealTimeLabel = new JLabel("Meal Time");
		MealTimeLabel.setBounds(418, 426, 115, 29);
		RecipePanel.add(MealTimeLabel);

		JPanel FavouritePanel = new JPanel();
		ViewPanel.add(FavouritePanel, "FavouriteViewPanel");
		FavouritePanel.setLayout(null);

		FavouriteRecipesScrollPane = new JScrollPane();
		FavouriteRecipesScrollPane.setBounds(40, 43, 701, 360);
		FavouritePanel.add(FavouriteRecipesScrollPane);

		FavouriteRecipesColumns = new String[] { "Recipe Name" };
		Object[][] FavouriteRecipesData = {};
		FavouriteRecipesTableModel = new DefaultTableModel(FavouriteRecipesData, FavouriteRecipesColumns);

		FavouriteRecipesTable = new JTable(FavouriteRecipesTableModel);
		FavouriteRecipesScrollPane.setColumnHeaderView(FavouriteRecipesTable);
		FavouriteRecipesScrollPane.setViewportView(FavouriteRecipesTable);

		RecipeFinderPanel = new JPanel();
		ViewPanel.add(RecipeFinderPanel, "RecipeFinderPanel");
		RecipeFinderPanel.setLayout(null);

		SearchRecipeTextField = new JTextField();
		SearchRecipeTextField.setBounds(43, 42, 635, 40);
		RecipeFinderPanel.add(SearchRecipeTextField);
		SearchRecipeTextField.setColumns(10);

		JButton SearchButton = new JButton("Search");
		SearchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if textfield > 0 then connect the api and upload the text in the textlabel

				if (SearchRecipeTextField.getText() != null) {
					try {
						APIManager.getAPI(SearchRecipeTextField.getText(), SearchedRecipesLabel);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		SearchButton.setBounds(688, 42, 89, 40);
		RecipeFinderPanel.add(SearchButton);

		SearchedRecipesLabel = new JLabel("Recipes Found: ");
		SearchedRecipesLabel.setVerticalAlignment(SwingConstants.TOP);
		SearchedRecipesLabel.setBounds(43, 125, 635, 349);
		RecipeFinderPanel.add(SearchedRecipesLabel);

		JPanel RecipeDetailsPanel = new JPanel();
		ViewPanel.add(RecipeDetailsPanel, "RecipeDetailsPanel");
		RecipeDetailsPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 765, 489);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		RecipeDetailsPanel.add(scrollPane);

		JPanel RecipeDetailsContentPanel = new JPanel();
		RecipeDetailsContentPanel.setBounds(0, 0, 750, 800);
		RecipeDetailsContentPanel.setPreferredSize(new Dimension(750, 1100));
		scrollPane.setViewportView(RecipeDetailsContentPanel);
		RecipeDetailsContentPanel.setLayout(null);

		JLabel LabelRecipeName = new JLabel("Recipe Details");
		LabelRecipeName.setHorizontalAlignment(SwingConstants.CENTER);
		LabelRecipeName.setFont(new Font("Tahoma", Font.PLAIN, 23));
		LabelRecipeName.setBounds(333, 121, 381, 45);
		RecipeDetailsContentPanel.add(LabelRecipeName);

		RecipeDetailsTitle = new JLabel("Salmon Rolls");
		RecipeDetailsTitle.setFont(new Font("Tahoma", Font.PLAIN, 37));
		RecipeDetailsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeDetailsTitle.setBounds(333, 43, 371, 87);
		RecipeDetailsContentPanel.add(RecipeDetailsTitle);

		JLabel DetailsLabel = new JLabel("");
		DetailsLabel.setIcon(new ImageIcon("C:\\Users\\Aaron\\Downloads\\meal.png"));
		DetailsLabel.setBounds(43, 11, 225, 188);
		RecipeDetailsContentPanel.add(DetailsLabel);

		JSplitPane RecipeDescriptionSplitPane = new JSplitPane();
		RecipeDescriptionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		RecipeDescriptionSplitPane.setEnabled(false);
		RecipeDescriptionSplitPane.setBounds(35, 210, 705, 110);
		RecipeDetailsContentPanel.add(RecipeDescriptionSplitPane);

		JLabel RecipeDescriptionLabel = new JLabel("Recipe Description");
		RecipeDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RecipeDescriptionSplitPane.setLeftComponent(RecipeDescriptionLabel);

		RecipeDescriptionTextArea = new JTextArea();
		RecipeDescriptionTextArea.setText(
				"A light meal, which is quick, healthy, and easy to make! Try this if you enjoy seafood, or want to try something new. Ready in under an hour, and requires no cooking.");
		RecipeDescriptionTextArea.setLineWrap(true);
		RecipeDescriptionSplitPane.setRightComponent(RecipeDescriptionTextArea);

		JSplitPane ProcessDetailsSplitPane = new JSplitPane();
		ProcessDetailsSplitPane.setEnabled(false);
		ProcessDetailsSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		ProcessDetailsSplitPane.setBounds(35, 331, 273, 87);
		RecipeDetailsContentPanel.add(ProcessDetailsSplitPane);

		JLabel RecipeProcessDetailsLabel = new JLabel("Process Details");
		RecipeProcessDetailsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RecipeProcessDetailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ProcessDetailsSplitPane.setLeftComponent(RecipeProcessDetailsLabel);

		RecipeProcessDetailsTextArea = new JTextArea();
		ProcessDetailsSplitPane.setRightComponent(RecipeProcessDetailsTextArea);
		RecipeProcessDetailsTextArea.setLineWrap(true);
		RecipeProcessDetailsTextArea.setText("Time: 30 mins\r\nDifficulty: Easy\r\nServings: 2");

		JSplitPane NutritionSplitPane = new JSplitPane();
		NutritionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		NutritionSplitPane.setEnabled(false);
		NutritionSplitPane.setBounds(396, 331, 344, 87);
		RecipeDetailsContentPanel.add(NutritionSplitPane);

		JLabel NutritionLabel = new JLabel("Nutrition Details");
		NutritionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NutritionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		NutritionSplitPane.setLeftComponent(NutritionLabel);

		NutritionTextArea = new JTextArea();
		NutritionTextArea.setText("Energy: 500kcal\r\nVegetarian: Yes");
		NutritionTextArea.setLineWrap(true);
		NutritionSplitPane.setRightComponent(NutritionTextArea);

		JSplitPane IngredientsSplitPane = new JSplitPane();
		IngredientsSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		IngredientsSplitPane.setEnabled(false);
		IngredientsSplitPane.setBounds(35, 429, 705, 157);
		RecipeDetailsContentPanel.add(IngredientsSplitPane);

		JLabel RecipeIngredientsLabel = new JLabel("Ingredients");
		RecipeIngredientsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeIngredientsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IngredientsSplitPane.setLeftComponent(RecipeIngredientsLabel);

		RecipeIngredientsTextArea = new JTextArea();
		RecipeIngredientsTextArea.setText(
				"Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here ");
		RecipeIngredientsTextArea.setLineWrap(true);
		IngredientsSplitPane.setRightComponent(RecipeIngredientsTextArea);

		JSplitPane InstructionsSplitPane = new JSplitPane();
		InstructionsSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		InstructionsSplitPane.setEnabled(false);
		InstructionsSplitPane.setBounds(35, 597, 705, 210);
		RecipeDetailsContentPanel.add(InstructionsSplitPane);

		JLabel RecipeInstructionsLabel = new JLabel("Instructions");
		RecipeInstructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeInstructionsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InstructionsSplitPane.setLeftComponent(RecipeInstructionsLabel);

		RecipeInstructionsTextArea = new JTextArea();
		RecipeInstructionsTextArea.setText(
				"Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here ");
		RecipeInstructionsTextArea.setLineWrap(true);
		InstructionsSplitPane.setRightComponent(RecipeInstructionsTextArea);

		JSplitPane RecommendationsSplitPane = new JSplitPane();
		RecommendationsSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		RecommendationsSplitPane.setEnabled(false);
		RecommendationsSplitPane.setBounds(35, 845, 705, 147);
		RecipeDetailsContentPanel.add(RecommendationsSplitPane);

		JLabel RecipeRecommendationsLabel = new JLabel("Recommendations");
		RecipeRecommendationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeRecommendationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RecommendationsSplitPane.setLeftComponent(RecipeRecommendationsLabel);

		RecipeRecommendationsTable = new JTable();
		RecipeRecommendationsTable
				.setModel(new DefaultTableModel(new Object[][] { { "Pancakes", "Porridge", "Rasmalai", null }, },
						new String[] { "New column", "New column", "New column", "New column" }));
		RecommendationsSplitPane.setRightComponent(RecipeRecommendationsTable);

		// login function
		// get connection to the database
		// checks the username textfield and password textfield against the database
		// if yes - show mainpanel; no - give a label saying "Details not correct";
	}

	/**
	 * Renders a custom table column header that contains a checkbox.
	 */
	class CheckboxCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -5199637653685240121L;
		Color green = Color.decode("#A6FFCB");

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			TableCellRenderer boolRenderer = table.getDefaultRenderer(Boolean.class);
			JComponent boolComp = null;
			if (value.toString().equals("false")) {
				boolComp = (JComponent) boolRenderer.getTableCellRendererComponent(table, false, isSelected, hasFocus,
						row, column);
			} else {
				boolComp = (JComponent) boolRenderer.getTableCellRendererComponent(table, true, isSelected, hasFocus,
						row, column);
			}
			boolComp.setBackground(green);
			boolComp.setBorder(BorderFactory.createEmptyBorder());
			return boolComp;
		}
	}
}
