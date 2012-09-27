package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*; 

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.line.GameLine;

public class DetectorShape extends DraggableElement{
	private Direction direction = Direction.DOWN;
	private FigureShape figureShape = FigureShape.CIRCLE;
	private ElementType elementType = ElementType.DETECTOR_SHAPE;
	
	public DetectorShape() {
	    setSize(Element.ELEM_SIZE);
	}

	public ElementType getElementType() {
		return elementType;
	}

	public FigureShape getFigureShape() {
		return figureShape;
	}

	@Override
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

	@Override
	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		if (!(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else {
			this.isSelected(true);
			this.setDraggable(true);
			this.setDraggedCoord(e.getX(), e.getY());
			gamePointers.resetPointer(this.getX(), this.getY(), 2);
			Pointer pointer = gamePointers.getPointer(this.getX(), this.getY());
			pointer.setDetectorShape(null);
			pointer.isDetectorShape(false);
		} 
	}

	@Override
	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
													JPanel panel) {
		if (this.isSelected()) {
			this.setDraggable(false);

			Pointer pointer = gamePointers.getPointer(this.getX(), this.getY());
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
				g.fillOval(getX(), getY(), getSize(), getSize());
				g.setColor(Color.BLACK);
				g.drawOval(getX(), getY(), getSize(), getSize());
				break;
			case SQUARE:
				g.fillRect(getX(), getY(), getSize(), getSize());
				g.setColor(Color.BLACK);
				g.drawRect(getX(), getY(), getSize(), getSize());
				break;
			case ROUND_SQUARE:
				g.fillRoundRect(getX(), getY(), getSize(), getSize(), 10, 10);
				g.setColor(Color.BLACK);
				g.drawRoundRect(getX(), getY(), getSize(), getSize(), 10, 10);
				break;
		}
	}
}