package sweng.group.one.client_app_desktop.presentation;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

//import org.assertj.core.util.Arrays;
import org.junit.Test;

public class SlideTest {
	
	private final static int SLIDE_SIZE = 400;

	@Test
	public void addTest() {
		Slide largeSlide = new Slide(SLIDE_SIZE, SLIDE_SIZE);
		
		//add a valid component
		PresElementTest validElement = new PresElementTest();
		largeSlide.add(validElement);
		assertEquals(true, largeSlide.getElements().contains(validElement));
		ArrayList<Component> slideJComponents = new ArrayList<>(Arrays.asList(largeSlide.getComponents()));
		assertEquals(true, slideJComponents.contains(validElement.component));
		
		//add an invalid component
		Slide smallSlide = new Slide(10, 10);
		PresElementTest invalidElement = new PresElementTest();
		assertThrows(IllegalArgumentException.class,
				()->{
					smallSlide.add(invalidElement);
				});
	}
	
	@Test
	public void displaySlideTest() {
		Slide slide = new Slide(SLIDE_SIZE, SLIDE_SIZE);
		
		ArrayList<PresElementTest> elements = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			PresElementTest e = new PresElementTest();
			elements.add(e);
			slide.add(e);
		}
		
		//all elements should be visable on the slide
		slide.displaySlide();
		for (PresElement e: slide.getElements()) {
			assertEquals(true, e.component.isVisible());
		}
		
		//after a given time, all the elements should not be visable
		long end = System.currentTimeMillis() + PresElementTest.ELEMENT_DURATION_MS + PresElementTest.LEEWAY_MS;
		while (System.currentTimeMillis() < end) {
			//wait for timer to end
		}
		for (PresElement e: slide.getElements()) {
			assertEquals(false, e.component.isVisible());
		}
		
	}
	
	@Test
	public void preferredLayoutSizeTest() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(SLIDE_SIZE*3, SLIDE_SIZE*2);
		frame.setVisible(true);
		
		Slide slide = new Slide(SLIDE_SIZE, SLIDE_SIZE);
		frame.add(slide);
		Dimension d = slide.preferredLayoutSize(frame);
		
		//test if dimensions get as large as frame height
		assertEquals(true, d.height == frame.getHeight());
		assertEquals(false, d.width == frame.getWidth());
		
		//change aspect ratio of the panel and test again for when the frame is taller than wide
		frame.setSize(SLIDE_SIZE, SLIDE_SIZE*2);
		d = slide.preferredLayoutSize(frame);
		assertEquals(false, d.height == frame.getHeight());
		assertEquals(true, d.width == frame.getWidth());
		
		//test if aspect ratio is 1:1
		assertEquals(true, d.height == d.width);
		frame.dispose();
	}

	@Test
	public void layoutContainerTest() {
		//create frame to add to
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(SLIDE_SIZE, SLIDE_SIZE);
		frame.setVisible(true);
		
		Slide slide = new Slide(SLIDE_SIZE, SLIDE_SIZE);
		slide.setPreferredSize(new Dimension(SLIDE_SIZE, SLIDE_SIZE));
		
		PresElement e = new DemoElement(new Point(0, 0), SLIDE_SIZE/2, SLIDE_SIZE/2, 0, slide);
		slide.add(e);
		
		frame.add(slide);
		frame.validate();
		
		slide.layoutContainer(frame);
		
		//component is where it is meant to be
		assertEquals(e.component, slide.getComponentAt(1, 1));
		
		//component is not where it is not meant to be
		assertEquals(slide, slide.getComponentAt(slide.getWidth()-1, slide.getHeight()-1));
		frame.dispose();
	}
	
}
