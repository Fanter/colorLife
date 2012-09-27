package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import javax.swing.*;

import ru.fanter.colorlife.*;

public class Receiver extends DraggableElement {
	private Pointer pointer;
	private FigureColor figureColor;
	private FigureShape figureShape;
	private int elementCounter;
	private ElementType elementType = ElementType.RECEIVER;

	public Receiver(FigureColor figureColor, FigureShape figureShape) {
		this.figureColor = figureColor;
		this.figureShape = figureShape;
		setSize(Element.ELEM_SIZE);
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

	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
													JPanel panel) {
		if (this.isSelected()) {
			this.setDraggable(false);

			pointer = gamePointers.getPointer(this.getX(), this.getY());
			gamePointers.setPointer(this.getX(), this.getY(), Direction.END, Direction.END);
			pointer.setReceiver(this);
			pointer.isReceiver(true);
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void draw(Graphics g) {
		int OFFSET = 6;
		Color usedColor;
		Color innerColor = Color.WHITE;
		
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

		if (figureColor == null) {
			usedColor = Color.WHITE;
		} else {
			usedColor = figureColor.getColor();
		}

		g.setColor(usedColor);
		if (figureShape == null) {
			g.fillRect(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET);
			g.setColor(Color.BLACK);
			g.drawRect(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET);
			g.setColor(innerColor);
			g.fillRect(getX(), getY(), getSize(), getSize());
			g.setColor(Color.BLACK);
			g.drawRect(getX(), getY(), getSize(), getSize());
		} else {
			switch(figureShape) {
				case CIRCLE:
					g.fillOval(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET);
					g.setColor(Color.BLACK);
					g.drawOval(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET);
					g.setColor(innerColor);
					g.fillOval(getX(), getY(), getSize(), getSize());
					g.setColor(Color.BLACK);
					g.drawOval(getX(), getY(), getSize(), getSize());
					break;
				case SQUARE:
					g.fillRect(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET);
					g.setColor(Color.BLACK);
					g.drawRect(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET);
					g.setColor(innerColor);
					g.fillRect(getX(), getY(), getSize(), getSize());
					g.setColor(Color.BLACK);
					g.drawRect(getX(), getY(), getSize(), getSize());
					break;
				case ROUND_SQUARE:
					g.fillRoundRect(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET, 10, 10);
					g.setColor(Color.BLACK);
					g.drawRoundRect(getX() - OFFSET/2, getY() - OFFSET/2, getSize() + OFFSET, getSize() + OFFSET, 10, 10);
					g.setColor(innerColor);
					g.fillRoundRect(getX(), getY(), getSize(), getSize(), 10, 10);
					g.setColor(Color.BLACK);
					g.drawRoundRect(getX(), getY(), getSize(), getSize(), 10, 10);
					break;
			}//switch(figureShape)
		}
		drawCounterDots(g);
	}

	public void drawCounterDots(Graphics g) {
		int counterX;
		int counterY;
		int centerX;
		int centerY;
		int cDiameter = 8;
		double distanceFromCenter = getSize()/2 + cDiameter/2 + 6;
		int degrees = 45;
		double radians;

		centerX = getX() + getSize() / 2;
		centerY = getY() + getSize() / 2;

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