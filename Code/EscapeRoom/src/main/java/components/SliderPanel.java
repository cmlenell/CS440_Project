package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SliderPanel extends JPanel {

	private JLabel sliderLabel;
	private JSlider slider;

	private GridLayout gridLayout;

	public SliderPanel(String sliderName) {
		sliderLabel = new JLabel(sliderName);
		sliderLabel.setFont(new Font("Arial", Font.BOLD, 25));
		sliderLabel.setForeground(Color.white);
		
		slider = new JSlider(JSlider.HORIZONTAL, 45, 155, 85); //(slider direction, min value, max value, default value)
		slider.setMajorTickSpacing(5); // Increment and decrement the slider by 1
		slider.setBackground(Color.black);
		
		gridLayout = new GridLayout(1, 4); //1 row, 4 columns (padding, label, slider, padding)

		//Set JPanel layout and background color
		super.setLayout(gridLayout);
		super.setBackground(new Color(0,0,0,0));
		//super.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Add components to the JPanel
		addPadding(10, 10);
		super.add(sliderLabel);
		super.add(slider);
		addPadding(10, 10);

	}

	// Adds an empty component to the JPanel that acts as padding for the other
	// components
	private void addPadding(int width, int height) {
		super.add(Box.createRigidArea(new Dimension(width, height)));
	}

	public double getValue() {
		return slider.getValue() * .001;
	}
}
