package com.terminal.game;

import java.awt.Color;
import java.util.Arrays;

import com.terminal.engine.Game;
import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.Renderer;
import com.terminal.engine.gfx.lightning.Light;
import com.terminal.engine.gfx.rendering.Font;
import com.terminal.engine.gfx.rendering.Image;
import com.terminal.engine.gfx.rendering.ImageTile;
import com.terminal.engine.sfx.SoundClip;
import com.terminal.game.levels.Level1;
import com.terminal.game.levels.LevelMenu;
import com.terminal.game.levels.manager.LevelManager;

public class JAJG extends Game{

	public JAJG(int width, int height, float scale, String title) {
		super(width, height, scale, title);
	}

	@Override
	public void update(GameContainer container, float dt) {
		container.getLevelManager().getAtualLevel().update(container, dt);
	}

	@Override
	public void render(GameContainer container, Renderer render) {
		container.getLevelManager().getAtualLevel().render(container, render);
	}

}
