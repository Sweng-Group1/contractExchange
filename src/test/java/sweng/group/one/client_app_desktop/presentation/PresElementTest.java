package sweng.group.one.client_app_desktop.presentation;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.JPanel;

import org.junit.Test;

public class PresElementTest extends PresElement {
	
	static final long ELEMENT_DURATION_MS = 1000;
	static final long LEEWAY_MS = 5*ELEMENT_DURATION_MS/100; //5% leeway time
	static final int PRES_ELEMENT_TEST_SIZE = 100;

	
	/*
	 * Constructs a concrete square PresElement
	 */
	public PresElementTest() {
		super(new Point(0,0), PRES_ELEMENT_TEST_SIZE, PRES_ELEMENT_TEST_SIZE, (float)(ELEMENT_DURATION_MS/1000), new Slide(10,10));
		this.component = new JPanel();
	}

	@Test
	public void displayElementTest() {
		PresElement p = new PresElementTest();
		p.displayElement();
		assertEquals(true, p.component.isVisible());
		
		long end = System.currentTimeMillis() + ELEMENT_DURATION_MS;
		
		while(System.currentTimeMillis() < end + LEEWAY_MS) {
			//wait for the duration to pass
		}
		
		assertEquals(false, p.component.isVisible());
	}

}
