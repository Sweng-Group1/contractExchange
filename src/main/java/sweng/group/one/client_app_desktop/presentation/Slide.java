package sweng.group.one.client_app_desktop.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Slide extends JPanel {
	
	private int pointWidth;
	private int pointHeight;
	private ArrayList<PresElement> elements;

	//example constructor
	public Slide(int PtWidth, int PtHeight){
		super(new GridBagLayout());
		
		this.setElements(new ArrayList<>());
		
		this.setPointWidth(PtWidth);
		this.setPointHeight(PtHeight);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		//fill the grid with glue -> sets up grid system
		for (int x = 0; x < pointWidth; x++) {
			for (int y = 0; y < pointHeight; y++) {
				gbc.gridx = x;
				gbc.gridy = y;
				this.add(Box.createGlue(), gbc);
			}
		}
		this.validate();
	}
	
	/**
	 * Adds a given PresElement to this Slide
	 * 
	 * @param element - PresElement to be added
	 * @throws IllegalArgumentException
	 */
	public void add(PresElement element) throws IllegalArgumentException{
		
		if (element.pos.x + element.width > pointWidth ||
				element.pos.y + element.height > pointHeight) {
			throw new IllegalArgumentException("Element the dimensions of this slide");
		}
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = element.pos.x;
		gbc.gridy = element.pos.y;
		gbc.gridwidth = element.width;
		gbc.gridheight = element.height;
		
		this.add(element.component, gbc);
		this.getElements().add(element);
	}
	
	public void displaySlide() {
		for (PresElement e:getElements()) {
			e.displayElement();
		}
	}

	public int getPointWidth() {
		return pointWidth;
	}

	public void setPointWidth(int pointWidth) {
		this.pointWidth = pointWidth;
	}

	public int getPointHeight() {
		return pointHeight;
	}

	public void setPointHeight(int pointHeight) {
		this.pointHeight = pointHeight;
	}

	public ArrayList<PresElement> getElements() {
		return elements;
	}

	public void setElements(ArrayList<PresElement> elements) {
		this.elements = elements;
	}
}
