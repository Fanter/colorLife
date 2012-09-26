package ru.fanter.colorlife;

import java.awt.*;

import ru.fanter.colorlife.entity.*;
import ru.fanter.colorlife.entitylogic.*;

public class Pointer {
	private final int SIZE = 10;
	private final int ARRAY_SIZE = 3;
	private int x;
	private int y;
	private boolean isSplitter = false;
	private boolean isColorChanger = false;
	private boolean isShapeChanger = false;
	private boolean isRecycler = false;
	private boolean isDetectorColor = false;
	private boolean isDetectorShape = false;
	private boolean isMerger = false;
	private boolean isReceiver =false;
	private DetectorColor detectorColor;
	private DetectorShape detectorShape;
	private Receiver receiver;
	private Merger merger;
	private Direction[] directionArray = new Direction[ARRAY_SIZE];

	public Pointer(int x, int y) {
		this.x = x;
		this.y = y;
		for (int i = 0; i < ARRAY_SIZE; i++) {
			directionArray[i] = Direction.NONE;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isContainingElements() {
		boolean isArrow;
		isArrow = isArrow();

		if (isSplitter || isColorChanger || isShapeChanger ||
					isRecycler || isDetectorColor || isDetectorShape ||
					isMerger || isReceiver || isArrow) {
			return true;
		}
		return false;
	}

	private boolean isArrow() {
		for(int i = 0; i < ARRAY_SIZE; i++) {
			if (directionArray[i] != Direction.NONE) {
				return true;
			}
		}
		return false;
	}

//////////////////////////////////////////////////////
//SPLITTER
//////////////////////////////////////////////////////
	public boolean isSplitter() {
		return isSplitter;
	}

	public void isSplitter(boolean splitter) {
		this.isSplitter = splitter;
	}

//////////////////////////////////////////////////////
//COLOR CHANGER
//////////////////////////////////////////////////////
	public boolean isColorChanger() {
		return isColorChanger;
	}

	public void isColorChanger(boolean colorChanger) {
		this.isColorChanger = colorChanger;
	} 

//////////////////////////////////////////////////////
//SHAPE CHANGER
//////////////////////////////////////////////////////
	public boolean isShapeChanger() {
		return isShapeChanger;
	}

	public void isShapeChanger(boolean shapeChanger) {
		this.isShapeChanger = shapeChanger;
	}

//////////////////////////////////////////////////////
//RECYCLER
//////////////////////////////////////////////////////
	public boolean isRecycler() {
		return isRecycler;
	}

	public void isRecycler(boolean recycler) {
		this.isRecycler = recycler;
	}

//////////////////////////////////////////////////////
//DETECTOR COLOR
//////////////////////////////////////////////////////
	public boolean isDetectorColor() {
		return isDetectorColor;
	}

	public void isDetectorColor(boolean detectorColor) {
		this.isDetectorColor = detectorColor;
	}

	public DetectorColor getDetectorColor() {
		return detectorColor;
	}

	public void setDetectorColor(DetectorColor detectorColor) {
		this.detectorColor = detectorColor;
	}

//////////////////////////////////////////////////////
//DETECTOR SHAPE
//////////////////////////////////////////////////////
	public boolean isDetectorShape() {
		return isDetectorShape;
	}

	public void isDetectorShape(boolean detectorShape) {
		this.isDetectorShape = detectorShape;
	}

	public DetectorShape getDetectorShape() {
		return detectorShape;
	}

	public void setDetectorShape(DetectorShape detectorShape) {
		this.detectorShape = detectorShape;
	}

//////////////////////////////////////////////////////
//MERGER
//////////////////////////////////////////////////////
	public boolean isMerger() {
		return isMerger;
	}

	public void isMerger(boolean merger) {
		this.isMerger = merger;
	}

	public FigureShape getMergerShape() {
		return merger.getMergerShape();
	}

	public void setMergerShape(FigureShape figureShape) {
		merger.setMergerShape(figureShape);
	}

	public FigureColor getMergerColor() {
		return merger.getMergerColor();
	}

	public void setMergerColor(FigureColor figureColor) {
		merger.setMergerColor(figureColor);
	}

	public void setMerger(Merger merger) {
		this.merger = merger;
	}

	public void resetMerger() {
		merger.setMergerColor(null);
		merger.setMergerShape(null);
	}

//////////////////////////////////////////////////////
//RECEIVER
//////////////////////////////////////////////////////
	public boolean isReceiver() {
		return isReceiver;
	}

	public void isReceiver(boolean isReceiver) {
		this.isReceiver = isReceiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public FigureColor getReceiverColor() {
		return receiver.getReceiverColor();
	}

	public FigureShape getReceiverShape() {
		return receiver.getReceiverShape();
	}

	public void increaseReceiverCounter() {
		receiver.increaseCounter();
	}
//////////////////////////////////////////////////////
//DIRECTION
//////////////////////////////////////////////////////
	public Direction getDirection() {
		return directionArray[0];
	}

	public void setDirectionArray(Direction dir, int index) {
		if (index >= 0 && index < directionArray.length) {
			directionArray[index] = dir;
		}
	}

	public Direction[] getDirectionArray() {
		return directionArray;
	}

	public boolean isContaining(int xCoord, int yCoord) {
		boolean firstCondition;
		boolean secondCondition;

		firstCondition = (xCoord > x && xCoord < (x + GameWindow.SQUARE_SIZE));
		secondCondition = (yCoord > y && yCoord < (y + GameWindow.SQUARE_SIZE));
		return firstCondition && secondCondition;
	}

	public void drawArray(Graphics g) {
		int coordX = x + GameWindow.SQUARE_SIZE/2 - SIZE/2;
		int coordY = y + GameWindow.SQUARE_SIZE/2 - SIZE/2;

		for (int i = 0; i < ARRAY_SIZE; i++) {
			switch(directionArray[i]) {
				case LEFT:
					g.setColor(Color.GREEN);
					g.fillRect(coordX -18, coordY, SIZE + 5, SIZE + 5);
					break;
				case RIGHT:
					g.setColor(Color.RED);
					g.fillRect(coordX + 18, coordY, SIZE + 5, SIZE + 5);
					break;
				case UP:
					g.setColor(Color.CYAN);
					g.fillRect(coordX, coordY - 18, SIZE + 5, SIZE + 5);
					break;
				case DOWN:
					g.setColor(Color.YELLOW);
					g.fillRect(coordX, coordY + 18, SIZE + 5, SIZE + 5);
					break;
			}//switch
		}//for
	}
}