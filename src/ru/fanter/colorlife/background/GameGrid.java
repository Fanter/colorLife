package ru.fanter.colorlife.background;

import java.awt.Graphics;
import java.util.*;

import ru.fanter.colorlife.*;

public class GameGrid {
	List<Cell> cellList = new ArrayList<Cell>();

	public GameGrid() {
		for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
			for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
				Cell cell = new Cell(i, j);
				cellList.add(cell);
			}
		}
	}
	
	public void draw(Graphics g) {
		Iterator<Cell> it = cellList.iterator();
		while(it.hasNext()) {
			Cell cell = it.next();
			cell.draw(g);
		}
	}
}