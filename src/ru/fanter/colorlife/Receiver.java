package ru.fanter.colorlife;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Receiver extends DraggableElement {
	private final int DIAMETER = 36;
	private final int FINAL_COUNT = 8;
	private Pointer pointer;
	private FigureColor figureColor;
	private FigureShape figureShape;
	private int elementCounter;
	private ElementType elementType = ElementType.RECEIVER;

	public Receiver(FigureColor figureColor, FigureShape figureShape) {
		super.init(DIAMETER);
		this.figureColor = figureColor;
		this.figureShape = figureShape;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public FigureColor getReceiverColor() {
		return figureColor;
	}

	public FigureShape getReceiverShape() {
		return figureShape;
	}

	public void increaseCounter() {
		elementCounter++;
	}

	public void resetCount() {
		elementCounter = 0;
	}

	public int getCounter() {
		return elementCounter;
	}

	public void setRecieverColor(FigureColor figureColor) {
		this.figureColor = figureColor;
	}

	public void setReceiverShape(FigureShape figureShape) {
		this.figureShape = figureShape;
	}
	
	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isContaining(e.getX(), e.getY())) {
			switch(e.getButton()) {
				case MouseEvent.BUTTON1:
					figureColor = figureColor.next();
					break;
				case MouseEvent.BUTTON3:
					figureShape = figureShape.next();
					break;
				default:
					break;
			}
			panel.repaint();
		}
	}

	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		if (!(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else {
			this.isSelected(true);
			this.setDraggable(true);
			elementCounter = 0;
			setInitX(this.getX());
			setInitY(this.getY());
			this.setDraggedCoord(e.getX(), e.getY());
			gamePointers.resetPointer(this.getX(), this.getY());
			pointer = gamePointers.getPointer(this.getX(), this.getY());
			pointer.setReceiver(null);
			pointer.isReceiver(false);
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
				pointer.setReceiver(null);
				pointer.isReceiver(false);
			} else {
				gamePointers.setPointer(this.getX(), this.getY(), Direction.END, Direction.END);
				pointer.setReceiver(this);
				pointer.isReceiver(true);
			}
			gameLine.constructLines();
			panel.repaint();
		}//if(this.isSelecter())
	}

	public void draw(Graphics g) {
		int OFFSET = 6;
		Color usedColor;
		Color innerColor = Color.WHITE;

		if (figureColor == null) {
			usedColor = Color.WHITE;
		} else {
			usedColor = figureColor.getColor();
		}

		g.setColor(usedColor);
		if (figureShape == null) {
			g.fillRect(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET);
			g.setColor(Color.BLACK);
			g.drawRect(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET);
			g.setColor(innerColor);
			g.fillRect(getX(), getY(), getDiameter(), getDiameter());
			g.setColor(Color.BLACK);
			g.drawRect(getX(), getY(), getDiameter(), getDiameter());
		} else {
			switch(figureShape) {
				case CIRCLE:
					g.fillOval(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET);
					g.setColor(Color.BLACK);
					g.drawOval(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET);
					g.setColor(innerColor);
					g.fillOval(getX(), getY(), getDiameter(), getDiameter());
					g.setColor(Color.BLACK);
					g.drawOval(getX(), getY(), getDiameter(), getDiameter());
					break;
				case SQUARE:
					g.fillRect(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET);
					g.setColor(Color.BLACK);
					g.drawRect(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET);
					g.setColor(innerColor);
					g.fillRect(getX(), getY(), getDiameter(), getDiameter());
					g.setColor(Color.BLACK);
					g.drawRect(getX(), getY(), getDiameter(), getDiameter());
					break;
				case ROUND_SQUARE:
					g.fillRoundRect(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET, 10, 10);
					g.setColor(Color.BLACK);
					g.drawRoundRect(getX() - OFFSET/2, getY() - OFFSET/2, getDiameter() + OFFSET, getDiameter() + OFFSET, 10, 10);
					g.setColor(innerColor);
					g.fillRoundRect(getX(), getY(), getDiameter(), getDiameter(), 10, 10);
					g.setColor(Color.BLACK);
					g.drawRoundRect(getX(), getY(), getDiameter(), getDiameter(), 10, 10);
					break;
			}//switch
		}
		drawCounterDots(g);
	}

	public void drawCounterDots(Graphics g) {
		int counterX;
		int counterY;
		int centerX;
		int centerY;
		int cDiameter = 8;
		double distanceFromCenter = getDiameter()/2 + cDiameter/2 + 6;
		int degrees = 45;
		double radians;

		centerX = getX() + getDiameter() / 2;
		centerY = getY() + getDiameter() / 2;

		for(int i = 0; i < 8; i++) {
			radians = Math.toRadians(i * degrees + 23);
			counterX = centerX - (int) (distanceFromCenter * (Math.sin(radians)));
			counterY = centerY + (int) (distanceFromCenter * (Math.cos(radians)));

			if (elementCounter > i) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GRAY);
			}
			g.fillOval(counterX - cDiameter/2, counterY - cDiameter/2, cDiameter, cDiameter);
		}
	}
}