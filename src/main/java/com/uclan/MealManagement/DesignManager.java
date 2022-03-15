package com.uclan.MealManagement;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DesignManager {
	// a custom text field with rounded corners
	class JRoundedTextField extends JTextField {
		private Shape shape;

		public JRoundedTextField(int size) {
			super(size);
			setOpaque(false);

		}

		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
			super.paintComponent(g);
		}

		protected void paintBorder(Graphics g) {
			g.setColor(getForeground());
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

		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
			super.paintComponent(g);
		}

		protected void paintBorder(Graphics g) {
			g.setColor(getForeground());
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

		public JRoundedButton(String text) {
			super(text);
			setContentAreaFilled(false);
			setRolloverEnabled(true);
			setFocusable(false);
		}

		protected void paintComponent(Graphics g) {
			if (getModel().isArmed()) {
				g.setColor(Color.decode("#eeeeee").darker());
			} else {
				g.setColor(Color.decode("#eeeeee"));
			}
			// fills colour in the button
			g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
			super.paintComponent(g);
		}

		protected void paintBorder(Graphics g) {
			if (getModel().isRollover()) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.decode("#595959").brighter());
			}
			// draws a border for the rounded button
			g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
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
		private Color stopTop;
		private Color stopBottom;
		private Paint colorGradient;
		private Point[] stopPoints = new Point[2];

		public JGradientButton(Color stopTop, Color stopBottom) {
			this("", stopTop, stopBottom);
		}

		public JGradientButton(String text, Color stopTop, Color stopBottom) {
			this(text, null, stopTop, stopBottom);
		}

		public JGradientButton(String text, Icon icon, Color stopTop, Color stopBottom) {
			super(text, icon);

			setContentAreaFilled(false);
			setFocusPainted(false);

			this.stopTop = stopTop;
			this.stopBottom = stopBottom;

			addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					invalidate();
				}
			});
		}

		// public GradientButton(String text, Shape shape, Color stopTop, Color
		// stopBottom) {

		// }

		public void invalidate() {
			super.invalidate();

			stopPoints[0] = new Point(0, 0);
			stopPoints[1] = new Point(0, getHeight());

			if (getModel().isPressed()) {
				colorGradient = new GradientPaint(stopPoints[0], stopBottom, stopPoints[1], stopTop);
			} else {
				colorGradient = new GradientPaint(stopPoints[0], stopTop, stopPoints[1], stopBottom);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			g2.setPaint(colorGradient);
			// g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,50,50);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.dispose();

			super.paintComponent(g);
		}
	}

	//////////////////////////////////////////////////////////////////////////// ROUNDED
	//////////////////////////////////////////////////////////////////////////// GRADIENT
	//////////////////////////////////////////////////////////////////////////// BUTTON/////////////////////////////////////////////////////

	class JRoundedGradientButton extends JButton {
		private static final long serialVersionUID = 1L;
		private Color stopTop;
		private Color stopBottom;
		private Paint colorGradient;
		private Point[] stopPoints = new Point[2];
		private Shape shape;

		public JRoundedGradientButton(Color stopTop, Color stopBottom) {
			this("", stopTop, stopBottom);
		}

		public JRoundedGradientButton(String text, Color stopTop, Color stopBottom) {
			this(text, null, stopTop, stopBottom);
			setContentAreaFilled(false);
			setRolloverEnabled(true);
			setFocusable(false);
		}

		public JRoundedGradientButton(String text, Icon icon, Color stopTop, Color stopBottom) {
			super(text, icon);

			setContentAreaFilled(false);
			setFocusPainted(false);

			this.stopTop = stopTop;
			this.stopBottom = stopBottom;

			addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					invalidate();
				}
			});
		}

		// public GradientButton(String text, Shape shape, Color stopTop, Color
		// stopBottom) {

		// }

		public void invalidate() {
			super.invalidate();

			stopPoints[0] = new Point(0, 0);
			stopPoints[1] = new Point(0, getHeight());

			if (getModel().isPressed()) {
				colorGradient = new GradientPaint(stopPoints[0], stopBottom, stopPoints[1], stopTop);
			} else {
				colorGradient = new GradientPaint(stopPoints[0], stopTop, stopPoints[1], stopBottom);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			g2.setPaint(colorGradient);
			// g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,50,50);

			g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
			g2.dispose();

			super.paintComponent(g);
		}

		protected void paintBorder(Graphics g) {
			// g.setColor(getForeground());
			if (getModel().isArmed()) {
				g.setColor(Color.decode("#eeeeee").darker());
			} else {
				g.setColor(Color.decode("#eeeeee"));
			}
			g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
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

		public int VERTICAL = 0;
		public int HORIZONTAL = 1;
		public int DIAGONAL_DOWN = 2;
		public int DIAGONAL_UP = 3;

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

			if (direction == HORIZONTAL)
				gradientPaint = new GradientPaint(0, getHeight() / 2, color1, getWidth(), getHeight() / 2, color2);

			else if (direction == DIAGONAL_DOWN)
				gradientPaint = new GradientPaint(0, getHeight(), color1, getWidth(), 0, color2);

			else if (direction == DIAGONAL_UP)
				gradientPaint = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);

			else
				gradientPaint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

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

		public int VERTICAL = 0;
		public int HORIZONTAL = 1;
		public int DIAGONAL_DOWN = 2;
		public int DIAGONAL_UP = 3;

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

			if (direction == HORIZONTAL)
				gradientPaint = new GradientPaint(0, getHeight() / 2, color1, getWidth(), getHeight() / 2, color2);

			else if (direction == DIAGONAL_DOWN)
				gradientPaint = new GradientPaint(0, getHeight(), color1, getWidth(), 0, color2);

			else if (direction == DIAGONAL_UP)
				gradientPaint = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);

			else
				gradientPaint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

			graphics2d.setPaint(gradientPaint);
			graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
		}

		protected void paintBorder(Graphics g) {
			g.setColor(getForeground());
			// g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 50, 50);
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
}
