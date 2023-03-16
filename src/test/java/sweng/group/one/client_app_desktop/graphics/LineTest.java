package sweng.group.one.client_app_desktop.graphics;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import sweng.group.one.client_app_desktop.presentation.Slide;


public class LineTest {
	private static final int SLIDE_X = 800;
	private static final int SLIDE_Y = 400;
	private static final int THICKNESS = 5;

	@Test
	public void graphicsOperationsArePerformed() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// G2d Mock
		BasicStroke bs = new BasicStroke();
		Graphics2D g2d = Mockito.mock(Graphics2D.class);
		when(g2d.getClip()).thenReturn(new Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getClip().getBounds()).thenReturn(new Rectangle(SLIDE_X, SLIDE_Y));
		when(g2d.getStroke()).thenReturn(bs);

		Line classUnderTest = new Line(new Point(0, 0), SLIDE_X, SLIDE_Y, 0, slide, THICKNESS, new Point(0, 0),
				new Point(SLIDE_X, SLIDE_Y), Color.BLACK);
		classUnderTest.drawLine(g2d);

		int calculatedThickness = (int) (THICKNESS * (slide.getWidth() / (float) slide.getPointWidth()));

		verify(g2d, times(1)).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		verify(g2d, times(1)).setStroke(new BasicStroke(calculatedThickness));
		verify(g2d, times(1)).setStroke(bs);
		verify(g2d, times(1)).drawLine(0, 0, SLIDE_X, SLIDE_Y);
	}

	@Test
	public void cannotDrawOutsideOfBoundingBox() {
		Slide slide = new Slide(SLIDE_X, SLIDE_Y);

		// pos.x > from.x
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(10, 0), SLIDE_X, SLIDE_Y, 0, slide, THICKNESS, new Point(0, 0),
					new Point(SLIDE_X, SLIDE_Y), Color.BLACK);
		});

		// pos.x > to.x
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(10, 0), SLIDE_X, SLIDE_Y, 0, slide, THICKNESS, new Point(10, 0), new Point(0, SLIDE_Y),
					Color.BLACK);
		});

		// pos.y > from.y
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(0, 10), SLIDE_X, SLIDE_Y, 0, slide, THICKNESS, new Point(0, 0),
					new Point(SLIDE_X, SLIDE_Y), Color.BLACK);
		});

		// pos.y > to.y
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(0, 10), SLIDE_X, SLIDE_Y, 0, slide, THICKNESS, new Point(0, 10), new Point(SLIDE_X, 0),
					Color.BLACK);
		});

		// (pos.x + width) < from.x
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(0, 0), SLIDE_X / 4, SLIDE_Y, 0, slide, THICKNESS, new Point(SLIDE_X / 3, 0),
					new Point(SLIDE_X / 4, SLIDE_Y), Color.BLACK);
		});

		// (pos.x + width) < to.x
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(0, 0), SLIDE_X / 4, SLIDE_Y, 0, slide, THICKNESS, new Point(0, 0),
					new Point(SLIDE_X, SLIDE_Y), Color.BLACK);
		});

		// (pos.y + height) < from.y
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(0, 0), SLIDE_X, SLIDE_Y / 4, 0, slide, THICKNESS, new Point(0, SLIDE_Y / 3),
					new Point(SLIDE_X, SLIDE_Y / 4), Color.BLACK);
		});

		// (pos.y + height) < to.y
		assertThrows(IllegalArgumentException.class, () -> {
			new Line(new Point(0, 0), SLIDE_X, SLIDE_Y / 4, 0, slide, THICKNESS, new Point(0, 0),
					new Point(SLIDE_X, SLIDE_Y), Color.BLACK);
		});
	}
}
