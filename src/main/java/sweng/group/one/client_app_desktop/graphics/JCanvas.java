package sweng.group.one.client_app_desktop.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * JCanvas is the component used by shapes to draw on. It's a simple JPanel.
 * 
 * @author joe2k01
 *
 */
public class JCanvas extends JPanel {
	private final CanvasOperation canvasOperation;

	/**
	 * JCanvas constructor.
	 * 
	 * @param canvasOperation Callback for graphical operations
	 */
	public JCanvas(CanvasOperation canvasOperation) {
		this.canvasOperation = canvasOperation;

		this.setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);

		canvasOperation.draw(arg0);
	}
}
