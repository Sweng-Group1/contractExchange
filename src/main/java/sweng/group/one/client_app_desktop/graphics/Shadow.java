package sweng.group.one.client_app_desktop.graphics;

import java.awt.Color;

/**
 * The “Shadow” class stores information about shadows.
 * 
 * @author joe2k01
 *
 */
public class Shadow {
	private final Color shadowColour;
	private final float shadowDx;
	private final float shadowDy;
	private final int shadowBlurRadius;

	/**
	 * Shadow constructor.
	 * 
	 * @param shadowColour     Colour of the shadow
	 * @param shadowDx         x offset of shadow
	 * @param shadowDy         y offset of shadow
	 * @param shadowBlurRadius size of “blurRadius" 0 to 100
	 */
	public Shadow(Color shadowColour, float shadowDx, float shadowDy, int shadowBlurRadius) {
		this.shadowColour = shadowColour;
		this.shadowDx = shadowDx;
		this.shadowDy = shadowDy;
		this.shadowBlurRadius = shadowBlurRadius;
	}

	public Color getShadowColour() {
		return shadowColour;
	}

	public float getShadowDx() {
		return shadowDx;
	}

	public float getShadowDy() {
		return shadowDy;
	}

	public int getShadowBlurRadius() {
		return shadowBlurRadius;
	}
}
