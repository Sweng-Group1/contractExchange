package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.presentation.DemoElement;

/*
 * This is an example of how a "PresElement" is added to a Slide
 * 
 * 
 * 
 * Note: Due to the nature of GridBagLayout, the "Slide" component may not
 * reach the sides of the JFrame, however the elements you add should reach
 * the edge of the Slide element. The aspect ratio is also not fixed.
 * 
 * These problems are mitigated by displaying the "Slide" in another container;
 * e.g our Presentation element (which you have not been given).
 * 
 */
public class App 
{
    public static void main(String[] args)
    {
    	//Set up JFrame
    	JFrame frame = new JFrame("Test Window");
    	frame.setSize(800, 400);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	//create slide and add to frame
    	Slide slide = new Slide(80, 40);
    	frame.add(slide, BorderLayout.CENTER);
    	
    	//create a DemoElement and add to slide
    	DemoElement demo = new DemoElement(new Point(40, 0), 40, 20, 0, slide);
    	slide.add(demo);
    	
    	//Frame needs to be validated to display anything
    	frame.validate();
    }
}
