package com.terminal.game.entities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.Renderer;
import com.terminal.engine.gfx.rendering.Font;
import com.terminal.engine.gfx.rendering.ImageTile;
import com.terminal.game.entities.renderizable.Renderizable;
import com.terminal.game.levels.manager.Level;

public class Player extends Renderizable{

	public enum PlayerSprite{
		//IDLE(0,0,1,2,3), WALK(1,0,1,2,3), ATTACK(2,PlayerSprite.IDLE,0,1,2,3,2,1,0),DEATH(3,true,0,1,2,2,3,4,5,6), FLYING(5,5,6), FLYING_BACK(5,PlayerSprite.IDLE,5,4,3,2,1,0), START_FLY(5,PlayerSprite.FLYING,0,1,2,3,4,5,6);
		IDLE(1,0,1,2,3), WALK(2,0,1,2,3);
		public int y;
		public int[] x;
		public int atualX;
		public boolean rendering = true, stopRenderingOnEnd = false;
		public PlayerSprite changeAnimation;
		
		PlayerSprite(int y, int... x) {
			this.y = y;
			this.x = x;
		}
		
		PlayerSprite(int y,boolean stopRenderingOnEnd, int... x) {
			this.y = y;
			this.x = x;
			this.stopRenderingOnEnd = true;
		}
		
		PlayerSprite(int y, PlayerSprite change, int... x) {
			this.y = y;
			this.x = x;
			this.changeAnimation = change;
		}
		
		public boolean hasChangeAnimation() {
			return changeAnimation != null;
		}
		
		public boolean isOnLastFrame() {
			return atualX >= x.length;
		}
		
		
	}
	
	
	private ImageTile tile;
	private PlayerSprite sprite;
	
	private int acceleration = 2;
	private int jumpAcceleration = 15;
	private int moveX, moveY;
	private Level level;
	
	public class Chunk{
		public int x,y;

		public Chunk(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		
		
	}
	
	public Player(int x, int y, int width, int height, Level level) {
		tile = new ImageTile("/sla.png", 16, 24); 
		sprite = PlayerSprite.IDLE;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		moveX = 0;
		moveY = 0;
		this.level = level;
	}
	
	private boolean flip;
	private int sprX = 15;
	private int sprY = 15;
	private int jumps = 0;
	private float i;
	private ArrayList<Chunk> chunks = new ArrayList<>();
	@Override
	public void update(GameContainer container, float dt) {

		
		if (level.getCollision(x+moveX, y)) {
			while (!level.getCollision(x+Math.signum(moveX), y)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x+moveX+sprX, y+sprY)) {
			while (!level.getCollision(x+Math.signum(moveX)+sprX, y+sprY)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x+moveX+sprX, y)) {
			while (!level.getCollision(x+Math.signum(moveX)+sprX, y)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x+moveX, y+sprY)) {
			while (!level.getCollision(x+Math.signum(moveX), y+sprY)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x+moveX, y+moveY)) {
			while (!level.getCollision(x+Math.signum(moveX), y+moveY)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x+moveX+sprX, y+sprY+moveY)) {
			while (!level.getCollision(x+Math.signum(moveX)+sprX, y+sprY+moveY)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x+moveX+sprX, y+moveY)) {
			while (!level.getCollision(x+Math.signum(moveX)+sprY, y+moveY)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x+moveX, y+sprY+moveY)) {
			while (!level.getCollision(x+Math.signum(moveX), y+sprY+moveY)) x+=Math.signum(moveX);
			moveX = 0;
		}
		
		if (level.getCollision(x, y+moveY)) {
			while (!level.getCollision(x, y+moveY)) y+=Math.signum(moveY);
			moveY = 0;
		}
		
		if (level.getCollision(x, y+moveY+sprY)) {
			while (!level.getCollision(x, y+moveY+sprY)) y+=Math.signum(moveY);
			moveY = 0;
		}
		
		if (level.getCollision(x+sprX, y+moveY)) {
			while (!level.getCollision(x+sprX, y+moveY)) y+=Math.signum(moveY);
			moveY = 0;
		}
		
		if (level.getCollision(x+sprX, y+moveY+sprY)) {
			while (!level.getCollision(x+sprX, y+moveY+sprY)) y+=Math.signum(moveY);
			moveY = 0;
		}
		
		if (level.getCollision(x+moveX, y+moveY)) {
			while (!level.getCollision(x+moveX, y+moveY)) y+=Math.signum(moveY);
			moveY = 0;
		}
		
		if (level.getCollision(x+moveX, y+moveY+sprY)) {
			while (!level.getCollision(x+moveX, y+moveY+sprY)) y+=Math.signum(moveY);
			moveY = 0;
		}
		
		if (level.getCollision(x+sprX+moveX, y+moveY)) {
			while (!level.getCollision(x+sprX, y+moveY)) y+=Math.signum(moveY);
			moveY = 0;
		}
		
		if (level.getCollision(x+sprX+moveX, y+moveY+sprY)) {
			while (!level.getCollision(x+sprX+moveX, y+moveY+sprY)) y+=Math.signum(moveY);
			moveY = 0;
		}

		boolean ground = level.getCollision(x, y+1) || level.getCollision(x, y+sprY+1) || level.getCollision(x+sprX, y+sprY+1);
		
		x+=moveX;
		y+=moveY;
		
		chunks.add(new Chunk((int)x, (int)y));
		chunks.add(new Chunk((int)x+sprX, (int)y));
		chunks.add(new Chunk((int)x, (int)y+sprY));
		chunks.add(new Chunk((int)x+sprX, (int)y+sprY));

		if (!ground && moveY <= 8) moveY += 2; // gravidade
		else jumps = 0;
		/*if (container.getInput().isKeyDown(KeyEvent.VK_SPACE)) {
			if (sprite == PlayerSprite.FLYING) sprite = PlayerSprite.FLYING_BACK;
			else sprite = PlayerSprite.START_FLY;
		}*/
		
		
		if (moveX > 0) {
			moveX -= 1; //friccao
			flip = true; // inverte o sprite
			if (sprite == PlayerSprite.WALK || sprite == PlayerSprite.IDLE) sprite = PlayerSprite.WALK; // muda o sprite
		}
		else if (moveX < 0) {
			moveX +=1; //friccao
			flip = false; // inverte o sprite
			if (sprite == PlayerSprite.WALK || sprite == PlayerSprite.IDLE) sprite = PlayerSprite.WALK; // muda o sprite
		} else {
			if (sprite == PlayerSprite.WALK || sprite == PlayerSprite.IDLE) sprite = PlayerSprite.IDLE; // muda o sprite
		}
		if (moveY > 0) moveY-= 1;
		if (moveY < 0) moveY+= 1;
		
		if (container.getInput().isKey(KeyEvent.VK_D)) {
			moveX = (int) (acceleration * (container.getInput().isKey(KeyEvent.VK_SHIFT) ? 1.5 : 1));
		}
		if (container.getInput().isKey(KeyEvent.VK_A)) {
			moveX = (int) (-acceleration * (container.getInput().isKey(KeyEvent.VK_SHIFT) ? 1.5 : 1));
		}
		if (container.getInput().isKeyDown(KeyEvent.VK_W)) {
			if (jumps < 1) {
				moveY = -jumpAcceleration;
				jumps++;
			}
		}
		
		i+=dt*8;
		int index = (int)i;
		if (index >= sprite.x.length) {
			if (sprite.hasChangeAnimation()) {
				sprite = sprite.changeAnimation;
				sprite.atualX = sprite.x[0];
			} else {
				if (sprite.stopRenderingOnEnd) {
					sprite.rendering = false;
				} else {
					sprite.atualX = sprite.x[0];
				}
			}
			i=0;
		} else {
			sprite.atualX = sprite.x[index];
		}
		
	}

	@Override
	public void render(GameContainer container, Renderer render) {
		//if (sprite.rendering) render.drawImage(tile.getTileImage(sprite.atualX, sprite.y, flip), (int)x, (int)y);
	render.fillRect((int)x, (int)y, sprX+1, sprY+1, Color.red.getRGB());
		render.drawString("MOVEX "+moveX+" MOVEY "+moveY+" JUMPS "+jumps, 60, 0, Color.cyan, Font.STANDART12, 32);
		for (Chunk c : chunks) {
			render.drawRect(c.x, c.y, 1, 1, Color.green.getRGB());
		}

			chunks.clear();
	}
	
	
}
