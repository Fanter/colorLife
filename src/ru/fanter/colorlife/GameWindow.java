package ru.fanter.colorlife;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ru.fanter.colorlife.background.*;
import ru.fanter.colorlife.entity.LineArrow;
import ru.fanter.colorlife.entitylogic.*;
import ru.fanter.colorlife.line.GameLine;

enum GameState {
    RUNNING, PAUSE, LOST, WON, EDITING, DEBUGGING;
}

enum GameMode {
    FULLSCREEN, WINDOW;
}

public class GameWindow extends JFrame {
    public static final int SQUARE_NUMBER = 10;
    public static final int SQUARE_SIZE = 60;
    public static final int FIELD_SIZE = SQUARE_SIZE * SQUARE_NUMBER;
    public static final int FOOTER_HEIGHT = 100;
    public static final int UPDATE_RATE = 35; // number of updates per seconds
    public static final int UPDATE_PERIOD = 1000 / UPDATE_RATE; // time for one
                                                                // update in
                                                                // millis
    public static final int INDENT_X = 10;
    public static final int INDENT_Y = 10;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 740;

    private GamePanel gamePanel;
    private GameGrid gameGrid;
    private LineArrow lineArrow;
    private GameLine gameLine;
    private GamePointers gamePointers;
    private GameFigures gameFigures;
    private GameElements gameElements;
    private GameState gameState;
    private GameReceivers gameReceivers;

    private boolean allowToStart;

    public GameWindow() {
        gameInit();
        gameWindowInit();
        gameStart();
    }

    public void gameInit() {
        gamePanel = new GamePanel();
        gameGrid = new GameGrid();
        lineArrow = new LineArrow();
        gamePointers = new GamePointers();
        gameLine = new GameLine(gamePointers, lineArrow);
        gameFigures = new GameFigures();
        gameElements = new GameElements();
        gameReceivers = new GameReceivers(gamePointers);
        gameState = GameState.EDITING;

        allowToStart = true;
    }

    public void gameWindowInit() {
        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gamePanel.setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(BorderLayout.CENTER, gamePanel);
        this.pack();
        this.setVisible(true);
    }

    public void gameStart() {
        gameLine.constructLines();
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                gameLoop();
            }
        };
        gameThread.start();
    }

    public void gameLoop() {
        long startTime;
        long elapsedTime;
        long timeToSleep;

        while (true) {
            startTime = System.currentTimeMillis();
            gameUpdate();
            repaint();
            elapsedTime = System.currentTimeMillis() - startTime;
            timeToSleep = UPDATE_PERIOD - elapsedTime;
            try {
                if (timeToSleep < 10) {
                    timeToSleep = 10;
                }
                Thread.sleep(timeToSleep);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void gameUpdate() {
        switch (gameState) {
        case DEBUGGING:
        case RUNNING:
            if (allowToStart) {
                gameFigures.clear();
                gameFigures.createFigure(lineArrow.getX(), lineArrow.getY(),
                        lineArrow.getDirection());
                allowToStart = false;
            }

            gameFigures.update(gamePointers);
            if (gameFigures.checkErrors()) {
                gameState = GameState.LOST;
            } else if (gameReceivers.isFull()) {
                gameState = GameState.WON;
            }
            break;
        case EDITING:
            gameFigures.clear();
            gameReceivers.resetCount();
            allowToStart = true;
            break;
        case PAUSE:
            break;
        case LOST:
        case WON:
            allowToStart = true;
            break;
        default:
            break;
        }
    }

    public class GamePanel extends JPanel {
        public GamePanel() {
            setFocusable(true);
            requestFocus();
            ArrowMouseListener arrowListener = new ArrowMouseListener();
            GameKeyListener gameKeyListener = new GameKeyListener();
            addMouseListener(arrowListener);
            addMouseMotionListener(arrowListener);
            addKeyListener(gameKeyListener);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            gameGrid.draw(g);
            gameLine.draw(g);
            gamePointers.draw(g);
            gameReceivers.draw(g);
            gameFigures.draw(g);
            gameElements.draw(g);
            switch (gameState) {
            case LOST:
                drawErrorMessage(g);
                break;
            case WON:
                drawWinningMessage(g);
                break;
            default:
                break;
            }
        }

        public void drawErrorMessage(Graphics g) {
            int firstLineWidth;

            Font gameOverFont = new Font("SansSerif", Font.BOLD, 50);
            FontMetrics metrics = g.getFontMetrics(gameOverFont);
            firstLineWidth = metrics.stringWidth("ERROR");
            g.setColor(Color.BLACK);
            g.setFont(gameOverFont);
            g.drawString("ERROR", GameWindow.WIDTH / 2 - firstLineWidth / 2,
                    GameWindow.HEIGHT / 2 - metrics.getHeight());
        }

        public void drawWinningMessage(Graphics g) {
            int firstLineWidth;

            Font gameOverFont = new Font("SansSerif", Font.BOLD, 50);
            FontMetrics metrics = g.getFontMetrics(gameOverFont);
            firstLineWidth = metrics.stringWidth("YOU HAVE WON");
            g.setColor(Color.BLACK);
            g.setFont(gameOverFont);
            g.drawString("YOU HAVE WON", GameWindow.WIDTH / 2 - firstLineWidth
                    / 2, GameWindow.HEIGHT / 2 - metrics.getHeight());
        }
    }

    public class ArrowMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            switch (gameState) {
            case DEBUGGING:
            case EDITING:
                gameElements.mouseClicked(e, gamePointers, gameLine, gamePanel);
                gameLine.getLineArrow().mouseClicked(e, gamePointers, gameLine,
                        gamePanel);
                switch (gameState) {
                case DEBUGGING:
                    gameReceivers.mouseClicked(e, gamePointers, gameLine,
                            gamePanel);
                    break;
                default:
                    break;
                }
                break;
            default:
                break;
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            switch (gameState) {
            case DEBUGGING:
            case EDITING:
                gameLine.getLineArrow()
                        .mousePressed(e, gamePointers, gamePanel);
                gameElements.mousePressed(e, gamePointers, gamePanel);
                gameReceivers.mousePressed(e, gamePointers, gamePanel);
                break;
            default:
                break;
            }

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            switch (gameState) {
            case DEBUGGING:
            case EDITING:
                gameLine.getLineArrow()
                        .mouseDragged(e, gamePointers, gamePanel);
                gameElements.mouseDragged(e, gamePointers, gamePanel);
                gameReceivers.mouseDragged(e, gamePointers, gamePanel);
                break;
            default:
                break;
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            switch (gameState) {
            case DEBUGGING:
            case EDITING:
                gameLine.getLineArrow().mouseReleased(e, gamePointers,
                        gameLine, gamePanel);
                gameElements.mouseReleased(e, gamePointers, gameLine,
                        gamePanel);
                gameReceivers.mouseReleased(e, gamePointers, gameLine,
                        gamePanel);
                break;
            default:
                break;
            }
        }
    }

    public class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode());

            switch (e.getKeyCode()) {
            case 68: // 'D'
                gameState = GameState.DEBUGGING;
                break;
            case 83: // 'S'
                gameState = GameState.RUNNING;
                break;
            case 80: // 'P'
                gameState = GameState.PAUSE;
                break;
            case 65: // 'A'
                gameState = GameState.EDITING;
                break;
            default:
                break;
            }
        }
    }

}