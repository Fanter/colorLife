package ru.fanter.colorlife;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Recycler extends DraggableElement {
	private final int DIAMETER = 36;
	private Pointer pointer;
	private ElementType elementType = ElementType.RECYCLER;

	public Recycler() {
		super.init(DIAMETER);
	}

	public ElementType getElementType() {
		return elementType;
	}
	
	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {}

	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		if (!(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else {
			this.isSelected(true);
			this.setDraggable(true);
			this.setDraggedCoord(e.getX(), e.getY());
			gamePointers.resetPointer(this.getX(), this.getY());
			pointer = gamePointers.getPointer(this.getX(), this.getY());
			pointer.isRecycler(false);
		} 
	}

	public void mouseDragged(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		pointer = gamePointers.getPointer(e.getX(), e.getY());

		if (this.isDraggable() && !pointer.isContainingElements()) {
			this.setPosition(e.getX(), e.getY());
			panel.repaint();
		}
	}

	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine
													, GameArrows gameArrows, JPanel panel) {
		if (this.isSelected()) {
			this.setDraggable(false);
			pointer = gamePointers.getPointer(this.getX(), this.getY());
			if (e.getY() > GameWindow.FIELD_SIZE + GameWindow.INDENT_Y) {
				gamePointers.resetPointer(this.getX(), this.getY());
				pointer.isRecycler(false);
			} else {
				gamePointers.setPointer(this.getX(), this.getY(), Direction.END, Direction.END);
				pointer.isRecycler(true);
			}
			gameLine.constructLines();
			panel.repaint();
		}//if(this.isSelecter())
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), getDiameter(), getDiameter());
		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getDiameter(), getDiameter());
	}
}