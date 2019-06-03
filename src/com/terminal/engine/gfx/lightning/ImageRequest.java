package com.terminal.engine.gfx.lightning;

import com.terminal.engine.gfx.rendering.Image;

public class ImageRequest {

	public Image image;
	public int zDepth, offX, offY;
	public ImageRequest(Image image, int zDepth, int offX, int offY) {
		super();
		this.image = image;
		this.zDepth = zDepth;
		this.offX = offX;
		this.offY = offY;
	}

	
	
}
