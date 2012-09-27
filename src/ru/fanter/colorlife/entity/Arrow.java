package ru.fanter.colorlife.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import ru.fanter.colorlife.*;
import ru.fanter.colorlife.line.GameLine;

public class Arrow extends DraggableElement{
    private ElementType elementType;
    private Direction direction;
    
    public Arrow(Direction direction, ElementType elementType) {
        this.direction = direction;
        this.elementType = elementType;
        setSize(Element.ARROW_SIZE);
    }
    
    public ElementType getElementType() {
        return elementType;
    }

    @Override
    public void mousePressed(MouseEvent e, GamePointers gamePointers, JPanel panel) {
        if (!(this.isContaining(e.getX(), e.getY()))) {
            this.isSelected(false);
        } else {
            this.isSelected(true);
            this.setDraggable(true);
            this.setDraggedCoord(e.getX(), e.getY());
            gamePointers.resetPointer(this.getX(), this.getY());
        } 
    }
    
    @Override
    public void mouseReleased(MouseEvent e, GamePointers gamePointers, GameLine gameLine,
                                                    JPanel panel) {
        if (this.isSelected()) {
            this.setDraggable(false);

            if (e.getY() > GameWindow.FIELD_SIZE + GameWindow.INDENT_Y) {
                gamePointers.resetPointer(this.getX(), this.getY());
            } else {
                gamePointers.setPointer(this.getX(), this.getY(), direction);
            }
            gameLine.constructLines();
            panel.repaint();
        }
    }
    
    public boolean isContaining(int coordX, int coordY) {
        boolean isInsideX;
        boolean isInsideY;

        isInsideX = coordX >= getX() && coordX <= getX() + 16;
        isInsideY = coordY >= getY() && coordY <= getY() + 16;
        return isInsideX && isInsideY;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        switch(direction) {
            case UP:
                g2.setColor(Color.CYAN);
                g2.fillRect(getX(), getY(), 16, 16);
                g2.setColor(Color.BLACK);
                g2.drawRect(getX(), getY(), 16, 16);
                break;
            case DOWN:
                g2.setColor(Color.YELLOW);
                g2.fillRect(getX(), getY(), 16, 16);
                g2.setColor(Color.BLACK);
                g2.drawRect(getX(), getY(), 16, 16);
                break;
            case RIGHT:
                g2.setColor(Color.RED);
                g2.fillRect(getX(), getY(), 16, 16);
                g2.setColor(Color.BLACK);
                g2.drawRect(getX(), getY(), 16, 16);
                break;
            case LEFT:
                g2.setColor(Color.GREEN);
                g2.fillRect(getX(), getY(), 16, 16);
                g2.setColor(Color.BLACK);
                g2.drawRect(getX(), getY(), 16, 16);
                break;
            default:
                break;
        }//switch(direction)
    }
}
