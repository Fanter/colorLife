package ru.fanter.colorlife;

import java.awt.*;

public class ArrowRight extends DraggableArrow {
	private final int INIT_X = 120;
	private final int INIT_Y = GameWindow.FIELD_SIZE + 80;
	private final Direction direction = Direction.RIGHT;
	private final Color color = Color.RED;
	
	public ArrowRight() {
		super.init(INIT_X, INIT_Y, direction);
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int smallRectX = getX() + getDiameter();
		int smallRectY = getY() + getDiameter()/2 - getSmallDiameter()/2;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.RED);
		g2.fillRect(getX(), getY(), getDiameter(), getDiameter());
		g2.fillRect(smallRectX, smallRectY, getSmallDiameter(), getSmallDiameter());
		g2.setColor(Color.BLACK);
		g2.drawRect(getX(), getY(), getDiameter(), getDiameter());
		g2.drawRect(smallRectX, smallRectY, getSmallDiameter(), getSmallDiameter());
	}
}