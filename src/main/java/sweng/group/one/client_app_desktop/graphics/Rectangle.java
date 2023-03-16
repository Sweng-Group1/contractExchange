package sweng.group.one.client_app_desktop.graphics;

import java.awt.BasicStroke;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Stroke;

import sweng.group.one.client_app_desktop.presentation.Slide;

/**
 * The “Rectangle” class draws a rectangle of given dimensions on a “Slide”
 * object, with the possibility of a border and shadow.
 * 
 * @author joe2k01
 *
 */
public class Rectangle extends Shape {

	// Shadow parameters to be accessed in CanvasOperation
	private final int shadowDx, shadowDy, shadowBlurRadius;
	private final Color shadowColour;

	// Border Parameters to be accessed in CanvasOperation
	private final int borderWidth;
	private final Color borderColour;

	// Rect Parameters to be accessed in CanvasOperation
	private final int rectWidth, rectHeight;

	// Access to gradient paint for testing
	private Paint gradient;

	/**
	 * Draws the rectangle.
	 * 
	 * @param g2d Graphics2D object to draw the rectangle with.
	 */
	public void drawRect(Graphics2D g2d) {
		Stroke previousStroke = g2d.getStroke();
		Paint previousPaint = g2d.getPaint();

		// Get bounding box
		java.awt.Rectangle r = g2d.getClip().getBounds();

		// Map width/height
		int mWidth = relativeToSlide(width);
		int mHeight = relativeToSlide(height);

		// Adjust bounding box
		r.setBounds(0, 0, mWidth, mHeight);

		int x = 0;
		int y = 0;

		// Map rectangle width/height
		int mRectWidth = relativeToSlide(rectWidth);
		int mRectHeight = relativeToSlide(rectHeight);

		// Draw shadow
		if (shadow != null) {
			// Map shadow and work out positioning
			int mShadowDx = relativeToSlide(shadowDx);
			int mShadowDy = relativeToSlide(shadowDy);
			int mShadowBlurRadius = relativeToSlide(shadowBlurRadius);

			// Pad rectangle to shadow
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
				int shadowXStart, shadowYStart, shadowXEnd, shadowYEnd;

				// Work out gradient start and end
				if (mShadowDx >= 0) { // Left to right
					shadowXStart = 0;
					shadowXEnd = mWidth;
				} else { // Right to left
					shadowXStart = mWidth;
					shadowXEnd = 0;
				}

				if (mShadowDy >= 0) { // Top to bottom
					shadowYStart = 0;
					shadowYEnd = mHeight;
				} else { // Bottom to top
					shadowYStart = mHeight;
					shadowYEnd = 0;
				}

				gradient = new GradientPaint(shadowXStart, shadowYStart, shadowColour, shadowXEnd, shadowYEnd,
						transparent);

				g2d.setPaint(gradient);

				g2d.fillRect((mShadowDx > 0) ? mShadowDx : 0, (mShadowDy > 0) ? mShadowDy : 0,
						mWidth + ((mShadowDx > 0) ? 0 : mShadowDx), mHeight + ((mShadowDy > 0) ? 0 : mShadowDy));
			}
		}

		// Draw border
		if (border != null) {
			// Map border
			int mBorderWidth = relativeToSlide(borderWidth);

			// Pad rectangle to border
			x += mBorderWidth;
			y += mBorderWidth;

			g2d.setStroke(new BasicStroke(mBorderWidth));
			g2d.setPaint(borderColour);
			g2d.fillRect(x - mBorderWidth, y - mBorderWidth, mRectWidth + 2 * mBorderWidth,
					mRectHeight + 2 * mBorderWidth);
		}

		// Draw rect
		g2d.setPaint(fillColour);
		g2d.fillRect(x, y, mRectWidth, mRectHeight);

		// Reset paint
		g2d.setPaint(previousPaint);
		// Reset stroke
		g2d.setStroke(previousStroke);
	}

	/**
	 * Calculates the element's position to accommodate shadow and border
	 * 
	 * @param oldPos The rectangle's body position
	 * @param border The border
	 * @param shadow The shadow
	 * @return The element's position
	 */
	private static Point getPos(Point oldPos, Border border, Shadow shadow) {
		Point pos = new Point(oldPos.x, oldPos.y);

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
	 * @param rectWidth The rectangle's width
	 * @param border    The border
	 * @param shadow    The shadow
	 * @return The element's width
	 */
	private static int getWidth(int rectWidth, Border border, Shadow shadow) {
		int width = rectWidth;

		if (border != null)
			width += border.getBorderWidth() * 2;

		if (shadow != null) {
			boolean centerShadow = (shadow.getShadowDx() == 0 && shadow.getShadowDy() == 0);

			width += Math.abs(shadow.getShadowDx())
					+ (centerShadow ? 2 * shadow.getShadowBlurRadius() : shadow.getShadowBlurRadius());
		}

		return width;
	}

	/**
	 * Calculates the element's height to accommodate shadow and border
	 * 
	 * @param rectHeight The rectangle's height
	 * @param border     The border
	 * @param shadow     The shadow
	 * @return The element's height
	 */
	private static int getHeight(int rectHeight, Border border, Shadow shadow) {
		int height = rectHeight;

		if (border != null)
			height += border.getBorderWidth() * 2;

		if (shadow != null) {
			boolean centerShadow = (shadow.getShadowDx() == 0 && shadow.getShadowDy() == 0);

			height += Math.abs(shadow.getShadowDy())
					+ (centerShadow ? 2 * shadow.getShadowBlurRadius() : shadow.getShadowBlurRadius());
		}

		return height;
	}

	/**
	 * Rectangle constructor.
	 * 
	 * @param pos        Coordinate of the top left of the body of the rectangle.
	 * @param rectWidth  Width (relative to the parent Slide class) of the body of
	 *                   the rectangle.
	 * @param rectHeight Height (relative to the parent Slide class) of the body of
	 *                   the rectangle.
	 * @param duration   Duration.
	 * @param slide      Slide owning the rectangle.
	 * @param fillColour Colour of the fill of the shape.
	 * @param border     Rectangle's border.
	 * @param shadow     Rectangle's shadow.
	 */
	public Rectangle(Point pos, int rectWidth, int rectHeight, float duration, Slide slide, Color fillColour,
			Border border, Shadow shadow) {
		super(getPos(pos, border, shadow), getWidth(rectWidth, border, shadow), getHeight(rectHeight, border, shadow),
				duration, slide, fillColour, border, shadow);

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
			this.borderWidth = border.getBorderWidth();
			this.borderColour = border.getBorderColour();
		} else { // Defaults
			this.borderWidth = 0;
			this.borderColour = new Color(0, 0, 0, 0);
		}

		this.rectWidth = rectWidth;
		this.rectHeight = rectHeight;

		// Draw shape
		CanvasOperation canvasOperation = g -> {
			Graphics2D g2d = (Graphics2D) g;

			drawRect(g2d);
		};

		JCanvas canvas = new JCanvas(canvasOperation);

		this.component = canvas;
		this.displayElement();
	}

	public Paint getGradient() {
		return gradient;
	}
}
