package ru.fanter.colorlife.entity;

import java.awt.Graphics;
import java.awt.Color;

import ru.fanter.colorlife.Direction;
import ru.fanter.colorlife.FigureColor;
import ru.fanter.colorlife.FigureShape;
import ru.fanter.colorlife.GamePointers;
import ru.fanter.colorlife.GameWindow;
import ru.fanter.colorlife.Pointer;

public class Figure extends Element{
	private int dx = 2;        //speed
	private int dy = 2;
	private Direction direction;
	private boolean isCloneFigure = false;
	private boolean isDead = false;
	private Figure ignoredFigure;
	private FigureColor figureColor = FigureColor.RED;
	private FigureShape figureShape = FigureShape.CIRCLE;

	public Figure(int x, int y, Direction direction) {
		setX(x);
		setY(y);
		setSize(Element.FIGURE_SIZE);
		this.direction = direction;
		setPosition(x, y);
	}

	public void move() {
		switch(direction) {
			case UP:
			    setY(getY() - dy);
				break;
			case RIGHT:
			    setX(getX() + dx);
				break;
			case DOWN:
			    setY(getY() + dy);
				break;
			case LEFT:
			    setX(getX() - dx);
				break;
			default:
				break;
		}//switch(direction)
	}//move()

	public boolean isOutOfField() {
		if (getX() < GameWindow.INDENT_X || getY() < GameWindow.INDENT_Y 
						|| getX() + getSize() > GameWindow.FIELD_SIZE + GameWindow.INDENT_X
						|| getY() + getSize() > GameWindow.FIELD_SIZE + GameWindow.INDENT_Y) {
			return true;
		}
		return false;
	}

	public boolean isCentered(Pointer pointer) {
		boolean isXCentered;
		boolean isYCentered;

		isXCentered = getX() == (pointer.getX() + GameWindow.SQUARE_SIZE/2 - getSize()/2);
		isYCentered = getY() == (pointer.getY() + GameWindow.SQUARE_SIZE/2 - getSize()/2);
		return isXCentered && isYCentered;
	}

	public boolean isIntersects(Figure figure) {
		if (this != figure) {
			if (Math.abs(this.getX() - figure.getX()) < getSize() &&
					Math.abs(this.getY() - figure.getY()) < getSize() &&
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
			default:
			    break;
		}//switch(mergeColor)
	}
	
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isCloneFigure() {
        return isCloneFigure;
    }

    public void isCloneFigure(boolean isClone) {
        this.isCloneFigure = isClone;
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
}