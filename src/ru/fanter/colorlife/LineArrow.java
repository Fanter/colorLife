package ru.fanter.colorlife;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.util.List;
import java.util.Iterator;

public class LineArrow {
	public static final int SIZE = 36;
	private int initX;	//coordinates of line arrow when it was pressed by mouse
	private int initY;	//used to return arrow to its initial coordinates if position where
								//arrow was placed forbidden(e.g. occupied by another element)

	private int lineX;			//used to build a game line
	private int lineY;			//
	private int x;				//coordinates of line arrow top left corner
	private int y;				//
	private int draggedX;		//distance between top left corner of line arrow
	private int draggedY;		//and place where it was clicked by mouse
	private boolean isMoved = false;
	private boolean draggable = false;
	private boolean selected = false; 
	private Direction direction;

	public LineArrow() {
		x = GameWindow.SQUARE_SIZE / 2;
		y = GameWindow.FIELD_SIZE / 2;
		setLinePosition();
		setPosition(x, y);
		direction = Direction.RIGHT;
	}

	public void move(int x, int y) {
		this.x = x - draggedX;
		this.y = y - draggedY;
	}

	public void setLineX(int lineX) {
		this.lineX = lineX;
	}

	public int getLineX() {
		return lineX;
	}

	public void setLineY(int lineY) {
		this.lineY = lineY;
	}

	public int getLineY() {
		return lineY;
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

	public void setDraggedCoord(int dx, int dy) {
		draggedX = dx - x;
		draggedY = dy - y;
	}

	public void setDirection(Direction newDirection) {
		direction = newDirection;
	}

	public Direction getDirection() {
		return direction;
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

	public boolean setPosition(int dx, int dy) {
		boolean firstCondition;
		boolean secondCondition;

		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				firstCondition = dx > i && dx < i + GameWindow.SQUARE_SIZE;
				secondCondition = dy > j && dy < j + GameWindow.SQUARE_SIZE;
				if (firstCondition && secondCondition) {
					x = i + GameWindow.SQUARE_SIZE/2 - SIZE/2;
					y = j + GameWindow.SQUARE_SIZE/2 - SIZE/2;
					return true;
				}
			}
		}
		return false;
	}

	public boolean setLinePosition() {
		boolean firstCondition;
		boolean secondCondition;

		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				firstCondition = x >= i && x <= i + GameWindow.SQUARE_SIZE;
				secondCondition = y >= j && y <= j + GameWindow.SQUARE_SIZE;
				if (firstCondition && secondCondition) {
					lineX = i + GameWindow.SQUARE_SIZE/2;
					lineY = j + GameWindow.SQUARE_SIZE/2;
					return true;
				}
			}
		}
		return false;
	}

	public boolean isContaining(int coordX, int coordY) {
		boolean isInsideX;
		boolean isInsideY;

		isInsideX = coordX >= x && coordX <= x + SIZE;
		isInsideY = coordY >= y && coordY <= y + SIZE;
		return isInsideX && isInsideY;
	}

	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isSelected()) {
			setDirection(direction.next());
			gamePointers.setPointer(this.getX(), this.getY(), getDirection());
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {

		if (e.getY() > GameWindow.FIELD_SIZE || !(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else if (this.isContaining(e.getX(), e.getY())) {
			this.isSelected(true);
			initX = this.getX();
			initY = this.getY();

			gamePointers.resetPointer(this.getX(), this.getY());
			this.setDraggable(true);
			this.setDraggedCoord(e.getX(), e.getY());
		} 
	}

	public void mouseDragged(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		Pointer pointer = gamePointers.getPointer(e.getX(), e.getY());
		Direction[] direction = pointer.getDirectionArray();

		if (this.isSelected() && this.isDraggable() && direction[0] == Direction.NONE) {
			this.setPosition(e.getX(), e.getY());
			panel.repaint();
		}
	}

	public void mouseReleased(MouseEvent e, GamePointers gamePointers, 
					GameLine gameLine, JPanel panel) {
		if (this.isSelected()) {
			this.setDraggable(false);

			this.setLinePosition();
			gamePointers.setPointer(this.getX(), this.getY(), this.getDirection());
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, SIZE, SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SIZE, SIZE);
	}
}