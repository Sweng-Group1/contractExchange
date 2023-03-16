package sweng.group.one.client_app_desktop.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Point2D;

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
	private final int radius, shadowDx, shadowDy, shadowBlurRadius;
	private final Color shadowColour;

	// Border Parameters to be accessed in CanvasOperation
	private final Color borderColour;
	private final int borderWidth;

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

		// Map width/height
		int mWidth = relativeToSlide(width);
		int mHeight = relativeToSlide(height);

		// Adjust bounding box
		r.setBounds(0, 0, mWidth, mHeight);

		int x = 0;
		int y = 0;

		// Map radius
		int mRadius = relativeToSlide(radius);
		int mDiameter = 2 * mRadius;

		// Draw shadow
		if (shadow != null) {
			// Map shadow and work out positioning
			int mShadowDx = relativeToSlide(shadowDx);
			int mShadowDy = relativeToSlide(shadowDy);
			int mShadowBlurRadius = relativeToSlide(shadowBlurRadius);

			// Pad circle to shadow
			x += Math.abs(Math.min(0, mShadowDx));
			y += Math.abs(Math.min(0, mShadowDy));

			if (mShadowDx < 0)
				x += mShadowBlurRadius;
			if (mShadowDy < 0)
				y += mShadowBlurRadius;

			if (mShadowDx == 0 && mShadowDy == 0) {
				x += mShadowBlurRadius;
				y += mShadowBlurRadius;
			}

			// End colour of shadow
			Color transparent = new Color(shadowColour.getRed(), shadowColour.getBlue(), shadowColour.getGreen(), 0);

			if (mShadowDx == 0 && mShadowDy == 0) { // Radial gradient
				float[] dist = { 0.0f, 1.0f };

				Color[] colors = { shadowColour, transparent };

				gradient = new RadialGradientPaint(mWidth / 2f, mHeight / 2f, Math.min(mWidth, mHeight) / 2, dist,
						colors, CycleMethod.NO_CYCLE);

				g2d.setPaint(gradient);

				g2d.fillOval(0, 0, mWidth, mHeight);
			} else { // Linear gradient
				// Work out shadow start position
				int shadowX, shadowY;
				if (mShadowDx < 0)
					shadowX = mWidth - (-mShadowDx + mDiameter + mShadowBlurRadius);
				else
					shadowX = mShadowDx;

				if (mShadowDy < 0)
					shadowY = mHeight - (-mShadowDy + mDiameter + mShadowBlurRadius);
				else
					shadowY = mShadowDy;

				// Work out gradient start and end
				int shadowXStart, shadowYStart, shadowXEnd, shadowYEnd;
				if (mShadowDx >= 0) { // Left to right
					shadowXStart = shadowX;
					shadowXEnd = shadowX + mDiameter + mShadowBlurRadius;
				} else { // Right to left
					shadowXStart = shadowX + mDiameter + mShadowBlurRadius;
					shadowXEnd = shadowX;
				}

				if (mShadowDy >= 0) { // Top to bottom
					shadowYStart = shadowY;
					shadowYEnd = shadowY + mDiameter + mShadowBlurRadius;
				} else { // Bottom to top
					shadowYStart = shadowY + mDiameter + mShadowBlurRadius;
					shadowYEnd = shadowY;
				}

				gradient = new GradientPaint(shadowXStart, shadowYStart, shadowColour, shadowXEnd, shadowYEnd,
						transparent);

				g2d.setPaint(gradient);

				g2d.fillOval(shadowX, shadowY, mDiameter + mShadowBlurRadius, mDiameter + mShadowBlurRadius);

			}
		}

		// Draw border
		if (border != null) {
			// Map border
			int mBorderWidth = relativeToSlide(borderWidth);

			// Pad circle to border
			x += mBorderWidth;
			y += mBorderWidth;

			g2d.setStroke(new BasicStroke(mBorderWidth));
			g2d.setPaint(borderColour);
			g2d.fillOval(x - mBorderWidth, y - mBorderWidth, mDiameter + 2 * mBorderWidth,
					mDiameter + 2 * mBorderWidth);
		}

		// Draw circle
		g2d.setPaint(fillColour);
		g2d.fillOval(x, y, 2 * mRadius, 2 * mRadius);

		// Reset paint
		g2d.setPaint(previousPaint);
		// Reset stroke
		g2d.setStroke(previousStroke);
	}

	/**
	 * Calculates the element's position to accommodate shadow and border
	 * 
	 * @param oldPos The circls'e centre position
	 * @param radius The circle's radius
	 * @param border The border
	 * @param shadow The shadow
	 * @return The element's position
	 */
	private static Point getPos(Point oldPos, int radius, Border border, Shadow shadow) {
		Point pos = new Point(oldPos.x - radius, oldPos.y - radius);

		if (border != null)
			pos.setLocation(pos.x - border.getBorderWidth(), pos.y - border.getBorderWidth());

		if (shadow != null) {
			boolean centerShadow = (shadow.getShadowDx() == 0 && shadow.getShadowDy() == 0);

			if (shadow.getShadowDx() < 0)
				pos.setLocation(pos.x + shadow.getShadowDx() - shadow.getShadowBlurRadius(), pos.y);

			if (shadow.getShadowDy() < 0)
				pos.setLocation(pos.x, pos.y + shadow.getShadowDy() - shadow.getShadowBlurRadius());

			if (centerShadow)
				pos.setLocation(pos.x - shadow.getShadowBlurRadius(), pos.y - shadow.getShadowBlurRadius());
		}

		return pos;
	}

	/**
	 * Calculates the element's width to accommodate shadow and border
	 * 
	 * @param radius The circle's radius
	 * @param border The border
	 * @param shadow The shadow
	 * @return The element's width
	 */
	private static int getWidth(int radius, Border border, Shadow shadow) {
		int width = 2 * radius;

		if (border != null)
			width += border.getBorderWidth() * 2;

		if (shadow != null) {
			boolean centerShadow = (shadow.getShadowDx() == 0 && shadow.getShadowDy() == 0);

			width += Math.abs((int) shadow.getShadowDx())
					+ (centerShadow ? 2 * shadow.getShadowBlurRadius() : shadow.getShadowBlurRadius());
		}

		return width;
	}

	/**
	 * Calculates the element's height to accommodate shadow and border
	 * 
	 * @param radius The circle's radius
	 * @param border The border
	 * @param shadow The shadow
	 * @return The element's height
	 */
	private static int getHeight(int radius, Border border, Shadow shadow) {
		int height = 2 * radius;

		if (border != null)
			height += border.getBorderWidth() * 2;

		if (shadow != null) {
			boolean centerShadow = (shadow.getShadowDx() == 0 && shadow.getShadowDy() == 0);

			height += Math.abs((int) shadow.getShadowDy())
					+ (centerShadow ? 2 * shadow.getShadowBlurRadius() : shadow.getShadowBlurRadius());
		}

		return height;
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
		super(getPos(pos, radius, border, shadow), getWidth(radius, border, shadow), getHeight(radius, border, shadow),
				duration, slide, fillColour, border, shadow);

		this.radius = radius;

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
		if (border != null) {
			this.borderColour = border.getBorderColour();
			this.borderWidth = border.getBorderWidth();
		} else { // Defaults
			this.borderColour = new Color(0, 0, 0, 0);
			this.borderWidth = 0;
		}

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
