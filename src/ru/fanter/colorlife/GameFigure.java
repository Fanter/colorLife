package ru.fanter.colorlife;

import java.awt.Graphics;
import java.awt.Color;

public class GameFigure {
	private final int DIAMETER = 36;
	private int x;
	private int y;
	private int dx = 2;
	private int dy = 2;
	private Direction direction = Direction.RIGHT;

	public GameFigure() {
		x = GameWindow.SQUARE_SIZE / 2;
		y = GameWindow.FIELD_SIZE / 2;
		setPosition();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void move() {
		switch(direction) {
			case UP:
				this.x += 0;
				this.y -= dy;
				break;
			case RIGHT:
				this.x += dx;
				this.y += 0;
				break;
			case DOWN:
				this.x += 0;
				this.y += dy;
				break;
			case LEFT:
				this.x -= dx;
				this.y += 0;
				break;
			default:
				break;
		}//switch(direction)
	}//move()

	public boolean setPosition() {
		boolean firstCondition;
		boolean secondCondition;

		for(int i = 0; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = 0; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				firstCondition = x >= i && x <= i + GameWindow.SQUARE_SIZE;
				secondCondition = y >= j && y <= j + GameWindow.SQUARE_SIZE;
				if (firstCondition && secondCondition) {
					x = i + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					y = j + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					return true;
				}
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, DIAMETER, DIAMETER);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, DIAMETER, DIAMETER);
	}

	public void checkDirection(GamePointers gamePointers) {
		Pointer pointer = gamePointers.getPointer(x, y);

		switch(pointer.getDirection()) {
			case DOWN:
				switch(direction) {
					case RIGHT:
						if (x > pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setX(pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.DOWN;
						}
						break;
					case LEFT:
						if (x < pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setX(pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.DOWN;
						}
						break;
					case UP:
						if (y < pointer.getY() +GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setY(pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.DOWN;
						}
						break;
					default:
						break;
				}//switch(direction)
				break;
			case LEFT:
				switch(direction) {
					case RIGHT:
						if (x > pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setX(pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.LEFT;
						}
						break;
					case DOWN:
						if (y > pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setY(pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.LEFT;
						}
						break;
					case UP:
						if (y < pointer.getY() +GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setY(pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.LEFT;
						}
						break;
					default:
						break;
				}//switch(direction)
				break;
			case RIGHT:
				switch(direction) {
					case LEFT:
						if (x < pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setX(pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.RIGHT;
						}
						break;
					case DOWN:
						if (y > pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setY(pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.RIGHT;
						}
						break;
					case UP:
						if (y < pointer.getY() +GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setY(pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.RIGHT;
						}
						break;
					default:
						break;
				}//switch(direction)
				break;
			case UP:
				switch(direction) {
					case RIGHT:
						if (x > pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setX(pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.UP;
						}
						break;
					case DOWN:
						if (y > pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setY(pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.UP;
						}
						break;
					case LEFT:
						if (x < pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2) {
							setX(pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
							direction = Direction.UP;
						}
						break;
					default:
						break;
				}//switch(direction)
				break;
			default:
				break;
		}//switch(pointer.getDirection())
	}//checkDirection
}