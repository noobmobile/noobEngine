package com.terminal.game;

import java.util.Arrays;

import com.terminal.engine.GameContainer;
import com.terminal.game.levels.Level1;
import com.terminal.game.levels.LevelMenu;
import com.terminal.game.levels.manager.LevelManager;

public class Launcher {

	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new JAJG(640, 320, 2f, "?"), new LevelManager(Arrays.asList(new LevelMenu(), new Level1())));
		gc.start();
	}
	
}
