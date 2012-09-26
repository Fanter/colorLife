package ru.fanter.colorlife;

import java.util.*;
import java.awt.Graphics;

import ru.fanter.colorlife.entity.*;

public class GamePointers {
    List<Pointer> pointerList = new ArrayList<Pointer>();

    public GamePointers() {
        for (int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i += GameWindow.SQUARE_SIZE) {
            for (int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j += GameWindow.SQUARE_SIZE) {
                Pointer pointer = new Pointer(i, j);
                pointerList.add(pointer);
            }
        }
    }

    public Pointer getPointer(int x, int y) {
        Iterator<Pointer> it = pointerList.iterator();
        while (it.hasNext()) {
            Pointer pointer = it.next();
            if (pointer.isContaining(x, y)) {
                return pointer;
            }
        }
        return new Pointer(-100, -100);
    }

    public void setPointer(int x, int y, Direction direction) {
        Pointer pointer = getPointer(x, y);
        pointer.setDirectionArray(direction, 0);
    }

    public void setPointer(int x, int y, Direction direction, int index) {
        Pointer pointer = getPointer(x, y);
        pointer.setDirectionArray(direction, index);
    }

    public void setPointer(int x, int y, Direction direction1,
            Direction direction2) {
        Pointer pointer = getPointer(x, y);
        pointer.setDirectionArray(direction1, 0);
        pointer.setDirectionArray(direction2, 1);
    }

    public void resetPointer(DraggableArrow arrow) {
        Pointer pointer = getPointer(arrow.getX(), arrow.getY());
        pointer.setDirectionArray(Direction.NONE, 0);
        pointer.setDirectionArray(Direction.NONE, 1);
    }

    public void resetPointer(int arrowX, int arrowY) {
        Pointer pointer = getPointer(arrowX, arrowY);
        pointer.setDirectionArray(Direction.NONE, 0);
        pointer.setDirectionArray(Direction.NONE, 1);
    }

    public void resetPointer(int dx, int dy, int index) {
        Pointer pointer = getPointer(dx, dy);
        pointer.setDirectionArray(Direction.NONE, index);
    }

    public void draw(Graphics g) {
        Iterator<Pointer> it = pointerList.iterator();
        while (it.hasNext()) {
            Pointer pointer = it.next();
            pointer.drawArray(g);
        }
    }
}