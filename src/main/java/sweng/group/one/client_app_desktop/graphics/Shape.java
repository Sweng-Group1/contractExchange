package sweng.group.one.client_app_desktop.graphics;

import java.awt.Color;
import java.awt.Point;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

/**
 * Shape is the abstract class to be extended by Rectangle and Circle.
 * 
 * @author joe2k01
 *
 */
public abstract class Shape extends PresElement {
	protected final Border border;
	protected final Shadow shadow;
	protected Color fillColour;

	protected Shape(Point pos, int width, int height, float duration, Slide slide, Color fillColour, Border border,
			Shadow shadow) {
		super(pos, width, height, duration, slide);

		this.border = border;
		this.shadow = shadow;
		this.fillColour = fillColour;
	}

	/**
	 * From https://www.arduino.cc/reference/en/language/functions/math/map/ Maps a
	 * value within certain bounds onto another pair of bounds.
	 * 
	 * @param x       The value to map.
	 * @param in_min  The minimum value of x.
	 * @param in_max  The maximum value of x.
	 * @param out_min The minimum output value
	 * @param out_max The maximum output value
	 * @return The mapped value of x onto the pair of output bounds.
	 */
	protected int map(int x, int in_min, int in_max, int out_min, int out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	protected int relativeToSlide(int input) {
		return (int) Math.floor(input * slide.getWidth() / (float) slide.getPointWidth());
	}
}
