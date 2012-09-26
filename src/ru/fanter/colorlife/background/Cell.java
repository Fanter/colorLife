package ru.fanter.colorlife.background;

import java.awt.Graphics;

public class Cell {
	private int direction = 0;
	private int x = 0;
	private int y = 0;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void draw(Graphics g) {
		g.drawRect(x, y, GameWindow.SQUARE_SIZE, GameWindow.SQUARE_SIZE);
	}
}