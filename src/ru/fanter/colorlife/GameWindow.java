package ru.fanter.colorlife;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Iterator;

enum GameState {
	RUNNING, PAUSE, LOST, WON, EDITING, DEBUGGING;
}

enum GameMode {
	FULLSCREEN, WINDOWED;
}

public class GameWindow extends JFrame {
	public static final int SQUARE_NUMBER = 10;
	public static final int SQUARE_SIZE = 64;
	public static final int FIELD_SIZE = SQUARE_SIZE * SQUARE_NUMBER;
	public static final int FOOTER_HEIGHT = 100;
	public static final int UPDATE_RATE = 35;					//number of updates per seconds
	public static final int UPDATE_PERIOD = 1000/UPDATE_RATE;	//time for one update in millis
	public static final int INDENT_X = 10;
	public static final int INDENT_Y = 10;
	public static int WIDTH;
	public static int HEIGHT;
	GraphicsEnvironment env;
	GraphicsDevice defaultScreen;
	DisplayMode defaultMode;
	DisplayMode newMode;

	GamePanel gamePanel;
	GameGrid gameGrid;
	GameLine gameLine;
	GamePointers gamePointers;
	GameArrows gameArrows;
	GameFigures gameFigures;
	GameElements gameElements;
	GameState gameState;
	GameReceivers gameReceivers;
	Figure figure;

	public GameWindow() {
		gameInit();
		gameWindowInit();
		gameStart();
	}

	public void gameInit() {
		gamePanel = new GamePanel();
		gameGrid = new GameGrid();
		gamePointers = new GamePointers();
		gameLine = new GameLine(gamePointers);
		gameArrows = new GameArrows();
		gameFigures = new GameFigures();
		gameElements = new GameElements();
		gameReceivers = new GameReceivers(gamePointers);
		figure = gameFigures.getInitialFigure();
		gameState = GameState.PAUSE;
	}

	public void gameWindowInit() {
		env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		defaultScreen = env.getDefaultScreenDevice();
		defaultMode = defaultScreen.getDisplayMode();
		newMode = new DisplayMode(1024, 768, 32, 60);
		WIDTH = 700;
		HEIGHT = 768;

		gamePanel.setPreferredSize(new Dimension(700, 768));
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gamePanel.setBackground(Color.LIGHT_GRAY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(BorderLayout.CENTER, gamePanel);
//		this.setUndecorated(true);
//		this.setResizable(false);
		this.pack();
//		defaultScreen.setFullScreenWindow(this);
//		defaultScreen.setDisplayMode(newMode);
		this.setVisible(true);
	}

	public void gameStart(){
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

		while(true) {
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
		switch(gameState) {
			case DEBUGGING: case RUNNING:
				gameFigures.update(gamePointers);
				if (gameFigures.isCollisionHappened()) {
					gameState = GameState.LOST;
				} else if (gameReceivers.isFull()) {
					gameState = GameState.WON;
				}
				break;
			case PAUSE: LOST: WON:
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
			gameArrows.draw(g);
			gameReceivers.draw(g);
			gameFigures.draw(g);
			gameElements.draw(g);
			switch(gameState) {
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
			int secondLineWidth;

			Font gameOverFont = new Font("SansSerif",Font.BOLD, 50);
			FontMetrics metrics = g.getFontMetrics(gameOverFont);
			firstLineWidth = metrics.stringWidth("ERROR");
			g.setColor(Color.BLACK);
			g.setFont(gameOverFont);
			g.drawString("ERROR", GameWindow.WIDTH/2 - firstLineWidth/2,
							 GameWindow.HEIGHT/2 - metrics.getHeight());
		}

		public void drawWinningMessage(Graphics g) {
			int firstLineWidth;
			int secondLineWidth;

			Font gameOverFont = new Font("SansSerif",Font.BOLD, 50);
			FontMetrics metrics = g.getFontMetrics(gameOverFont);
			firstLineWidth = metrics.stringWidth("YOU HAVE WON");
			g.setColor(Color.BLACK);
			g.setFont(gameOverFont);
			g.drawString("YOU HAVE WON", GameWindow.WIDTH/2 - firstLineWidth/2,
							 GameWindow.HEIGHT/2 - metrics.getHeight());
		}
	}

	public class ArrowMouseListener extends MouseAdapter {
		@Override 
		public void mouseClicked(MouseEvent e) {
			switch(gameState) {
				case DEBUGGING:
					gameElements.mouseClicked(e, gamePointers, gameLine, gamePanel);
					gameLine.getLineArrow().mouseClicked(e, gamePointers, gameLine, gamePanel);
					switch(gameState) {
						case DEBUGGING:
							gameReceivers.mouseClicked(e, gamePointers, gameLine, gamePanel);
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
			gameArrows.mousePressed(e, gamePointers, gamePanel);
			gameLine.getLineArrow().mousePressed(e, gamePointers, gamePanel);
			gameElements.mousePressed(e, gamePointers, gamePanel);
			gameReceivers.mousePressed(e, gamePointers, gamePanel);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			gameArrows.mouseDragged(e, gamePointers, gamePanel);
			gameLine.getLineArrow().mouseDragged(e, gamePointers, figure, gamePanel);
			gameElements.mouseDragged(e, gamePointers, gamePanel);
			gameReceivers.mouseDragged(e, gamePointers, gamePanel);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			gameArrows.mouseReleased(e, gamePointers, gameLine, gamePanel);
			gameLine.getLineArrow().mouseReleased(e, gamePointers, gameLine, gameArrows, gamePanel);
			gameElements.mouseReleased(e, gamePointers, gameLine, gameArrows, gamePanel);
			gameReceivers.mouseReleased(e, gamePointers, gameLine, gameArrows, gamePanel);
		}
	}

	public class GameKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());

			switch(e.getKeyCode()) {
				case 65: 
					gameState = GameState.DEBUGGING;
					break;
				case 83:
					gameState = GameState.RUNNING;
					break;
				case 68:
					gameState = GameState.PAUSE;
					break;
				default:
					break;
			}
		}
	}

}