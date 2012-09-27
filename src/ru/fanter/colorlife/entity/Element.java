package ru.fanter.colorlife.entity;

import ru.fanter.colorlife.GameWindow;

public abstract class Element {
    public static final int ARROW_SIZE = 8;
    public static final int ELEM_SIZE = 36;
    public static final int FIGURE_SIZE = 36;
    public static final int LINE_ARROW_SIZE = 36;
    private int size;
    private int x;
    private int y;
    
    //aligns element inside field cell
    public boolean setPosition(int dx, int dy) {
        boolean isXInsideFieldCell;
        boolean isYInsideFieldCell;

        for(int i = GameWindow.INDENT_X; i < GameWindow.FIELD_SIZE; i+=GameWindow.SQUARE_SIZE) {
            for(int j = GameWindow.INDENT_Y; j < GameWindow.FIELD_SIZE; j+=GameWindow.SQUARE_SIZE) {
                isXInsideFieldCell = dx > i && dx < i + GameWindow.SQUARE_SIZE;
                isYInsideFieldCell = dy > j && dy < j + GameWindow.SQUARE_SIZE;
                if (isXInsideFieldCell && isYInsideFieldCell) {
                    x = i + GameWindow.SQUARE_SIZE/2 - size/2;
                    y = j + GameWindow.SQUARE_SIZE/2 - size/2;
                    return true;
                }
            }
        }
        return false;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getX() {
        return x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getY() {
        return y;
    }
}
