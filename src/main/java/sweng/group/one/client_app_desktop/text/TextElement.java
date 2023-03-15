package sweng.group.one.client_app_desktop.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JLabel;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

/**
 * The “TextElement” class displays an arbitrary string, in an arbitrary font of
 * arbitrary size. The “TextElement” will fill the space on a "Slide" which is
 * specified by the element's position, width and height.
 * 
 * @author joe2k01
 *
 */
public class TextElement extends PresElement {

	/**
	 * TextElement constructor.
	 * 
	 * @param text       text to be displayed.
	 * @param fontName   name of the font to be displayed.
	 * @param fontSizePt size of the font.
	 * @param Colour     the colour of the text.
	 * @param duration   Duration.
	 * @param pos        The coordinates of the top left corner of the TextElement.
	 *                   on the “Slide” class.
	 * @param width      width of text element.
	 * @param height     height of TextElement.
	 * @param slide      The Slide owning the TextElement.
	 */
	public TextElement(String text, String fontName, int fontSizePt, Color Colour, float duration, Point pos, int width,
			int height, Slide slide) {
		super(pos, width, height, duration, slide);

		// Load system fonts
		Font[] t = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

		// Try to load requested font
		Font loadedFont = Arrays.stream(t).filter(font -> font.getFontName().equals(fontName)).findFirst().orElse(null);

		// Fallback to Arial
		Font validFont = (loadedFont != null) ? new Font(loadedFont.getFontName(), Font.PLAIN, fontSizePt)
				: new Font("Arial", Font.PLAIN, fontSizePt);

		// Wrap in html for automatic line wrap
		this.component = new JLabel("<html>" + text + "</html>");
		this.component.setForeground(Colour);
		this.component.setFont(validFont);
	}

	public JComponent getComponent() {
		return this.component;
	}

}
