package com.uclan.MealManagement;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

public class MealManagementInterface extends JFrame {

	// member variables
	private JFrame frame;
	private CardLayout cardLayout = new CardLayout(0, 0);
	private CardLayout viewCardLayout = new CardLayout(0, 0);

	private JPanel contentPane;
	private JPanel cardLayoutPane;
	private JPanel ViewPanel;
	private JPanel RecipeFinderPanel;
	private JPanel RecipeDetailsPanel;
	private JPanel LoggingPanel;
	private JPanel FridgePanel;
	private JPanel AddRecipePanel;
	private JPanel MainPanel;
	private JPanel MenuPanel;
	private JPanel MealPlanPanel;
	private JPanel RecipePanel;
	private JPanel FavouritePanel;
	private JPanel RecipeDetailsContentPanel;
	private JPanel SettingsPanel;

	private DefaultTableModel model;
	private DefaultTableModel MealPlanTableModel;
	private DefaultTableModel RecipeTableModel;
	private DefaultTableModel IngredientTableModel;
	private DefaultTableModel FavouriteRecipesTableModel;
	private DefaultTableModel RecRecipesTableModel;
	private JTable FridgeTable;
	private JTable MealPlanTable;
	private JTable RecipeTable;
	private JTable IngredientTable;
	private JTable FavouriteRecipesTable;
	private JTable RecipeRecommendationsTable;
	private JTable RecRecipesTable;
	private JTable RecipeFinderTable;

	private JTextField UsernameTextField;
	private JTextField PasswordTextField;
	private JTextField RecipeNameTextField;
	private JTextField IngredientNameTextField;
	private JTextField ItemNameTextField;
	private JTextField QuantityTextField;
	private JTextField CaloriesTextField;
	private JTextField SearchRecipeTextField;
	private JTextField MealTimeTextField;
	private JTextField BestBeforeTextField;
	private JTextField ChangePasswordTextField;
	private JTextField CalorieIntakeTextField;
	private JTextField CookTimeTextField;
	private JTextField DescriptionTextField;
	private JTextField TimeTextField;
	private JTextField DiffTextField;
	private JTextField ServeTextField;
	private JTextField CalTextField;
	private JTextField DietCatTextField;
	private JTextField InstTextField;

	private String mUsername;
	private String mUserID;
	private String previousPanel;
	private String[] MealPlanColumns;
	private String[] IngredientColumns;
	private String[] FavouriteRecipesColumns;
	private String[] RecRecipesColumns;
	private Object[] RecipeColumns;

	private JScrollPane IngredientScrollPane;
	private JScrollPane FavouriteRecipesScrollPane;
	private JScrollPane FridgeScrollPane;
	private JScrollPane MealPlanScrollPane;
	private JScrollPane RecipeScrollPane;
	private JScrollPane RecRecipesScrollPane;
	private JScrollPane scrollPane;
	private JLabel SearchedRecipesLabel;
	private JLabel RecipeDetailsTitle;
	private JLabel TotalItemsLabel;
	private JLabel CheckRecipeLabel;
	private JLabel DateTextLabel;
	private JLabel UsernameLabel;
	private JLabel PasswordLabel;
	private JLabel InvalidDetailsLabel;
	private JLabel ItemNameLabel;
	private JLabel QuantityLabel;
	private JLabel CaloriesLabel;
	private JLabel BestBeforeLabel;
	private JLabel MealPlanLabel;
	private JLabel DateLabel;
	private JLabel RecipeNameLabel;
	private JLabel IngredientNameLabel;
	private JLabel MealTimeLabel;
	private JLabel DescriptionLabel;
	private JLabel TimeLabel;
	private JLabel DiffLabel;
	private JLabel ServeLabel;
	private JLabel CalLabel;
	private JLabel DietCatLabel;
	private JLabel InstLabel;
	private JLabel RecipesLabel;
	private JLabel MyFavouriteLabel;
	private JLabel RecLabel;
	private JLabel RecipeFinderLabel;
	private JLabel LabelRecipeName;
	private JLabel DetailsLabel;
	private JLabel RecipeDescriptionLabel;
	private JLabel RecipeProcessDetailsLabel;
	private JLabel NutritionLabel;
	private JLabel RecipeIngredientsLabel;
	private JLabel RecipeInstructionsLabel;
	private JLabel RecipeRecommendationsLabel;

	private JTextArea RecipeDescriptionTextArea;
	private JTextArea RecipeProcessDetailsTextArea;
	private JTextArea NutritionTextArea;
	private JTextArea RecipeIngredientsTextArea;
	private JTextArea RecipeInstructionsTextArea;

	private JButton GenerateMealPlanButton;
	private JButton SaveMealPlanButton;
	private JButton FinishWeekButton;
	private JButton DeleteFridgeIngredientsButton;
	private JButton SearchButton;
	private JButton BackRecipeDetailsButton;
	private JButton CreateYourOwnRecipeButton;

	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Calendar calendar = Calendar.getInstance();

	// Arraylists
	private ArrayList<Recipe> mRecipeList = new ArrayList<Recipe>(); // this is the recipe list from the api
	public ArrayList<String> FridgeIngredientList = new ArrayList<String>();
	public ArrayList<String> RecipeQueryList = new ArrayList<String>();
	public static ArrayList<String> IngredientTableList = new ArrayList<String>();
	ArrayList<Recipe> mPossibleRecipes;
	private ArrayList<String> FridgeTableColumnName;
	private ArrayList<String> FavouriteRecipesColumnName;
	private JLabel MealPlanStatusLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LogFileManager.openLog(System.getProperty("user.dir"));
		LogFileManager.writeToLog("Meal Management started.");
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
		setTitle("Meal Management Application");
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

		// The logging panel added to the parent
		LoggingPanel = new JGradientPanel(lightPurple, lightPink, 2);
		cardLayoutPane.add(LoggingPanel, "LoggingPanel");
		LoggingPanel.setLayout(null);

		UsernameLabel = new JLabel("Username");
		UsernameLabel.setForeground(Color.WHITE);
		UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		UsernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		UsernameLabel.setBounds(451, 220, 167, 40);
		LoggingPanel.add(UsernameLabel);

		JButton LoginButton = new JRoundedButton("Login");
		LoginButton.setBackground(Color.WHITE);
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		LoginButton.setFont(new Font("Tahoma", Font.BOLD, 18));
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
								InvalidDetailsLabel.setText("Invalid username and password. Try Again?");
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
					InvalidDetailsLabel.setText("Enter Details");
				}

			}
		});
		LoginButton.setToolTipText("take me to main panel");
		LoginButton.setBounds(575, 412, 116, 57);
		LoggingPanel.add(LoginButton);

		PasswordLabel = new JLabel("Password");
		PasswordLabel.setForeground(Color.WHITE);
		PasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		PasswordLabel.setBounds(451, 300, 138, 40);
		LoggingPanel.add(PasswordLabel);

		UsernameTextField = new JRoundedTextField(0);
		UsernameTextField.setBounds(628, 209, 192, 51);
		UsernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		UsernameTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		LoggingPanel.add(UsernameTextField);
		UsernameTextField.setColumns(10);

		PasswordTextField = new JRoundedPasswordField(0);
		PasswordTextField.setColumns(10);
		PasswordTextField.setBounds(628, 289, 192, 51);
		PasswordTextField.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		LoggingPanel.add(PasswordTextField);

		InvalidDetailsLabel = new JLabel("");
		InvalidDetailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		InvalidDetailsLabel.setForeground(Color.WHITE);
		InvalidDetailsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InvalidDetailsLabel.setBounds(707, 415, 391, 51);
		LoggingPanel.add(InvalidDetailsLabel);

		// The main panel added to the parent
		MainPanel = new JGradientPanel(lightPurple, lightPink, 2);
		cardLayoutPane.add(MainPanel, "MainPanel");
		MainPanel.setLayout(null);

		MenuPanel = new JGradientPanel(menuPurple, menuPurple);
		MenuPanel.setBounds(0, 0, 78, 729);
		MainPanel.add(MenuPanel);
		MenuPanel.setLayout(null);

		JButton FridgeMenuButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		FridgeMenuButton.setToolTipText("Fridge");
		FridgeMenuButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/fridgeIcon.png")));
		FridgeMenuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "FridgeViewPanel");
				ResultSet rsFridgeQuery;
				try {
					rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
					DesignManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery, FridgeTableColumnName);

					// make fridge ingredient list

					while (rsFridgeQuery.next()) {
						FridgeIngredientList.add(rsFridgeQuery.getString(1));
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					LogFileManager.logError(e2.getMessage());
				}
			}
		});

		FridgeMenuButton.setBounds(0, 148, 78, 49);
		MenuPanel.add(FridgeMenuButton);

		JButton MealPlanButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		MealPlanButton.setToolTipText("Meal Plan");
		MealPlanButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/mealIcon.png")));
		MealPlanButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "MealPlanViewPanel");
			}
		});
		MealPlanButton.setBounds(0, 198, 78, 49);
		MenuPanel.add(MealPlanButton);

		JButton RecipeButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		RecipeButton.setToolTipText("Recipe");
		RecipeButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/recipeIcon.png")));
		RecipeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "RecipeViewPanel");
				ResultSet rsRecipeQuery;
				try {
					rsRecipeQuery = SQLManager.RecipeQuery();
					DesignManager.populateTableWithResultSetWithCheckBox(RecipeTable, rsRecipeQuery, mUserID);

					// SQLManager.populateSuggestedRecipes(mUserID, SuggestedRecipesLabel);

					// make fridge ingredient list

					while (rsRecipeQuery.next()) {
						RecipeQueryList.add(rsRecipeQuery.getString(2));
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					LogFileManager.logError(e2.getMessage());
				}
			}
		});
		RecipeButton.setBounds(0, 248, 78, 49);
		MenuPanel.add(RecipeButton);

		JButton FavouritesButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		FavouritesButton.setToolTipText("My Favourites");
		FavouritesButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/favouriteIcon.png")));
		FavouritesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResultSet rsFavouriteRecipeQuery;
				// query of the favourite recipes
				viewCardLayout.show(ViewPanel, "FavouriteViewPanel");
				try {
					rsFavouriteRecipeQuery = SQLManager.getFavouriteRecipesResultSet(mUserID);
					DesignManager.populateTableWithResultSet(FavouriteRecipesTable, rsFavouriteRecipeQuery,
							FavouriteRecipesColumnName);

					SQLManager.populateSuggestedRecipesInTable(mUserID, RecRecipesTable);

				} catch (Exception e1) {
					e1.printStackTrace();
					LogFileManager.logError(e1.getMessage());
				}
			}
		});
		FavouritesButton.setBounds(0, 298, 78, 49);
		MenuPanel.add(FavouritesButton);

		JButton RecipeFinderButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		RecipeFinderButton.setToolTipText("Search Recipes");
		RecipeFinderButton.setIcon(new ImageIcon(MealManagementInterface.class.getResource("/finderIcon.png")));
		RecipeFinderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "RecipeFinderPanel");
			}
		});
		RecipeFinderButton.setBounds(0, 348, 78, 49);
		MenuPanel.add(RecipeFinderButton);

		JButton SettingsButton = new JGradientButton(lightMenuPurple, lightMenuPurple);
		SettingsButton.setToolTipText("Settings");
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

		FridgeScrollPane = new JScrollPane();
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
		AddFridgeIngredientsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ItemNameTextField.getText().length() > 0 && BestBeforeTextField.getText().length() > 0
						&& QuantityTextField.getText().length() > 0 && CaloriesTextField.getText().length() > 0) {
					try {
						SQLManager.AddFridgeQuery(mUserID, ItemNameTextField.getText(), BestBeforeTextField.getText(),
								QuantityTextField.getText(), CaloriesTextField.getText());
						ResultSet rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
						DesignManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery, FridgeTableColumnName);
						TotalItemsLabel.setText("Total Items: " + SQLManager.TotalFridgeItemsQuery());
						ItemNameTextField.setText("");
						QuantityTextField.setText("");
						CaloriesTextField.setText("");
						BestBeforeTextField.setText("");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						LogFileManager.logError(e1.getMessage());
					}
				}
			}
		});
		AddFridgeIngredientsButton.setBounds(1126, 30, 49, 44);
		FridgePanel.add(AddFridgeIngredientsButton);

		ItemNameTextField = new JRoundedTextField(0);
		ItemNameTextField.setBounds(34, 30, 222, 53);
		ItemNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		FridgePanel.add(ItemNameTextField);
		ItemNameTextField.setColumns(10);

		QuantityTextField = new JRoundedTextField(0);
		QuantityTextField.setColumns(10);
		QuantityTextField.setBounds(591, 30, 222, 53);
		QuantityTextField.setHorizontalAlignment(SwingConstants.CENTER);
		FridgePanel.add(QuantityTextField);

		CaloriesTextField = new JRoundedTextField(0);
		CaloriesTextField.setColumns(10);
		CaloriesTextField.setBounds(866, 30, 222, 53);
		CaloriesTextField.setHorizontalAlignment(SwingConstants.CENTER);
		FridgePanel.add(CaloriesTextField);

		ItemNameLabel = new JLabel("Ingredient Name");
		ItemNameLabel.setForeground(Color.WHITE);
		ItemNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ItemNameLabel.setBounds(34, -5, 202, 40);
		FridgePanel.add(ItemNameLabel);

		QuantityLabel = new JLabel("Quantity");
		QuantityLabel.setForeground(Color.WHITE);
		QuantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		QuantityLabel.setBounds(591, -5, 202, 40);
		FridgePanel.add(QuantityLabel);

		CaloriesLabel = new JLabel("Calories");
		CaloriesLabel.setForeground(Color.WHITE);
		CaloriesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CaloriesLabel.setBounds(866, -5, 202, 40);
		FridgePanel.add(CaloriesLabel);

		BestBeforeLabel = new JLabel("Best Before");
		BestBeforeLabel.setForeground(Color.WHITE);
		BestBeforeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BestBeforeLabel.setBounds(310, -5, 202, 40);
		FridgePanel.add(BestBeforeLabel);

		BestBeforeTextField = new JRoundedTextField(0);
		BestBeforeTextField.setColumns(10);
		BestBeforeTextField.setBounds(310, 30, 222, 53);
		BestBeforeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		FridgePanel.add(BestBeforeTextField);

		DeleteFridgeIngredientsButton = new JButton();
		DeleteFridgeIngredientsButton.setBackground(Color.WHITE);
		DeleteFridgeIngredientsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = FridgeTable.getSelectedRow();
				String itemName = String.valueOf(FridgeTable.getModel().getValueAt(selectedRow, 0));
				try {
					SQLManager.DeleteFridgeQuery(mUserID, itemName);
					ResultSet rsFridgeQuery = SQLManager.FridgeQuery(mUserID);
					DesignManager.populateTableWithResultSet(FridgeTable, rsFridgeQuery, FridgeTableColumnName);
					TotalItemsLabel.setText("Total Items: " + SQLManager.TotalFridgeItemsQuery());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					LogFileManager.logError(e1.getMessage());
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

		MealPlanPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(MealPlanPanel, "MealPlanViewPanel");
		MealPlanPanel.setLayout(null);

		MealPlanScrollPane = new JScrollPane();
		MealPlanScrollPane.setBounds(34, 110, 1217, 391);
		MealPlanScrollPane.getViewport().setBackground(white);
		MealPlanPanel.add(MealPlanScrollPane);

		MealPlanColumns = new String[] { "Meal", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
				"Sunday" };
		Object[][] MealPlanData = {};
		MealPlanTableModel = new DefaultTableModel(MealPlanData, MealPlanColumns);
		MealPlanTable = new JTable(MealPlanTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
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

		MealPlanTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// If a user in the recipe table is double clicked then that recipes details are
				// displayed opening the recipe details panel

				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1
						&& MealPlanTable.getSelectedRows().length == 1 && MealPlanTable.getSelectedColumn() != 0) {

					viewCardLayout.show(ViewPanel, "RecipeDetailsPanel");
					previousPanel = "MealPlanViewPanel";

					// Fill in details
					try {
						// Get recipe details
//						RecipeTable.getSelectedRow() + 1
						int selectedRow = MealPlanTable.getSelectedRow();
						int selectedColumn = MealPlanTable.getSelectedColumn();
						String recipeName = (String) MealPlanTable.getValueAt(selectedRow, selectedColumn);
						int recipeID = SQLManager.getRecipeID(recipeName);
						HashMap<String, String> recipeDetails = SQLManager.getRecipeDetails(recipeID);

						String recipeDescription = recipeDetails.get("RecipeDescription");
						// String recipeName = recipeDetails.get("RecipeName");
						String recipeDifficulty = recipeDetails.get("RecipeDifficulty");
						String recipeTime = recipeDetails.get("recipeTime");
						String mealTime = recipeDetails.get("mealTime");
						String recipeServings = recipeDetails.get("RecipeServings");
						String recipeCalories = recipeDetails.get("RecipeCalories");
						String recipeDietCategory = recipeDetails.get("dietCategory");
						ArrayList<Ingredient> recipeIngredients = SQLManager
								.getIngredientsOfRecipe(Integer.toString(recipeID)); // need to
																						// change
						// this
						String recipeIngredientsString = "";
						String recipeInstructions = recipeDetails.get("RecipeInstructions");

						// Create string from ingredients
						for (int i = 0; i < recipeIngredients.size(); i++) {
							recipeIngredientsString += recipeIngredients.get(i).name + "\n";
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
						LogFileManager.logError(e1.getMessage());
					}
				}

			}
		});
		MealPlanScrollPane.setViewportView(MealPlanTable);

		MealPlanLabel = new JLabel("MEAL PLAN FOR YOU");
		MealPlanLabel.setForeground(Color.WHITE);
		MealPlanLabel.setBackground(Color.WHITE);
		MealPlanLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MealPlanLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		MealPlanLabel.setBounds(358, 11, 456, 57);
		MealPlanPanel.add(MealPlanLabel);

		// Generate meal plan button
		GenerateMealPlanButton = new JButton("Generate New Meal Plan");
		GenerateMealPlanButton.setBackground(Color.WHITE);
		GenerateMealPlanButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Declare meal plan variables
				MealPlan RecipeMealPlan = null;

				// Get meal plan
				try {
					RecipeMealPlan = MealAlgorithmManager.CalculateMealPlan(mUserID);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					LogFileManager.logError(e1.getMessage());
				}

				// error text
				if (MealAlgorithmManager.IsMealPlanGenerated == false) {
					MealPlanStatusLabel.setText("Could not generate meal plan");
				} else {
					MealPlanStatusLabel.setText("Meal plan generated");
				}

				// Create rows for meal plan table
				MealPlanTableModel.setRowCount(0);
				for (int j = 0; j < 3; j++) {

					MealPlanTableModel.addRow(new Object[] {});
				}

				// Set up columns for meal plan JTable
				MealPlanTable.setValueAt("breakfast", 0, 0);
				MealPlanTable.setValueAt("lunch", 1, 0);
				MealPlanTable.setValueAt("dinner", 2, 0);
				int maxColumn = 8;
				int maxRow = 3;

				// Populate JTable with meal plan
				for (int i = 1; i < maxColumn; i++) {
					// Get day
					MealPlanDay day = RecipeMealPlan.days.get(i - 1);

					// Get meals from day
					Recipe breakfastRecipe = day.breakfastRecipe;
					Recipe lunchRecipe = day.lunchRecipe;
					Recipe dinnerRecipe = day.dinnerRecipe;

					// Put meals into jtable
					if (breakfastRecipe != null) {
						MealPlanTable.setValueAt(breakfastRecipe.mName, 0, i);
					}
					if (lunchRecipe != null) {
						MealPlanTable.setValueAt(lunchRecipe.mName, 1, i);
					}
					if (dinnerRecipe != null) {
						MealPlanTable.setValueAt(dinnerRecipe.mName, 2, i);
					}
				}

				// Enable save button
				SaveMealPlanButton.setEnabled(true);
			}
		});
		GenerateMealPlanButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		GenerateMealPlanButton.setBounds(435, 642, 258, 57);
		MealPlanPanel.add(GenerateMealPlanButton);

		SaveMealPlanButton = new JButton("Save Meal Plan");
		SaveMealPlanButton.setBackground(Color.WHITE);
		SaveMealPlanButton.setEnabled(false);
		SaveMealPlanButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Disable buttons
				GenerateMealPlanButton.setEnabled(false);
				SaveMealPlanButton.setEnabled(false);
				FinishWeekButton.setEnabled(true);

				// Remove ingredients from database
				for (int i = 0; i < MealAlgorithmManager.mealPlan.days.size(); i++) {
					MealPlanDay mealPlanDay = MealAlgorithmManager.mealPlan.days.get(i);
					Recipe breakfastRecipe = mealPlanDay.breakfastRecipe;
					Recipe lunchRecipe = mealPlanDay.lunchRecipe;
					Recipe dinnerRecipe = mealPlanDay.dinnerRecipe;
					if (breakfastRecipe != null) {
						// Find all ingredients
						for (int j = 0; j < breakfastRecipe.IngredientList.size(); j++) {
							// Get ingredient
							String ingredient = breakfastRecipe.IngredientList.get(j).name;

							// Remove
							try {
								SQLManager.decrementIngredient(ingredient);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

					if (lunchRecipe != null) {
						// Find all ingredients
						for (int j = 0; j < lunchRecipe.IngredientList.size(); j++) {
							// Get ingredient
							String ingredient = lunchRecipe.IngredientList.get(j).name;

							// Remove
							try {
								SQLManager.decrementIngredient(ingredient);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

					if (dinnerRecipe != null) {
						// Find all ingredients
						for (int j = 0; j < dinnerRecipe.IngredientList.size(); j++) {
							// Get ingredient
							String ingredient = dinnerRecipe.IngredientList.get(j).name;

							// Remove
							try {
								SQLManager.decrementIngredient(ingredient);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}

			}
		});
		SaveMealPlanButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		SaveMealPlanButton.setBounds(715, 642, 258, 57);
		MealPlanPanel.add(SaveMealPlanButton);

		DateLabel = new JLabel("Date: ");
		DateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		DateLabel.setBounds(1096, 63, 41, 36);
		MealPlanPanel.add(DateLabel);

		DateTextLabel = new JLabel("21/03/2022");
		DateTextLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		DateTextLabel.setBounds(1147, 63, 93, 36);
		MealPlanPanel.add(DateTextLabel);

		FinishWeekButton = new JButton("Finish Week");
		FinishWeekButton.setBackground(Color.WHITE);
		FinishWeekButton.setEnabled(false);
		FinishWeekButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Add to date
				calendar.add(Calendar.DATE, 7);

				// Get date text
				String date = dateFormat.format(calendar.getTime());

				// Set label
				DateTextLabel.setText(date);

				// Re-enable the generate and save buttons
				GenerateMealPlanButton.setEnabled(true);
				SaveMealPlanButton.setEnabled(false);
				FinishWeekButton.setEnabled(false);

				// Reset JTable
				MealPlanTableModel.setRowCount(0);
			}
		});
		FinishWeekButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		FinishWeekButton.setBounds(993, 642, 258, 57);
		MealPlanPanel.add(FinishWeekButton);

		MealPlanStatusLabel = new JLabel("");
		MealPlanStatusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		MealPlanStatusLabel.setForeground(Color.WHITE);
		MealPlanStatusLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		MealPlanStatusLabel.setBounds(795, 512, 456, 50);
		MealPlanPanel.add(MealPlanStatusLabel);

		AddRecipePanel = new JGradientPanel(lightPurple, lightPink, 2);
		AddRecipePanel.setBackground(Color.LIGHT_GRAY);
		ViewPanel.add(AddRecipePanel, "AddRecipeViewPanel");
		AddRecipePanel.setLayout(null);

		RecipePanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(RecipePanel, "RecipeViewPanel");
		RecipePanel.setLayout(null);

		RecipeScrollPane = new JScrollPane();
		RecipeScrollPane.setBounds(34, 81, 1217, 575);
		RecipeScrollPane.getViewport().setBackground(white);
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
					// Get recipeID that has been clicked
					String recipeID = Integer
							.toString((Integer) RecipeTable.getValueAt(RecipeTable.getSelectedRow(), 0));

					// Decide whether to add or remove from favourites
					if ((Boolean) RecipeTable.getValueAt(RecipeTable.getSelectedRow(),
							RecipeTable.getColumnCount() - 1) == true) {
						// add favourite
						try {
							SQLManager.AddFavouriteRecipe(mUserID, recipeID);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							LogFileManager.logError(e1.getMessage());
						}
					} else {
						try {
							SQLManager.DeleteFavouriteRecipe(mUserID, recipeID);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							LogFileManager.logError(e1.getMessage());
						}
					}
				}

				// If a user in the recipe table is double clicked then that recipes details are
				// displayed opening the recipe details panel

				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1
						&& RecipeTable.getSelectedRows().length == 1 && RecipeTable.getSelectedColumn() - 4 != 0) {

					viewCardLayout.show(ViewPanel, "RecipeDetailsPanel");
					previousPanel = "RecipeViewPanel";

					// Fill in details
					try {
						// Get recipe details
//						RecipeTable.getSelectedRow() + 1
						int recipeID = (Integer) RecipeTable.getModel().getValueAt(RecipeTable.getSelectedRow(), 0);
						HashMap<String, String> recipeDetails = SQLManager.getRecipeDetails(recipeID);

						String recipeDescription = recipeDetails.get("RecipeDescription");
						String recipeName = recipeDetails.get("RecipeName");
						String recipeDifficulty = recipeDetails.get("RecipeDifficulty");
						String recipeTime = recipeDetails.get("recipeTime");
						String mealTime = recipeDetails.get("mealTime");
						String recipeServings = recipeDetails.get("RecipeServings");
						String recipeCalories = recipeDetails.get("RecipeCalories");
						String recipeDietCategory = recipeDetails.get("dietCategory");
						ArrayList<Ingredient> recipeIngredients = SQLManager
								.getIngredientsOfRecipe(Integer.toString(recipeID));

						String recipeIngredientsString = "";
						String recipeInstructions = recipeDetails.get("RecipeInstructions");

						// Create string from ingredients
						for (int i = 0; i < recipeIngredients.size(); i++) {
							recipeIngredientsString += recipeIngredients.get(i).name + "\n";
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
						LogFileManager.logError(e1.getMessage());
					}
				}

			}
		});
		RecipeTable.setBounds(32, 99, 709, 378);
		RecipeTable.setBackground(white);
		RecipeTable.setForeground(black);
		RecipeTable.setRowHeight(32);
		RecipeTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		RecipeTable.setShowGrid(false);
		RecipeTable.setShowVerticalLines(false);
		RecipeTable.setShowHorizontalLines(true);
		RecipeScrollPane.setViewportView(RecipeTable);

		RecipeNameLabel = new JLabel("Recipe Name");
		RecipeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RecipeNameLabel.setForeground(Color.WHITE);
		RecipeNameLabel.setBounds(34, 29, 115, 29);
		AddRecipePanel.add(RecipeNameLabel);

		RecipeNameTextField = new JTextField();
		RecipeNameTextField.setColumns(10);
		RecipeNameTextField.setBounds(159, 29, 339, 40);
		AddRecipePanel.add(RecipeNameTextField);

		JButton AddRecipeButton = new JButton("Add Recipe");
		AddRecipeButton.setBackground(Color.WHITE);
		AddRecipeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		AddRecipeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (RecipeNameTextField.getText().length() > 0 && IngredientTable.getRowCount() > 0) {

					try {
						// add recipe
						SQLManager.AddRecipeQuery(RecipeNameTextField.getText(), MealTimeTextField.getText(),
								DescriptionTextField.getText(), TimeTextField.getText(), DietCatTextField.getText(),
								CalTextField.getText(), DiffTextField.getText(), ServeTextField.getText(),
								InstTextField.getText(), "");
						// get the recipeid of the new recipe

						// add ingredients with the recipeid of the new recipe
						for (int i = 0; i < IngredientTableList.size(); i++) {
							SQLManager.AddIngredientsFromList(IngredientTableList.get(i), SQLManager.RecipeID());
						}
						// update the recipe table
						ResultSet rsRecipeQuery = SQLManager.RecipeQuery();
						DesignManager.populateTableWithResultSetWithCheckBox(RecipeTable, rsRecipeQuery, mUserID);
						CheckRecipeLabel.setText("Recipe Added");
						// add a new column dynamically
						// RecipeTableModel.addColumn("Favorite", new Object[] { false, false, true });
						RecipeTable.setModel(RecipeTableModel);
						RecipeNameTextField.setText("");
						MealTimeTextField.setText("");
						DescriptionTextField.setText("");
						TimeTextField.setText("");
						DietCatTextField.setText("");
						CalTextField.setText("");
						DiffTextField.setText("");
						ServeTextField.setText("");
						InstTextField.setText("");

						// delete all row in the ingredient jtable and arraylist
						IngredientTableList.clear();
						DesignManager.deleteAllRows(IngredientTableModel);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						LogFileManager.logError(e1.getMessage());
					}
				}
			}
		});
		AddRecipeButton.setBounds(1079, 673, 172, 45);
		AddRecipePanel.add(AddRecipeButton);

		IngredientNameTextField = new JTextField();
		IngredientNameTextField.setColumns(10);
		IngredientNameTextField.setBounds(191, 80, 143, 36);
		AddRecipePanel.add(IngredientNameTextField);

		IngredientNameLabel = new JLabel("Ingredient Name");
		IngredientNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IngredientNameLabel.setForeground(Color.WHITE);
		IngredientNameLabel.setBounds(34, 79, 159, 29);
		AddRecipePanel.add(IngredientNameLabel);

		IngredientScrollPane = new JScrollPane();
		IngredientScrollPane.setBounds(34, 138, 1217, 191);
		AddRecipePanel.add(IngredientScrollPane);

		IngredientColumns = new String[] { "Ingredient Name" };
		Object[][] IngredientData = {};
		IngredientTableModel = new DefaultTableModel(IngredientData, IngredientColumns) {
			public boolean isCellEditable(int row, int col) {
				return true;
			}
		};
		IngredientTable = new JTable(IngredientTableModel);
		JTableHeader IngredientHeader = IngredientTable.getTableHeader();
		IngredientHeader.setBackground(lightPink);
		IngredientHeader.setForeground(black);
		IngredientHeader.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		IngredientTable.setBackground(Color.WHITE);
		IngredientTable.setForeground(black);
		IngredientTable.setRowHeight(32);
		IngredientTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		IngredientTable.setShowGrid(false);
		IngredientTable.setShowVerticalLines(false);
		IngredientTable.setShowHorizontalLines(true);
		IngredientScrollPane.setColumnHeaderView(IngredientTable);
		IngredientScrollPane.getViewport().setBackground(white);
		IngredientScrollPane.setViewportView(IngredientTable);

		JButton AddIngredientsButton = new JButton("Add Ingredients");
		AddIngredientsButton.setBackground(Color.WHITE);
		AddIngredientsButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
		AddIngredientsButton.setBounds(344, 80, 154, 36);
		AddRecipePanel.add(AddIngredientsButton);

		MealTimeTextField = new JTextField();
		MealTimeTextField.setBounds(700, 80, 143, 36);
		AddRecipePanel.add(MealTimeTextField);
		MealTimeTextField.setColumns(10);

		MealTimeLabel = new JLabel("Meal Time");
		MealTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		MealTimeLabel.setForeground(Color.WHITE);
		MealTimeLabel.setBounds(541, 80, 115, 29);
		AddRecipePanel.add(MealTimeLabel);

		DescriptionLabel = new JLabel("Recipe Description ");
		DescriptionLabel.setForeground(Color.WHITE);
		DescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		DescriptionLabel.setBounds(541, 21, 159, 46);
		AddRecipePanel.add(DescriptionLabel);

		DescriptionTextField = new JTextField();
		DescriptionTextField.setBounds(700, 11, 551, 56);
		AddRecipePanel.add(DescriptionTextField);
		DescriptionTextField.setColumns(10);

		TimeLabel = new JLabel("Recipe Time");
		TimeLabel.setForeground(Color.WHITE);
		TimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TimeLabel.setBounds(987, 77, 115, 39);
		AddRecipePanel.add(TimeLabel);

		TimeTextField = new JTextField();
		TimeTextField.setBounds(1123, 74, 128, 41);
		AddRecipePanel.add(TimeTextField);
		TimeTextField.setColumns(10);

		DiffLabel = new JLabel("Difficulty");
		DiffLabel.setForeground(Color.WHITE);
		DiffLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		DiffLabel.setBounds(34, 340, 128, 29);
		AddRecipePanel.add(DiffLabel);

		DiffTextField = new JTextField();
		DiffTextField.setColumns(10);
		DiffTextField.setBounds(34, 380, 128, 41);
		AddRecipePanel.add(DiffTextField);

		ServeLabel = new JLabel("Servings");
		ServeLabel.setForeground(Color.WHITE);
		ServeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ServeLabel.setBounds(219, 340, 128, 29);
		AddRecipePanel.add(ServeLabel);

		ServeTextField = new JTextField();
		ServeTextField.setColumns(10);
		ServeTextField.setBounds(219, 380, 128, 41);
		AddRecipePanel.add(ServeTextField);

		CalLabel = new JLabel("Calories");
		CalLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CalLabel.setForeground(Color.WHITE);
		CalLabel.setBounds(400, 340, 128, 29);
		AddRecipePanel.add(CalLabel);

		CalTextField = new JTextField();
		CalTextField.setColumns(10);
		CalTextField.setBounds(400, 380, 128, 41);
		AddRecipePanel.add(CalTextField);

		DietCatLabel = new JLabel("Diet Category");
		DietCatLabel.setForeground(Color.WHITE);
		DietCatLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		DietCatLabel.setBounds(587, 340, 128, 29);
		AddRecipePanel.add(DietCatLabel);

		DietCatTextField = new JTextField();
		DietCatTextField.setColumns(10);
		DietCatTextField.setBounds(587, 380, 128, 41);
		AddRecipePanel.add(DietCatTextField);

		InstLabel = new JLabel("Recipe Instructions");
		InstLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InstLabel.setForeground(Color.WHITE);
		InstLabel.setBounds(34, 432, 238, 47);
		AddRecipePanel.add(InstLabel);

		InstTextField = new JTextField();
		InstTextField.setColumns(10);
		InstTextField.setBounds(34, 490, 1217, 172);
		AddRecipePanel.add(InstTextField);

		CheckRecipeLabel = new JLabel("");
		CheckRecipeLabel.setForeground(Color.WHITE);
		CheckRecipeLabel.setBounds(866, 673, 178, 45);
		AddRecipePanel.add(CheckRecipeLabel);

		JButton BackEditRecipeButton = new JButton("Back");
		BackEditRecipeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "RecipeViewPanel");
			}
		});
		BackEditRecipeButton.setBackground(Color.WHITE);
		BackEditRecipeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BackEditRecipeButton.setBounds(34, 673, 115, 45);
		AddRecipePanel.add(BackEditRecipeButton);

		RecipesLabel = new JLabel("RECIPES");
		RecipesLabel.setForeground(Color.WHITE);
		RecipesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipesLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		RecipesLabel.setBounds(385, 11, 360, 59);
		RecipePanel.add(RecipesLabel);

		CreateYourOwnRecipeButton = new JButton("Create Your Own Recipe");
		CreateYourOwnRecipeButton.setBackground(Color.WHITE);
		CreateYourOwnRecipeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, "AddRecipeViewPanel");
			}
		});
		CreateYourOwnRecipeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CreateYourOwnRecipeButton.setForeground(Color.BLACK);
		CreateYourOwnRecipeButton.setBounds(992, 667, 259, 38);
		RecipePanel.add(CreateYourOwnRecipeButton);

		FavouritePanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(FavouritePanel, "FavouriteViewPanel");
		FavouritePanel.setLayout(null);

		FavouriteRecipesScrollPane = new JScrollPane();
		FavouriteRecipesScrollPane.setBounds(41, 95, 1194, 298);
		FavouritePanel.add(FavouriteRecipesScrollPane);

		FavouriteRecipesColumns = new String[] { "Recipe Name" };
		Object[][] FavouriteRecipesData = {};
		FavouriteRecipesTableModel = new DefaultTableModel(FavouriteRecipesData, FavouriteRecipesColumns);
		FavouriteRecipesColumnName = new ArrayList<String>();
		FavouriteRecipesColumnName.add("");
		FavouriteRecipesColumnName.add("Recipe Name");

		FavouriteRecipesTable = new JTable(FavouriteRecipesTableModel);
		FavouriteRecipesTable.setBackground(Color.WHITE);
		FavouriteRecipesTable.setForeground(black);
		FavouriteRecipesTable.setRowHeight(32);
		FavouriteRecipesTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		FavouriteRecipesTable.setShowGrid(false);
		FavouriteRecipesTable.setShowVerticalLines(false);
		FavouriteRecipesTable.setShowHorizontalLines(true);
		FavouriteRecipesScrollPane.setColumnHeaderView(FavouriteRecipesTable);
		FavouriteRecipesScrollPane.getViewport().setBackground(white);
		FavouriteRecipesScrollPane.setViewportView(FavouriteRecipesTable);

		RecRecipesScrollPane = new JScrollPane();
		RecRecipesScrollPane.setBounds(41, 462, 1194, 234);
		FavouritePanel.add(RecRecipesScrollPane);

		RecRecipesColumns = new String[] { "Recipe Name" };
		Object[][] RecRecipesData = {};
		RecRecipesTableModel = new DefaultTableModel(RecRecipesData, RecRecipesColumns);

		RecRecipesTable = new JTable(RecRecipesTableModel);
		JTableHeader RecRecipesHeader = RecRecipesTable.getTableHeader();
		RecRecipesHeader.setBackground(Color.decode("#f4d8e4"));
		RecRecipesHeader.setForeground(black);
		RecRecipesHeader.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		RecRecipesTable.setBackground(Color.WHITE);
		RecRecipesTable.setForeground(black);
		RecRecipesTable.setRowHeight(32);
		RecRecipesTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		RecRecipesTable.setShowGrid(false);
		RecRecipesTable.setShowVerticalLines(false);
		RecRecipesTable.setShowHorizontalLines(true);
		RecRecipesScrollPane.setColumnHeaderView(RecRecipesTable);
		RecRecipesScrollPane.getViewport().setBackground(white);
		RecRecipesScrollPane.setViewportView(RecRecipesTable);

		MyFavouriteLabel = new JLabel("MY FAVOURITES");
		MyFavouriteLabel.setForeground(Color.WHITE);
		MyFavouriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MyFavouriteLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		MyFavouriteLabel.setBounds(324, 11, 515, 56);
		FavouritePanel.add(MyFavouriteLabel);

		RecLabel = new JLabel("You might also like: ");
		RecLabel.setForeground(Color.WHITE);
		RecLabel.setBackground(Color.WHITE);
		RecLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		RecLabel.setBounds(41, 404, 540, 47);
		FavouritePanel.add(RecLabel);

		RecipeFinderPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(RecipeFinderPanel, "RecipeFinderPanel");
		RecipeFinderPanel.setLayout(null);

		SearchRecipeTextField = new JTextField();
		SearchRecipeTextField.setBounds(43, 99, 1053, 40);
		RecipeFinderPanel.add(SearchRecipeTextField);
		SearchRecipeTextField.setColumns(10);

		SearchButton = new JButton("Search");
		SearchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if textfield > 0 then connect the api and upload the text in the textlabel

				if (SearchRecipeTextField.getText() != null) {
					try {

						// Need if conditiosn to change parameters of get APIm,

						// Get recipes
						ArrayList<Recipe> recipeList = APIManager.getRecipesFromAPI(SearchRecipeTextField.getText(),
								SearchedRecipesLabel);

						// Populate JTable with recipes
						DesignManager.PopulateJTableWithRecipeList(RecipeFinderTable, recipeList,
								FridgeTableColumnName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						LogFileManager.logError(e1.getMessage());
					}
				}
			}
		});
		SearchButton.setBounds(1127, 99, 89, 40);
		RecipeFinderPanel.add(SearchButton);

		SearchedRecipesLabel = new JLabel("JSON format: ");
		SearchedRecipesLabel.setForeground(Color.WHITE);
		SearchedRecipesLabel.setVerticalAlignment(SwingConstants.TOP);
		SearchedRecipesLabel.setBounds(43, 151, 1173, 59);
		RecipeFinderPanel.add(SearchedRecipesLabel);

		RecipeFinderLabel = new JLabel("RECIPE FINDER");
		RecipeFinderLabel.setForeground(Color.WHITE);
		RecipeFinderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeFinderLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		RecipeFinderLabel.setBounds(383, 11, 472, 59);
		RecipeFinderPanel.add(RecipeFinderLabel);

		RecipeFinderTable = new JTable();
		RecipeFinderTable.setBounds(43, 232, 1173, 457);
		RecipeFinderPanel.add(RecipeFinderTable);

		RecipeDetailsPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(RecipeDetailsPanel, "RecipeDetailsPanel");
		RecipeDetailsPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1241, 660);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		RecipeDetailsPanel.add(scrollPane);

		RecipeDetailsContentPanel = new JGradientPanel(lightPurple, lightPink, 2);
		RecipeDetailsContentPanel.setBounds(0, 0, 750, 800);
		RecipeDetailsContentPanel.setPreferredSize(new Dimension(750, 1100));
		scrollPane.setViewportView(RecipeDetailsContentPanel);
		RecipeDetailsContentPanel.setLayout(null);

		LabelRecipeName = new JLabel("Recipe Details");
		LabelRecipeName.setForeground(Color.WHITE);
		LabelRecipeName.setHorizontalAlignment(SwingConstants.CENTER);
		LabelRecipeName.setFont(new Font("Tahoma", Font.PLAIN, 23));
		LabelRecipeName.setBounds(417, 130, 381, 45);
		RecipeDetailsContentPanel.add(LabelRecipeName);

		RecipeDetailsTitle = new JLabel("Salmon Rolls");
		RecipeDetailsTitle.setForeground(Color.WHITE);
		RecipeDetailsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeDetailsTitle.setFont(new Font("Tahoma", Font.PLAIN, 37));
		RecipeDetailsTitle.setBounds(328, 43, 554, 87);
		RecipeDetailsContentPanel.add(RecipeDetailsTitle);

		DetailsLabel = new JLabel("");
		// DetailsLabel.setIcon(new ImageIcon("C:\\Users\\Downloads\\meal.png"));
		DetailsLabel.setBounds(384, 11, 225, 188);
		RecipeDetailsContentPanel.add(DetailsLabel);

		JSplitPane RecipeDescriptionSplitPane = new JSplitPane();
		RecipeDescriptionSplitPane.setBackground(lightPink);
		RecipeDescriptionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		RecipeDescriptionSplitPane.setEnabled(false);
		RecipeDescriptionSplitPane.setBounds(35, 210, 1157, 110);
		RecipeDetailsContentPanel.add(RecipeDescriptionSplitPane);

		RecipeDescriptionLabel = new JLabel("Recipe Description");
		RecipeDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RecipeDescriptionSplitPane.setLeftComponent(RecipeDescriptionLabel);

		RecipeDescriptionTextArea = new JTextArea();
		RecipeDescriptionTextArea.setText(
				"A light meal, which is quick, healthy, and easy to make! Try this if you enjoy seafood, or want to try something new. Ready in under an hour, and requires no cooking.");
		RecipeDescriptionTextArea.setLineWrap(true);
		RecipeDescriptionSplitPane.setRightComponent(RecipeDescriptionTextArea);

		JSplitPane ProcessDetailsSplitPane = new JSplitPane();
		ProcessDetailsSplitPane.setBackground(lightPink);
		ProcessDetailsSplitPane.setEnabled(false);
		ProcessDetailsSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		ProcessDetailsSplitPane.setBounds(35, 331, 561, 87);
		RecipeDetailsContentPanel.add(ProcessDetailsSplitPane);

		RecipeProcessDetailsLabel = new JLabel("Process Details");
		RecipeProcessDetailsLabel.setBackground(Color.WHITE);
		RecipeProcessDetailsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RecipeProcessDetailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ProcessDetailsSplitPane.setLeftComponent(RecipeProcessDetailsLabel);

		RecipeProcessDetailsTextArea = new JTextArea();
		ProcessDetailsSplitPane.setRightComponent(RecipeProcessDetailsTextArea);
		RecipeProcessDetailsTextArea.setLineWrap(true);
		RecipeProcessDetailsTextArea.setText("Time: 30 mins\r\nDifficulty: Easy\r\nServings: 2");

		JSplitPane NutritionSplitPane = new JSplitPane();
		NutritionSplitPane.setBackground(lightPink);
		NutritionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		NutritionSplitPane.setEnabled(false);
		NutritionSplitPane.setBounds(606, 331, 586, 87);
		RecipeDetailsContentPanel.add(NutritionSplitPane);

		NutritionLabel = new JLabel("Nutrition Details");
		NutritionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NutritionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		NutritionSplitPane.setLeftComponent(NutritionLabel);

		NutritionTextArea = new JTextArea();
		NutritionTextArea.setText("Energy: 500kcal\r\nVegetarian: Yes");
		NutritionTextArea.setLineWrap(true);
		NutritionSplitPane.setRightComponent(NutritionTextArea);

		JSplitPane IngredientsSplitPane = new JSplitPane();
		IngredientsSplitPane.setBackground(lightPink);
		IngredientsSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		IngredientsSplitPane.setEnabled(false);
		IngredientsSplitPane.setBounds(35, 429, 1157, 157);
		RecipeDetailsContentPanel.add(IngredientsSplitPane);

		RecipeIngredientsLabel = new JLabel("Ingredients");
		RecipeIngredientsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeIngredientsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IngredientsSplitPane.setLeftComponent(RecipeIngredientsLabel);

		RecipeIngredientsTextArea = new JTextArea();
		RecipeIngredientsTextArea.setText(
				"Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here ");
		RecipeIngredientsTextArea.setLineWrap(true);
		IngredientsSplitPane.setRightComponent(RecipeIngredientsTextArea);

		JSplitPane InstructionsSplitPane = new JSplitPane();
		InstructionsSplitPane.setBackground(lightPink);
		InstructionsSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		InstructionsSplitPane.setEnabled(false);
		InstructionsSplitPane.setBounds(35, 597, 1157, 210);
		RecipeDetailsContentPanel.add(InstructionsSplitPane);

		RecipeInstructionsLabel = new JLabel("Instructions");
		RecipeInstructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RecipeInstructionsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InstructionsSplitPane.setLeftComponent(RecipeInstructionsLabel);

		RecipeInstructionsTextArea = new JTextArea();
		RecipeInstructionsTextArea.setText(
				"Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here Recipes details go here ");
		RecipeInstructionsTextArea.setLineWrap(true);
		InstructionsSplitPane.setRightComponent(RecipeInstructionsTextArea);

		BackRecipeDetailsButton = new JButton("Back");
		BackRecipeDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BackRecipeDetailsButton.setBounds(10, 682, 89, 36);
		RecipeDetailsPanel.add(BackRecipeDetailsButton);
		BackRecipeDetailsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewCardLayout.show(ViewPanel, previousPanel);
			}
		});
		BackRecipeDetailsButton.setBackground(Color.WHITE);

		SettingsPanel = new JGradientPanel(lightPurple, lightPink, 2);
		ViewPanel.add(SettingsPanel, "SettingsViewPanel");
		SettingsPanel.setLayout(null);

		JLabel ChangePasswordLabel = new JLabel("Change Password: ");
		ChangePasswordLabel.setForeground(Color.WHITE);
		ChangePasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChangePasswordLabel.setBounds(97, 551, 214, 68);
		SettingsPanel.add(ChangePasswordLabel);

		ChangePasswordTextField = new JRoundedTextField(0);
		ChangePasswordTextField.setBounds(281, 561, 232, 57);
		SettingsPanel.add(ChangePasswordTextField);
		ChangePasswordTextField.setColumns(10);

		JLabel LogoutLabel = new JLabel("Log Out?");
		LogoutLabel.setForeground(Color.WHITE);
		LogoutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardLayoutPane, "LoggingPanel");
			}
		});
		LogoutLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LogoutLabel.setBounds(1083, 636, 168, 57);
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
						LogFileManager.logError(e1.getMessage());
					}
				}
			}
		});
		ChangeButton.setBounds(540, 557, 157, 58);
		SettingsPanel.add(ChangeButton);

		JButton SavePreferencesButton = new JRoundedGradientButton("Save Preferences", Color.WHITE, Color.WHITE);
		SavePreferencesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserPreferenceManager.maxCookTime = Integer.valueOf(CookTimeTextField.getText());
				UserPreferenceManager.maxCalories = Integer.valueOf(CalorieIntakeTextField.getText());
			}
		});
		SavePreferencesButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SavePreferencesButton.setBackground(Color.WHITE);

		SavePreferencesButton.setBounds(97, 445, 611, 48);
		SettingsPanel.add(SavePreferencesButton);

		JLabel SettingsLabel = new JLabel("MY SETTINGS");
		SettingsLabel.setForeground(Color.WHITE);
		SettingsLabel.setBackground(Color.WHITE);
		SettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SettingsLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		SettingsLabel.setBounds(442, 11, 345, 77);
		SettingsPanel.add(SettingsLabel);

		JLabel HelpLabel = new JLabel("Need Help? -  email us - mealplan@management.co.uk");
		HelpLabel.setForeground(Color.WHITE);
		HelpLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		HelpLabel.setBounds(97, 630, 599, 68);
		SettingsPanel.add(HelpLabel);

		JLabel MyPreferencesLabel = new JLabel("My Preferences:");
		MyPreferencesLabel.setForeground(Color.WHITE);
		MyPreferencesLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		MyPreferencesLabel.setBounds(97, 74, 157, 48);
		SettingsPanel.add(MyPreferencesLabel);

		JLabel CalorieIntakeLabel = new JLabel("Calorie Intake:");
		CalorieIntakeLabel.setForeground(Color.WHITE);
		CalorieIntakeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CalorieIntakeLabel.setBounds(97, 133, 157, 48);
		SettingsPanel.add(CalorieIntakeLabel);

		JLabel DietLabel = new JLabel("Diet:");
		DietLabel.setForeground(Color.WHITE);
		DietLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DietLabel.setBounds(97, 377, 103, 48);
		SettingsPanel.add(DietLabel);

		JLabel DifficultyLabel = new JLabel("Difficulty:");
		DifficultyLabel.setForeground(Color.WHITE);
		DifficultyLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DifficultyLabel.setBounds(97, 304, 103, 48);
		SettingsPanel.add(DifficultyLabel);

		// Radio buttons for customisation

		JRadioButton EasyRadioButton = new JRadioButton("  Easy");
		EasyRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		EasyRadioButton.setForeground(Color.BLACK);
		EasyRadioButton.setBounds(206, 314, 125, 31);
		EasyRadioButton.setSelected(true);
		SettingsPanel.add(EasyRadioButton);

		JRadioButton MediumRadioButton = new JRadioButton("  Medium");
		MediumRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		MediumRadioButton.setBounds(370, 314, 125, 31);
		SettingsPanel.add(MediumRadioButton);

		JRadioButton DifficultRadioButton = new JRadioButton("  Difficult");
		DifficultRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		DifficultRadioButton.setBounds(527, 314, 170, 31);
		SettingsPanel.add(DifficultRadioButton);

		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(EasyRadioButton);
		difficultyGroup.add(MediumRadioButton);
		difficultyGroup.add(DifficultRadioButton);

		EasyRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserPreferenceManager.difficulty = "easy";
			}
		});
		MediumRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserPreferenceManager.difficulty = "medium";
			}
		});
		DifficultRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserPreferenceManager.difficulty = "difficult";
			}
		});

		JRadioButton VegRadioButton = new JRadioButton("  Veg");
		VegRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		VegRadioButton.setBounds(206, 381, 125, 42);
		SettingsPanel.add(VegRadioButton);

		JRadioButton VeganRadioButton = new JRadioButton("  Vegan");
		VeganRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		VeganRadioButton.setBounds(370, 381, 125, 42);
		SettingsPanel.add(VeganRadioButton);

		JRadioButton NoPreferencesRadioButton = new JRadioButton("  No Preferences");
		NoPreferencesRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		NoPreferencesRadioButton.setBounds(540, 381, 168, 42);
		SettingsPanel.add(NoPreferencesRadioButton);

		ButtonGroup dietGroup = new ButtonGroup();
		dietGroup.add(VegRadioButton);
		dietGroup.add(VeganRadioButton);
		dietGroup.add(NoPreferencesRadioButton);

		CalorieIntakeTextField = new JTextField();
		CalorieIntakeTextField.setColumns(10);
		CalorieIntakeTextField.setBounds(245, 139, 182, 42);
		SettingsPanel.add(CalorieIntakeTextField);

		JLabel KCalLabel = new JLabel("kcal");
		KCalLabel.setForeground(Color.WHITE);
		KCalLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		KCalLabel.setBounds(442, 137, 76, 44);
		SettingsPanel.add(KCalLabel);

		JLabel CookTimeLabel = new JLabel("Cook time max:");
		CookTimeLabel.setForeground(Color.WHITE);
		CookTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CookTimeLabel.setBounds(92, 213, 157, 48);
		SettingsPanel.add(CookTimeLabel);

		CookTimeTextField = new JTextField();
		CookTimeTextField.setColumns(10);
		CookTimeTextField.setBounds(240, 219, 182, 42);
		SettingsPanel.add(CookTimeTextField);

		JLabel CookTimeMinutesLabel = new JLabel("minutes");
		CookTimeMinutesLabel.setForeground(Color.WHITE);
		CookTimeMinutesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CookTimeMinutesLabel.setBounds(437, 217, 76, 44);
		SettingsPanel.add(CookTimeMinutesLabel);
	}

}
