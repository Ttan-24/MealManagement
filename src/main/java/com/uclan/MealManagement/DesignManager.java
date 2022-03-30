package com.uclan.MealManagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DesignManager {
	public static void populateTableWithResultSet(JTable table, ResultSet rs, ArrayList<String> columnName)
			throws SQLException {

		// Resultset metadata
		ResultSetMetaData metaData = rs.getMetaData();

		// Table columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(columnName.get(column));
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
		Color lightPink = Color.decode("#f4d8e4");
		// Set table model
		table.setModel(model);
		JTableHeader Header = table.getTableHeader();
		Header.setBackground(lightPink);
		Header.setForeground(black);
		Header.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	}

	public static void populateTableWithResultSetWithCheckBox(JTable table, ResultSet rs, String customerId)
			throws Exception {

		// Resultset metadata
		ResultSetMetaData metaData = rs.getMetaData();

		ArrayList<String> columnName = new ArrayList<String>();
		columnName.add("");
		columnName.add("Sr no.");
		columnName.add("Recipe Name");
		columnName.add("Meal Time");
		columnName.add("Favourite");

		// Table columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(columnName.get(column));
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

		ArrayList<String> favouriteList = SQLManager.getFavouriteRecipes(customerId);
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
		Color lightPink = Color.decode("#f4d8e4");
		// Set table model
		table.setModel(model);
		JTableHeader Header = table.getTableHeader();
		Header.setBackground(lightPink);
		Header.setForeground(black);
		Header.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	}

	public static void PopulateJTableWithRecipeList(JTable table, ArrayList<Recipe> recipeList,
			ArrayList<String> columnNames) throws SQLException {
		// Table columns
		Vector<String> columnNamesVector = new Vector<String>();
		int columnCount = columnNames.size();
		for (int column = 0; column < 2; column++) {
			columnNamesVector.add(columnNames.get(column));
		}

		// Table data
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (int i = 0; i < recipeList.size(); i++) {
			Vector<Object> vector = new Vector<Object>();
			vector.add(recipeList.get(i).mName);
			vector.add(recipeList.get(i).mMealTime);
			data.add(vector);
		}

		// Create model
		DefaultTableModel model = new DefaultTableModel(data, columnNamesVector);

		Color black = Color.getHSBColor(0.0f, 0.0f, 0.0f);
		Color lightPink = Color.decode("#f4d8e4");
		// Set table model
		table.setModel(model);
		JTableHeader Header = table.getTableHeader();
		Header.setBackground(lightPink);
		Header.setForeground(black);
		Header.setFont(new Font("Segoe UI", Font.PLAIN, 20));

	}

	public static void deleteAllRows(final DefaultTableModel model) {
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

}

// a custom text field with rounded corners
class JRoundedTextField extends JTextField {
	private Shape shape;

	public JRoundedTextField(int size) {
		super(size);
		setOpaque(false);

	}

	protected void paintComponent(Graphics graphics) {
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
		super.paintComponent(graphics);
	}

	protected void paintBorder(Graphics graphics) {
		graphics.setColor(getForeground());
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}
}

// a custom password text field with rounded corners
class JRoundedPasswordField extends JPasswordField {
	private Shape shape;

	public JRoundedPasswordField(int size) {
		super(size);
		setOpaque(false);

	}

	protected void paintComponent(Graphics graphics) {
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
		super.paintComponent(graphics);
	}

	protected void paintBorder(Graphics graphics) {
		graphics.setColor(getForeground());
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// JBUTTONMANAGER
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////// ROUNDED
////////////////////////////////////////////////////////////// BUTTON/////////////////////////////////////////////////////////////////////
// a custom button with rounded corners.
class JRoundedButton extends JButton {
	private static final long serialVersionUID = -7717051774668626390L;

	public JRoundedButton(String stringText) {
		super(stringText);
		setContentAreaFilled(false);
		setRolloverEnabled(true);
		setFocusable(false);
	}

	protected void paintComponent(Graphics graphics) {
		if (getModel().isArmed()) {
			graphics.setColor(Color.decode("#eeeeee").darker());
		} else {
			graphics.setColor(Color.decode("#eeeeee"));
		}
		// fills colour in the button
		graphics.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
		super.paintComponent(graphics);
	}

	protected void paintBorder(Graphics graphics) {
		if (getModel().isRollover()) {
			graphics.setColor(Color.red);
		} else {
			graphics.setColor(Color.decode("#595959").brighter());
		}
		// draws a border for the rounded button
		graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
	}

	Shape shape;

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
		}
		return shape.contains(x, y);
	}
}

////////////////////////////////////////////////////////////////////// GRADIENT
////////////////////////////////////////////////////////////////////// BUTTON
////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////////

class JGradientButton extends JButton {
	private static final long serialVersionUID = 1L;
	private Color colorTop;
	private Color colorBottom;
	private Paint colorGradient;
	private Point[] points = new Point[2];

	public JGradientButton(Color colorTop, Color colorBottom) {
		this("", colorTop, colorBottom);
	}

	public JGradientButton(String stringText, Color colorTop, Color colorBottom) {
		this(stringText, null, colorTop, colorBottom);
	}

	public JGradientButton(String stringText, Icon icon, Color colorTop, Color colorBottom) {
		super(stringText, icon);

		setContentAreaFilled(false);
		setFocusPainted(false);

		this.colorTop = colorTop;
		this.colorBottom = colorBottom;

		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				invalidate();
			}
		});
	}

	public void invalidate() {
		super.invalidate();

		points[0] = new Point(0, 0);
		points[1] = new Point(0, getHeight());

		if (getModel().isPressed()) {
			colorGradient = new GradientPaint(points[0], colorBottom, points[1], colorTop);
		} else {
			colorGradient = new GradientPaint(points[0], colorTop, points[1], colorBottom);
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics.create();

		graphics2D.setPaint(colorGradient);
		graphics2D.fillRect(0, 0, getWidth(), getHeight());
		graphics2D.dispose();

		super.paintComponent(graphics);
	}
}

//////////////////////////////////////////////////////////////////////////// ROUNDED
//////////////////////////////////////////////////////////////////////////// GRADIENT
//////////////////////////////////////////////////////////////////////////// BUTTON/////////////////////////////////////////////////////

class JRoundedGradientButton extends JButton {
	private static final long serialVersionUID = 1L;
	private Color colorTop;
	private Color colorBottom;
	private Paint colorGradient;
	private Point[] points = new Point[2];
	private Shape shape;

	public JRoundedGradientButton(Color colorTop, Color colorBottom) {
		this("", colorTop, colorBottom);
	}

	public JRoundedGradientButton(String stringText, Color colorTop, Color colorBottom) {
		this(stringText, null, colorTop, colorBottom);
		setContentAreaFilled(false);
		setRolloverEnabled(true);
		setFocusable(false);
	}

	public JRoundedGradientButton(String stringText, Icon icon, Color colorTop, Color colorBottom) {
		super(stringText, icon);

		setContentAreaFilled(false);
		setFocusPainted(false);

		this.colorTop = colorTop;
		this.colorBottom = colorBottom;

		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				invalidate();
			}
		});
	}

	public void invalidate() {
		super.invalidate();

		points[0] = new Point(0, 0);
		points[1] = new Point(0, getHeight());

		if (getModel().isPressed()) {
			colorGradient = new GradientPaint(points[0], colorBottom, points[1], colorTop);
		} else {
			colorGradient = new GradientPaint(points[0], colorTop, points[1], colorBottom);
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics.create();

		graphics2D.setPaint(colorGradient);

		graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
		graphics2D.dispose();

		super.paintComponent(graphics);
	}

	protected void paintBorder(Graphics graphics) {
		if (getModel().isArmed()) {
			graphics.setColor(Color.decode("#eeeeee").darker());
		} else {
			graphics.setColor(Color.decode("#eeeeee"));
		}
		graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// JPANELMANAGER
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class JGradientPanel extends JPanel {

	public int vertical = 0;
	public int horizontal = 1;
	public int diagonalDown = 2;
	public int diagonalUp = 3;

	private Color color1, color2, color3;
	private int direction;

	public JGradientPanel() {
		super();
		color1 = Color.black;
		color2 = Color.white;
		color3 = Color.white;
	}

	public JGradientPanel(Color color1, Color color2) {
		super();
		this.color1 = color1;
		this.color2 = color2;
	}

	public JGradientPanel(Color color1, Color color2, Color color3, int direction) {
		super();
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.direction = direction;
	}

	public JGradientPanel(Color color1, Color color2, int direction) {
		super();
		this.color1 = color1;
		this.color2 = color2;
		this.direction = direction;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		GradientPaint gradientPaint;

		if (direction == horizontal) {
			float height = getHeight() / 2;
			float width = getWidth();
			gradientPaint = new GradientPaint(0, height, color1, width, height, color2);
		} else if (direction == diagonalDown) {
			float height = getHeight();
			float width = getWidth();
			gradientPaint = new GradientPaint(0, height, color1, width, 0, color2);
		} else if (direction == diagonalUp) {
			float height = getHeight();
			float width = getWidth();
			gradientPaint = new GradientPaint(0, 0, color1, width, height, color2);
		} else {
			float height = getHeight();
			gradientPaint = new GradientPaint(0, 0, color1, 0, height, color2);
		}
		graphics2d.setPaint(gradientPaint);
		graphics2d.fillRect(0, 0, getWidth(), getHeight());
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}

class JRoundedGradientPanel extends JPanel {

	public int vertical = 0;
	public int horizontal = 1;
	public int diagonalDown = 2;
	public int diagonalUp = 3;

	private Color color1, color2;
	private int direction;
	private Shape shape;
	private int size;

	public JRoundedGradientPanel() {
		super();
		color1 = Color.black;
		color2 = Color.white;
	}

	public JRoundedGradientPanel(Color color1, Color color2) {
		super();
		this.color1 = color1;
		this.color2 = color2;
	}

	public JRoundedGradientPanel(Color color1, Color color2, int direction) {
		super();
		this.color1 = color1;
		this.color2 = color2;
		this.direction = direction;
	}

	public JRoundedGradientPanel(Color color1, Color color2, int direction, int size) {
		super();
		this.color1 = color1;
		this.color2 = color2;
		this.direction = direction;
		this.size = size;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		GradientPaint gradientPaint;

		if (direction == horizontal) {
			float height = getHeight() / 2;
			float width = getWidth();
			gradientPaint = new GradientPaint(0, height, color1, width, height, color2);
		} else if (direction == diagonalDown) {
			float height = getHeight();
			float width = getWidth();
			gradientPaint = new GradientPaint(0, height, color1, width, 0, color2);
		} else if (direction == diagonalUp) {
			float height = getHeight();
			float width = getWidth();
			gradientPaint = new GradientPaint(0, 0, color1, width, height, color2);
		} else {
			float height = getHeight();
			gradientPaint = new GradientPaint(0, 0, color1, 0, height, color2);
		}
		graphics2d.setPaint(gradientPaint);
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
	}

	protected void paintBorder(Graphics graphics) {
		graphics.setColor(getForeground());
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
