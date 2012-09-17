package ru.fanter.colorlife;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Merger extends DraggableElement{
	private final int DIAMETER = 38;
	private Direction direction = Direction.DOWN;
	private Pointer pointer;
	private FigureColor figureColor;
	private FigureShape figureShape;
	private ElementType elementType = ElementType.MERGER;

	public Merger() {
		super.init(DIAMETER);
	}

	public ElementType getElementType() {
		return elementType;
	}

	public FigureColor getMergerColor() {
		return figureColor;
	}

	public void setMergerColor(FigureColor figureColor) {
		this.figureColor = figureColor;
	}

	public FigureShape getMergerShape() {
		return figureShape;
	}

	public void setMergerShape(FigureShape figureShape) {
		this.figureShape = figureShape;
	}

	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isContaining(e.getX(), e.getY())) {
			direction = direction.next();
			gamePointers.setPointer(this.getX(), this.getY(), direction);
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
			setInitX(this.getX());
			setInitY(this.getY());
			this.setDraggedCoord(e.getX(), e.getY());
			gamePointers.resetPointer(this.getX(), this.getY());
			pointer = gamePointers.getPointer(this.getX(), this.getY());
			pointer.setMerger(null);
			pointer.isMerger(false);
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
				pointer.setMerger(null);
				pointer.isMerger(false);
			} else {
				gamePointers.setPointer(this.getX(), this.getY(), direction);
				pointer.setMerger(this);
				pointer.isMerger(true);
			}
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void draw(Graphics g) {
		if (figureColor != null) {
			g.setColor(figureColor.getColor());
		} else {
			g.setColor(Color.CYAN);
		}
		
		if (figureColor == null) {
			g.fillRect(getX(), getY(), getDiameter(), getDiameter());
			g.setColor(Color.BLACK);
			g.drawRect(getX(), getY(), getDiameter(), getDiameter());
			return;
		}
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