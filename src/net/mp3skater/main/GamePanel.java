package net.mp3skater.main;

import net.mp3skater.cells.Cell;
import net.mp3skater.io.KeyHandler;
import net.mp3skater.io.Mouse;
import net.mp3skater.utils.Board;
import net.mp3skater.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	private final int FPS = 60;
    Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();

	// COUNTER
	private static int updateCount = 0;
	private static int generationCount = 0;
	private static final int updateFPS = 20;

	// ZOOM
	private static int zoom = 1;
	private static boolean ex_upPressed = false;
	private static boolean ex_downPressed = false;

	// ELEMENTS
	ArrayList<Cell> cells = new ArrayList<>();
	ArrayList<Cell> babies = new ArrayList<>();

	// PAUSE
	private boolean isPause = true;
	private boolean exPause; // To see if Pause has been changed

	// OFFSET
	public static int offsetX = 0;
	public static int offsetY = 0;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.getColor("white", 0xa2a2a2));
		addMouseMotionListener(mouse);
		addMouseListener(mouse);

		// Implement KeyListener:
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);

		// Initialize Game
		Utils.setPieces(cells);
	}
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		// GAME LOOP
		double drawInterval = 1_000_000_000d/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	private Cell getCell(int x, int y, ArrayList<Cell> cellArrayList) {
		for(Cell c : cellArrayList) {
			if(c.getX() == x && c.getY() == y)
				return c;
		}
		return null;
	}
	private void removeDeadCells() {
        cells.removeIf(Cell::isDead);
	}
	private void babiesGrowUp() {
		for(Cell c : babies)
			c.resetBaby();
        cells.addAll(babies);
		babies.clear();
	}
	private void Controlupdates() {
		// ZOOM
		if(KeyHandler.upPressed && !ex_upPressed)
			zoom++;
		ex_upPressed=KeyHandler.upPressed;
		// ZOOM AWAY
		if(KeyHandler.downPressed && !ex_downPressed)
			zoom--;
		ex_downPressed=KeyHandler.downPressed;
		// BOARD DRAG
		if(mouse.pressed && (mouse.x!=mouse.exX || mouse.y!=mouse.exY)) {
			offsetX += mouse.x - mouse.exX;
			offsetY += mouse.y - mouse.exY;
		}
		// OFFSET RESET 0
		if(KeyHandler.zeroPressed) {
			offsetX = 0;
			offsetY = 0;
		}
	}
	private void changePauseState() {
		isPause = !isPause;
	}
	private void update() {
		Controlupdates();

		// RETURN PAUSE
		if(KeyHandler.pausePressed && !exPause)
			changePauseState();
		exPause = KeyHandler.pausePressed;
		if(isPause)
			return;
		// UPDATE RETURN
		if(updateCount < updateFPS) {
			updateCount++;
			return;
		}
		updateCount = 0;

		for(Cell c : cells) {
			// COUNT ADJACENT CELLS
			int count = 0;
			for (Cell test : cells) {
				// THERE'S A CELL NEARBY
				if (Math.abs(test.getX() - c.getX()) <= 1 && Math.abs(test.getY() - c.getY()) <= 1) {
					count++;
				}
			}
			// DEATH
			// +1 because it also counts itself
			if (count < 2 + 1 || count > 3 + 1)
				c.kill();
			// LIFE
			// for all adjacent cells, increase count by 1
			for(int y = -1; y<=1; y++) {
				for(int x = -1; x<=1; x++) {
					// ADD NEW BABY
					Cell cellC = getCell(c.getX()+x, c.getY()+y, cells);
					if(cellC == null) {
						Cell cellB = getCell(c.getX()+x, c.getY()+y, babies);
						if(cellB == null)
							babies.add(new Cell(x+c.getX(),y+c.getY(), 1));
						// INCREASE BABY-COUNTER
						else cellB.increaseBaby();
					}
				}
			}
		}
        // REMOVE FAILED BABIES
        babies.removeIf(c -> c.getBabyCounter() != 3);

		// SOME UPDATES
		generationCount++;
		removeDeadCells();
		babiesGrowUp();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		// BOARD
		board.draw(g2);

		// CELLS
		for(Cell c : cells) {
			// IF VISIBLE
			if(c.getPosX() > -Board.size && c.getPosX() < WIDTH && c.getPosY() > -Board.size && c.getPosY() < HEIGHT)
				c.draw(g2);
		}

		// FONT
		Font font = new Font ("Courier New", Font.BOLD, 20);
		g2.setFont(font);
		g2.setColor(Color.red);

		// PRINT INFO (RED)
		g2.drawString("Generations: " + generationCount, 10, 20);
		g2.drawString("Time between Generations: " +
				Math.round((double)updateFPS/FPS*100)/100.0, 10, 45);
		g2.drawString("Zoom: " + (zoom>0? zoom : "1/" + (-zoom+2)) + "x", 10, 70);

		// PAUSE
		if(isPause) {
			// NEW FONT
			font = new Font ("Courier New", Font.BOLD, 40);
			g2.setFont(font);
			g2.setColor(Color.gray);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			g2.fillRect(0, 0, WIDTH, HEIGHT);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2.setColor(Color.getColor("purple", 0x9628e3));
			g2.drawString("Game Paused", WIDTH/2-132, 315);
		}
	}
}
