package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.line.GameLine;

public class Splitter extends DraggableElement {
	private Direction direction1 = Direction.UP;
	private Direction direction2 = Direction.DOWN;
	private ElementType elementType = ElementType.SPLITTER;
	
	public Splitter() {
	    setSize(Element.ELEM_SIZE);
	}

	public ElementType getElementType() {
		return elementType;
	}

	@Override
	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isContaining(e.getX(), e.getY())) {
			direction1 = direction1.next();
			direction2 = direction2.next();
			gamePointers.setPointer(this.getX(), this.getY(), direction1, direction2);
			gameLine.constructLines();
			panel.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		if (!(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else {
			this.isSelected(true);
			this.setDraggable(true);
			this.setDraggedCoord(e.getX(), e.getY());
			gamePointers.resetPointer(this.getX(), this.getY());
			Pointer pointer = gamePointers.getPointer(this.getX(), this.getY());
			pointer.isSplitter(false);
		} 
	}

	@Override
	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
													JPanel panel) {
		if (this.isSelected()) {
			this.setDraggable(false);
			
			Pointer pointer = gamePointers.getPointer(this.getX(), this.getY());
			if (e.getY() > GameWindow.FIELD_SIZE + GameWindow.INDENT_Y) {
				gamePointers.resetPointer(this.getX(), this.getY());
				pointer.isSplitter(false);
			} else {
				gamePointers.setPointer(this.getX(), this.getY(), direction1, direction2);
				pointer.isSplitter(true);
			}
			gameLine.constructLines();
			panel.repaint();
		}//if(this.isSelecter())
	}

	public void draw(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(getX(), getY(), getSize(), getSize());
		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getSize(), getSize());
	}
}