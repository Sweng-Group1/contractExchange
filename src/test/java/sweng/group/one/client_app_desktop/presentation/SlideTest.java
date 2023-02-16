package sweng.group.one.client_app_desktop.presentation;

import static org.junit.Assert.*;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;

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

}
