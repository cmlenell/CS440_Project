package com.escaperoom.ui.component;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Picture extends JLabel {

	
	public Picture(String imagePath, int width, int height) {
		//Store image in mainImage
		Image mainImage = new ImageIcon(imagePath).getImage();
		
		//Scale the image to the specified width and height
		mainImage = mainImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		//Add image to JLabel and align it to the center
		this.setIcon(new ImageIcon(mainImage));			
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
}
