package de.hoehne.myfirsttest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

public class GetMyGUISize extends JPanel {

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawString(this.getSize().toString(), 100, 100);
		
		
		int width = (int) this.getSize().getWidth();
		int height = (int) this.getSize().getHeight();
		
		
		g2.drawLine(0, 0, width/2, height/2);
		

	}

	public static void main(String s[]) {
		JFrame f = new JFrame(GetMyGUISize.class.getName());
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
							}
					
		});
		
		
		
		
		GetMyGUISize canvas = new GetMyGUISize();
		f.getContentPane().add("Center", canvas);
		
		canvas.addComponentListener(new ComponentAdapter() {
		
			@Override
			public void componentResized(ComponentEvent e) {
				
				canvas.repaint();
				
				System.out.println(e.getComponent().getSize());
				
			}
		
		});
		

		
		f.pack();
		f.setSize(new Dimension(1000, 600));
		f.setVisible(true);
	}

}
