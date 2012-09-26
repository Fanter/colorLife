package ru.fanter.colorlife;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;

public abstract class DraggableElement {
	private int DIAMETER;
	private int x;
	private int y;
	private int draggedX;
	private int draggedY;
	private boolean draggable = false;
	private boolean selected = false;

	public abstract void draw(Graphics g);
	public abstract ElementType getElementType();
	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {};
	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {};
	public void mouseDragged(MouseEvent e, GamePointers gamePointers, JPanel panel) {};
	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine
													, GameArrows gameArrows, JPanel panel) {};

	public void init(int diameter) {
		this.DIAMETER = diameter;
	}

	public int getDiameter() {
		return DIAMETER;
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

	public void move(int x, int y) {
		this.x = x - draggedX;
		this.y = y - draggedY;
	}

	public void setDraggedCoord(int dx, int dy) {
		draggedX = dx - x;
		draggedY = dy - y;
	}

	public int getDraggedX() {
		return draggedX;
	}

	public int getDraggedY() {
		return draggedY;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean isDraggable) {
		draggable = isDraggable;
	}

	public boolean isSelected() {
		return selected;
	}

	public void isSelected(boolean isSelected) {
		selected = isSelected;
	}

	public boolean isContaining(int coordX, int coordY) {
		boolean isInsideX;
		boolean isInsideY;

		isInsideX = coordX >= x && coordX <= x + DIAMETER;
		isInsideY = coordY >= y && coordY <= y + DIAMETER;
		return isInsideX && isInsideY;
	}

	public boolean setPosition(int dx, int dy) {
		boolean isXInsideFieldCell;
		boolean isYInsideFieldCell;

		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				isXInsideFieldCell = dx > i && dx < i + GameWindow.SQUARE_SIZE;
				isYInsideFieldCell = dy > j && dy < j + GameWindow.SQUARE_SIZE;
				if (isXInsideFieldCell && isYInsideFieldCell) {
					x = i + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					y = j + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					return true;
				}
			}
		}
		return false;
	}
}