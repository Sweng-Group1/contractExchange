package sweng.group.one.client_app_desktop.graphics;

import java.awt.Color;

public class Border {
	private final Color borderColour;
	private final int borderWidth;
	
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
