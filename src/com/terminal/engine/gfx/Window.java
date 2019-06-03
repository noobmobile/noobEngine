package com.terminal.engine.gfx;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.terminal.engine.GameContainer;

public class Window {

	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy buffer;
	
	private Graphics2D gpu;
	
	
	public Window(GameContainer container) {
		image = new BufferedImage(container.getWidth(), container.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		canvas = new Canvas();
		Dimension dimension = new Dimension((int)(container.getWidth() * container.getScale()), (int)(container.getHeight() * container.getScale()));
		canvas.setPreferredSize(dimension);
		canvas.setMaximumSize(dimension);
		canvas.setMinimumSize(dimension);
		
		frame = new JFrame(container.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
		gpu = (Graphics2D) buffer.getDrawGraphics();
	}
	
	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	public void update() {
		gpu.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(),null);
		buffer.show();
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @return the canvas
	 */
	public Canvas getCanvas() {
		return canvas;
	}
	
}
