package com.terminal.engine;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.terminal.engine.gfx.Renderer;
import com.terminal.engine.gfx.Window;
import com.terminal.engine.gfx.rendering.Font;
import com.terminal.engine.inputing.Input;
import com.terminal.game.levels.manager.LevelManager;

public class GameContainer implements Runnable{

	private Thread thread;
	private Window window;
	private Renderer renderer;
	
	private boolean running = false;
	private final double FPS_LIMIT = 60;
	private final double UPDATE_CAP = 1D/FPS_LIMIT;
	private final double BILLION = 1_000_000_000;
	private int fps;
	private Game game;
	/**
	 * @return the fps
	 */
	public int getFPS() {
		return fps;
	}

	private Input input;

	
	/**
	 * @return the window
	 */
	public Window getWindow() {
		return window;
	}

	private LevelManager levelManager;
	public GameContainer(Game game, LevelManager manager) {
		this.game = game;
		this.levelManager= manager;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return game.getWidth();
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return game.getHeight();
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return game.getScale();
	}
	
	public String getTitle() {
		return game.getTitle();
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		
	}

	public void run() {
		running = true;
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / BILLION;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		
		while (running) {
			render = false; //limitar o fps
			firstTime = System.nanoTime() / BILLION;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				game.update(this, (float) UPDATE_CAP);
				input.update();
				if (frameTime >= 1D) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}
			if (render) {
				renderer.clear();
				game.render(this, renderer);
				renderer.drawString("FPS "+this.getFPS(), 0, 0, Color.cyan, Font.STANDART12, 32);
				renderer.process();
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		dispose();
	}
	
	
	
	/**
	 * @return the input
	 */
	public Input getInput() {
		return input;
	}

	private void dispose() {
		
	}

	/**
	 * @return the levelManager
	 */
	public LevelManager getLevelManager() {
		return levelManager;
	}

	/**
	 * @return the renderer
	 */
	public Renderer getRenderer() {
		return renderer;
	}
	
	
}
