package com.terminal.engine.gfx;

import java.awt.Color;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.sound.sampled.Line;

import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.lightning.ImageRequest;
import com.terminal.engine.gfx.lightning.Light;
import com.terminal.engine.gfx.lightning.LightRequest;
import com.terminal.engine.gfx.rendering.Font;
import com.terminal.engine.gfx.rendering.Image;
import com.terminal.engine.gfx.rendering.ImageTile;

public class Renderer {

	private int screenWidth, screenHeight;
	private int [] zb;
	private int[] lm, lb, pixels;
	
	private int zDepth = 0;
	private ArrayList<ImageRequest> imageRequests;
	private ArrayList<LightRequest> lightRequests;
	private boolean processing = false;
	private Comparator<ImageRequest> comparatorImage;
	public int ambientColor = Color.white.getRGB();
	/**
	 * @return the type
	 */

	public Renderer(GameContainer container) {
		screenWidth = container.getWidth();
		screenHeight = container.getHeight();
		pixels = ((DataBufferInt)container.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zb = new int[pixels.length];
		lm = new int[pixels.length];
		lb = new int[pixels.length];
		imageRequests = new ArrayList<>();
		lightRequests = new ArrayList<>();
		comparatorImage = new Comparator<ImageRequest>() {

			@Override
			public int compare(ImageRequest var1, ImageRequest var2) {
				if (var1.zDepth < var2.zDepth) return -1;
				else if (var1.zDepth > var2.zDepth) return 1;
				return 0;
			}
			
		};
		
	}

	public void clear() {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = 0;
				zb[i] = 0;
				lm[i] = ambientColor;
			}
	}
	
	public void setPixel(int x, int y, int value) {
		int alpha = (value >> 24) & 0xff;
		if (x < 0 || x >= screenWidth || y < 0 || y >= screenHeight || (alpha) == 0) return;
		int index = x + y * screenWidth;
		if (zb[index] > zDepth) return;
		zb[index] = zDepth;
		if (alpha == 255) pixels[index] = value;
		else {
			int pixelColor = pixels[index];
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) -  ((value >> 16) & 0xff)) * (alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) -  ((value >> 8) & 0xff)) * (alpha / 255f));
			int newBlue = ((pixelColor) & 0xff) - (int)((((pixelColor) & 0xff) -  ((value) & 0xff)) * (alpha / 255f));

			pixels[index] = (newRed << 16 | newGreen << 8 | newBlue);
		}
	}
	
	public int getColor(int x, int y) {
		if (x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) return -1;
		return pixels[x + y * screenWidth];
	}
	
	public void setLightMap(int x, int y, int value) {
		if (x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) return;

		int index = x + y * screenWidth;
		int baseColor = lm[index];
		
		int maxRed = Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
		int maxGreen = Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
		int maxBlue = Math.max(baseColor & 0xff, value & 0xff);
		
		lm[index] = (maxRed << 16 | maxGreen << 8 | maxBlue);
		
	}
	
	public void setLightBlock(int x, int y, int value) {
		if (x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) return;

		if (zb[x + y * screenWidth] > zDepth) return;
		
		lb[x + y * screenWidth] = value;
		
	}
	
	public void process() {
		processing = true;
		Collections.sort(imageRequests, comparatorImage);
		for (int i = 0; i < imageRequests.size(); i++) {
			ImageRequest request = imageRequests.get(i);
			setzDepth(request.zDepth);
			drawImage(request.image, request.offX, request.offY);
		}
		
		for (int i = 0; i <  lightRequests.size(); i++) {
			LightRequest request = lightRequests.get(i);
			this.drawLightRequest(request.light, request.x, request.y);
		}
		
		for (int i = 0; i < pixels.length; i++) {
			float red = ((lm[i] >> 16) & 0xff) / 255f;
			float green = ((lm[i] >> 8) & 0xff) / 255f;
			float blue = ((lm[i]) & 0xff) / 255f;
			pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * red) << 16 | (int) (((pixels[i] >> 8) & 0xff) * green) << 8 | (int)((pixels[i] & 0xff) * blue));
		}
		
		
		
		
		imageRequests.clear();
		lightRequests.clear();
		processing = false;
	}
	
	public void drawImage(Image image, int offX, int offY) {

		if (image.isAlpha() && !processing) {
			imageRequests.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}
		
		if (offX < -image.getWidth()) return;
		if (offY < -image.getHeight()) return;
		if (offX >= screenWidth) return;
		if (offY >= screenHeight) return;
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();
		
		if (offX < 0) newX -= offX;
		if (offY < 0) newY -= offY;
		if (newWidth + offX > screenWidth) newWidth -= (newWidth + offX - screenWidth);
		if (newHeight + offY > screenHeight) newHeight -= (newHeight + offY - screenHeight);

		for (int y = newY; y < newHeight; y++) {
		for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
				setLightBlock(x + offX, y+ offY, image.getLightBlock());
			}
		}
	}
	
	private void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY, boolean flip) {
		if (image.isAlpha() && !processing) {
			imageRequests.add(new ImageRequest(image.getTileImage(tileX, tileY, flip), zDepth, offX, offY));
			return;
		}
		
		if (offX < -image.getTileWidth()) return;
		if (offY < -image.getTileHeight()) return;
		if (offX >= screenWidth) return;
		if (offY >= screenHeight) return;
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileWidth();
		int newHeight = image.getTileHeight();
		
		if (offX < 0) newX -= offX;
		if (offY < 0) newY -= offY;
		if (newWidth + offX > screenWidth) newWidth -= (newWidth + offX - screenWidth);
		if (newHeight + offY > screenHeight) newHeight -= (newHeight + offY - screenHeight);

		for (int y = newY; y < newHeight; y++) {
		for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getPixels()[(x+ tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
				setLightBlock(x + offX, y+ offY, image.getLightBlock());
		}
		}
	}

	private void drawLightRequest(Light l, int offX, int offY) {
		for (int i = 0; i <= l.getDiameter(); i++) {
			drawLightLine(l, l.getRadius(), l.getRadius(),i,0,offX,offY);
			drawLightLine(l, l.getRadius(), l.getRadius(),i,l.getDiameter(),offX,offY);
			drawLightLine(l, l.getRadius(), l.getRadius(),0,i,offX,offY);
			drawLightLine(l, l.getRadius(), l.getRadius(),l.getDiameter(),i,offX,offY);
		}
	}
	
	public void drawLight(Light l, int offX, int offY) {
		this.lightRequests.add(new LightRequest(offX, offY, l));
	}
	
	private void drawLightLine(Light l, int x0, int y0, int x1, int y1, int offX, int offY) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int e2;
		
		while(true) {
			int screenX = x0 - l.getRadius() + offX;
			int screenY  = y0 - l.getRadius() + offY;
			
			if(screenX < 0 || screenX >= screenWidth || screenY < 0 || screenY >= screenHeight) {
				return;
			}
			
			int lightColor = l.getLightValue(x0, y0);
			if (lightColor == 0) {
				return;
			}
			
			if (lb[screenX + screenY * screenWidth] == Light.FULL) return;
			
			setLightMap(screenX, screenY, lightColor);
			if (x0 == x1 && y0 == y1) break;
			e2 = 2 *err;
			if (e2 > -1 * dy) {
				err -= dy;
				x0+=sx;
			}
			if (e2 < dx) {
				err+=dx;
				y0+=sy;
			}
		}
		
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color) {
		for (int x = 0; x < width; x++) {
			setPixel(x+offX, offY, color);
			setPixel(x+offX, offY+height, color);

		}
		for (int y = 0; y < height; y++) {
			setPixel(offX, y + offY, color);
			setPixel(offX+width, y + offY, color);
		}
	}
	
	public void fillRect(int offX, int offY, int width, int height, int color) {
	
		if (offX < -width) return;
		if (offY < -height) return;
		if (offX >= screenWidth) return;
		if (offY >= screenHeight) return;
		
		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;
		
		if (offX < 0) newX -= offX;
		if (offY < 0) newY -= offY;
		if (newWidth + offX > screenWidth) newWidth -= (newWidth + offX - screenWidth);
		if (newHeight + offY > screenHeight) newHeight -= (newHeight + offY - screenHeight);

		
		for (int y = newY; y < newHeight; y++) {
		for (int x = newX; x < newWidth; x++) {
				setPixel(x+offX, y+offY, color);
			}
		}
	}
	
	public void drawString(String text, int offX, int offY, int color, Font font, int off) {
		int offset = 0;
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - off;
			for (int y = 0; y < font.getFontImage().getHeight(); y++) {
				for (int x = 0; x < font.getWidths()[unicode]; x++) {
					if (font.getFontImage().getPixels()[(x+ font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
						setPixel(x + offX +offset , y+offY, color);
					}
				}
			}
			offset+= font.getWidths()[unicode];
		}
	}

	public void drawString(String text, int offX, int offY, Color color, Font font, int off) {
		drawString(text, offX, offY, color.getRGB(), font, off);
	}
	
	/**
	 * @return the zDepth
	 */
	public int getzDepth() {
		return zDepth;
	}

	/**
	 * @param zDepth the zDepth to set
	 */
	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
	
	
}
