package ru.fanter.colorlife.entity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import ru.fanter.colorlife.Direction;
import ru.fanter.colorlife.ElementType;
import ru.fanter.colorlife.GamePointers;
import ru.fanter.colorlife.GameWindow;
import ru.fanter.colorlife.line.GameLine;

public class LineArrow extends DraggableElement{
    private ElementType elementType = ElementType.LINE_ARROW;
	private Direction direction;

	public LineArrow() {
		setX(GameWindow.SQUARE_SIZE / 2);
		setY(GameWindow.FIELD_SIZE / 2);
		setSize(Element.LINE_ARROW_SIZE);
		setPosition(getX(), getY());
		direction = Direction.RIGHT;
	}

	public ElementType getElementType() {
	    return elementType;
	}

	public void setDirection(Direction newDirection) {
		direction = newDirection;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (this.isSelected()) {
			setDirection(direction.next());
			gamePointers.setPointer(this.getX(), this.getY(), getDirection());
			gameLine.constructLines();
			panel.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {

		if (e.getY() > GameWindow.FIELD_SIZE || !(this.isContaining(e.getX(), e.getY()))) {
			this.isSelected(false);
		} else if (this.isContaining(e.getX(), e.getY())) {
			this.isSelected(true);

			gamePointers.resetPointer(this.getX(), this.getY());
			this.setDraggable(true);
			this.setDraggedCoord(e.getX(), e.getY());
		} 
	}

	@Override
	public void mouseReleased(MouseEvent e, GamePointers gamePointers, 
					GameLine gameLine, JPanel panel) {
		if (this.isSelected()) {
			this.setDraggable(false);

			gamePointers.setPointer(this.getX(), this.getY(), this.getDirection());
			gameLine.constructLines();
			panel.repaint();
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(getX(), getY(), getSize(), getSize());
		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getSize(), getSize());
	}
}