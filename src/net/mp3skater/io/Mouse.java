package net.mp3skater.io;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {

    public int exX, exY;
    public int x, y;
    public boolean pressed;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        exX = x;
        exY = y;
        x = e.getX();
        y = e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        exX = x;
        exY = y;
        x = e.getX();
        y = e.getY();
    }
}