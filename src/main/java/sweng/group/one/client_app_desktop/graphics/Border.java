package sweng.group.one.client_app_desktop.graphics;

import java.awt.Color;

/**
 * The “Border” class stores information about borders.
 * 
 * @author joe2k01
 *
 */
public class Border {
	private final Color borderColour;
	private final int borderWidth;

	/**
	 * Border construcor.
	 * 
	 * @param borderColour colour of the border
	 * @param borderWidth  width of the border, relative to the “Slide” coordinates
	 */
	public Border(Color borderColour, int borderWidth) {
		this.borderColour = borderColour;
		this.borderWidth = borderWidth;
	}

	public Color getBorderColour() {
		return borderColour;
	}

	public int getBorderWidth() {
		return borderWidth;
	}
}
