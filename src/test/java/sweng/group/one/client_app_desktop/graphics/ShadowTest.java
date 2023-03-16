package sweng.group.one.client_app_desktop.graphics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.awt.Color;

import org.junit.Test;

public class ShadowTest {
	private static final Color COLOUR = Color.PINK;
	private static final int DX = 5;
	private static final int DY = 55;
	private static final int RADIUS = 69;
	
	@Test
	public void gettersTest() {
		Shadow classUnderTest = new Shadow(COLOUR, DX, DY, RADIUS);
		
		assertEquals(COLOUR, classUnderTest.getShadowColour());
		assertEquals(DX, classUnderTest.getShadowDx(), 0.001);
		assertEquals(DY, classUnderTest.getShadowDy(), 0.001);
		assertEquals(RADIUS, classUnderTest.getShadowBlurRadius());
	}
}
