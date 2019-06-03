package com.terminal.game.entities.renderizable;

import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.Renderer;

public abstract class Renderizable {

	public float x, y;
	public int width, height;
	public boolean dead;
	public abstract void update(GameContainer container, float dt) ;
	public abstract void render(GameContainer container, Renderer render);
	
}
