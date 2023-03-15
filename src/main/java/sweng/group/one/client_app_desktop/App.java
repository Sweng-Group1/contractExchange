package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.text.TextElement;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;

/*
 * This is an example of how a "PresElement" is added to a Slide
 * 
 */
public class App 
{
    public static void main(String[] args)
    {
    	//Set up JFrame
    	JFrame frame = new JFrame();
    	frame.setSize(800, 400);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	int slideX = 800;
    	int slideY = 400;
    	
    	//create slide and add elements to it
    	Slide slide = new Slide(slideX, slideY);
    	ArrayList<Slide> slides = new ArrayList<>();
    	slides.add(slide);
    	
    	slide.add(new DemoElement(new Point(slideX/2-1, slideY/2-1), slideX/2, slideY/2, 0, slide));
    	TextElement textElement = new TextElement("Quo usque tandem abutere, Catilina, patientia nostra?", "Copperplate", 22, Color.ORANGE, 0,
    			                               new Point(0, 10), 400, 100, slide);
    	slide.add(textElement);
    	
    	//create presentation and add to frame
    	Presentation pres = new Presentation(slides);
    	
    	frame.add(pres, BorderLayout.CENTER);
    	frame.validate();
    	
    	/*	
    	 * Hack to get it to properly draw on launch:
    	 * The issue seems to be with the JFrame taking time to open and
    	 * the internal items render with a size of 0x0 as the frame has
    	 * no dimensions
    	 * 
    	 * This shouldn't be a problem when not drawing on start
    	 */
    	frame.setSize(800, 401);
    }
}
