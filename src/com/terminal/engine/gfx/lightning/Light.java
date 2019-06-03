package com.terminal.engine.gfx.lightning;

import java.awt.Color;

public class Light {

	public static final int NONE = 0;
	public static final int FULL = 1;
	
	private int[] lm;
	private int radius, color, diameter;
	public Light(int radius, int color) {
		this.radius = radius;
		this.color = color;
		this.diameter = radius*2;
		lm = new int[diameter * diameter];
		for (int y = 0; y < diameter; y++) {
		for (int x = 0; x < diameter; x++) {
				double distance = Math.sqrt((x-radius) * (x-radius) + (y-radius) * (y-radius));
				if (distance < radius) {
					double power = 1 - (distance / radius);
					lm[x + y * diameter] = ((int)(((color >> 16) & 0xff) * power) << 16 | (int)(((color >> 8) & 0xff) * power) << 8 | (int)((color & 0xff) * power));
				}else {
					lm[x + y * diameter] = 0; 
				}
			}	
		}
		
	}
	
	public int getLightValue(int x, int y) {
		if (x < 0 || x >= diameter || y < 0 || y >= diameter) return 0; 
		return lm[x + y * diameter];
	}
	
	/**
	 * @return the lm
	 */
	public int[] getLm() {
		return lm;
	}
	/**
	 * @param lm the lm to set
	 */
	public void setLm(int[] lm) {
		this.lm = lm;
	}
	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}
	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}
	/**
	 * @return the diameter
	 */
	public int getDiameter() {
		return diameter;
	}
	/**
	 * @param diameter the diameter to set
	 */
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	
}
