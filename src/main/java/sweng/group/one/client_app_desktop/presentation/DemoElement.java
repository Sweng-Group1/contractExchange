package sweng.group.one.client_app_desktop.presentation;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JPanel;

public class DemoElement extends PresElement{

	public DemoElement(Point pos, int width, int height, float duration, Slide slide) {
		super(pos, width, height, duration, slide);
		this.component = new JPanel();
		this.component.setBackground(Color.RED);
		this.displayElement();
	}
}
