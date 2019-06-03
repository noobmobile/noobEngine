package com.terminal.engine;

import com.terminal.engine.gfx.Renderer;
import com.terminal.engine.inputing.Input;

public abstract class Game {

	public abstract void update(GameContainer container, float dt);
	public abstract void render(GameContainer container, Renderer render);

	private int width = 320, height = 240;
	private float scale = 1f;
	private String title = "?";
	
	public Game(int width, int height, float scale, String title) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.title = title;
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
	 * @return the scale
	 */
	public float getScale() {
		return scale;
	}
	
	public String getTitle() {
		return title;
	}
	
}
