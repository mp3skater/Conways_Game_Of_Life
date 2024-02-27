package net.mp3skater.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static boolean pausePressed;
    public static boolean zeroPressed;
    public static boolean upPressed;
    public static boolean downPressed;

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_ESCAPE) {
            pausePressed = true;
        }
        if(keyCode == KeyEvent.VK_0) {
            zeroPressed = true;
        }
        if(keyCode == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if(keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_ESCAPE) {
            pausePressed = false;
        }
        if(keyCode == KeyEvent.VK_0) {
            zeroPressed = false;
        }
        if(keyCode == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if(keyCode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
