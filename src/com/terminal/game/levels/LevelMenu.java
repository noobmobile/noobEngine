package com.terminal.game.levels;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.Renderer;
import com.terminal.engine.gfx.rendering.Font;
import com.terminal.engine.sfx.SoundClip;
import com.terminal.game.levels.manager.Level;

public class LevelMenu extends Level{

	private int choice;
	private SoundClip opening, move;

	
	@Override
	public void update(GameContainer container, float dt) {
		int oldChoice = choice;
		if (container.getInput().isKeyDown(KeyEvent.VK_S)) {
			choice +=1;
		} else if (container.getInput().isKeyDown(KeyEvent.VK_W)) {
			choice -=1;
		}
		if (choice < 0) choice = 2;
		else if (choice > 2) choice = 0;
		
		if (container.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
			switch(choice) {
			case 0:
				container.getLevelManager().setLevel(1);
			}
		}
		
		if (oldChoice != choice) move.play();
	}

	@Override
	public void render(GameContainer container, Renderer render) {
		render.drawString("Terminal", container.getWidth()/2-145, container.getHeight()/4, Color.white, Font.STANDART36, 0);
		
		render.drawString("IM READY", container.getWidth()/2-115, container.getHeight()/2, choice == 0 ? Color.yellow : Color.white, Font.STANDART24, 0);
		render.drawString("TAKE ME OUT", container.getWidth()/2-145, container.getHeight()/2+40, choice == 1 ? Color.yellow : Color.white, Font.STANDART24, 0);
		render.drawString("HELP ME PLS", container.getWidth()/2-140, container.getHeight()/2+80, choice == 2 ? Color.yellow : Color.white, Font.STANDART24, 0);
		
	}

	@Override
	public void init() {
		opening = new SoundClip("/opening.wav");
		move = new SoundClip("/pick.wav");
		move.setVolume(-20);
		opening.setVolume(-20);
		opening.play();		
		choice = 0;
	}

}
