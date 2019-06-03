package com.terminal.engine.inputing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.terminal.engine.GameContainer;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{

	private GameContainer container;
	
	private boolean[] mouse = new boolean[5];
	private boolean[] mouseLast = new boolean[5];
	private int mouseX, mouseY, scroll;
	
	private boolean[] keys = new boolean[256];
	private boolean[] keysLast = new boolean[256];
	
	public Input(GameContainer container) {
		this.container = container;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		container.getWindow().getCanvas().addKeyListener(this);
		container.getWindow().getCanvas().addMouseMotionListener(this);
		container.getWindow().getCanvas().addMouseWheelListener(this);
		container.getWindow().getCanvas().addMouseListener(this);;
	}

	
	public boolean isKey(int code) {
		return keys[code];
	}
	
	public boolean isKeyUp(int code) {
		return !keys[code] && keysLast[code];
	}
	
	public boolean isKeyDown(int code) {
		return keys[code] && !keysLast[code];
	}
	
	public boolean isMouse(int code) {
		return mouse[code];
	}
	
	public boolean isMouseUp(int code) {
		return !mouse[code] && mouseLast[code];
	}
	
	public boolean isMouseDown(int code) {
		return mouse[code] && !mouseLast[code];
	}
	
	/**
	 * @return the mouseX
	 */
	public int getMouseX() {
		return mouseX;
	}



	/**
	 * @return the mouseY
	 */
	public int getMouseY() {
		return mouseY;
	}



	/**
	 * @return the scroll
	 */
	public int getScroll() {
		return scroll;
	}



	public void update() {
		scroll  = 0;
		for (int i = 0; i < keys.length; i++) {
			keysLast[i] = keys[i];
		}
		for (int i = 0; i < mouse.length; i++) {
			mouseLast[i] = mouse[i];
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent paramMouseEvent) {
		mouseX = (int)(paramMouseEvent.getX() / container.getScale());
		mouseY = (int)(paramMouseEvent.getY() / container.getScale());
	}

	@Override
	public void mouseMoved(MouseEvent paramMouseEvent) {
		mouseX = (int)(paramMouseEvent.getX() / container.getScale());
		mouseY = (int)(paramMouseEvent.getY() / container.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent paramMouseEvent) {
		mouse[paramMouseEvent.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent paramMouseEvent) {
		mouse[paramMouseEvent.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent paramKeyEvent) {
		keys[paramKeyEvent.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent paramKeyEvent) {
		keys[paramKeyEvent.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent paramKeyEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent paramMouseWheelEvent) {
		scroll = paramMouseWheelEvent.getWheelRotation();
	}
	
}
