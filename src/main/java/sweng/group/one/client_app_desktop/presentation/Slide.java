package sweng.group.one.client_app_desktop.presentation;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * @author flt515
 *
 */
@SuppressWarnings("serial")
public class Slide extends JPanel implements LayoutManager {
	
	private int pointWidth;
	private int pointHeight;
	private ArrayList<PresElement> elements;

	//example constructor
	public Slide(int ptWidth, int ptHeight){
		super();
		this.setLayout(this);
		this.setElements(new ArrayList<>());
		this.setPointWidth(ptWidth);
		this.setPointHeight(ptHeight);
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
		
		this.add(element.component);
		this.getElements().add(element);
		element.component.validate();
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
	
	/**
	 * Needed by LayoutManager
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * Needed by LayoutManager
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
	}

	/**
	 *	Finds the preferred layout size for a given parent container (with a fixed aspect ratio)
	 *
	 *	@param parent: The parent container for the layout to be relative to
	 *
	 *	@return Dimension of preferred layout within parent
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int slideX = getPointWidth();
		int slideY = getPointHeight();
		int w = parent.getWidth();
		int h = parent.getHeight();
		
		float slideAspectRatio = (float)slideX/slideY;
		float presAspectRatio = (float)w/h;
		
		Dimension layoutSize = null;
		if(slideAspectRatio >= presAspectRatio) {
			layoutSize = new Dimension(w, (int) (w/slideAspectRatio));
		}
		else {
			layoutSize = new Dimension((int) (h*slideAspectRatio), h);
		}
		return layoutSize;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	/**
	 * Set the bounds of children components
	 */
	@Override
	public void layoutContainer(Container parent) {
		float w = parent.getWidth();
		float h = parent.getHeight();
		
		float cellW = w/pointWidth;
		float cellH = h/pointHeight;
		
		for(PresElement e : elements) {
			float eWidth = e.width * cellW;
			float eHeight = e.height * cellH;
			
			float eX = e.pos.x * cellW;
			float eY = e.pos.y * cellH;
			
			e.component.setBounds((int)eX, (int)eY, (int)eWidth, (int)eHeight);
		}
	}
}
