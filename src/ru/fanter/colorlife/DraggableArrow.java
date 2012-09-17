package ru.fanter.colorlife;

import java.awt.*;

public abstract class DraggableArrow {
	private final int DIAMETER = 16;
	private final int SMALL_DIAMETER = 8;
	private int x;
	private int y;
	private int draggedX;		//how far away mouse arrow from top 
	private int draggedY;		//left corner of draggable arrow
	private boolean draggable = false;
	private Direction direction = Direction.RIGHT;
	private Color color = Color.RED;

	public abstract void draw(Graphics g);

	public void init(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		direction = dir;
	}

	public void move(int x, int y) {
		this.x = x - draggedX;
		this.y = y - draggedY;
	}

	public int getDiameter() {
		return DIAMETER;
	}

	public int getSmallDiameter() {
		return SMALL_DIAMETER;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDraggedCoord(int dx, int dy) {
		draggedX = dx - x;
		draggedY = dy - y;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public boolean isDraggable() {
		return draggable;
	}

	/*
	 *set arrow position in the center of game square
	 *square selected depending on position of 
	 *top left corner of the arrow image
	 *return false if position cannot be established
	 */
	public boolean setPosition() {
		boolean firstCondition;
		boolean secondCondition;

		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_X; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
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

	public boolean setPosition(int dx, int dy) {
		boolean firstCondition;
		boolean secondCondition;

		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_X; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				firstCondition = dx > i && dx < i + GameWindow.SQUARE_SIZE;
				secondCondition = dy > j && dy < j + GameWindow.SQUARE_SIZE;
				if (firstCondition && secondCondition) {
					x = i + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					y = j + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					return true;
				}
			}
		}
		return false;
	}

	/*
	 *Check if position of mouse arrow 
	 *is inside draggable arrow area
	 */
	public boolean isContaining(int coordX, int coordY) {
		boolean isInsideX;
		boolean isInsideY;

		isInsideX = coordX >= x && coordX <= x + DIAMETER;
		isInsideY = coordY >= y && coordY <= y + DIAMETER;
		return isInsideX && isInsideY;
	}

	/*
	 *Check if there is another object inside 
	 *cell that contains this coordinates
	 */
	public boolean isInsideCellContaining(int coordX, int coordY) {
		boolean isThisArrowInsideCell1 = false;
		boolean isThisArrowInsideCell2 = false ;
		boolean isAnotherArrowInsideCell1 = false;
		boolean isAnotherArrowInsideCell2 = false;

		for(int i = 0; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = 0; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				isThisArrowInsideCell1 = x >= i && x <= i + GameWindow.SQUARE_SIZE;
				isThisArrowInsideCell2 = y >= j && y <= j + GameWindow.SQUARE_SIZE;
				isAnotherArrowInsideCell1 = coordX >= i && coordX <= i + GameWindow.SQUARE_SIZE;
				isAnotherArrowInsideCell2 = coordY >= j && coordY <= j + GameWindow.SQUARE_SIZE;

				if (isThisArrowInsideCell1 && isThisArrowInsideCell2 && 
									isAnotherArrowInsideCell1 && isAnotherArrowInsideCell2) {
					x = i + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					y = j + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					return true;
				}
			}
		}
		return false;
	}
}