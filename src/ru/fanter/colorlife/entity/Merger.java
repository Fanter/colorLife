package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.line.GameLine;

public class Merger extends DraggableElement{
	private Direction direction = Direction.DOWN;
	private FigureColor figureColor;
	private FigureShape figureShape;
	private ElementType elementType = ElementType.MERGER;
	
	public Merger() {
	    setSize(Element.ELEM_SIZE);
	}

	@Override
	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isContaining(e.getX(), e.getY())) {
			direction = direction.next();
			gamePointers.setPointer(this.getX(), this.getY(), direction);
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
			pointer.setMerger(null);
			pointer.isMerger(false);
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
			g.fillRect(getX(), getY(), getSize(), getSize());
			g.setColor(Color.BLACK);
			g.drawRect(getX(), getY(), getSize(), getSize());
			return;
		}
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
}