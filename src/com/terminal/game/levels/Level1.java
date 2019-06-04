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
import com.terminal.engine.gfx.rendering.Image;
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
	
	public void loadLevel(String path) {
		Image image = new Image(path);
		
		levelW = image.getWidth();
		levelH = image.getHeight();
		collision = new boolean[levelW * levelH];
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int index = x + y * levelW;
				if (image.getPixels()[index] == 0xff000000) {
					collision[index] = true;
				} else {
					collision[index] = false;
				}
			}	
		}
	}

	@Override
	public void render(GameContainer container, Renderer render) {
		
		for (int y = 0; y < levelH; y++) {
			for (int x = 0; x < levelW; x++) {
				int index = x + y * levelW;
				render.setPixel(x, y, !collision[index] ? 0xff0f0f0f : 0xfff9f9f9);
			}	
		}
		
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
		toRender.add(new Player(100, 100,64,64, this));
		loadLevel("/plats.png");
	}

}
