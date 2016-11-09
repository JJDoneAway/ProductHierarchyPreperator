package de.hoehne.myfirsttest.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;

import javax.swing.JApplet;
import javax.swing.JFrame;
 
/* 
/**
 * https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html
 * 
 * 
 * @author johannes
 *
 * This is like the FontDemo applet in volume 1, except that it 
 * uses the Java 2D APIs to define and render the graphics and text.
 */
 
public class MyFirstGUI extends JApplet {
 
    final static Color bg = Color.white;
    final static Color fg = Color.black;
    final static Color red = Color.red;
    final static Color white = Color.white;
  
    Dimension totalSize;
 
    public void init() {
        //Initialize drawing colors
        setBackground(bg);
        setForeground(fg);
    }
 
 
 
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Color fg3D = Color.lightGray;
        g2.setPaint(fg3D);
 
        // draw Line2D.Double
        g2.draw(new Line2D.Double(0,1,100,100));
        
        g2.drawLine(100, 0, 0, 100);
        
        for (int i = 0; i < 100; i++) {
			g2.drawLine(i, (int)Math.sin(i), i, (int)Math.sin(i));
		}
        
        
 
 

    }
 
    public static void main(String s[]) {
        JFrame f = new JFrame("ShapesDemo2D");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
            
        
        JApplet applet = new MyFirstGUI();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(550,100));
        f.setVisible(true);
    }
 
}
