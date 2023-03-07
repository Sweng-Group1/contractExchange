package sweng.group.one.client_app_desktop.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class JCanvas extends JPanel {
	private final CanvasOperation canvasOperation;
	
	public JCanvas(CanvasOperation canvasOperation) {
		this.canvasOperation = canvasOperation;
		
		this.setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
		
		canvasOperation.draw(arg0);
	}
}
