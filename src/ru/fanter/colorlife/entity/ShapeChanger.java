package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.line.GameLine;

public class ShapeChanger extends DraggableElement{
	private ElementType elementType = ElementType.SHAPE_CHANGER;
	
	public ShapeChanger() {
	    setSize(Element.ELEM_SIZE);
	}

	public ElementType getElementType() {
		return elementType;
	}

	@Override
	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		if (!(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else {
			this.isSelected(true);
			this.setDraggable(true);
			this.setDraggedCoord(e.getX(), e.getY());
			Pointer pointer = gamePointers.getPointer(this.getX(), this.getY());
			pointer.isShapeChanger(false);
		} 
	}

	@Override
	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
													JPanel panel) {
		if (this.isSelected()) {
			this.setDraggable(false);

			Pointer pointer = gamePointers.getPointer(this.getX(), this.getY());
			if (e.getY() > GameWindow.FIELD_SIZE + GameWindow.INDENT_Y) {
				pointer.isShapeChanger(false);
			} else {
				pointer.isShapeChanger(true);
			}
			panel.repaint();
		}//if(this.isSelecter())
	}

	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(getX(), getY(), getSize(), getSize());
		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getSize(), getSize());
	}
}