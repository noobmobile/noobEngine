package com.terminal.game.levels.manager;

import java.util.List;

import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.Renderer;
import com.terminal.game.entities.renderizable.Renderizable;

public abstract class Level {

	public abstract void update(GameContainer container, float dt) ;
	public abstract void render(GameContainer container, Renderer render);
	public abstract void init();
	protected List<Renderizable> toRender;
	public boolean[] collision;
	public int levelW, levelH;
	public boolean getCollision(int x, int y) {
		if (x < 0 || x >= levelW || y < 0 || y >= levelH) return true;
		return collision[x + y * levelW];
	}
	public boolean getCollision(float x, float y) {
		if (x < 0 || x >= levelW || y < 0 || y >= levelH) return true;
		return collision[((int)x) + ((int)y) * levelW];
	}
	
}
