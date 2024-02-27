package net.mp3skater.utils;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public class Board {

    public static int size = 50;
    public static final int stroke = 1;

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        // DRAW WHEN VISIBLE
        for(int y = -size; y<=GamePanel.HEIGHT; y+=size) {
            for(int x = -size; x<=GamePanel.WIDTH; x+=size) {
                g2.fillRect(x-stroke+(GamePanel.offsetX%size),
                            y-stroke+(GamePanel.offsetY%size),
                            size-2*stroke, size-2*stroke);
            }
        }
    }
}