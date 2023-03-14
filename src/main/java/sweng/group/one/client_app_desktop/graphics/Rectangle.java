package sweng.group.one.client_app_desktop.graphics;

import java.awt.BasicStroke;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
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
	private final int shadowDx, shadowDy, startShadowDx, startShadowDy, endShadowDx, endShadowDy, shadowBlurRadius;
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

		int mWidth = relativeToSlide(width);
		int mHeight = relativeToSlide(height);

		r.setBounds(0, 0, mWidth, mHeight);

		int x = 0;
		int y = 0;

		// Map rect width
		int mRectWidth = relativeToSlide(rectWidth);
		int mRectHeight = relativeToSlide(rectHeight);

		// Draw shadow
		if (shadow != null && (shadowDx != 0 || shadowDy != 0)) {
			// Map shadow and work out positioning
			int mShadowDx = relativeToSlide(shadowDx);
			// System.out.println(mShadowDx);
			int mShadowDy = relativeToSlide(shadowDy);
//			int mShadowBlurRadius = relativeToSlide(shadowBlurRadius);

			x += Math.abs(Math.min(0, mShadowDx));
			y += Math.abs(Math.min(0, mShadowDy));

//			if (mShadowDx < 0)
//				x += mShadowBlurRadius;
//			if (mShadowDy < 0)
//				y += mShadowBlurRadius;

			Color transparent = new Color(shadowColour.getRed(), shadowColour.getBlue(), shadowColour.getGreen(), 0);
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

			Color[] colours = { shadowColour, transparent };
			
			float[] dist = { 0, 1f - (shadowBlurRadius / 100f) };

			gradient = new LinearGradientPaint(new Point(shadowXStart, shadowYStart), new Point(shadowXEnd, shadowYEnd),
					dist, colours);

			// gradient = new GradientPaint(shadowXStart, shadowYStart, shadowColour,
			// shadowXEnd, shadowYEnd, transparent);

			g2d.setPaint(gradient);

			g2d.fillRect(mShadowDx >= 0 ? 0 : Math.abs(mShadowDx), mShadowDy >= 0 ? 0 : Math.abs(mShadowDy), mRectWidth,
					mRectHeight);
		}

		// Draw border
		if (border != null) {
			// Map border and work out positioning
			int mBorderWidth = relativeToSlide(borderWidth);

			x += mBorderWidth;
			y += mBorderWidth;
			g2d.setStroke(new BasicStroke(mBorderWidth));
			g2d.setPaint(borderColour);
//			g2d.fillRect(x - mBorderWidth, y - mBorderWidth, mRectWidth + 2 * mBorderWidth,
//					mRectHeight + 2 * mBorderWidth);
		}

		// Draw rect
		g2d.setPaint(fillColour);
		//g2d.fillRect(x, y, mRectWidth, mRectHeight);

//		// Map shadow blur and work out padding
//		int shadowBlurMapped = map(shadowBlurRadius, 0, 100, 0, (int) Math.round(r.getWidth()));
//		int startXShadowPadding = shadowBlurMapped / 2, startYShadowPadding = shadowBlurMapped / 2,
//				endXShadowPadding = shadowBlurMapped / 2, endYShadowPadding = shadowBlurMapped / 2;
//		if (shadowDx != 0 || shadowDy != 0) {
//			startXShadowPadding = shadowDx >= 0 ? 0 : shadowBlurMapped;
//			startYShadowPadding = shadowDy >= 0 ? 0 : shadowBlurMapped;
//			endXShadowPadding = shadowDx >= 0 ? shadowBlurMapped : 0;
//			endYShadowPadding = shadowDy >= 0 ? shadowBlurMapped : 0;
//		}
//
//		// Padding factors
//		int startXPadding = 0 + startShadowDx + startXShadowPadding;
//		int startYPadding = 0 + startShadowDy + startYShadowPadding;
//		int endXPadding = r.width - endShadowDx - endXShadowPadding;
//		int endYPadding = r.height - endShadowDy - endYShadowPadding;
//
//		// Shadow
//		if (shadow != null) {
//			// Shadow colour with 0 alpha
//			Color transparent = new Color(shadowColour.getRed(), shadowColour.getBlue(), shadowColour.getGreen(), 0);
//
//			if (shadowDx == 0 && shadowDy == 0) { // Radial gradient
//				// Colour distribution in gradient
//				float[] dist = { 0.0f, 1.0f };
//
//				Color[] colors = { shadowColour, transparent };
//
//				gradient = new RadialGradientPaint(r.width / 2f, r.height / 2f, Math.max(r.width, r.height) * 4 / 7,
//						dist, colors, CycleMethod.NO_CYCLE);
//			} else { // Linear gradient
//				int shadowXStart, shadowYStart, shadowXEnd, shadowYEnd;
//
//				// Work out gradient start and end
//				if (shadowDx >= 0) { // Left to right
//					shadowXStart = 0;
//					shadowXEnd = r.width;
//				} else { // Right to left
//					shadowXStart = r.width;
//					shadowXEnd = 0;
//				}
//
//				if (shadowDy >= 0) { // Top to bottom
//					shadowYStart = 0;
//					shadowYEnd = r.height;
//				} else { // Bottom to top
//					shadowYStart = r.height;
//					shadowYEnd = 0;
//				}
//
//				gradient = new GradientPaint(shadowXStart, shadowYStart, shadowColour, shadowXEnd, shadowYEnd,
//						transparent);
//			}
//
//			g2d.setPaint(gradient);
//
//			// Draw shadow
//			g2d.fillRect(shadowDx >= 0 ? shadowDx : 0, shadowDy >= 0 ? shadowDy : 0,
//					r.width + ((shadowDx >= 0) ? 0 : shadowDx), r.height + ((shadowDy >= 0) ? 0 : shadowDy));
//		}
//
//		// Border
//		int halfBorderWidth = 0;
//		if (border != null) {
//			int borderWidth = (int) (border.getBorderWidth() * (slide.getWidth() / (float) slide.getPointWidth()));
//			halfBorderWidth = borderWidth / 2;
//
//			int borderStartX = startXPadding + halfBorderWidth;
//			int borderStartY = startYPadding + halfBorderWidth;
//			g2d.setStroke(new BasicStroke(borderWidth));
//			g2d.setPaint(borderColour);
//			g2d.drawRect(borderStartX, borderStartY, endXPadding - borderStartX - halfBorderWidth,
//					endYPadding - borderStartY - halfBorderWidth);
//		}
//
//		// Rectangle
//		int startX = startXPadding + halfBorderWidth;
//		int startY = startYPadding + halfBorderWidth;
//		g2d.setPaint(fillColour);
//		g2d.fillRect(startX, startY, endXPadding - startX - halfBorderWidth, endYPadding - startY - halfBorderWidth);

//		g2d.setPaint(Color.RED);
//		g2d.fillRect(0, 0, r.width, r.height);

		// Reset paint
		g2d.setPaint(previousPaint);
		// Reset stroke
		g2d.setStroke(previousStroke);
	}

	private static Point getPos(Point oldPos, Border border, Shadow shadow) {
		Point pos = new Point(oldPos.x, oldPos.y);

		if (border != null)
			pos.setLocation(pos.x - border.getBorderWidth(), pos.y - border.getBorderWidth());

		if (shadow != null) {
			if (shadow.getShadowDx() < 0)
				pos.setLocation(pos.x + shadow.getShadowDx(), pos.y);

			if (shadow.getShadowDy() < 0)
				pos.setLocation(pos.x, pos.y + shadow.getShadowDy());
		}
		return pos;
	}

	private static int getWidth(int rectWidth, Border border, Shadow shadow) {
		int width = rectWidth;

		if (border != null)
			width += border.getBorderWidth() * 2;

		if (shadow != null)
			width += Math.abs(shadow.getShadowDx());

		return width;
	}

	private static int getHeight(int rectHeight, Border border, Shadow shadow) {
		int height = rectHeight;

		if (border != null)
			height += border.getBorderWidth() * 2;

		if (shadow != null)
			height += Math.abs(shadow.getShadowDy());

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
			// Shadow positioning
			this.startShadowDx = shadowDx >= 0 ? 0 : Math.abs(shadowDx);
			this.endShadowDx = Math.max(shadowDx, 0);
			this.startShadowDy = shadowDy >= 0 ? 0 : Math.abs(shadowDy);
			this.endShadowDy = Math.max(shadowDy, 0);
		} else { // Defaults
			// Shadow parameters
			this.shadowDx = 0;
			this.shadowDy = 0;
			this.shadowBlurRadius = 0;
			this.shadowColour = new Color(0, 0, 0, 0);
			// Shadow positioning
			this.startShadowDx = 0;
			this.endShadowDx = 0;
			this.startShadowDy = 0;
			this.endShadowDy = 0;
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

		canvas.setOpaque(true);
		canvas.setBackground(Color.DARK_GRAY);
	}

	public Paint getGradient() {
		return gradient;
	}
}
