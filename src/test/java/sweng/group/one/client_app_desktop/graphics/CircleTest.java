package sweng.group.one.client_app_desktop.graphics;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;

import org.junit.Test;
import org.mockito.Mockito;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class CircleTest {
	private static final int SLIDE_X = 800;
	private static final int SLIDE_Y = 400;
	private static final int BORDER_W = 5;
	private static final int HALF_BORDER_W = BORDER_W / 2;
	private static final int PLUS_SHADOW_DX = 5;
	private static final int PLUS_SHADOW_DY = 10;
	private static final int MINUS_SHADOW_DX = -5;
	private static final int MINUS_SHADOW_DY = -10;
	private static final int SHADOW_BLUR_RADIUS = 25;

	@Test
	public void drawsCircleNoShadowNoBorder() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		int radius = SLIDE_X / 2;
		int containerWidth = (int) Math.round(2.5 * radius);

		Circle classUnderTest = new Circle(new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 2, 0, slide, Color.RED, null,
				null);
		classUnderTest.drawCircle(g2d);

		int radiusPx = radius * Math.min(SLIDE_X, SLIDE_Y) / containerWidth;
		int diameterPx = radiusPx * 2;

		int x = SLIDE_X / 2 - radiusPx;
		int y = SLIDE_Y / 2 - radiusPx;

		verify(g2d, times(1)).setPaint(Color.RED);
		verify(g2d, times(1)).fillOval(x, y, diameterPx, diameterPx);
		verify(g2d, times(1)).setPaint(p);
		verify(g2d, times(1)).setStroke(bs);
	}

	@Test
	public void drawsWithBorder() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		Border border = new Border(Color.BLACK, BORDER_W);

		int radius = SLIDE_X / 2;
		int containerWidth = (int) Math.round(2.5 * radius);

		Circle classUnderTest = new Circle(new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 2, 0, slide, Color.RED,
				border, null);
		classUnderTest.drawCircle(g2d);

		int radiusPx = radius * Math.min(SLIDE_X, SLIDE_Y) / containerWidth;
		int diameterPx = radiusPx * 2;

		int calculatedBorderWidth = (int) (border.getBorderWidth()
				* (slide.getWidth() / (float) slide.getPointWidth()));
		int calculatedHalfBorderWidth = calculatedBorderWidth / 2;

		int x = SLIDE_X / 2 - radiusPx + calculatedHalfBorderWidth;
		int y = SLIDE_Y / 2 - radiusPx + calculatedHalfBorderWidth;

		verify(g2d, times(1)).setStroke(new BasicStroke(calculatedBorderWidth));
		verify(g2d, times(1)).setPaint(Color.BLACK);

		// Border
		verify(g2d, times(1)).drawOval(x - calculatedHalfBorderWidth, y - calculatedHalfBorderWidth,
				diameterPx + calculatedHalfBorderWidth, diameterPx + calculatedHalfBorderWidth);

		// Circle
		verify(g2d, times(1)).fillOval(x, y, diameterPx, diameterPx);
	}

	@Test
	public void drawsWithShadowBothPositive() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		Shadow shadow = new Shadow(Color.CYAN, PLUS_SHADOW_DX, PLUS_SHADOW_DY, SHADOW_BLUR_RADIUS);
		int radius = SLIDE_X / 2;
		int containerWidth = (int) Math.round(2.5 * radius);

		Circle classUnderTest = new Circle(new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 2, 0, slide, Color.RED, null,
				shadow);
		classUnderTest.drawCircle(g2d);

		int radiusPx = radius * Math.min(SLIDE_X, SLIDE_Y) / containerWidth;
		int diameterPx = radiusPx * 2;

		int x = SLIDE_X / 2 - radiusPx;
		int y = SLIDE_Y / 2 - radiusPx;

		int maxBlur = ((SLIDE_X - diameterPx) / 2) - PLUS_SHADOW_DY;
		int shadowBlurMapped = maxBlur * SHADOW_BLUR_RADIUS / 100; // Shortened map function

		// Circle
		verify(g2d, times(1)).fillOval(x, y, diameterPx, diameterPx);

		// Shadow
		verify(g2d, times(1)).fillOval(x + PLUS_SHADOW_DX, y + PLUS_SHADOW_DY, diameterPx + shadowBlurMapped,
				diameterPx + shadowBlurMapped);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(x, y, Color.CYAN, x + diameterPx, y + diameterPx, transparent);

			assertEquals(gradient.getColor1(), gp.getColor1());
			assertEquals(gradient.getColor2(), gp.getColor2());
			assertEquals(gradient.getPoint1(), gp.getPoint1());
			assertEquals(gradient.getPoint2(), gp.getPoint2());
		} else
			fail();
	}

	@Test
	public void drawsWithShadowBothNegative() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		Shadow shadow = new Shadow(Color.CYAN, MINUS_SHADOW_DX, MINUS_SHADOW_DY, SHADOW_BLUR_RADIUS);
		int radius = SLIDE_X / 2;
		int containerWidth = (int) Math.round(2.5 * radius);

		Circle classUnderTest = new Circle(new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 2, 0, slide, Color.RED, null,
				shadow);
		classUnderTest.drawCircle(g2d);

		int radiusPx = radius * Math.min(SLIDE_X, SLIDE_Y) / containerWidth;
		int diameterPx = radiusPx * 2;

		int x = SLIDE_X / 2 - radiusPx;
		int y = SLIDE_Y / 2 - radiusPx;

		int maxBlur = ((SLIDE_X - diameterPx) / 2) - MINUS_SHADOW_DX;
		int shadowBlurMapped = maxBlur * SHADOW_BLUR_RADIUS / 100; // Shortened map function

		// Circle
		verify(g2d, times(1)).fillOval(x, y, diameterPx, diameterPx);

		// Shadow
		verify(g2d, times(1)).fillOval(x + MINUS_SHADOW_DX - shadowBlurMapped, y + MINUS_SHADOW_DY - shadowBlurMapped,
				diameterPx, diameterPx);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(x + diameterPx, y + diameterPx, Color.CYAN, x, y, transparent);

			assertEquals(gradient.getColor1(), gp.getColor1());
			assertEquals(gradient.getColor2(), gp.getColor2());
			assertEquals(gradient.getPoint1(), gp.getPoint1());
			assertEquals(gradient.getPoint2(), gp.getPoint2());
		} else
			fail();
	}

	@Test
	public void drawsWithShadowBothZero() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		Shadow shadow = new Shadow(Color.CYAN, 0, 0, SHADOW_BLUR_RADIUS);
		int radius = SLIDE_X / 2;
		int containerWidth = (int) Math.round(2.5 * radius);

		Circle classUnderTest = new Circle(new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 2, 0, slide, Color.RED, null,
				shadow);
		classUnderTest.drawCircle(g2d);

		int radiusPx = radius * Math.min(SLIDE_X, SLIDE_Y) / containerWidth;
		int diameterPx = radiusPx * 2;

		int x = SLIDE_X / 2 - radiusPx;
		int y = SLIDE_Y / 2 - radiusPx;

		int maxBlur = ((SLIDE_X - diameterPx) / 2);
		int shadowBlurMapped = maxBlur * SHADOW_BLUR_RADIUS / 100; // Shortened map function

		// Circle
		verify(g2d, times(1)).fillOval(x, y, diameterPx, diameterPx);

		// Shadow
		verify(g2d, times(1)).fillOval(x - shadowBlurMapped / 2, y - shadowBlurMapped / 2,
				diameterPx + shadowBlurMapped, diameterPx + shadowBlurMapped);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof RadialGradientPaint) {
			RadialGradientPaint rgp = (RadialGradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			// Colour distribution in gradient
			float[] dist = { 0.0f, 1.0f };
			Color[] colors = { Color.CYAN, transparent };
			RadialGradientPaint gradient = new RadialGradientPaint(SLIDE_X / 2f, SLIDE_Y / 2f,
					Math.max(SLIDE_X, SLIDE_Y) * 4 / 7, dist, colors, CycleMethod.NO_CYCLE);

			assertArrayEquals(gradient.getColors(), rgp.getColors());
			assertEquals(gradient.getRadius(), rgp.getRadius(), 0.001);
			assertEquals(gradient.getCenterPoint(), rgp.getCenterPoint());
		} else
			fail();
	}

	@Test
	public void drawsWithShadowDyZero() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		Shadow shadow = new Shadow(Color.CYAN, PLUS_SHADOW_DX, 0, SHADOW_BLUR_RADIUS);
		int radius = SLIDE_X / 2;
		int containerWidth = (int) Math.round(2.5 * radius);

		Circle classUnderTest = new Circle(new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 2, 0, slide, Color.RED, null,
				shadow);
		classUnderTest.drawCircle(g2d);

		int radiusPx = radius * Math.min(SLIDE_X, SLIDE_Y) / containerWidth;
		int diameterPx = radiusPx * 2;

		int x = SLIDE_X / 2 - radiusPx;
		int y = SLIDE_Y / 2 - radiusPx;

		int maxBlur = ((SLIDE_X - diameterPx) / 2) - PLUS_SHADOW_DX;
		int shadowBlurMapped = maxBlur * SHADOW_BLUR_RADIUS / 100; // Shortened map function

		// Circle
		verify(g2d, times(1)).fillOval(x, y, diameterPx, diameterPx);

		// Shadow
		verify(g2d, times(1)).fillOval(x + PLUS_SHADOW_DX, y - shadowBlurMapped, diameterPx + shadowBlurMapped,
				diameterPx);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(x, y, Color.CYAN, x + diameterPx, y + diameterPx, transparent);

			assertEquals(gradient.getColor1(), gp.getColor1());
			assertEquals(gradient.getColor2(), gp.getColor2());
			assertEquals(gradient.getPoint1(), gp.getPoint1());
			assertEquals(gradient.getPoint2(), gp.getPoint2());
		} else
			fail();
	}

	@Test
	public void drawsWithShadowDxZero() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		Shadow shadow = new Shadow(Color.CYAN, 0, PLUS_SHADOW_DY, SHADOW_BLUR_RADIUS);
		int radius = SLIDE_X / 2;
		int containerWidth = (int) Math.round(2.5 * radius);

		Circle classUnderTest = new Circle(new Point(SLIDE_X / 2, SLIDE_Y / 2), SLIDE_X / 2, 0, slide, Color.RED, null,
				shadow);
		classUnderTest.drawCircle(g2d);

		int radiusPx = radius * Math.min(SLIDE_X, SLIDE_Y) / containerWidth;
		int diameterPx = radiusPx * 2;

		int x = SLIDE_X / 2 - radiusPx;
		int y = SLIDE_Y / 2 - radiusPx;

		int maxBlur = ((SLIDE_X - diameterPx) / 2) - PLUS_SHADOW_DY;
		int shadowBlurMapped = maxBlur * SHADOW_BLUR_RADIUS / 100; // Shortened map function

		// Circle
		verify(g2d, times(1)).fillOval(x, y, diameterPx, diameterPx);

		// Shadow
		verify(g2d, times(1)).fillOval(x - shadowBlurMapped, y + PLUS_SHADOW_DY, diameterPx,
				diameterPx + shadowBlurMapped);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(x, y, Color.CYAN, x + diameterPx, y + diameterPx, transparent);

			assertEquals(gradient.getColor1(), gp.getColor1());
			assertEquals(gradient.getColor2(), gp.getColor2());
			assertEquals(gradient.getPoint1(), gp.getPoint1());
			assertEquals(gradient.getPoint2(), gp.getPoint2());
		} else
			fail();
	}
}
