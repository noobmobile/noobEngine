package com.terminal.engine.gfx.rendering;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.terminal.engine.gfx.lightning.Light;

public class Image {

	private int width, height;
	private int[] pixels;
	private boolean alpha;
	private int lightBlock = Light.NONE;
	protected BufferedImage buffer;
	
	public Image(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.buffer = image;
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
	}
	
	public Image(int[] p, int w, int h) {
		this.pixels = p;
		this.width = w;
		this.height = h;
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the pixels
	 */
	public int[] getPixels() {
		return pixels;
	}


	/**
	 * @return the alpha
	 */
	public boolean isAlpha() {
		return alpha;
	}


	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return the lightBlock
	 */
	public int getLightBlock() {
		return lightBlock;
	}

	/**
	 * @param lightBlock the lightBlock to set
	 */
	public void setLightBlock(int lightBlock) {
		this.lightBlock = lightBlock;
	}
	
	
	
}
