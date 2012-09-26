package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.entitylogic.GameArrows;

public class DetectorShape extends DraggableElement{
	private final int DIAMETER = 36;
	private Direction direction = Direction.DOWN;
	private Pointer pointer;
	private FigureShape figureShape = FigureShape.CIRCLE;
	private ElementType elementType = ElementType.DETECTOR_SHAPE;

	public DetectorShape() {
		super.init(DIAMETER);
	}

	public ElementType getElementType() {
		return elementType;
	}

	public FigureShape getFigureShape() {
		return figureShape;
	}

	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isContaining(e.getX(), e.getY())) {
			switch(e.getButton()) {
				case MouseEvent.BUTTON1:
					direction = direction.next();
					break;
				case MouseEvent.BUTTON3:
					figureShape = figureShape.next();
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
			pointer.setDetectorShape(null);
			pointer.isDetectorShape(false);
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
				pointer.isDetectorShape(false);
				pointer.setDetectorShape(null);
			} else {
				gamePointers.setPointer(this.getX(), this.getY(), direction, 2);
				pointer.isDetectorShape(true);
				pointer.setDetectorShape(this);
			}
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		switch(figureShape) {
			case CIRCLE:
				g.fillOval(getX(), getY(), getDiameter(), getDiameter());
				g.setColor(Color.BLACK);
				g.drawOval(getX(), getY(), getDiameter(), getDiameter());
				break;
			case SQUARE:
				g.fillRect(getX(), getY(), getDiameter(), getDiameter());
				g.setColor(Color.BLACK);
				g.drawRect(getX(), getY(), getDiameter(), getDiameter());
				break;
			case ROUND_SQUARE:
				g.fillRoundRect(getX(), getY(), getDiameter(), getDiameter(), 10, 10);
				g.setColor(Color.BLACK);
				g.drawRoundRect(getX(), getY(), getDiameter(), getDiameter(), 10, 10);
				break;
		}
	}
}