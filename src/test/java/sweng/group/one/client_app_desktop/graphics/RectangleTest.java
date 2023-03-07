package sweng.group.one.client_app_desktop.graphics;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;

import org.junit.Test;
import org.mockito.Mockito;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class RectangleTest {
	private static final int SLIDE_X = 800;
	private static final int SLIDE_Y = 400;
	private static final int BORDER_W = 5;
	private static final int PLUS_SHADOW_DX = 5;
	private static final int PLUS_SHADOW_DY = 10;
	private static final int MINUS_SHADOW_DX = -5;
	private static final int MINUS_SHADOW_DY = -10;
	private static final int SHADOW_BLUR_RADIUS = 25;

	@Test
	public void drawsRectangleNoShadowNoBorder() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Paint p = new Color(0, 0, 0, 0);
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new java.awt.Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);
		when(g2d.getPaint()).thenReturn(p);

		Rectangle classUnderTest = new Rectangle(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, Color.RED, null, null);
		classUnderTest.drawRect(g2d);

		verify(g2d, times(1)).setPaint(Color.RED);
		verify(g2d, times(1)).fillRect(0, 0, SLIDE_X, SLIDE_Y);
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
		Rectangle classUnderTest = new Rectangle(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, Color.RED, border, null);
		classUnderTest.drawRect(g2d);

		int calculatedBorderWidth = (int) (border.getBorderWidth()
				* (slide.getWidth() / (float) slide.getPointWidth()));
		int calculatedHalfBorderWidth = calculatedBorderWidth / 2;
		verify(g2d, times(1)).setStroke(new BasicStroke(calculatedBorderWidth));
		verify(g2d, times(1)).setPaint(Color.BLACK);

		// Border
		verify(g2d, times(1)).drawRect(calculatedHalfBorderWidth, calculatedHalfBorderWidth,
				SLIDE_X - 2 * calculatedHalfBorderWidth, SLIDE_Y - 2 * calculatedHalfBorderWidth);

		// Rectangle
		verify(g2d, times(1)).fillRect(calculatedHalfBorderWidth, calculatedHalfBorderWidth,
				SLIDE_X - 2 * calculatedHalfBorderWidth, SLIDE_Y - 2 * calculatedHalfBorderWidth);
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
		Rectangle classUnderTest = new Rectangle(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, Color.RED, null, shadow);
		classUnderTest.drawRect(g2d);

		// / 4 and * 3 / 4 because shadow blur radius is 25% of width

		// Shadow
		verify(g2d, times(1)).fillRect(0, 0, SLIDE_X * 3 / 4 - PLUS_SHADOW_DX,
				SLIDE_Y - (SLIDE_X / 4) - PLUS_SHADOW_DY);

		// Rectangle
		verify(g2d, times(1)).fillRect(PLUS_SHADOW_DX, PLUS_SHADOW_DY, SLIDE_X, SLIDE_Y);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(0, 0, Color.CYAN, SLIDE_X, SLIDE_Y, transparent);

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
		Rectangle classUnderTest = new Rectangle(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, Color.RED, null, shadow);
		classUnderTest.drawRect(g2d);

		// / 4 and * 3 / 4 because shadow blur radius is 25% of width

		// Shadow
		verify(g2d, times(1)).fillRect(SLIDE_X / 4 - MINUS_SHADOW_DX, SLIDE_X / 4 - MINUS_SHADOW_DY,
				SLIDE_X * 3 / 4 + MINUS_SHADOW_DX, SLIDE_Y - (SLIDE_X / 4) + MINUS_SHADOW_DY);

		// Rectangle
		verify(g2d, times(1)).fillRect(0, 0, SLIDE_X + MINUS_SHADOW_DX, SLIDE_Y + MINUS_SHADOW_DY);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(SLIDE_X, SLIDE_Y, Color.CYAN, 0, 0, transparent);

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
		Rectangle classUnderTest = new Rectangle(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, Color.RED, null, shadow);
		classUnderTest.drawRect(g2d);

		int mappedBlurRadius = SLIDE_X / 4; // 25%
		int halfMappedBlurRadius = mappedBlurRadius / 2;

		// Rectangle
		verify(g2d, times(1)).fillRect(halfMappedBlurRadius, halfMappedBlurRadius, SLIDE_X - mappedBlurRadius,
				SLIDE_Y - mappedBlurRadius);

		// Shadow
		verify(g2d, times(1)).fillRect(0, 0, SLIDE_X, SLIDE_Y);

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
		Rectangle classUnderTest = new Rectangle(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, Color.RED, null, shadow);
		classUnderTest.drawRect(g2d);

		// / 4 and * 3 / 4 because shadow blur radius is 25% of width

		// Rectangle
		verify(g2d, times(1)).fillRect(0, 0, SLIDE_X * 3 / 4 - PLUS_SHADOW_DX, SLIDE_Y - (SLIDE_X / 4));

		// Shadow
		verify(g2d, times(1)).fillRect(PLUS_SHADOW_DX, 0, SLIDE_X, SLIDE_Y);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(0, 0, Color.CYAN, SLIDE_X, SLIDE_Y, transparent);

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
		Rectangle classUnderTest = new Rectangle(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, Color.RED, null, shadow);
		classUnderTest.drawRect(g2d);

		// (/ 4) and (* 3 / 4) because shadow blur radius is 25% of width

		// Rectangle
		verify(g2d, times(1)).fillRect(0, 0, SLIDE_X - (SLIDE_X / 4), SLIDE_Y - (SLIDE_X / 4) - PLUS_SHADOW_DY);

		// Shadow
		verify(g2d, times(1)).fillRect(0, PLUS_SHADOW_DY, SLIDE_X, SLIDE_Y);

		Paint calculatedGradient = classUnderTest.getGradient();
		if (calculatedGradient instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) calculatedGradient;

			Color transparent = new Color(Color.CYAN.getRed(), Color.CYAN.getBlue(), Color.CYAN.getGreen(), 0);
			GradientPaint gradient = new GradientPaint(0, 0, Color.CYAN, SLIDE_X, SLIDE_Y, transparent);

			assertEquals(gradient.getColor1(), gp.getColor1());
			assertEquals(gradient.getColor2(), gp.getColor2());
			assertEquals(gradient.getPoint1(), gp.getPoint1());
			assertEquals(gradient.getPoint2(), gp.getPoint2());
		} else
			fail();
	}
}
