package com.terminal.engine.gfx.rendering;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ImageTile extends Image {

	private int tileWidth, tileHeight;

	public ImageTile(String path, int tileWidth, int tileHeight) {
		super(path);
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	public Image getTileImage(int tileX, int tileY, boolean flip) {
		if (flip) {
			int[] p = new int[tileWidth * tileHeight];
			for (int y = 0; y < tileHeight; y++) {
				for (int x = 0; x < tileWidth; x++) {
					p[x + y * tileWidth] = this.getPixels()[(x + tileX * tileWidth)
							+ (y + tileY * tileHeight) * this.getWidth()];
				}
			}
			return new Image(p, tileWidth, tileHeight);

		} else {
			int[] p = new int[tileWidth * tileHeight];
			BufferedImage fl = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
			int[] b = ((DataBufferInt) fl.getRaster().getDataBuffer()).getData();
			for (int y = 0; y < tileHeight; y++) {
				for (int x = 0; x < tileWidth; x++) {
					p[x + y * tileWidth] = this.getPixels()[(x + tileX * tileWidth)
							+ (y + tileY * tileHeight) * this.getWidth()];
					b[x + y * tileWidth] = this.getPixels()[(x + tileX * tileWidth)
							+ (y + tileY * tileHeight) * this.getWidth()];
				}
			}
			return new Image(((DataBufferInt) flipHoriz(fl).getRaster().getDataBuffer()).getData(), tileWidth,
					tileHeight);

		}
	}

	public BufferedImage flipHoriz(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = newImage.createGraphics();
		gg.drawImage(image, image.getHeight(), 0, -image.getWidth(), image.getHeight(), null);
		gg.dispose();
		return newImage;
	}

	/**
	 * @return the tileWidth
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * @return the tileHeight
	 */
	public int getTileHeight() {
		return tileHeight;
	}

}
