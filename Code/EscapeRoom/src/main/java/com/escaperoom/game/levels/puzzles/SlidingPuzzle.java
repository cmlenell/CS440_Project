package com.escaperoom.game.levels.puzzles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SlidingPuzzle extends JPanel implements MouseListener {

	// Block Area
	private JPanel blocksArea;
	private int gridSize;
	private ArrayList<JPanel> blocks;
	
	private JButton exitButton;
	
	private boolean solved = false;
	private boolean quit = false;

	public SlidingPuzzle(int gridSize) {
		this.gridSize = gridSize;

		super.setLayout(new BorderLayout());
		createComponents();
		addComponents();
	}

	private void addComponents() {
		//Add all the blocks to the blockArea JPanel
		for (JPanel block : blocks) {
			blocksArea.add(block);
		}
		
		super.add(blocksArea);
		super.add(exitButton, BorderLayout.SOUTH);
		
	}

	private void createComponents() {
		blocksArea = new JPanel();
		blocksArea.setLayout(new GridLayout(gridSize, gridSize, 3, 3)); // 3,3 is the hgap and vgap
		blocksArea.setPreferredSize(new Dimension(1000, 800));
		
		blocks = generateBlocks();
		Collections.shuffle(blocks);
		
		exitButton = new JButton("QUIT PUZZLE");
		exitButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
		exitButton.addMouseListener(this);

	}

	private ArrayList<JPanel> generateBlocks() {
		int numOfBlocks = (gridSize * gridSize);
		ArrayList<JPanel> randomOrderBlocks = new ArrayList<JPanel>();

		// For each block
		for (int i = 0; i < numOfBlocks - 1; i++) {
			// Create a new JPanel
			JPanel panel = new JPanel();
			panel.setBackground(Color.LIGHT_GRAY);
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.addMouseListener(this);

			// Create JLabel for number
			JLabel number = new JLabel(String.valueOf(i + 1));
			number.setFont(new Font("Times New Roman", Font.BOLD, 50));
			panel.add(number);

			// Add the JPanel to the blocks array
			randomOrderBlocks.add(panel);
		}

		// Add a JPanel for a blank spot
		JPanel panel = new JPanel();
		panel.addMouseListener(this);
		randomOrderBlocks.add(panel);

		return randomOrderBlocks;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JPanel block = getClickedBlock(e.getSource());

		// If the mouse didn't enter a blank spot
		if (block != null && block.getComponentCount() != 0) {
			block.setBackground(Color.gray);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JPanel block = getClickedBlock(e.getSource());

		// If the mouse didn't enter a blank spot
		if (block != null && block.getComponentCount() != 0) {
			block.setBackground(Color.LIGHT_GRAY);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		JPanel clickedBlock = getClickedBlock(e.getSource());

		if(clickedBlock != null) {
			int clickedBlockIndex = blocks.indexOf(clickedBlock);
	
			// Get the top, bottom, left, and right blocks from the clicked block
			int topBlockIndex = clickedBlockIndex - gridSize;
			int bottomBlockIndex = clickedBlockIndex + gridSize;
			int leftBlockIndex = clickedBlockIndex - 1;
			int rightBlockIndex = clickedBlockIndex + 1;
	
			// Get the index of the blank block
			int blankBlockIndex = getBlankPanelIndex();
	
			// If the clicked block is adjacent to a blank block
			if (blankBlockIndex != -1 && (blankBlockIndex == topBlockIndex || blankBlockIndex == bottomBlockIndex
					|| blankBlockIndex == leftBlockIndex || blankBlockIndex == rightBlockIndex)) {
	
				// Swap block positions
				Collections.swap(blocks, blankBlockIndex, clickedBlockIndex);
	
				redrawPanel();
	
				super.repaint();
				super.revalidate();
	
			}
		}

	}

	// Redraws the blocks after two blocks were swapped and also checks if the
	// puzzle was solved
	private void redrawPanel() {
		// Redraw the blocks and check if order is correct
		blocksArea.removeAll();

		boolean isInOrder = true; //Assume the blocks are in order
		for (int i = 0; i < blocks.size(); i++) {
			JPanel block = blocks.get(i);
			blocksArea.add(block);

			
			if (isInOrder && block.getComponentCount() != 0) {
				// Get the current block number
				int blockNumber = Integer.valueOf(((JLabel) block.getComponent(0)).getText());

				// If the block number is not in the same order as i (not in order)
				if (blockNumber != (i + 1)) {
					isInOrder = false;
				}
			}
		}
		
		//If the blocks are in order
		if(isInOrder) {
			//Puzzle has been solved
			solved = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Object src = e.getSource();
		
		if(src.equals(exitButton)) {
			System.out.println("Quit");
			quit = true;
		}

	}

	private JPanel getClickedBlock(Object src) {
		for (JPanel block : blocks) {
			if (src.equals(block)) {
				return block;
			}
		}
		return null;
	}

	private int getBlankPanelIndex() {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).getComponentCount() == 0) {
				return i;
			}
		}
		return -1;
	}
	

	public boolean isPuzzleSolved() {
		return solved;
	}
	
	public boolean isQuitting() {
		return quit;
	}

}
