package com.terminal.game.levels;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.Renderer;
import com.terminal.engine.gfx.lightning.Light;
import com.terminal.engine.gfx.rendering.Font;
import com.terminal.engine.sfx.SoundClip;
import com.terminal.game.entities.Player;
import com.terminal.game.entities.renderizable.Renderizable;
import com.terminal.game.levels.manager.Level;

public class Level1 extends Level{


	@Override
	public void update(GameContainer container, float dt) {
		if (container.getInput().isKeyDown(KeyEvent.VK_ENTER)) container.getLevelManager().setLevel(0);
		if (container.getInput().isKeyDown(KeyEvent.VK_1)) container.getRenderer().ambientColor = container.getRenderer().ambientColor == Color.white.getRGB() ? Color.darkGray.getRGB() : Color.WHITE.getRGB();
		for (int i = 0; i < toRender.size(); i++) {
			Renderizable r = toRender.get(i);
			r.update(container, dt);
			if (r.dead) {
				toRender.remove(i);
				i--;
			}
		}
	}

	@Override
	public void render(GameContainer container, Renderer render) {
		for (int i = 0; i < toRender.size(); i++) {
			Renderizable r = toRender.get(i);
			r.render(container, render);
			if (r.dead) {
				toRender.remove(i);
				i--;
			}
		}
	}

	@Override
	public void init() {
		toRender = new ArrayList<Renderizable>();
		toRender.add(new Player(100, 100,64,64));
	}

}
