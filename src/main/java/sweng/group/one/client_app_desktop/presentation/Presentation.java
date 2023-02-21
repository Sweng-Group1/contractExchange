package sweng.group.one.client_app_desktop.presentation;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author flt515
 *
 */
@SuppressWarnings("serial")
public class Presentation extends JPanel {
	private ArrayList<Slide> slides;
	private int currentSlide;
	
	public Presentation(List<Slide> slides){
		this.slides = new ArrayList<>();
		for(Slide s: slides) {
			this.addSlide(s);
		}
		
		currentSlide = 0;
		showCurrentSlide();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeCurrentSlide();
			}
		});
	}
	
	public void addSlide(Slide newSlide) {
		slides.add(newSlide);
		this.add(newSlide);
		newSlide.setVisible(false);
	}
	
	private void showCurrentSlide() {
		Slide desiredSlide = slides.get(currentSlide);
		for (Slide slide:slides) {
			slide.setVisible(slide == desiredSlide);
		}
		desiredSlide.displaySlide();
		resizeCurrentSlide();
		desiredSlide.validate();
	}
	
	public void nextSlide() {
		int maxSlide = slides.size()-1;
		currentSlide = (currentSlide + 1) % maxSlide;
		showCurrentSlide();
	}
	
	public void prevSlide() {
		int maxSlide = slides.size()-1;
		currentSlide--;
		currentSlide = currentSlide > 0 ? currentSlide : maxSlide;
		showCurrentSlide();
	}
	
	public Slide getCurrentSlide() {
		return slides.get(currentSlide);
	}
	
	public List<Slide> getSlides() {
		return slides;
	}
	
	
	/*
	 * Resize currentSlide to keep a fixed aspect ratio with reference to
	 * the size of the Presentation
	 */
	private void resizeCurrentSlide() {
		Slide currentSlide = getCurrentSlide();
		Dimension preferredLayout = currentSlide.preferredLayoutSize(this);
		currentSlide.setPreferredSize(preferredLayout);
	}
}
