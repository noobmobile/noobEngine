package com.terminal.game.levels.manager;

import java.util.List;

import com.terminal.engine.GameContainer;

public class LevelManager {

	private List<Level> levels;
	private int atualLevel;
	
	public LevelManager(List<Level> levels) {
		this.levels = levels;
		atualLevel = 0;
		levels.get(atualLevel).init();
	}
	
	public void setLevel(int level) {
		if (atualLevel > levels.size()) return;
		atualLevel = level;
		levels.get(atualLevel).init();
	}
	
	public Level getAtualLevel() {
		return levels.get(atualLevel);
	}
	
}
