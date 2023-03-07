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

public class TextElement extends PresElement {

	public TextElement(String text, String fontName, int fontSizePt, Color Colour, float duration, Point pos, int width,
			int height, Slide slide) {
		super(pos, width, height, duration, slide);

		Font[] t = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

		Font loadedFont = Arrays.stream(t).filter(font -> font.getFontName().equals(fontName)).findFirst().orElse(null);

		Font validFont = (loadedFont != null) ? new Font(loadedFont.getFontName(), Font.PLAIN, fontSizePt)
				: new Font("Arial", Font.PLAIN, fontSizePt);

		this.component = new JLabel("<html>" + text + "</html>");
		this.component.setForeground(Colour);
		this.component.setFont(validFont);
	}
	
	public JComponent getComponent() {
		return this.component;
	}

}
