package com.terminal.engine.gfx.rendering;

public class Font {

	private Image fontImage;
	private int[] offsets, widths;
	public final static Font STANDART12 = new Font("/font12.png");
	public final static Font STANDART24 = new Font("/font24.png");
	public final static Font STANDART36 = new Font("/font36.png");
	
	public Font(String path) {
		fontImage = new Image(path);
		
		offsets = new int[256];
		widths = new int[256];
		int unicode = 0;
		for (int x = 0; x < fontImage.getWidth(); x++) {
			if (fontImage.getPixels()[x] == 0xff0000ff) {
				offsets[unicode] = x;
			}
			
			if (fontImage.getPixels()[x] == 0xffffff00) {
				widths[unicode] = x - offsets[unicode];
				unicode++;
			}
		}
		
	}

	/**
	 * @return the fontImage
	 */
	public Image getFontImage() {
		return fontImage;
	}

	/**
	 * @return the offsets
	 */
	public int[] getOffsets() {
		return offsets;
	}

	/**
	 * @return the widths
	 */
	public int[] getWidths() {
		return widths;
	}
	
}
