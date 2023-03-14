package sweng.group.one.client_app_desktop.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;

import sweng.group.one.client_app_desktop.presentation.Slide;

import java.awt.MultipleGradientPaint.CycleMethod;

/**
 * The “Circle” class draws a circle of a given radius and colour at a given
 * position on a “Slide” object.
 * 
 * @author joe2k01
 *
 */
public class Circle extends Shape {
	// Shadow parameters to be accessed in CanvasOperation
	private final int radius, containerWidth, shadowDx, shadowDy, shadowBlurRadius;
	private final Color shadowColour;

	// Border Parameters to be accessed in CanvasOperation
	private final Color borderColour;

	// Access to gradient paint for testing
	private Paint gradient;

	/**
	 * Draws the circle.
	 * 
	 * @param g2d Graphics2D object to draw the circle with.
	 */
	public void drawCircle(Graphics2D g2d) {
		Stroke previousStroke = g2d.getStroke();
		Paint previousPaint = g2d.getPaint();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Get bounding box
		Rectangle r = g2d.getClip().getBounds();

		int borderWidth = 0;
		int halfBorderWidth = 0;

		// Calculate border parameters
		if (border != null) {
			borderWidth = (int) (border.getBorderWidth() * (slide.getWidth() / (float) slide.getPointWidth()));
			halfBorderWidth = borderWidth / 2;
		}

		// Map parameters to slide coordinates and apply border padding
		int radiusPx = radius * Math.min(r.width, r.height) / containerWidth;
		int diameterPx = radiusPx * 2;
		int x = r.width / 2 - radiusPx + halfBorderWidth;
		int y = r.height / 2 - radiusPx + halfBorderWidth;

		// Shadow
		if (shadow != null) {
			// Shadow colour with 0 alpha
			Color transparent = new Color(shadowColour.getRed(), shadowColour.getBlue(), shadowColour.getGreen(), 0);

			if (shadowDx == 0 && shadowDy == 0) { // Radial gradient
				// Colour distribution in gradient
				float[] dist = { 0.0f, 1.0f };

				Color[] colors = { shadowColour, transparent };

				gradient = new RadialGradientPaint(r.width / 2f, r.height / 2f, r.width * 4 / 7, dist, colors,
						CycleMethod.NO_CYCLE);

				// Map shadow blur
				int maxBlur = ((r.width - diameterPx) / 2);
				int shadowBlurMapped = map(shadowBlurRadius, 0, 100, 0, maxBlur);

				// Draw shadow
				g2d.setPaint(gradient);
				g2d.fillOval(x - shadowBlurMapped / 2 - halfBorderWidth, y - shadowBlurMapped / 2 - halfBorderWidth,
						diameterPx + shadowBlurMapped + halfBorderWidth,
						diameterPx + shadowBlurMapped + halfBorderWidth);
			} else { // Linear gradient
				int shadowXStart, shadowYStart, shadowXEnd, shadowYEnd;

				// Work out gradient start and end
				if (shadowDx >= 0) { // Left to right
					shadowXStart = x;
					shadowXEnd = x + diameterPx;
				} else { // Right to left
					shadowXStart = x + diameterPx;
					shadowXEnd = x;
				}

				if (shadowDy >= 0) { // Top to bottom
					shadowYStart = y;
					shadowYEnd = y + diameterPx;
				} else { // Bottom to top
					shadowYStart = y + diameterPx;
					shadowYEnd = y;
				}

				gradient = new GradientPaint(shadowXStart, shadowYStart, shadowColour, shadowXEnd, shadowYEnd,
						transparent);

				// Map shadow blur
				int maxBlur = ((r.width - diameterPx) / 2) - Math.max(shadowDx, shadowDy);
				int shadowBlurMapped = map(shadowBlurRadius, 0, 100, 0, maxBlur);

				// Draw shadow
				g2d.setPaint(gradient);
				g2d.fillOval(x + shadowDx - ((shadowDx > 0) ? 0 : shadowBlurMapped),
						y + shadowDy - ((shadowDy > 0) ? 0 : shadowBlurMapped),
						diameterPx + ((shadowDx > 0) ? shadowBlurMapped : 0),
						diameterPx + ((shadowDy > 0) ? shadowBlurMapped : 0));
			}
		}

		// Circle
		g2d.setPaint(fillColour);
		g2d.fillOval(x, y, diameterPx, diameterPx);

		// Border
		if (border != null) {
			g2d.setStroke(new BasicStroke(borderWidth));
			g2d.setPaint(borderColour);
			g2d.drawOval(x - halfBorderWidth, y - halfBorderWidth, diameterPx + halfBorderWidth,
					diameterPx + halfBorderWidth);
		}

		// Reset paint
		g2d.setPaint(previousPaint);
		// Reset stroke
		g2d.setStroke(previousStroke);
	}

	/**
	 * Circle constructor.
	 * 
	 * @param pos        Coordinate of the centre of the circle on the parent
	 *                   “Slide” class.
	 * @param radius     Radius of the circle, relative to the centre of the circle.
	 * @param duration   Duration.
	 * @param slide      Slide owning the circle.
	 * @param fillColour Colour of the fill of the shape.
	 * @param border     Circle's border.
	 * @param shadow     Circle's shadow.
	 */
	public Circle(Point pos, int radius, float duration, Slide slide, Color fillColour, Border border, Shadow shadow) {
		super(new Point(pos.x - radius, pos.y - radius), (int) Math.round(2.5 * radius), (int) Math.round(2.5 * radius),
				duration, slide, fillColour, border, shadow);

		this.radius = radius;

		this.containerWidth = (int) Math.round(2.5 * radius);

		if (shadow != null) {
			// Shadow parameters
			this.shadowDx = (int) shadow.getShadowDx();
			this.shadowDy = (int) shadow.getShadowDy();
			this.shadowBlurRadius = shadow.getShadowBlurRadius();
			this.shadowColour = shadow.getShadowColour();
		} else { // Defaults
			// Shadow parameters
			this.shadowDx = 0;
			this.shadowDy = 0;
			this.shadowBlurRadius = 0;
			this.shadowColour = new Color(0, 0, 0, 0);
		}

		// Border parameters
		if (border != null)
			this.borderColour = border.getBorderColour();
		else // Defaults
			this.borderColour = new Color(0, 0, 0, 0);

		// Draw shape
		CanvasOperation canvasOperation = g -> {
			Graphics2D g2d = (Graphics2D) g;

			drawCircle(g2d);
		};

		JCanvas canvas = new JCanvas(canvasOperation);

		this.component = canvas;
		this.displayElement();
	}

	public Paint getGradient() {
		return gradient;
	}
}
