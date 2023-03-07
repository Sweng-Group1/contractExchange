package sweng.group.one.client_app_desktop.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

public class Line extends PresElement {
	private final Point from, to;
	private final int thickness;
	private final Color colour;

	/**
	 * Maps the input coordinate to a pixel value.
	 * 
	 * @param input     Input coordinate.
	 * @param pixelSize Axis size in pixels.
	 * @param size      Axis size in custom coordinates system.
	 * @return The mapped coordinate.
	 */
	public int mapCoordinateToPixels(int input, int pixelSize, int size) {
		return input * pixelSize / size;
	}

	public void drawLine(Graphics2D g2d) {
		Stroke previousStroke = g2d.getStroke();

		Rectangle r = g2d.getClip().getBounds();

		int calculatedThickness = (int) (thickness * (slide.getWidth() / (float) slide.getPointWidth()));

		g2d.setColor(colour);
		g2d.setStroke(new BasicStroke(calculatedThickness));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int posX = mapCoordinateToPixels(pos.x, r.width, width);
		int posY = mapCoordinateToPixels(pos.y, r.height, height);

		int fromX = mapCoordinateToPixels(from.x, r.width, width) - posX;
		int fromY = mapCoordinateToPixels(from.y, r.height, height) - posY;
		int toX = mapCoordinateToPixels(to.x, r.width, width) - posX;
		int toY = mapCoordinateToPixels(to.y, r.height, height) - posY;

		g2d.drawLine(fromX, fromY, toX, toY);

		g2d.setStroke(previousStroke);
	}

	public Line(Point pos, int width, int height, float duration, Slide slide, int thickness, Point from, Point to,
			Color colour) {
		super(pos, width, height, duration, slide);

		this.colour = colour;
		this.thickness = thickness;
		this.from = from;
		this.to = to;

		// Check bounding box
		if (pos.x > from.x || pos.x > to.x || pos.y > from.y || pos.y > to.y || (pos.x + width) < from.x
				|| (pos.x + width) < to.x || (pos.y + height) < from.y || (pos.y + height) < to.y) {
			throw new IllegalArgumentException("The line lies outside its bounding box");
		}

		// Draw shape
		CanvasOperation canvasOperation = g -> {
			Graphics2D g2d = (Graphics2D) g;

			drawLine(g2d);
		};

		JCanvas canvas = new JCanvas(canvasOperation);

		this.component = canvas;
		this.displayElement();
	}

}
