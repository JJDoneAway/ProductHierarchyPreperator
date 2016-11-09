package de.hoehne.myfirsttest.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class DragLine extends JApplet {

	private static final long serialVersionUID = 1L;

	final static Color bg = Color.white;
	final static Color fg = Color.black;

	private static Point a = new Point(50, 50);
	private static Point b = new Point(500, 200);
	
	private static boolean a_cateched = false;
	private static boolean b_cateched = false;

	Dimension totalSize;

	public static boolean isInCatchBox(Point mouse, Point target) {

		if ((mouse.getX() - 10 <= target.getX() && mouse.getX() + 10 >= target.getX())
				&& (mouse.getY() - 10 <= target.getY() && mouse.getY() + 10 >= target.getY())) {
			return true;
		}

		return false;
	}

	public void init() {

		this.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(!a_cateched && isInCatchBox(e.getPoint(), a)){
					a_cateched = true;
				}
				
				a = e.getPoint();
				e.getComponent().repaint();
				
				
				System.out.println("hello dragged a: " + isInCatchBox(e.getPoint(), a));
				System.out.println("hello dragged b: " + isInCatchBox(e.getPoint(), b));
			}

		});
		setBackground(bg);
		setForeground(fg);
	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		// draw Line2D.Double
		g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.draw(new Line2D.Double(a, b));

	}

	public static void main(String s[]) {
		JFrame f = new JFrame("ShapesDemo2D");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		JApplet applet = new DragLine();
		f.getContentPane().add("Center", applet);

		applet.init();

		f.pack();
		f.setSize(new Dimension(600, 600));
		f.setVisible(true);
	}

}
