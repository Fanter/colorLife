package ru.fanter.colorlife;

import java.awt.Graphics;
import java.awt.Color;

public class Figure {
	private final int DIAMETER = 36;
	private int x;
	private int y;
	private int dx = 2;
	private int dy = 2;
	private Direction direction;
	private boolean isCloneFigure = false;
	private boolean isDead = false;
	private Figure ignoredFigure;
	private FigureColor figureColor = FigureColor.RED;
	private FigureShape figureShape = FigureShape.CIRCLE;

	public Figure(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		setPosition();
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void isDead(boolean dead) {
		this.isDead = dead;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setFigureColor(FigureColor color) {
		figureColor = color;
	}

	public FigureColor getFigureColor() {
		return figureColor;
	}

	public void setFigureShape(FigureShape shape) {
		figureShape = shape;
	}

	public FigureShape getFigureShape() {
		return figureShape;
	}

	public void changeColor() {
		figureColor = figureColor.next();
	}

	public void changeShape() {
		figureShape = figureShape.next();
	}

	public void setIgnoredFigure(Figure ignoredFigure) {
		this.ignoredFigure = ignoredFigure;
	}

	public Figure getIgnoredFigure() {
		return ignoredFigure;
	}

	public void move() {
		switch(direction) {
			case UP:
				this.x += 0;
				this.y -= dy;
				break;
			case RIGHT:
				this.x += dx;
				this.y += 0;
				break;
			case DOWN:
				this.x += 0;
				this.y += dy;
				break;
			case LEFT:
				this.x -= dx;
				this.y += 0;
				break;
			default:
				break;
		}//switch(direction)
	}//move()

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isCloneFigure() {
		return isCloneFigure;
	}

	public void isCloneFigure(boolean isClone) {
		this.isCloneFigure = isClone;
	}

	public boolean setPosition() {
		boolean firstCondition;
		boolean secondCondition;

		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				firstCondition = x >= i && x <= i + GameWindow.SQUARE_SIZE;
				secondCondition = y >= j && y <= j + GameWindow.SQUARE_SIZE;
				if (firstCondition && secondCondition) {
					x = i + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					y = j + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					return true;
				}
			}
		}
		return false;
	}

	public boolean setPosition(int dx, int dy) {
		boolean firstCondition;
		boolean secondCondition;

		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				firstCondition = dx > i && dx < i + GameWindow.SQUARE_SIZE;
				secondCondition = dy > j && dy < j + GameWindow.SQUARE_SIZE;
				if (firstCondition && secondCondition) {
					x = i + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					y = j + GameWindow.SQUARE_SIZE/2 - DIAMETER/2;
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCentered(Pointer pointer) {
		boolean isXCentered;
		boolean isYCentered;

		isXCentered = x == (pointer.getX() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
		isYCentered = y == (pointer.getY() + GameWindow.SQUARE_SIZE/2 - DIAMETER/2);
		return isXCentered && isYCentered;
	}

	public boolean isIntersects(Figure figure) {
		if (this != figure) {
			if (Math.abs(this.getX() - figure.getX()) < DIAMETER &&
					Math.abs(this.getY() - figure.getY()) < DIAMETER &&
					figure.getIgnoredFigure() != this) {
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g) {

		g.setColor(figureColor.getColor());
		switch(figureShape) {
			case CIRCLE:
				g.fillOval(x, y, DIAMETER, DIAMETER);
				g.setColor(Color.BLACK);
				g.drawOval(x, y, DIAMETER, DIAMETER);
				break;
			case SQUARE:
				g.fillRect(x, y, DIAMETER, DIAMETER);
				g.setColor(Color.BLACK);
				g.drawRect(x, y, DIAMETER, DIAMETER);
				break;
			case ROUND_SQUARE:
				g.fillRoundRect(x, y, DIAMETER, DIAMETER, 10, 10);
				g.setColor(Color.BLACK);
				g.drawRoundRect(x, y, DIAMETER, DIAMETER, 10, 10);
				break;
		}

	}

	public void checkDirection(GamePointers gamePointers) {
		Pointer pointer = gamePointers.getPointer(getX(), getY());
		Direction[] pointerDirection = pointer.getDirectionArray();

		if (!isCloneFigure && this.isCentered(pointer)) {
			directionChange(pointer, pointerDirection[0]);
		} else {
			isCloneFigure = false;
		}
	}//checkDirection

	public void updateIgnoredFigure(GamePointers gamePointers) {
		Pointer pointer = gamePointers.getPointer(getX(), getY());

		if (ignoredFigure != null && this.isCentered(pointer)) {
			ignoredFigure = null;
		}
	}

	/*
	 *Looking at which direction figure is moving(inner switch)
	 *and changes it direction to pointer direction
	 */
	private void directionChange(Pointer pointer, Direction pointerDirection) {
		switch(pointerDirection) {
			case DOWN:
				direction = Direction.DOWN;
				break;
			case LEFT:
				direction = Direction.LEFT;
				break;
			case RIGHT:
				direction = Direction.RIGHT;
				break;
			case UP:
				direction = Direction.UP;
				break;
			default:
				break;
		}
	}

	public void setMergedColor(FigureColor mergeColor) {
		switch(mergeColor) {
			case RED:
				switch(figureColor) {
					case RED:
						figureColor = FigureColor.RED;
						break;
					case BLUE:
						figureColor = FigureColor.MAGENTA;
						break;
					case GREEN:
						figureColor = FigureColor.YELLOW;
						break;
					default:
						break;
				}
				break;
			case GREEN:
				switch(figureColor) {
					case RED:
						figureColor = FigureColor.YELLOW;
						break;
					case BLUE:
						figureColor = FigureColor.MAGENTA;
						break;
					case GREEN:
						figureColor = FigureColor.GREEN;
						break;
					default:
						break;
				}
				break;
			case BLUE:
				switch(figureColor) {
					case RED:
						figureColor = FigureColor.MAGENTA;
						break;
					case BLUE:
						figureColor = FigureColor.BLUE;
						break;
					case GREEN:
						figureColor = FigureColor.YELLOW;
						break;
					default:
						break;
				}
				break;
		}//switch(mergeColor)
	}
}