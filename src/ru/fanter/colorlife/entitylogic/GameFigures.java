package ru.fanter.colorlife.entitylogic;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;

import ru.fanter.colorlife.Direction;
import ru.fanter.colorlife.FigureColor;
import ru.fanter.colorlife.FigureShape;
import ru.fanter.colorlife.GamePointers;
import ru.fanter.colorlife.Pointer;
import ru.fanter.colorlife.entity.Figure;

public class GameFigures {
	List<Figure> figureList = new ArrayList<Figure>();
	List<Figure> tempFigureList = new ArrayList<Figure>();
	private boolean isError = false;

	public GameFigures() {

	}

	public void clear() {
		figureList.clear();
	}

	public void createFigure(int x, int y, Direction direction) {
		Figure figure = new Figure(x, y, direction);
		figureList.add(figure);
	}

	public void update(GamePointers gamePointers) {
		isError = false;
		synchronized(figureList) {
			Iterator<Figure> it = figureList.iterator();
			while(it.hasNext()) {
				Figure figure = it.next();
				if (figure.isDead()) {
					it.remove();
					continue;
				}
				figure.checkDirection(gamePointers);
				figure.move();
				figure.checkDirection(gamePointers);
				figure.updateIgnoredFigure(gamePointers);
				changeFigureProperties(figure, gamePointers);
			}
			figureList.addAll(tempFigureList);
			tempFigureList.clear();
		}
		checkCollisions();
		checkOutOfField();
	}

	private void changeFigureProperties(Figure figure, GamePointers gamePointers) {
		Pointer pointer = gamePointers.getPointer(figure.getX(), figure.getY());
		Direction[] direction = pointer.getDirectionArray();

		if (figure.isCentered(pointer)) {
			if (pointer.isSplitter()) {
				Figure newFigure = new Figure(figure.getX(), figure.getY(), direction[1]);
				newFigure.isCloneFigure(true);
				newFigure.setFigureColor(figure.getFigureColor());
				newFigure.setFigureShape(figure.getFigureShape());
				newFigure.setIgnoredFigure(figure);
				figure.setIgnoredFigure(newFigure);
				tempFigureList.add(newFigure);
			} else if (pointer.isColorChanger()) {
				figure.changeColor();
			} else if (pointer.isShapeChanger()) {
				figure.changeShape();
			} else if (pointer.isRecycler()) {
				figure.isDead(true);
			} else if (pointer.isDetectorColor()) {
				if (pointer.getDetectorColor().getFigureColor() == figure.getFigureColor()) {
					figure.setDirection(direction[2]);
				}
			} else if (pointer.isDetectorShape()) {
				if (pointer.getDetectorShape().getFigureShape() == figure.getFigureShape()) {
					figure.setDirection(direction[2]);
				}
			} else if (pointer.isMerger()) {
				if (mergeFigure(figure, pointer)) {
					figure.setDirection(direction[0]);
				}
			} else if (pointer.isReceiver()) {
				if (pointer.getReceiverColor() == figure.getFigureColor() &&
							pointer.getReceiverShape() == figure.getFigureShape()) {
					figure.isDead(true);
					pointer.increaseReceiverCounter();
				} else {
					isError = true;
				}
			}
		}
	}

	public void checkCollisions() {
		Iterator<Figure> it = figureList.iterator();
		while(it.hasNext()) {
			Figure tempFigure = it.next();
			if (isCollided(tempFigure)) {
				isError = true;
				return;
			}
		}
	}

	public void checkOutOfField() {
		Iterator<Figure> it = figureList.iterator();
		while(it.hasNext()) {
			Figure tempFigure = it.next();
			if (tempFigure.isOutOfField()) {
				isError = true;
				return;
			}
		}
	}

	public boolean checkErrors() {
		return isError;
	}

	private boolean isCollided(Figure figure) {
		Iterator<Figure> it = figureList.iterator();
		while(it.hasNext()) {
			Figure tempFigure = it.next();
			if (tempFigure.isIntersects(figure)) {
				return true;
			}
		}
		return false;
	}

	private boolean mergeFigure(Figure figure, Pointer pointer) {
		FigureColor mergerColor = pointer.getMergerColor();
		FigureShape mergerShape = pointer.getMergerShape();

		if (mergerShape == null) {
			pointer.setMergerShape(figure.getFigureShape());
			pointer.setMergerColor(figure.getFigureColor());
			figure.isDead(true);
		} else if (figure.getFigureShape() == mergerShape) {
			figure.setMergedColor(mergerColor);
			pointer.resetMerger();
			return true;
		}
		return false;
	}

	public void draw(Graphics g) {
		synchronized(figureList) {
			Iterator<Figure> it = figureList.iterator();
			while(it.hasNext()) {
				Figure figure = it.next();
				figure.draw(g);
			}
		}
	}
}