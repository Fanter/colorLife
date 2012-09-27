package ru.fanter.colorlife.line;

import java.awt.*;

import ru.fanter.colorlife.Direction;
import ru.fanter.colorlife.GameWindow;

public class LineSegment {
	private static int WIDTH = 8;
	private static int LENGTH = GameWindow.SQUARE_SIZE;
	private int x;
	private int y;
	private int nextX;
	private int nextY;
	private Direction direction;

	public LineSegment(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		nextX = x;
		nextY = y;
		direction = dir;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getNextX() {
		return nextX;
	}

	public int getNextY() {
		return nextY;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setNextCoords() {
		switch(direction) {
			case RIGHT:
				nextX += GameWindow.SQUARE_SIZE;
				nextY += 0;
				break;
			case LEFT:
				nextX -= GameWindow.SQUARE_SIZE;
				nextY += 0;
				break;
			case UP:
				nextX += 0;
				nextY -= GameWindow.SQUARE_SIZE;
				break;
			case DOWN:
				nextX += 0;
				nextY += GameWindow.SQUARE_SIZE;
				break;
			default:
			    break;
		}
	}

	public void draw(Graphics g) {

		switch(direction) {
			case RIGHT:
				g.fillRect(x - WIDTH/2, y - WIDTH/2 + 1, LENGTH + WIDTH, WIDTH);
				break;
			case LEFT:
				g.fillRect(x - WIDTH/2 - GameWindow.SQUARE_SIZE, y - WIDTH/2 + 1, LENGTH + WIDTH, WIDTH);
				break;
			case UP:
				g.fillRect(x - WIDTH/2, y - GameWindow.SQUARE_SIZE, WIDTH, LENGTH);
				break;
			case DOWN:
				g.fillRect(x - WIDTH/2, y, WIDTH, LENGTH);
				break;
			default:
				break;
		}
	}

	@Override
	public int hashCode() {
		return x + y;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isCoordinatesTheSame;
		boolean isDirectionTheSame;

		if (obj instanceof LineSegment) {
			LineSegment segment = (LineSegment) obj;
			isCoordinatesTheSame = this.x == segment.getX() && this.y == segment.getY();
			isDirectionTheSame = this.direction == segment.getDirection();
			return isCoordinatesTheSame && isDirectionTheSame;
		}
		return false;
	}
}