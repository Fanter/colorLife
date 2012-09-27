package ru.fanter.colorlife.entitylogic;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.entity.*;
import ru.fanter.colorlife.line.GameLine;

public class GameElements {
	private final int INIT_X = 170;
	private final int INIT_Y = GameWindow.FIELD_SIZE + GameWindow.INDENT_Y + 10;
	private final int PADDING = 70;
	private final int PADDING_Y = 47;
	private List<DraggableElement> elementList= new ArrayList<DraggableElement>();
	private List<ElementType> typeList = new ArrayList<ElementType>();
	private ElementCounter elementCounter;

	public GameElements() {
		setUpLevel(); 
	}

	public void setUpLevel() {
		elementCounter = new ElementCounter();
		elementCounter.setNumberOfElements();
		addElement(ElementType.SPLITTER);
		addElement(ElementType.COLOR_CHANGER);
		addElement(ElementType.SHAPE_CHANGER);
		addElement(ElementType.RECYCLER);
		addElement(ElementType.DETECTOR_COLOR);
		addElement(ElementType.DETECTOR_SHAPE);
		addElement(ElementType.MERGER);
		addElement(ElementType.ARROW_UP);
		addElement(ElementType.ARROW_DOWN);
		addElement(ElementType.ARROW_RIGHT);
		addElement(ElementType.ARROW_LEFT);
	}

	public List<DraggableElement> getElementList() {
		return elementList;
	}

	public void addElement(ElementType elementType) {
		switch(elementType) {
			case SPLITTER:
				Splitter splitter = new Splitter();
				splitter.setX(INIT_X);
				splitter.setY(INIT_Y);
				elementList.add(0, splitter);
				break;
			case COLOR_CHANGER:
				ColorChanger colorChanger = new ColorChanger();
				colorChanger.setX(INIT_X);
				colorChanger.setY(INIT_Y + PADDING_Y);
				elementList.add(0, colorChanger);
				break;
			case SHAPE_CHANGER:
				ShapeChanger shapeChanger = new ShapeChanger();
				shapeChanger.setX(INIT_X + PADDING);
				shapeChanger.setY(INIT_Y);
				elementList.add(0, shapeChanger);
				break;
			case RECYCLER:
				Recycler recycler = new Recycler();
				recycler.setX(INIT_X + PADDING);
				recycler.setY(INIT_Y + PADDING_Y);
				elementList.add(0, recycler);
				break;
			case DETECTOR_COLOR:
				DetectorColor detectorColor = new DetectorColor();
				detectorColor.setX(INIT_X + PADDING * 2);
				detectorColor.setY(INIT_Y);
				elementList.add(0, detectorColor);
				break;
			case DETECTOR_SHAPE:
				DetectorShape detectorShape = new DetectorShape();
				detectorShape.setX(INIT_X + PADDING * 2);
				detectorShape.setY(INIT_Y + PADDING_Y);
				elementList.add(0, detectorShape);
				break;
			case MERGER:
				Merger merger = new Merger();
				merger.setX(INIT_X + PADDING * 3);
				merger.setY(INIT_Y);
				elementList.add(0, merger);
				break;
			case ARROW_UP:
			    Arrow arrowUp = new Arrow(Direction.UP, elementType);
			    arrowUp.setX(80);
			    arrowUp.setY(GameWindow.FIELD_SIZE + 50);
			    elementList.add(0, arrowUp);
			    break;
			case ARROW_DOWN:
                Arrow arrowDown = new Arrow(Direction.DOWN, elementType);
                arrowDown.setX(80);
                arrowDown.setY(GameWindow.FIELD_SIZE + 80);
                elementList.add(0, arrowDown);
                break;
			case ARROW_RIGHT:
                Arrow arrowRight = new Arrow(Direction.RIGHT, elementType);
                arrowRight.setX(120);
                arrowRight.setY(GameWindow.FIELD_SIZE + 80);
                elementList.add(0, arrowRight);
                break;
			case ARROW_LEFT:
                Arrow arrowLeft = new Arrow(Direction.LEFT, elementType);
                arrowLeft.setX(40);
                arrowLeft.setY(GameWindow.FIELD_SIZE + 80);
                elementList.add(0, arrowLeft);
                break;
			default:
				break;
		}//switch(elementType)
	}//addElement()

	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		Iterator<? extends DraggableElement> it = elementList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.mouseClicked(e, gamePointers, gameLine, panel);
		}
	}

	//TODO substitute e.getY() > ... by isMouseInsidePanel(e.getY(), e.getX());
	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		Iterator<? extends DraggableElement> it = elementList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			if (e.getY() > GameWindow.FIELD_SIZE && element.isContaining(e.getX(), e.getY())) {
				elementCounter.decElementCount(element.getElementType());
				if (elementCounter.getElementCount(element.getElementType()) > 0) {
					typeList.add(element.getElementType());
				}
			}
			element.mousePressed(e, gamePointers, panel);
		}

		Iterator<ElementType> typeIt = typeList.iterator();
		while(typeIt.hasNext()) {
			ElementType eType = typeIt.next();
			addElement(eType);
		}
		typeList.clear();
	}

	public void mouseDragged(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		Iterator<? extends DraggableElement> it = elementList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.mouseDragged(e, gamePointers, panel);
		}
	}

	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
													JPanel panel) {
		Iterator<? extends DraggableElement> it = elementList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.mouseReleased(e, gamePointers, gameLine, panel);
			if (element.isSelected() && e.getY() > GameWindow.FIELD_SIZE + GameWindow.INDENT_Y ||
									(!element.setPosition(element.getX(), element.getY()) && element.isSelected())) {
				elementCounter.incElementCount(element.getElementType());
				typeList.add(element.getElementType());
				it.remove();
			}
		}

		Iterator<ElementType> typeIt = typeList.iterator();
		while(typeIt.hasNext()) {
			ElementType eType = typeIt.next();
			if (elementCounter.getElementCount(eType) == 1) {
				addElement(eType);
			}
		}
		typeList.clear();
	}

	public void draw(Graphics g) {
		Iterator<? extends DraggableElement> it = elementList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.draw(g);
		}
	}
}