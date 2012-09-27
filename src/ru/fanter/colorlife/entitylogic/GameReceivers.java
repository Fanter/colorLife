package ru.fanter.colorlife.entitylogic;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.entity.*;

public class GameReceivers {
	public static final int ELEMENTS_TO_WIN = 8;
	private List<DraggableElement> receiverList = new ArrayList<DraggableElement>();

	public GameReceivers(GamePointers gamePointers) {
		setUpLevel(gamePointers); 
	}

	public void setUpLevel(GamePointers gamePointers) {
		addElement(gamePointers);
	}

	public List<DraggableElement> getElementList() {
		return receiverList;
	}

	public void resetCount() {
		Iterator<? extends DraggableElement> it = receiverList.iterator();
		while(it.hasNext()) {
			Receiver element = (Receiver)it.next();
			element.resetCount();
		}
	}

	public void addElement(GamePointers gamePointers) {
		Pointer pointer;
		int receiverX = 501;
		int receiverY = 351;
		int receiver2X = 501;
		int receiver2Y = 201;

		Receiver receiver = new Receiver(FigureColor.RED, FigureShape.CIRCLE);
		receiver.setX(receiverX);
		receiver.setY(receiverY);
		receiver.setPosition(receiver.getX(), receiver.getY());
		receiverList.add(receiver);
		pointer = gamePointers.getPointer(receiverX, receiverY);
		pointer.isReceiver(true);
		pointer.setReceiver(receiver);
		gamePointers.setPointer(receiverX, receiverY, Direction.END, Direction.END);

		Receiver receiver2 = new Receiver(FigureColor.GREEN, FigureShape.CIRCLE);
		receiver2.setX(receiver2X);
		receiver2.setY(receiver2Y);
		receiver2.setPosition(receiver2.getX(), receiver2.getY());
		receiverList.add(receiver2);
		pointer = gamePointers.getPointer(receiver2X, receiver2Y);
		pointer.isReceiver(true);
		pointer.setReceiver(receiver2);
		gamePointers.setPointer(receiver2X, receiver2Y, Direction.END, Direction.END);
	}//addElement()

	public boolean isFull() {
		Iterator<? extends DraggableElement> it = receiverList.iterator();
		while(it.hasNext()) {
			Receiver element = (Receiver)it.next();
			if (element.getCounter() < ELEMENTS_TO_WIN) {
				return false;
			}
		}
		return true;
	}

	public void mouseClicked(MouseEvent e, GamePointers gamePointers, GameLine gameLine, JPanel panel) {
		Iterator<? extends DraggableElement> it = receiverList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.mouseClicked(e, gamePointers, gameLine, panel);
		}
	}

	public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		Iterator<? extends DraggableElement> it = receiverList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.mousePressed(e, gamePointers, panel);
		}
	}

	public void mouseDragged(MouseEvent e, GamePointers gamePointers, JPanel panel) {
		Iterator<? extends DraggableElement> it = receiverList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.mouseDragged(e, gamePointers, panel);
		}
	}

	public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
													JPanel panel) {
		Iterator<? extends DraggableElement> it = receiverList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.mouseReleased(e, gamePointers, gameLine, panel);
		}
	}

	public void draw(Graphics g) {
		Iterator<? extends DraggableElement> it = receiverList.iterator();
		while(it.hasNext()) {
			DraggableElement element = it.next();
			element.draw(g);
		}
	}
}