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
import java.awt.font.TextAttribute;
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
import javax.swing.JPasswordField;
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
import javax.swing.table.JTableHeader;
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
	private DefaultTableModel RecRecipesTableModel;
	private String[] IngredientColumns;
	private String[] FavouriteRecipesColumns;
	private String[] RecRecipesColumns;
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
	private JPanel RecipeDetailsPanel;
	private JTextField BestBeforeTextField;
	private JLabel TotalItemsLabel;
	private JTable RecRecipesTable;
	private JTextField ChangePasswordTextField;
	private JPanel LoggingPanel;
	private JPanel FridgePanel;
	private ArrayList<String> FridgeTableColumnName;
	private ArrayList<String> FavouriteRecipesColumnName;

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
			// System.out.println("Checking if we can make" + EachRecipe.mName);
			boolean canMakeRecipe = true;

			// looking through each ingredients in the each recipe
			for (int j = 0; j < EachRecipe.IngredientList.size(); j++) {

				String EachIngredient = EachRecipe.IngredientList.get(j);

				// System.out.println("do I have?: " + EachIngredient);
				boolean foundItem = false;

				// looking through each fridge ingredients
				for (int k = 0; k < FridgeItemList.size(); k++) {
					String EachFridgeItem = FridgeItemList.get(k);
					// System.out.println("Checking each fridge item");

					// looking if recipe ingredient is in fridge
					if (EachFridgeItem.equals(EachIngredient)) {
						// System.out.println("matched : " + EachIngredient);
						// System.out.println("I will put that in found ingredients list");
						foundItem = true;
					} else {
						// System.out.println("this isnt what i am looking for" + EachFridgeItem);
					}

				}
				if (foundItem == false) {
					canMakeRecipe = false;
				}
				// System.out.println();
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
	 * 
	 * @throws Exception
	 */
	public MealManagementInterface() throws Exception {
		initialize();
	}

	private void initialize() throws Exception {

		Color white = Color.getHSBColor(0.0f, 0.0f, 1.0f);
		Color black = Color.getHSBColor(0.0f, 0.0f, 0.0f);
		Color green = Color.decode("#A6FFCB");
		Color lightPink = Color.decode("#EAAFC8");
		Color lightPurple = Color.decode("#654EA3");
		Color menuPurple = Color.decode("#6547B5");
		Color lightMenuPurple = Color.decode("#745DB4");

		// The parent of all the cards
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		cardLayoutPane = new JGradientPanel(lightPurple, lightPink, 2);
		cardLayoutPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cardLayoutPane);
		cardLayoutPane.setLayout(cardLayout);

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
		LoggingPanel = new JGradientPanel(lightPurple, lightPink, 2);
		cardLayoutPane.add(LoggingPanel, "LoggingPanel");
		LoggingPanel.setLayout(null);

		JLabel UsernameLabel = new JLabel("Username");
		UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		UsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UsernameLabel.setBounds(518, 212, 100, 40);
		LoggingPanel.add(UsernameLabel);

		JButton LoginButton = new JButton("Login");
		LoginButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		LoginButton.addMouseListener(new MouseAdapter() {
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
		LoginButton.setToolTipText("take me to main panel");
		LoginButton.setBounds(682, 413, 89, 31);
		LoggingPanel.add(LoginButton);

		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PasswordLabel.setBounds(518, 286, 100, 40);
		LoggingPanel.add(PasswordLabel);

		UsernameTextField = new JTextField();
		UsernameTextField.setBounds(682, 215, 138, 40);
		LoggingPanel.add(UsernameTextField);
		UsernameTextField.setColumns(10);

		PasswordTextField = new JPasswordField();
		PasswordTextField.setColumns(10);
		PasswordTextField.setBounds(682, 289, 138, 40);
		LoggingPanel.add(PasswordTextField);

		// The main panel added to the parent
		JPanel MainPanel = new JGradientPanel(lightPurple, lightPink, 2);
		cardLayoutPane.add(MainPanel, "MainPanel");
		MainPanel.setLayout(null);

		JPanel MenuPanel = new JGradientPanel(menuPurple, menuPurple);
		MenuPanel.setBounds(0, 0, 78, 729);
		MainPanel.add(MenuPanel);
		MenuPanel.setLayout(null);

		JButton FridgeMenuButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		FridgeMenuButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/fridgeIcon.png")));
		FridgeMenuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "FridgeViewPanel");
				ResultSet rsFridgeQuery;
				try {
					rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
					SQLManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery, FridgeTableColumnName);

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

		FridgeMenuButton.setBounds(0, 105, 78, 49);
		MenuPanel.add(FridgeMenuButton);

		JButton MealPlanButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		MealPlanButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/mealIcon.png")));
		MealPlanButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "MealPlanViewPanel");

				ArrayList<String> BreakfastList = new ArrayList<String>();
				ArrayList<String> LunchList = new ArrayList<String>();
				ArrayList<String> DinnerList = new ArrayList<String>();
				try {
					ArrayList<Recipe> RecipeMealPlan = MealAlgorithmManager.CalculateMealPlan(mUserID, BreakfastList,
							LunchList, DinnerList);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//
//				try {

				///// test

				///////
//					String recipeName = null;
//					String recipeMealTime = null;
//					ResultSet rsRecipeQuery = SQLManager.RecipeQuery();
//					while (rsRecipeQuery.next()) {
//						recipeName = rsRecipeQuery.getString(2);
//						recipeMealTime = rsRecipeQuery.getString(3);
//						Recipe recipe = new Recipe(recipeName, recipeMealTime);
//						recipe.IngredientList = SQLManager.getIngredientsOfRecipe(rsRecipeQuery.getString(1));
//						mRecipeList.add(recipe);
//					}
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				// algorithm in which things can be made
//				mPossibleRecipes = detectRecipe(FridgeIngredientList, mRecipeList);
//
//				ArrayList<String> BreakfastList = new ArrayList<String>();
//				ArrayList<String> LunchList = new ArrayList<String>();
//				ArrayList<String> DinnerList = new ArrayList<String>();
//				// finds all breakfast and makes them into new list
//				for (int i = 0; i < mPossibleRecipes.size(); i++) {
//					String RecipeName = mPossibleRecipes.get(i).mName;
//					String mealTime = mPossibleRecipes.get(i).mMealTime;
//					if ("breakfast".equals(mealTime)) {
//						BreakfastList.add(RecipeName);
//					}
//					if ("lunch".equals(mealTime)) {
//						LunchList.add(RecipeName);
//					}
//					if ("dinner".equals(mealTime)) {
//						DinnerList.add(RecipeName);
//					}
//				}

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
		MealPlanButton.setBounds(0, 155, 78, 49);
		MenuPanel.add(MealPlanButton);

		JButton RecipeButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		RecipeButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/recipeIcon.png")));
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
		RecipeButton.setBounds(0, 205, 78, 49);
		MenuPanel.add(RecipeButton);

		JButton FavouritesButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		FavouritesButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/favouriteIcon.png")));
		FavouritesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResultSet rsFavouriteRecipeQuery;
				// query of the favourite recipes
				viewCardLayout.show(ViewPanel, "FavouriteViewPanel");
				try {
					rsFavouriteRecipeQuery = SQLManager.getFavouriteRecipesResultSet(mUserID);
					SQLManager.populateTableWithResultSet(FavouriteRecipesTable, rsFavouriteRecipeQuery,
							FavouriteRecipesColumnName);

					SQLManager.populateSuggestedRecipesInTable(mUserID, RecRecipesTable, RecRecipesColumns);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		FavouritesButton.setBounds(0, 254, 78, 49);
		MenuPanel.add(FavouritesButton);

		JButton RecipeFinderButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		RecipeFinderButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/finderIcon.png")));
		RecipeFinderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "RecipeFinderPanel");
			}
		});
		RecipeFinderButton.setBounds(0, 302, 78, 49);
		MenuPanel.add(RecipeFinderButton);

		JButton RecipeDetailsButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		RecipeDetailsButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/detailsIcon.png")));
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
		RecipeDetailsButton.setBounds(0, 350, 78, 49);
		MenuPanel.add(RecipeDetailsButton);

		JButton SettingsButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		SettingsButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/settingsIcon.png")));
		SettingsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "SettingsViewPanel");
			}
		});
		SettingsButton.setBounds(0, 398, 78, 49);
		MenuPanel.add(SettingsButton);

		ViewPanel = new JPanel();
		ViewPanel.setBounds(79, 0, 1261, 729);
		MainPanel.add(ViewPanel);
		ViewPanel.setLayout(viewCardLayout);

		FridgePanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(FridgePanel, "FridgeViewPanel");
		FridgePanel.setLayout(null);

		JScrollPane FridgeScrollPane = new JScrollPane();
		FridgeScrollPane.setBounds(34, 94, 1200, 561);
		// FridgeScrollPane.getViewport().setBackground(white);
		FridgePanel.add(FridgeScrollPane);

		FridgeTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		FridgeTable.setBounds(20, 81, 744, 404);
		FridgeTable.setBackground(white);
		FridgeTable.setForeground(black); // the text colour will change
		FridgeTable.setRowHeight(32);
		FridgeTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
//		// removes grid lines from the done table
		FridgeTable.setShowGrid(false);
		FridgeTable.setShowVerticalLines(false);
		FridgeTable.setShowHorizontalLines(true);
		FridgeTable.setAutoCreateRowSorter(true); // sorts columns asc-desc
		FridgeScrollPane.setViewportView(FridgeTable);
		FridgeTableColumnName = new ArrayList<String>();
		FridgeTableColumnName.add("");
		FridgeTableColumnName.add("Fridge Ingredients");
		FridgeTableColumnName.add("Best Before Date");
		FridgeTableColumnName.add("Quantity");
		FridgeTableColumnName.add("Calories");

		JButton AddFridgeIngredientsButton = new JButton();
		AddFridgeIngredientsButton.setBackground(Color.WHITE);
		AddFridgeIngredientsButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/plusSign.png")));
		AddFridgeIngredientsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		AddFridgeIngredientsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ItemNameTextField.getText().length() > 0 && BestBeforeTextField.getText().length() > 0
						&& QuantityTextField.getText().length() > 0 && CaloriesTextField.getText().length() > 0) {
					try {
						SQLManager.AddFridgeQuery(mUserID, ItemNameTextField.getText(), BestBeforeTextField.getText(),
								QuantityTextField.getText(), CaloriesTextField.getText());
						ResultSet rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
						SQLManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery, FridgeTableColumnName);
						TotalItemsLabel.setText("Total Items: " + SQLManager.TotalFridgeItemsQuery());
						ItemNameTextField.setText("");
						QuantityTextField.setText("");
						CaloriesTextField.setText("");
						BestBeforeTextField.setText("");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		AddFridgeIngredientsButton.setBounds(1126, 30, 49, 44);
		FridgePanel.add(AddFridgeIngredientsButton);

		ItemNameTextField = new JRoundedTextField(0);
		ItemNameTextField.setBounds(34, 30, 222, 53);
		FridgePanel.add(ItemNameTextField);
		ItemNameTextField.setColumns(10);

		QuantityTextField = new JRoundedTextField(0);
		QuantityTextField.setColumns(10);
		QuantityTextField.setBounds(591, 30, 222, 53);
		FridgePanel.add(QuantityTextField);

		CaloriesTextField = new JRoundedTextField(0);
		CaloriesTextField.setColumns(10);
		CaloriesTextField.setBounds(866, 30, 222, 53);
		FridgePanel.add(CaloriesTextField);

		JLabel ItemNameLabel = new JLabel("Ingredient Name");
		ItemNameLabel.setForeground(Color.WHITE);
		ItemNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ItemNameLabel.setBounds(34, -5, 202, 40);
		FridgePanel.add(ItemNameLabel);

		JLabel QuantityLabel = new JLabel("Quantity");
		QuantityLabel.setForeground(Color.WHITE);
		QuantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		QuantityLabel.setBounds(591, -5, 202, 40);
		FridgePanel.add(QuantityLabel);

		JLabel CaloriesLabel = new JLabel("Calories");
		CaloriesLabel.setForeground(Color.WHITE);
		CaloriesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CaloriesLabel.setBounds(866, -5, 202, 40);
		FridgePanel.add(CaloriesLabel);

		JLabel BestBeforeLabel = new JLabel("Best Before");
		BestBeforeLabel.setForeground(Color.WHITE);
		BestBeforeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BestBeforeLabel.setBounds(310, -5, 202, 40);
		FridgePanel.add(BestBeforeLabel);

		BestBeforeTextField = new JRoundedTextField(0);
		BestBeforeTextField.setColumns(10);
		BestBeforeTextField.setBounds(310, 30, 222, 53);
		FridgePanel.add(BestBeforeTextField);

		JButton DeleteFridgeIngredientsButton = new JButton();
		DeleteFridgeIngredientsButton.setBackground(Color.WHITE);
		DeleteFridgeIngredientsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = FridgeTable.getSelectedRow();
				String itemName = String.valueOf(FridgeTable.getModel().getValueAt(selectedRow, 0));
				try {
					SQLManager.DeleteFridgeQuery(mUserID, itemName);
					ResultSet rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
					SQLManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery, FridgeTableColumnName);
					TotalItemsLabel.setText("Total Items: " + SQLManager.TotalFridgeItemsQuery());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		DeleteFridgeIngredientsButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/delete.png")));
		DeleteFridgeIngredientsButton.setBounds(1185, 30, 49, 43);
		FridgePanel.add(DeleteFridgeIngredientsButton);

		TotalItemsLabel = new JLabel("Total Items: " + SQLManager.TotalFridgeItemsQuery());
		TotalItemsLabel.setForeground(Color.WHITE);
		TotalItemsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TotalItemsLabel.setBounds(34, 666, 236, 41);
		FridgePanel.add(TotalItemsLabel);

		JPanel MealPlanPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(MealPlanPanel, "MealPlanViewPanel");
		MealPlanPanel.setLayout(null);

		JScrollPane MealPlanScrollPane = new JScrollPane();
		MealPlanScrollPane.setBounds(34, 110, 1217, 391);
		MealPlanScrollPane.getViewport().setBackground(white);
		MealPlanPanel.add(MealPlanScrollPane);

		MealPlanColumns = new String[] { "Meal", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
				"Sunday" };
		Object[][] MealPlanData = {};
		MealPlanTableModel = new DefaultTableModel(MealPlanData, MealPlanColumns);
		MealPlanTable = new JTable(MealPlanTableModel);
		MealPlanTable.setBounds(32, 99, 709, 378);
		JTableHeader MealPlanHeader = MealPlanTable.getTableHeader();
		MealPlanHeader.setBackground(lightPink);
		MealPlanHeader.setForeground(black);
		MealPlanHeader.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		MealPlanTable.setBackground(white);
		MealPlanTable.setForeground(black); // the text colour will change
		MealPlanTable.setRowHeight(110);
		MealPlanTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		// removes grid lines from the done table
		MealPlanTable.setShowGrid(false);
		MealPlanTable.setShowVerticalLines(true);
		MealPlanTable.setShowHorizontalLines(true);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);

		MealPlanTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		MealPlanTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		MealPlanTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		MealPlanTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		MealPlanTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		MealPlanTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		MealPlanTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		MealPlanTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		MealPlanScrollPane.setViewportView(MealPlanTable);

		JLabel MealPlanLabel = new JLabel("MEAL PLAN FOR YOU");
		MealPlanLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MealPlanLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		MealPlanLabel.setBounds(358, 11, 456, 57);
		MealPlanPanel.add(MealPlanLabel);

//		JTableHeader MealPlanHeader = MealPlanTable.getTableHeader();
//		MealPlanHeader.setBackground(green);
//		MealPlanHeader.setForeground(black);
//		MealPlanHeader.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		JPanel RecipePanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(RecipePanel, "RecipeViewPanel");
		RecipePanel.setLayout(null);

		JScrollPane RecipeScrollPane = new JScrollPane();
		RecipeScrollPane.setBounds(34, 121, 1217, 335);
		// FridgeScrollPane.getViewport().setBackground(white);
		RecipePanel.add(RecipeScrollPane);

		RecipeColumns = new Object[] { false, "Recipe Name" };
		Object[][] RecipeData = {};
		RecipeTableModel = new DefaultTableModel(RecipeData, RecipeColumns);

		RecipeTable = new JTable(RecipeTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if (column == 3) {
					return true;
				} else {
					return false;
				}
			};
		};

		RecipeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// add and delete favourites to db
				if (RecipeTable.getSelectedColumn() == RecipeTable.getColumnCount() - 1) {
					if ((Boolean) RecipeTable.getValueAt(RecipeTable.getSelectedRow(),
							RecipeTable.getColumnCount() - 1) == true) {
						// add favourite
						try {
							SQLManager.AddFavouriteRecipe(mUserID, Integer.toString(RecipeTable.getSelectedRow() + 1));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						try {
							SQLManager.DeleteFavouriteRecipe(mUserID,
									Integer.toString(RecipeTable.getSelectedRow() + 1));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

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
		RecipeNameLabel.setBounds(34, 634, 115, 29);
		RecipePanel.add(RecipeNameLabel);

		RecipeNameTextField = new JTextField();
		RecipeNameTextField.setColumns(10);
		RecipeNameTextField.setBounds(34, 674, 115, 25);
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
		AddRecipeButton.setBounds(1079, 673, 172, 26);
		RecipePanel.add(AddRecipeButton);

		IngredientNameTextField = new JTextField();
		IngredientNameTextField.setColumns(10);
		IngredientNameTextField.setBounds(185, 674, 115, 25);
		RecipePanel.add(IngredientNameTextField);

		JLabel IngredientNameLabel = new JLabel("Ingredient Name");
		IngredientNameLabel.setBounds(185, 634, 115, 29);
		RecipePanel.add(IngredientNameLabel);

		IngredientScrollPane = new JScrollPane();
		IngredientScrollPane.setBounds(34, 467, 1217, 156);
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
		AddIngredientsButton.setBounds(331, 675, 121, 23);
		RecipePanel.add(AddIngredientsButton);

		SuggestedRecipesLabel = new JLabel("Suggested Recipes");
		SuggestedRecipesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SuggestedRecipesLabel.setBounds(34, 81, 711, 29);
		RecipePanel.add(SuggestedRecipesLabel);

		MealTimeTextField = new JTextField();
		MealTimeTextField.setBounds(489, 675, 113, 23);
		RecipePanel.add(MealTimeTextField);
		MealTimeTextField.setColumns(10);

		JLabel MealTimeLabel = new JLabel("Meal Time");
		MealTimeLabel.setBounds(489, 635, 115, 29);
		RecipePanel.add(MealTimeLabel);

		JLabel RecipesLabel = new JLabel("RECIPES");
		RecipesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipesLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		RecipesLabel.setBounds(385, 11, 360, 59);
		RecipePanel.add(RecipesLabel);

		JPanel FavouritePanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(FavouritePanel, "FavouriteViewPanel");
		FavouritePanel.setLayout(null);

		FavouriteRecipesScrollPane = new JScrollPane();
		FavouriteRecipesScrollPane.setBounds(41, 95, 1194, 343);
		FavouritePanel.add(FavouriteRecipesScrollPane);

		FavouriteRecipesColumns = new String[] { "Recipe Name" };
		Object[][] FavouriteRecipesData = {};
		FavouriteRecipesTableModel = new DefaultTableModel(FavouriteRecipesData, FavouriteRecipesColumns);
		FavouriteRecipesColumnName = new ArrayList<String>();
		FavouriteRecipesColumnName.add("");
		FavouriteRecipesColumnName.add("Recipe Name");

		FavouriteRecipesTable = new JTable(FavouriteRecipesTableModel);
		FavouriteRecipesScrollPane.setColumnHeaderView(FavouriteRecipesTable);
		FavouriteRecipesScrollPane.setViewportView(FavouriteRecipesTable);

		JScrollPane RecRecipesScrollPane = new JScrollPane();
		RecRecipesScrollPane.setBounds(41, 507, 1194, 189);
		FavouritePanel.add(RecRecipesScrollPane);

		RecRecipesColumns = new String[] { "Recipe Name" };
		Object[][] RecRecipesData = {};
		RecRecipesTableModel = new DefaultTableModel(RecRecipesData, RecRecipesColumns);

		RecRecipesTable = new JTable(RecRecipesTableModel);
		RecRecipesScrollPane.setColumnHeaderView(RecRecipesTable);
		RecRecipesScrollPane.setViewportView(RecRecipesTable);

		JLabel MyFavouriteLabel = new JLabel("MY FAVOURITES");
		MyFavouriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MyFavouriteLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		MyFavouriteLabel.setBounds(324, 11, 515, 56);
		FavouritePanel.add(MyFavouriteLabel);

		JLabel RecLabel = new JLabel("You might also like: ");
		RecLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		RecLabel.setBounds(41, 449, 540, 47);
		FavouritePanel.add(RecLabel);

		RecipeFinderPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(RecipeFinderPanel, "RecipeFinderPanel");
		RecipeFinderPanel.setLayout(null);

		SearchRecipeTextField = new JTextField();
		SearchRecipeTextField.setBounds(43, 99, 1053, 40);
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
		SearchButton.setBounds(1127, 99, 89, 40);
		RecipeFinderPanel.add(SearchButton);

		SearchedRecipesLabel = new JLabel("Recipes Found: ");
		SearchedRecipesLabel.setVerticalAlignment(SwingConstants.TOP);
		SearchedRecipesLabel.setBounds(43, 151, 1173, 567);
		RecipeFinderPanel.add(SearchedRecipesLabel);

		JLabel RecipeFinderLabel = new JLabel("RECIPE FINDER");
		RecipeFinderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeFinderLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		RecipeFinderLabel.setBounds(383, 11, 472, 59);
		RecipeFinderPanel.add(RecipeFinderLabel);

		RecipeDetailsPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(RecipeDetailsPanel, "RecipeDetailsPanel");
		RecipeDetailsPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1241, 660);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		RecipeDetailsPanel.add(scrollPane);

		JPanel RecipeDetailsContentPanel = new JGradientPanel(lightPurple, lightPink, 2);
		RecipeDetailsContentPanel.setBounds(0, 0, 750, 800);
		RecipeDetailsContentPanel.setPreferredSize(new Dimension(750, 1100));
		scrollPane.setViewportView(RecipeDetailsContentPanel);
		RecipeDetailsContentPanel.setLayout(null);

		JLabel LabelRecipeName = new JLabel("Recipe Details");
		LabelRecipeName.setHorizontalAlignment(SwingConstants.CENTER);
		LabelRecipeName.setFont(new Font("Tahoma", Font.PLAIN, 23));
		LabelRecipeName.setBounds(811, 121, 381, 45);
		RecipeDetailsContentPanel.add(LabelRecipeName);

		RecipeDetailsTitle = new JLabel("Salmon Rolls");
		RecipeDetailsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeDetailsTitle.setFont(new Font("Tahoma", Font.PLAIN, 37));
		RecipeDetailsTitle.setBounds(328, 43, 554, 87);
		RecipeDetailsContentPanel.add(RecipeDetailsTitle);

		JLabel DetailsLabel = new JLabel("");
		// DetailsLabel.setIcon(new ImageIcon("C:\\Users\\Downloads\\meal.png"));
		DetailsLabel.setBounds(384, 11, 225, 188);
		RecipeDetailsContentPanel.add(DetailsLabel);

		JSplitPane RecipeDescriptionSplitPane = new JSplitPane();
		RecipeDescriptionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		RecipeDescriptionSplitPane.setEnabled(false);
		RecipeDescriptionSplitPane.setBounds(35, 210, 1157, 110);
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
		ProcessDetailsSplitPane.setBounds(35, 331, 561, 87);
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
		NutritionSplitPane.setBounds(606, 331, 586, 87);
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
		IngredientsSplitPane.setBounds(35, 429, 1157, 157);
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
		InstructionsSplitPane.setBounds(35, 597, 1157, 210);
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
		RecommendationsSplitPane.setBounds(35, 845, 1157, 147);
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

		JPanel SettingsPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(SettingsPanel, "SettingsViewPanel");
		SettingsPanel.setLayout(null);

		JLabel ChangePasswordLabel = new JLabel("Change Password: ");
		ChangePasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChangePasswordLabel.setBounds(97, 165, 214, 68);
		SettingsPanel.add(ChangePasswordLabel);

		ChangePasswordTextField = new JRoundedTextField(0);
		ChangePasswordTextField.setBounds(278, 175, 232, 57);
		SettingsPanel.add(ChangePasswordTextField);
		ChangePasswordTextField.setColumns(10);

		JLabel LogoutLabel = new JLabel("Log Out?");
		LogoutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardLayoutPane, "LoggingPanel");
			}
		});
		LogoutLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LogoutLabel.setBounds(97, 287, 168, 57);
		Font font = LogoutLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		LogoutLabel.setFont(font.deriveFont(attributes));
		SettingsPanel.add(LogoutLabel);

		JButton ChangeButton = new JRoundedGradientButton("Save Changes", white, white);
		ChangeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ChangeButton.setBackground(Color.WHITE);
		ChangeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ChangePasswordTextField.getText().length() != 0) {
					try {
						SQLManager.UpdatePassword(mUserID, ChangePasswordTextField.getText());
						ChangePasswordTextField.setText("");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		ChangeButton.setBounds(520, 175, 157, 58);
		SettingsPanel.add(ChangeButton);

		JLabel SettingsLabel = new JLabel("MY SETTINGS");
		SettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SettingsLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		SettingsLabel.setBounds(442, 11, 345, 77);
		SettingsPanel.add(SettingsLabel);

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
