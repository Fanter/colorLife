package ru.fanter.colorlife.entitylogic;

import java.util.*;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.JPanel;

import ru.fanter.colorlife.DraggableArrow;
import ru.fanter.colorlife.GameLine;
import ru.fanter.colorlife.GamePointers;
import ru.fanter.colorlife.GameWindow;
import ru.fanter.colorlife.Pointer;
import ru.fanter.colorlife.entity.ArrowDown;
import ru.fanter.colorlife.entity.ArrowLeft;
import ru.fanter.colorlife.entity.ArrowRight;
import ru.fanter.colorlife.entity.ArrowUp;

public class GameArrows {
	private List<DraggableArrow> arrowStaticList = new ArrayList<DraggableArrow>();
	private List<DraggableArrow> arrowList = new ArrayList<DraggableArrow>();
	private DraggableArrow arrow = null;
	private Pointer pointer;

	public GameArrows() {
		init();
	}

	private void init() {
		arrowStaticList.add(new ArrowUp());
		arrowStaticList.add(new ArrowDown());
		arrowStaticList.add(new ArrowRight());
		arrowStaticList.add(new ArrowLeft());
	}

	public List<DraggableArrow> getArrowStaticList() {
		return arrowStaticList;
	}

	public List<DraggableArrow> getArrowList() {
		return arrowList;
	}

	public DraggableArrow createArrow(DraggableArrow arrow) {
		DraggableArrow tempArrow;
		if (arrow instanceof ArrowUp) {
			tempArrow = new ArrowUp();
			arrowList.add(tempArrow);
			return tempArrow;
		} else if (arrow instanceof ArrowDown) {
			tempArrow = new ArrowDown();
			arrowList.add(tempArrow);
			return tempArrow;
		} else if (arrow instanceof ArrowRight) {
			tempArrow = new ArrowRight();
			arrowList.add(tempArrow);
			return tempArrow;
		} 
		tempArrow = new ArrowLeft();
		arrowList.add(tempArrow);
		return tempArrow;
	}

	public void deleteArrow(DraggableArrow arrow) {
		Iterator<? extends DraggableArrow> it = arrowList.iterator();
		while(it.hasNext()) {
			DraggableArrow tempArrow = it.next();
			if (tempArrow == arrow) {
				it.remove();
			}
		}
	}

	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		if (e.getY() > GameWindow.FIELD_SIZE + GameWindow.INDENT_Y) {
			Iterator<? extends DraggableArrow> it = arrowStaticList.iterator();
			while(it.hasNext()) {
				arrow = it.next();
				if(arrow.isContaining(e.getX(), e.getY())) {
					arrow = createArrow(arrow);
					break;
				}
				arrow = null;
			}
			System.out.println(arrowList);
		} else {
			Iterator<? extends DraggableArrow> it = arrowList.iterator();
			while(it.hasNext()) {
				arrow = it.next();
				if(arrow.isContaining(e.getX(), e.getY())) {
					break;
				}
				arrow = null;
			}
		}

		if(arrow != null) {
			gamePointers.resetPointer(arrow);
		}
		if (arrow != null && arrow.isContaining(e.getX(), e.getY())) {
			arrow.setDraggable(true);
			arrow.setDraggedCoord(e.getX(), e.getY());
		}
	}

	public void mouseDragged(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		pointer = gamePointers.getPointer(e.getX(), e.getY());

		if (arrow != null && arrow.isDraggable() && !pointer.isContainingElements()) {
			arrow.setPosition(e.getX(), e.getY());
			panel.repaint();
		}
	}

	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		if (arrow != null) {
			arrow.setDraggable(false);
			if(e.getY() < GameWindow.FIELD_SIZE + GameWindow.INDENT_Y) {
				gamePointers.setPointer(arrow.getX(), arrow.getY(), arrow.getDirection());
				gameLine.constructLines();
				panel.repaint();
			} else {
				deleteArrow(arrow);
				arrow = null;
				gameLine.constructLines();
				panel.repaint();
			}
		}
	}

	public void draw(Graphics g) {
		Iterator<? extends DraggableArrow> it = arrowStaticList.iterator();
		while(it.hasNext()) {
			DraggableArrow arrow = it.next();
			arrow.draw(g);
		}

		Iterator<? extends DraggableArrow> it2 = arrowList.iterator();
		while(it2.hasNext()) {
			DraggableArrow arrow = it2.next();
			arrow.draw(g);
		}
	}
}