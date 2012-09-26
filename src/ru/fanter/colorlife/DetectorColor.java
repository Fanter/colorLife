package ru.fanter.colorlife;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DetectorColor extends DraggableElement{
	private final int DIAMETER = 36;
	private Direction direction = Direction.DOWN;
	private Pointer pointer;
	private FigureColor figureColor = FigureColor.RED;
	private ElementType elementType = ElementType.DETECTOR_COLOR;

	public DetectorColor() {
		super.init(DIAMETER);
	}

	public ElementType getElementType() {
		return elementType;
	}

	public FigureColor getFigureColor() {
		return figureColor;
	}

	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isContaining(e.getX(), e.getY())) {
			switch(e.getButton()) {
				case MouseEvent.BUTTON1:
					direction = direction.next();
					break;
				case MouseEvent.BUTTON3:
					figureColor = figureColor.next();
					break;
				default:
					System.out.println("mousevent=" + e.getButton());
					break;
			}
			gamePointers.setPointer(this.getX(), this.getY(), direction, 2);
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		if (!(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else {
			this.isSelected(true);
			this.setDraggable(true);
			this.setDraggedCoord(e.getX(), e.getY());
			gamePointers.resetPointer(this.getX(), this.getY(), 2);
			pointer = gamePointers.getPointer(this.getX(), this.getY());
			pointer.setDetectorColor(null);
			pointer.isDetectorColor(false);
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
				gamePointers.resetPointer(this.getX(), this.getY(), 2);
				pointer.isDetectorColor(false);
				pointer.setDetectorColor(null);
			} else {
				gamePointers.setPointer(this.getX(), this.getY(), direction, 2);
				pointer.isDetectorColor(true);
				pointer.setDetectorColor(this);
			}
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void draw(Graphics g) {
		g.setColor(figureColor.getColor());
		g.fillRect(getX(), getY(), getDiameter(), getDiameter());
		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getDiameter(), getDiameter());
	}
}