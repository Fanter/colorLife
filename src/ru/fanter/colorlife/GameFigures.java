package ru.fanter.colorlife;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;

public class GameFigures {
	List<Figure> figureList = new ArrayList<Figure>();
	List<Figure> tempFigureList = new ArrayList<Figure>();

	public GameFigures() {
		Figure figure = new Figure(GameWindow.SQUARE_SIZE / 2, GameWindow.FIELD_SIZE / 2, Direction.RIGHT);
		figureList.add(figure);
	}

	public Figure getInitialFigure() {
		return figureList.get(0);
	}

	public void update(GamePointers gamePointers) {
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
				System.out.println("isReceiver");
				if (pointer.getReceiverColor() == figure.getFigureColor() &&
							pointer.getReceiverShape() == figure.getFigureShape()) {
					figure.isDead(true);
					pointer.increaseReceiverCounter();
				}
			}
		}
	}

	public boolean isCollisionHappened() {
		Iterator<Figure> it = figureList.iterator();
		while(it.hasNext()) {
			Figure tempFigure = it.next();
			if (isCollided(tempFigure)) {
				return true;
			}
		}
		return false;
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