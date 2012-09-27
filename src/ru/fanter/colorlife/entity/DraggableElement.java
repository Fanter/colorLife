package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import javax.swing.*;

import java.awt.event.*;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.line.GameLine;

public abstract class DraggableElement extends Element{
	private int draggedX;
	private int draggedY;
	private boolean draggable = false;
	private boolean selected = false;

	public abstract void draw(Graphics g);
	public abstract ElementType getElementType();
	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {};
	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {};
	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
													JPanel panel) {};
    public void mouseDragged(MouseEvent e, GamePointers gamePointers, JPanel panel) {
        Pointer pointer = gamePointers.getPointer(e.getX(), e.getY());

        if (this.isDraggable() && !pointer.isContainingElements()) {
            this.setPosition(e.getX(), e.getY());
            panel.repaint();
        }
    }

	public void move(int x, int y) {
		setX(x - draggedX);
		setY(y - draggedY);
	}

	public void setDraggedCoord(int mouseX, int mouseY) {
		draggedX = mouseX - getX();
		draggedY = mouseY - getY();
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

	public boolean isContaining(int mouseX, int mouseY) {
		boolean isInsideX;
		boolean isInsideY;

		isInsideX = mouseX >= getX() && mouseX <= getX() + getSize();
		isInsideY = mouseY >= getY() && mouseY <= getY() + getSize();
		return isInsideX && isInsideY;
	}
}