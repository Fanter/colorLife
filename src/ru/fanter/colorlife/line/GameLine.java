package ru.fanter.colorlife.line;

import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

import ru.fanter.colorlife.Direction;
import ru.fanter.colorlife.GamePointers;
import ru.fanter.colorlife.GameWindow;
import ru.fanter.colorlife.Pointer;
import ru.fanter.colorlife.entity.LineArrow;

public class GameLine {
	private int x;			//coordinates of initial point from where
	private int y;			//we create line
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

		if (cycleCount > MAX_NUMBER_OF_CYCLES || x > GameWindow.FIELD_SIZE || 
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
	    alignCoords(lineArrow.getX(), lineArrow.getY());
		cycleCount = 0;
		lineList.clear();
	}
	
    private void alignCoords(int lx, int ly) {
        boolean firstCondition;
        boolean secondCondition;

        for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
            for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
                firstCondition = lx >= i && lx <= i + GameWindow.SQUARE_SIZE;
                secondCondition = ly >= j && ly <= j + GameWindow.SQUARE_SIZE;
                if (firstCondition && secondCondition) {
                    x = i + GameWindow.SQUARE_SIZE/2;
                    y = j + GameWindow.SQUARE_SIZE/2;
                    return;
                }
            }
        }
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