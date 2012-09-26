package ru.fanter.colorlife;

import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

public class GameLine {
	private int x;			//coordinates of initial point which we get 
	private int y;			//from LineArrow
	private static final int MAX_NUMBER_OF_CYCLES = 500;
	private int cycleCount;
	private List<LineSegment> lineList = new ArrayList<LineSegment>();
	private LineArrow lineArrow;
	private GamePointers gamePointers;

	public GameLine(GamePointers gamePointers, LineArrow lineArrow) {
		this.lineArrow = lineArrow;
		this.gamePointers = gamePointers;
		lineArrow.setDirection(Direction.RIGHT);
		gamePointers.setPointer(lineArrow.getX(), lineArrow.getY(), lineArrow.getDirection());
	}

	public void constructLines() {
		resetValues();
		constructLinesRecursion(x, y, lineArrow.getDirection());
	}

	private void constructLinesRecursion(int x, int y, Direction direction) {
		Pointer pointer = gamePointers.getPointer(x, y);
		Direction[] directionArray = pointer.getDirectionArray();
		cycleCount++;

		if (cycleCount > 500 || x > GameWindow.FIELD_SIZE || 
						y > GameWindow.FIELD_SIZE || x < 0 || y < 0 ||
						direction == Direction.END) {
			return;
		} 

		if (directionArray[1] != Direction.NONE && directionArray[0] == Direction.NONE) {
			addLineSegment(x, y, directionArray[1]);
		} else if (directionArray[0] != Direction.NONE && directionArray[1] == Direction.NONE) {
			addLineSegment(x, y, directionArray[0]);
		} else if (directionArray[0] != Direction.NONE && directionArray[1] != Direction.NONE) {
			addLineSegment(x, y, directionArray[0]);
			addLineSegment(x, y, directionArray[1]);
		} else {
			addLineSegment(x, y, direction);
		}

		if (directionArray[2] != Direction.NONE) {
			addLineSegment(x, y, directionArray[2]);
		}
	}

	private void addLineSegment(int x, int y, Direction direction) {
		LineSegment segment = new LineSegment(x, y, direction);
		if (isSegmentUnique(segment)) {
			lineList.add(segment);
			segment.setNextCoords();
			constructLinesRecursion(segment.getNextX(), segment.getNextY(), direction);
		} 
	}

	private boolean isSegmentUnique(LineSegment segment) {
		Iterator<LineSegment> it = lineList.iterator();
		while(it.hasNext()) {
			LineSegment tempSegment = it.next();
			if (tempSegment.equals(segment)) {
				return false;
			}
		}
		return true;
	}

	private void resetValues() {
		x = lineArrow.getLineX();
		y = lineArrow.getLineY();
		cycleCount = 0;
		lineList.clear();
	}

	public LineArrow getLineArrow() {
		return lineArrow;
	}

	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Iterator<LineSegment> it = lineList.iterator();
		while(it.hasNext()) {
			LineSegment segment = it.next();
			segment.draw(g);
		}
		lineArrow.draw(g);
	}
}