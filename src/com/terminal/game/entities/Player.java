package com.terminal.game.entities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.terminal.engine.GameContainer;
import com.terminal.engine.gfx.Renderer;
import com.terminal.engine.gfx.rendering.ImageTile;
import com.terminal.game.entities.renderizable.Renderizable;
import com.terminal.game.levels.manager.Level;

public class Player extends Renderizable{

	public enum PlayerSprite{
		IDLE(0,0,1,2,3), WALK(1,0,1,2,3), ATTACK(2,PlayerSprite.IDLE,0,1,2,3,2,1,0),DEATH(3,true,0,1,2,2,3,4,5,6), FLYING(5,5,6), FLYING_BACK(5,PlayerSprite.IDLE,5,4,3,2,1,0), START_FLY(5,PlayerSprite.FLYING,0,1,2,3,4,5,6);
		
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
	
	private int acceleration = 3;
	private int moveX, moveY;
	private Level level;
	
	public Player(int x, int y, int width, int height, Level level) {
		tile = new ImageTile("/skeleton.png", 64, 64); 
		sprite = PlayerSprite.ATTACK;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		moveX = 0;
		moveY = 0;
		this.level = level;
	}
	
	private boolean flip;
	private float i, j, fallSpeed = 10, fallDistance;
	@Override
	public void update(GameContainer container, float dt) {

		if (!level.getCollision((int)x, (int)y+moveY) && !level.getCollision((int)x+10, (int)y+moveY+10) && !level.getCollision((int)x+10+moveX, (int)y+moveY+10)) {
			y+=moveY;
		} 
		if (!level.getCollision((int)x+moveX, (int)y) && !level.getCollision((int)x+moveX+10, (int)y+10) && !level.getCollision((int)x+moveX+10, (int)y+10+moveY)) {
			x+=moveX;
		} 
		
		
		if (container.getInput().isKey(KeyEvent.VK_D)) {
			moveX = acceleration;
		}
		if (container.getInput().isKey(KeyEvent.VK_A)) {
			moveX = -acceleration;
		}
		if (container.getInput().isKey(KeyEvent.VK_W)) {
			moveY = -acceleration;
		}
		if (container.getInput().isKey(KeyEvent.VK_S)) {
			moveY = acceleration;
		}
		
		
		
		if (moveX > 0) {
			moveX -= 1; //friccao
			flip = true; // inverte o sprite
			sprite = PlayerSprite.WALK; // muda o sprite
		}
		else if (moveX < 0) {
			moveX +=1; //friccao
			flip = false; // inverte o sprite
			sprite = PlayerSprite.WALK; // muda o sprite
		} else {
			sprite = PlayerSprite.IDLE; // parado
		}
		
		if (moveY > 0) moveY-= 1;
		if (moveY < 0) moveY+= 1;

		
		
		i+=dt*8;
		int index = (int)i;
		if (index >= sprite.x.length) {
			if (sprite.hasChangeAnimation()) {
				sprite = sprite.changeAnimation;
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
		
		if (container.getInput().isMouseDown(MouseEvent.BUTTON1)) {
			j++;
			if (j >= PlayerSprite.values().length) j=0;
			sprite = PlayerSprite.values()[(int) j];
			sprite.atualX = 0;
			sprite.rendering = true;
			
		}		
	}

	@Override
	public void render(GameContainer container, Renderer render) {
		//if (sprite.rendering) render.drawImage(tile.getTileImage(sprite.atualX, sprite.y, flip), (int)x, (int)y);
		render.fillRect((int)x, (int)y, 10, 10, Color.red.getRGB());
	}
	
	
}
