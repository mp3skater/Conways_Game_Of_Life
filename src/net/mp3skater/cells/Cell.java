package net.mp3skater.cells;

import net.mp3skater.utils.Board;
import net.mp3skater.main.GamePanel;

import java.awt.*;

import static net.mp3skater.utils.Board.size;
import static net.mp3skater.utils.Board.stroke;

public class Cell {

    private final int x;
    private final int y;
    private boolean dead = false; // Cells that will die the next update
    private int babyCounter = 0; // Cells that will be alive the next update when count == 3
    private final Color cellColor;

    public Cell(int x, int y) {
        cellColor = Color.getColor("blue", 0x31a4d9);
        this.x = x;
        this.y = y;
    }
    public Cell(int x, int y, int babyCounter) {
        cellColor = Color.getColor("blue", 0x31a4d9);
        this.x = x;
        this.y = y;
        this.babyCounter = babyCounter;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(cellColor);
        g2.fillRect(x*size-stroke+ GamePanel.offsetX, y*size-stroke+GamePanel.offsetY, size-2*stroke, size-2*stroke);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getPosX() {
        return x* Board.size+GamePanel.offsetX;
    }
    public int getPosY() {
        return y*Board.size+GamePanel.offsetY;
    }
    public boolean isDead() {
        return dead;
    }
    public void kill() {
        dead = true;
    }
    public void increaseBaby() {
        babyCounter++;
    }
    public void resetBaby() {
        babyCounter = 0;
    }
    public int getBabyCounter() {
        return babyCounter;
    }
}
