package com.escaperoom.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Button extends JButton implements MouseListener {
	

	public Button(String buttonLabel, int width, int height) {
		super.setText(buttonLabel);
		super.setMaximumSize(new Dimension(width, height));
		super.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		super.setFont(new Font("Arial", Font.BOLD, 25));

		
		
		super.setBorderPainted(false);
		super.setBackground(new Color(33, 33, 33));
		super.setForeground(Color.WHITE); //Sets the text color of the button
		super.setOpaque(true);
		super.addMouseListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		super.setBackground(Color.red);
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		super.setBackground(new Color(33, 33, 33));
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		super.setBackground(new Color(33, 33, 33));
		
	}

}
