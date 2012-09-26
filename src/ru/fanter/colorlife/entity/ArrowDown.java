package ru.fanter.colorlife.entity;

import java.awt.*;

import ru.fanter.colorlife.Direction;
import ru.fanter.colorlife.DraggableArrow;
import ru.fanter.colorlife.GameWindow;

public class ArrowDown extends DraggableArrow {
	private final int INIT_X = 80;
	private final int INIT_Y = GameWindow.FIELD_SIZE + 80;
	private final Direction direction = Direction.DOWN;
	private final Color color = Color.YELLOW;
	
	public ArrowDown() {
		super.init(INIT_X, INIT_Y, direction);
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int smallRectX = getX() + getDiameter()/2 - getSmallDiameter()/2;
		int smallRectY = getY() + getDiameter();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.YELLOW);
		g2.fillRect(getX(), getY(), getDiameter(), getDiameter());
		g2.fillRect(smallRectX, smallRectY, getSmallDiameter(), getSmallDiameter());
		g2.setColor(Color.BLACK);
		g2.drawRect(getX(), getY(), getDiameter(), getDiameter());
		g2.drawRect(smallRectX, smallRectY, getSmallDiameter(), getSmallDiameter());
	}
}