package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.graphics.Border;
import sweng.group.one.client_app_desktop.graphics.Line;
import sweng.group.one.client_app_desktop.graphics.Rectangle;
import sweng.group.one.client_app_desktop.graphics.Shadow;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;

/*
 * This is an example of how a "PresElement" is added to a Slide
 * 
 */
public class App {
	public static void main(String[] args) {
		// Set up JFrame
		JFrame frame = new JFrame();
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int slideX = 800;
		int slideY = 400;

		// create slide and add elements to it
		Slide slide = new Slide(slideX, slideY);
		ArrayList<Slide> slides = new ArrayList<>();
		slides.add(slide);

		// slide.add(new DemoElement(new Point(slideX/2-1, slideY/2-1), slideX/2,
		// slideY/2, 0, slide));
		
//		Line line = new Line(new Point(0, 0), slideX, slideY, 0, slide, 2, new Point(slideX / 2 + 10, 0), new Point(slideX / 2 + 10, slideY), Color.RED);
//		slide.add(line);
//		
//		Line line2 = new Line(new Point(0, 0), slideX, slideY, 0, slide, 2, new Point(10, 0), new Point(10, slideY), Color.RED);
//		slide.add(line2);
//		
//		Line line3 = new Line(new Point(0, 0), slideX, slideY, 0, slide, 2, new Point(0, 10), new Point(slideX, 10), Color.RED);
//		slide.add(line3);
//		
//		Line line4 = new Line(new Point(0, 0), slideX, slideY, 0, slide, 2, new Point(0, slideY / 2 + 10), new Point(slideX, slideY / 2 + 10), Color.RED);
//		slide.add(line4);
//		
//		Line line5 = new Line(new Point(0, 0), slideX, slideY, 0, slide, 2, new Point(0, slideY / 2 + 10), new Point(slideX, slideY / 2 + 10), Color.RED);
//		slide.add(line5);

//		Border border = new Border(Color.PINK, 10);
//		Shadow shadow = new Shadow(Color.ORANGE, 0, 0, 25);
//		Rectangle rectangle = new Rectangle(new Point(0, 0), slideX / 2, slideY / 2, 0, slide, Color.BLUE, border, shadow);
//		slide.add(rectangle);
//		
		Border border2 = new Border(Color.PINK, 5);
		Shadow shadow2 = new Shadow(Color.ORANGE, 0, -5, 25);
		Rectangle rectangle2 = new Rectangle(new Point(slideX / 4, slideY / 4), 200, 200, 0, slide, Color.BLUE, border2, shadow2);
		slide.add(rectangle2);

//		slide.setBackground(Color.BLACK);

		// create presentation and add to frame
		Presentation pres = new Presentation(slides);

		frame.add(pres, BorderLayout.CENTER);
		frame.validate();

		/*
		 * Hack to get it to properly draw on launch: The issue seems to be with the
		 * JFrame taking time to open and the internal items render with a size of 0x0
		 * as the frame has no dimensions
		 * 
		 * This shouldn't be a problem when not drawing on start
		 */
		frame.setSize(800, 401);
	}
}
