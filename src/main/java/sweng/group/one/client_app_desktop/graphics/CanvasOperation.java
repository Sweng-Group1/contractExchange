package sweng.group.one.client_app_desktop.graphics;

import java.awt.Graphics;

/**
 * Interface that provides a callback for graphical operations.
 * 
 * @author joe2k01
 *
 */
public interface CanvasOperation {
	/**
	 * A callback to be executed withing a JCanvas to draw graphics on screen.
	 * 
	 * @param g The current Graphics object.
	 */
	public void draw(Graphics g);
}
