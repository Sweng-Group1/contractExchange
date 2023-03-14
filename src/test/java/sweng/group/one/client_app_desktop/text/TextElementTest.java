package sweng.group.one.client_app_desktop.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.junit.Test;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class TextElementTest {
	private static final int SLIDE_X = 800;
	private static final int SLIDE_Y = 400;
	private static final int FONT_SIZE = 22;
	private static final String CONTENT = "Test Content";

	@Test
	public void canCreateText() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);
		
		Font[] t = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String fontName = t[0].getFontName();
		
		// Create element
		TextElement classUnderTest = new TextElement(CONTENT, fontName, FONT_SIZE, Color.RED, 0,
				new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 4, SLIDE_Y / 4, slide);

		JComponent jComponent = classUnderTest.getComponent();
		if (jComponent instanceof JLabel) {
			JLabel component = (JLabel) jComponent;

			// Check attributes
			assertEquals("<html>" + CONTENT + "</html>", component.getText());
			assertEquals(fontName, component.getFont().getFontName());
			assertEquals(FONT_SIZE, component.getFont().getSize());
		} else
			fail();
	}

	@Test
	public void fontFallbacksToArial() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);
		
		// Create element with bad font name
		TextElement classUnderTest = new TextElement(CONTENT, "A bad font name", FONT_SIZE, Color.RED, 0,
				new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 4, SLIDE_Y / 4, slide);

		JComponent jComponent = classUnderTest.getComponent();
		if (jComponent instanceof JLabel) {
			JLabel component = (JLabel) jComponent;

			// Check font falls back to arial
			assertTrue(component.getFont().getFontName().toLowerCase().contains("arial"));
		} else
			fail();
	}
}
