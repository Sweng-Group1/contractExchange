package sweng.group.one.client_app_desktop.graphics;

import java.awt.Color;

public class Shadow {
	private final Color shadowColour;
	private final float shadowDx;
	private final float shadowDy;
	private final int shadowBlurRadius;

	public Shadow(Color shadowColour, float shadowDx, float shadowDy, int shadowBlurRadius) {
		this.shadowColour = shadowColour;
		this.shadowDx = shadowDx;
		this.shadowDy = shadowDy;
		this.shadowBlurRadius = shadowBlurRadius;

		// 100 because of rectangle meaning 100% of parent.
		if (shadowBlurRadius > 100 || shadowBlurRadius < 0) {
			throw new IllegalArgumentException("The shadow blur radius cannot exceed 100 and cannot be less than 0.");
		}

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
