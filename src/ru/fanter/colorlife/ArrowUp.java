package ru.fanter.colorlife;

import java.awt.*;

public class ArrowUp extends DraggableArrow {
	private final int INIT_X = 80;
	private final int INIT_Y = GameWindow.FIELD_SIZE + 50;
	private final Direction direction = Direction.UP;
	private final Color color = Color.CYAN;
	
	public ArrowUp() {
		super.init(INIT_X, INIT_Y, direction);
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int smallRectX = getX() + getDiameter()/2 - getSmallDiameter()/2;
		int smallRectY = getY() - getSmallDiameter();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.CYAN);
		g2.fillRect(getX(), getY(), getDiameter(), getDiameter());
		g2.fillRect(smallRectX, smallRectY, getSmallDiameter(), getSmallDiameter());
		g2.setColor(Color.BLACK);
		g2.drawRect(getX(), getY(), getDiameter(), getDiameter());
		g2.drawRect(smallRectX, smallRectY, getSmallDiameter(), getSmallDiameter());
	}
}